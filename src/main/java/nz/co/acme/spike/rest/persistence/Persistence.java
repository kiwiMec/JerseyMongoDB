/**
 * RestServiceTemplate : A proof of concept ReST / MongoDB service for 
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

package nz.co.acme.spike.rest.persistence;

import nz.co.acme.spike.rest.resource.IdList;
import nz.co.acme.spike.rest.resource.Entity;
import com.mongodb.BasicDBObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.WriteResult;

/**
 * Data persistence services class.
 * 
 * Contains a number of persistence methods for dealing with the MongoDB back
 * end.
 * 
 * @author Michael Chester
 */
public class Persistence {

    private static final Logger logger
            = Logger.getLogger(Persistence.class.getName());

    private final String database;
    private final String collection;

    /**
     * Create a new 'Persistence' object that uses the collection named in the
     * collection parameter within the database named in the database parameter.
     *
     * @param database
     * @param collection
     */
    public Persistence(String database, String collection) {
        logger.log(Level.INFO, "New Persistence object created for the "
                + "collection {0} in the database {1}.", 
                new Object[]{collection, database});
        this.database = database;
        this.collection = collection;
    }

    /**
     * Creates a new resource from the given entity.
     * 
     * This will persist the given entity to the document store.  
     * 
     * Note that no checking is done for duplicates so it will happily add the
     * same entity twice.  Uniqueness is assumed to be enforced by the
     * generation of a new UUID by the caller.  A uniqueness constraint could
     * also be placed on the id field of the collection.
     *
     * @param entity
     * @return a copy of the created entity, or null if an exception took place
     */
    public Entity create(Entity entity) {
        Access access = new Access(database, collection);
        WriteResult<Entity, String> writeResult
                = access.getJacksonDBCollection().insert(entity);
        Entity createdEntity = writeResult.getSavedObject();
        if (createdEntity != null) {
            logger.log(Level.INFO, "Created resource {0}.",
                    createdEntity.toString());
            return createdEntity;
        }
        logger.log(Level.WARNING, "Unable to create resource {0}.", entity);
        return null;
    }

    /**
     * Get a list of id's as contained in the id fields of the document 
     * collection.
     *
     * This allows inspection of the available documents by id.  At the moment
     * it is not paginated.  Because the documents are not indexed sequentially
     * but by unique id it would be difficult to accurately paginate from this
     * level.
     * 
     * @return an IdList containing all id fields in the collection.
     */
    public IdList readIds() {
        Access access = new Access(database, collection);
        BasicDBObject query = new BasicDBObject();
        BasicDBObject field = new BasicDBObject();
        field.put("id", 1);
        DBCursor dbCursor = access.getJacksonDBCollection().find(query, field);
        IdList idList = new IdList();
        while (dbCursor.hasNext()) {
            idList.addId(((Entity) dbCursor.next()).getId());
        }
        logger.log(Level.INFO, "Read resource ID list {0}.", idList);
        return idList;
    }

    /**
     * Read an Entity from persistence and return it to the caller.
     * 
     * Returns the requested entity if it exists and is recovered.  If an issue
     * prevents the entity from being recovered and returned then the event is
     * logged and null is returned.
     *
     * @param id
     * @return Entity if found, null otherwise
     */
    public Entity read(String id) {
        Access access = new Access(database, collection);
        DBCursor<Entity> dbCursor
                = access.getJacksonDBCollection().find(DBQuery.is("id", id));
        if (dbCursor.count() != 1) {
            logger.log(Level.INFO, "Failed to find resource {0}.", id);
            return null;
        }
        Entity entity = dbCursor.next();
        logger.log(Level.INFO, "Retrieved resource {0}.", entity);
        return entity;
    }

    /**
     * Update a persisted entity with the supplied replacement.
     * 
     * Assumes that the id field is unique either by constraint or application
     * management.  Looks up the entity persisted under the given id and 
     * effectively replaces it with the new on provided in the parameter.
     * 
     * At this level we do not enforce the id provided to be equivalent to that
     * in the entity.  This allows for total update of a persisted entity.  If
     * this activity should not update the id then the calling thread will need
     * to ensure that the id's are the same.
     *
     * @param id
     * @param entity
     * @return a copy of the updated entity, or null otherwise
     */
    public Entity update(String id, Entity entity) {
        Access access = new Access(database, collection);
        WriteResult<Entity, String> writeResult
                = access.getJacksonDBCollection()
                .update(DBQuery.is("id", id), entity);
        if (writeResult.getN() > 0) {
            Entity updated = read(id);
            logger.log(Level.INFO, "Updated resource {0}.", updated);
            return updated;
        } else {
            logger.log(Level.INFO, "Failed to update resource {0}.", entity);
            return null;
        }
    }

    /**
     * Deletes the entity or entities with the given id from the persistence
     * layer.
     *
     * @param id
     * @return Number of entities deleted from the persistence layer.
     */
    public int delete(String id) {
        Access access = new Access(database, collection);
        WriteResult<Entity, String> writeResult = access
                .getJacksonDBCollection().remove(new BasicDBObject("id", id));
        int numberDeleted = writeResult.getN();
        logger.log(Level.INFO, "Number of resources deleted for id {0} is {1}.",
                new Object[]{id, numberDeleted});
        return numberDeleted;
    }

}
