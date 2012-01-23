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
package play.modules.neo4j.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Neo4j Index annotation. Will create a neo4j index if a field is annotated this annotation.
 * 
 * @author bsimard
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Neo4jIndex {

    /**
     * Name of the index.
     */
    String value() default "";

    /**
     * exact is the default and uses a Lucene keyword analyzer. fulltext uses a white-space tokenizer in its analyzer.
     */
    String type() default "exact";

    /**
     * This parameter goes together with type: fulltext and converts values to lower case during both additions and
     * querying, making the index case insensitive. Defaults to true.
     */
    String lowerCase() default "true";

}
