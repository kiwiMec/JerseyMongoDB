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

package nz.co.acme.spike.rest.service;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import nz.co.acme.spike.rest.persistence.Persistence;
import nz.co.acme.spike.rest.resource.Entity;
import nz.co.acme.spike.rest.resource.IdList;

/**
 * ReST service implementation.
 * 
 * This class implements the resource methods for the ReST service.  It is 
 * implemented as a per call servlet JAX-RS instance.  All JAX-RS annotations
 * are contained in the implemented interface.
 *
 * @author Michael Chester
 */
public class Service implements Api {

    private static final Logger logger = 
            Logger.getLogger(Service.class.getName());

    private final Persistence persistence;

    /**
     * Initialise this 'Service' instance with a new 'Persistence' connection.
     */
    public Service() {
        logger.info("New Service created.");
        persistence = new Persistence("Service", "Resource");
    }

    /**
     * Produces a list of ids that can be used to inquire after individual 
     * entities.
     *
     * @return JSON object containing an array of id's.
     */
    @Override
    public IdList listIds() {
        logger.info("Reading ID list.");
        return persistence.readIds();
    }

    /**
     * Accepts a JSON representation of an entity, updates it's id with a new
     * value and commits the entity to the persistence layer.
     *
     * @param entity
     * @return A JSON representation of the persisted object.
     */
    @Override
    public Entity create(Entity entity) {
        entity.setId(UUID.randomUUID().toString());
        logger.log(Level.INFO, "Creating new resource {0}.", entity);
        Entity createdEntity = persistence.create(entity);
        if (entity == null) {
            throw new WebApplicationException(
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
        return createdEntity;
    }

    /**
     * Return a copy of the entity persisted with the id in the given path
     * parameter.
     *
     * @param id  
     * @return A JSON representation of the entity or spring a 404 not found.
     */
    @Override
    public Entity read(String id) {
        logger.log(Level.INFO, "Retrieving resource with id {0}.", id);
        Entity entity = persistence.read(id);
        if (entity == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return entity;
    }

    /**
     * Update the entity persisted with the path parameter id to contain the 
     * detail of the provided entity.
     *
     * @param id
     * @param entity
     * @return A JSON representation of the updated persisted entity.
     */
    @Override
    public Entity update(String id, Entity entity) {
        if (entity.getId().compareTo(id) != 0) {
            logger.info("ID provided does not match ID in entity.");
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        logger.log(Level.INFO, "Updating resource {0} with {1}.", 
                new Object[]{id, entity});
        return persistence.update(id, entity);
    }

    /**
     * Delete from persistence the entities that have the id given in the path 
     * parameter. 
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        logger.log(Level.INFO, "Deleting resource with id {0}.", id);
        if (persistence.delete(id) != 1) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

}
