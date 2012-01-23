/**
 * This file is part of logisima-play-neo4j.
 *
 * logisima-play-neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * logisima-play-neo4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with logisima-play-neo4j. If not, see <http://www.gnu.org/licenses/>.
 * 
 * @See https://github.com/sim51/logisima-play-neo4j
 */
package play.modules.neo4j.cli.export;

import play.modules.neo4j.util.Neo4jUtils;

public class YmlNode {

    public String                  id;
    private Class                  model;
    private org.neo4j.graphdb.Node dbNode;

    /**
     * Constructor.
     * 
     * @param node
     */
    public YmlNode(org.neo4j.graphdb.Node node) {
        this.dbNode = node;
        this.model = Neo4jUtils.getClassNameFromNode(node);
        // getting the key value of the object
        if (dbNode.getProperty("key", null) != null) {
            System.out.println("Model class name " + model.getSimpleName());
            id = model.getSimpleName() + "_" + dbNode.getProperty("key").toString();
        }
        else {
            id = "" + dbNode.getId();
        }
    }

    /**
     * Convert <code>dbNode</code> to YML format.
     * 
     * @return an yml string that represent the <code>dbNode</code>.
     */
    public String toYml() {
        String yml = "";
        if (id != null) {
            System.out.println("Generate yml for node " + id);
            yml = "\n" + model.getSimpleName() + "(" + id + "):";
            // export all atributes, except key
            for (String property : dbNode.getPropertyKeys()) {
                if (dbNode.getProperty(property, null) != null && !property.equals("key")) {
                    yml += "\n " + property + ": " + dbNode.getProperty(property);
                }
            }
            yml += "\n";
        }
        return yml;
    }
}
