#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
${symbol_pound}set( ${symbol_dollar}symbol_pound = '${symbol_pound}' )
${symbol_pound}set( ${symbol_dollar}symbol_dollar = '${symbol_dollar}' )
${symbol_pound}set( ${symbol_dollar}symbol_escape = '${symbol_escape}' )
/**
 * ${symbol_dollar}{artifactId} : A proof of concept ReST / MongoDB service for 
 * educational purposes only.
 *  Copyright (C) 2014  Michael Chester
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ${symbol_dollar}{package}.persistence;

import ${symbol_dollar}{package}.resource.Entity;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.util.logging.Logger;
import org.mongojack.JacksonDBCollection;

/**
 * Database connection access object.
 * 
 * Primarily this class is simply to separate the logic required to set up the
 * Jackson connection from the document persistence functionality.
 *
 * @author Michael Chester
 */
public class Access {

    private static final Logger logger = 
            Logger.getLogger(Access.class.getName());

    private static MongoClient mongoClient;

    /**
     * Set the static value of mongoClient
     * 
     * @param mongoClient
     */
    public static void setMongoClient(MongoClient mongoClient) {
        Access.mongoClient = mongoClient;
    }

    private final DB db;
    private final DBCollection dbCollection;
    private final JacksonDBCollection<Entity, String> 
            jacksonDBCollection;

    /**
     * Create a new 'Access' object that connects to the collection named in the
     * collection parameter within the database named in the database parameter.
     *
     * This constructor creates a connection for use with a single thread.  It 
     * should not be shared between threads.
     * 
     * @param database
     * @param collection
     */
    public Access(String database, String collection) {
        logger.info("New MongoAccess created.");
        db = mongoClient.getDB(database);
        dbCollection = db.getCollection(collection);
        jacksonDBCollection = JacksonDBCollection.wrap(dbCollection,
                Entity.class, String.class);
    }

    /**
     * Get the 'jacksonDBCollection' collection connection object.
     *
     * @return
     */
    public JacksonDBCollection<Entity, String>
            getJacksonDBCollection() {
        logger.info("Retrieving Jackson collection reference.");
        return jacksonDBCollection;
    }

}
