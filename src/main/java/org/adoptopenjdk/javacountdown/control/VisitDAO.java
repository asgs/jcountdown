/*
 * Copyright [2013] Adopt OpenJDK Programme
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.adoptopenjdk.javacountdown.control;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.adoptopenjdk.javacountdown.entity.Visit;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Data Access Object for the Visitor collection.
 * 
 * @author Alex Theedom
 */
public class VisitDAO extends BasicDAO<Visit, Key<Visit>> {

    //private static final Logger LOGGER = LoggerFactory.getLogger(VisitDAO.class);

    public VisitDAO(Class<Visit> entityClass, DatastoreImpl datastore) {
        super(entityClass, datastore);
    }

    public String getCountries() {

        DBObject fields = new BasicDBObject("country", 1);
        fields.put("version", 1);
        DBObject project = new BasicDBObject("$project", fields);
        DBObject groupFields = new BasicDBObject("_id", "$country");
        groupFields.put("total", new BasicDBObject("$sum", 1));
        DBObject group = new BasicDBObject("$group", groupFields);
        AggregationOutput output = getCollection().aggregate(project, group);

        String results = output.toString();

        //LOGGER.debug("Retrieved countries {}", results);

        return results;
    }

}
