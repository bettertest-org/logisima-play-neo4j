package play.modules.neo4j.relationship;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;

import play.modules.neo4j.exception.Neo4jPlayException;
import play.modules.neo4j.model.Neo4jModel;

public class Neo4jRelationFactory {

    public static <T extends Neo4jModel> List<T> getModelsFromRelatedTo(String relationName, String direction,
            Field field, Node node) {
        // construction of the return type
        List<T> list = new ArrayList();
        try {
            if (field.getType().isAssignableFrom(List.class)) {
                ParameterizedType listType = (ParameterizedType) field.getGenericType();
                // Here we have the class name of the parameter of the list
                Class<?> clazz = (Class<?>) listType.getActualTypeArguments()[0];

                // getting node constructor
                Constructor constructor = clazz.getDeclaredConstructor(Node.class);
                constructor.setAccessible(true);

                Traverser traverser = node.traverse(Order.BREADTH_FIRST, StopEvaluator.END_OF_GRAPH,
                        ReturnableEvaluator.ALL_BUT_START_NODE, DynamicRelationshipType.withName(relationName),
                        Direction.valueOf(direction));
                for (Node item : traverser.getAllNodes()) {
                    T nodeWrapper = (T) constructor.newInstance(item);
                    list.add(nodeWrapper);
                }
            }
            else {
                throw new Neo4jPlayException("Field with 'Neo4jRelatedTo' annotation must be a List");
            }
        } catch (Exception e) {
            throw new Neo4jPlayException(e);
        }
        return list;
    }
}
