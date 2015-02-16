#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * ${artifactId} : A proof of concept ReST / MongoDB service for educational
 * purposes only. Copyright (C) 2014 Michael Chester
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ${package}.rest.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import ${package}.rest.events.Events;
import ${package}.rest.persistence.Persistence;
import ${package}.rest.resource.Entity;
import ${package}.rest.resource.LinkList;

/**
 * ReST service implementation.
 *
 * This class implements the resource methods for the ReST service. It is
 * implemented as a per call servlet JAX-RS instance. All JAX-RS annotations are
 * contained in the implemented interface. Each method calls the appropriate
 * event methods to handle associated business logic.
 *
 * @author Michael Chester
 */
public class Service implements Api {

    private static final Logger logger
            = Logger.getLogger(Service.class.getName());

    private final Events events;
    private final Persistence persistence;

    /**
     * Initialise this 'Service' instance with a new 'Persistence' connection.
     */
    public Service() {
        logger.info("New Service created.");
        events = new Events();
        persistence = new Persistence("${artifactId}", "Resource");
    }

    /**
     * Produces a list of ids that can be used to inquire after individual
     * entities.
     *
     * @return JSON object containing an array of id's.
     */
    @Override
    public LinkList listIds(UriInfo uriInfo) {
        logger.info("Reading ID list.");
        events.preListIds(uriInfo);
        if (uriInfo.getAbsolutePath().toString().endsWith("/") == false) {
            throw new WebApplicationException(
                    Response.Status.NOT_FOUND);
        }
        return events.postListIds(uriInfo, persistence.readIds());
    }

    /**
     * Accepts a JSON representation of an entity, updates it's id with a new
     * value and commits the entity to the persistence layer.
     *
     * @param entity
     * @return A JSON representation of the persisted object.
     */
    @Override
    public Entity create(UriInfo uriInfo, Entity entity) {
        entity.setId(UUID.randomUUID().toString());
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Z"));
        entity.setCreated(zonedDateTime.toString());
        logger.log(Level.INFO, "Creating new resource {0}.", entity);
        Entity newEntity = events.preCreate(uriInfo, entity);
        Entity createdEntity = persistence.create(newEntity);
        if (createdEntity == null) {
            throw new WebApplicationException(
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
        return events.postCreate(uriInfo, newEntity, createdEntity);
    }

    /**
     * Return a copy of the entity persisted with the id in the given path
     * parameter.
     *
     * @param id
     * @return A JSON representation of the entity or spring a 404 not found.
     */
    @Override
    public Entity read(UriInfo uriInfo, String id) {
        String newId = events.preRead(uriInfo, id);
        logger.log(Level.INFO, "Retrieving resource with id {0}.", newId);
        Entity entity = persistence.read(newId);
        logger.info(entity.toString());
        if (entity == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return events.postRead(uriInfo, id, entity);
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
    public Entity update(UriInfo uriInfo, String id, Entity entity) {
        events.preUpdate(uriInfo, id, entity);
        String lastUpdated = entity.getUpdated();
        if (entity.getId().compareTo(id) != 0) {
            logger.info("ID provided does not match ID in entity.");
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        logger.log(Level.INFO, "Updating resource {0} with {1}.",
                new Object[]{id, entity});
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Z"));
        entity.setUpdated(zonedDateTime.toString());
        Entity updatedEntity = persistence.update(id, entity.getCreated(), 
                lastUpdated, entity);
        if (null == updatedEntity) {
            logger.info("Unable to find the appropriate resource to update.");
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return events.postUpdate(uriInfo, id, entity, updatedEntity);
    }

    /**
     * Delete from persistence the entities that have the id given in the path
     * parameter.
     *
     * @param id
     */
    @Override
    public void delete(UriInfo uriInfo, String id) {
        logger.log(Level.INFO, "Deleting resource with id {0}.", id);
        String newId = events.preDelete(uriInfo, id);
        if (persistence.delete(newId) != 1) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        events.postDelete(uriInfo, newId);
    }

}
