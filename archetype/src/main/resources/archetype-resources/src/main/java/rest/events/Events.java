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
package ${package}.rest.events;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.core.UriInfo;
import ${package}.rest.resource.Entity;
import ${package}.rest.resource.Link;
import ${package}.rest.resource.LinkList;

/**
 * Hooks for business logic event bubbling.
 *
 * In this class are methods to handle business logic. Event code should be
 * asynchronous so that the ReST call can return before the client's connection
 * times out.
 *
 * @author Michael Chester
 */
public class Events {

    private static final Logger logger
            = Logger.getLogger(Events.class.getName());

    /**
     * Events POJO creation method.
     */
    public Events() {
        logger.info("New Events created.");
    }

    /**
     * Initial event as the listIds method executes.
     *
     * @param uriInfo
     */
    public void preListIds(UriInfo uriInfo) {
        logger.info("Pre-listId event.");
    }

    /**
     * Final event before the listIds method returns.
     *
     * @param uriInfo
     * @param list the idList recovered from persistence.
     * @return the idList to return to the ReST client.
     */
    public LinkList postListIds(UriInfo uriInfo, List<String> list) {
        logger.info("Post-listId event.");
        LinkList linkList = new LinkList();
        for (String item : list) {
            linkList.addLink(new Link("UTF-8", uriInfo.getAbsolutePath() + item,
                    "resource", "Reference to a resource.",
                    "application/json;version=0.1", "GET"));
        }
        return linkList;
    }

    /**
     * Initial event as the create method executes.
     *
     * @param uriInfo
     * @param entity the entity provided by the client pre-persistence.
     * @return the entity to persist.
     */
    public Entity preCreate(UriInfo uriInfo, Entity entity) {
        logger.info("Pre-create event.");
        return entity;
    }

    /**
     * Final event before the create method returns.
     *
     * @param uriInfo
     * @param entity initial entity provided by the client.
     * @param createdEntity entity persisted.
     * @return entity to return to the client.
     */
    public Entity postCreate(UriInfo uriInfo, Entity entity,
            Entity createdEntity) {
        logger.info("Post-create event.");
        if (null == createdEntity.getLinks()) {
            createdEntity.setLinks(new ArrayList<Link>());
        }

        createdEntity.getLinks().add(new Link("UTF-8",
                uriInfo.getAbsolutePath() + entity.getId(),
                "self", "Reference to this resource.",
                "application/json;version=0.1", "GET"));
        createdEntity.getLinks().add(new Link("UTF-8",
                uriInfo.getBaseUri().toString(),
                "collection", "Reference to resource root.",
                "application/json;version=0.1", "GET"));
        return createdEntity;
    }

    /**
     * Initial event as the read method executes.
     *
     * @param uriInfo
     * @param id the id of the entity the client has requested.
     * @return the id of the entity to retrieve.
     */
    public String preRead(UriInfo uriInfo, String id) {
        logger.info("Pre-read event.");
        return id;
    }

    /**
     * Final event before the read method returns.
     *
     * @param uriInfo
     * @param id the id of the entity the client has requested.
     * @param entity the entity retrieved.
     * @return the entity to return to the client.
     */
    public Entity postRead(UriInfo uriInfo, String id, Entity entity) {
        logger.info("Post-read event.");
        entity.getLinks().add(new Link("UTF-8",
                uriInfo.getAbsolutePath().toString(),
                "self", "Reference to this resource.",
                "application/json;version=0.1", "GET"));
        entity.getLinks().add(new Link("UTF-8",
                uriInfo.getBaseUri().toString(),
                "collection", "Reference to resource root.",
                "application/json;version=0.1", "GET"));
        return entity;
    }

    /**
     * Initial event as the update method executes.
     *
     * @param uriInfo
     * @param id the id of the entity the client has requested to be updated.
     * @param entity the updated entity as provided by the client.
     * @return the entity to update with.
     */
    public Entity preUpdate(UriInfo uriInfo, String id, Entity entity) {
        logger.info("Pre-update event.");
        return entity;
    }

    /**
     * Final event before the update method returns.
     *
     * @param uriInfo
     * @param id the id of the entity the client has requested to be updated.
     * @param entity the updated entity as provided by the client.
     * @param updated the entity as updated.
     * @return the entity to return to the client.
     */
    public Entity postUpdate(UriInfo uriInfo, String id, Entity entity,
            Entity updated) {
        logger.info("Post-update event.");
        updated.getLinks().add(new Link("UTF-8",
                uriInfo.getAbsolutePath().toString(),
                "self", "Reference to this resource.",
                "application/json;version=0.1", "GET"));
        updated.getLinks().add(new Link("UTF-8",
                uriInfo.getBaseUri().toString(),
                "collection", "Reference to resource root.",
                "application/json;version=0.1", "GET"));
        return updated;
    }

    /**
     * Initial event as the delete method executes.
     *
     * @param uriInfo
     * @param id the id of the event to delete as provided by the client.
     * @return the id of the event to delete.
     */
    public String preDelete(UriInfo uriInfo, String id) {
        logger.info("Pre-delete event.");
        return id;
    }

    /**
     * Final event before the delete method returns.
     *
     * @param uriInfo
     * @param id the id of the event to delete as provided by the client.
     */
    public void postDelete(UriInfo uriInfo, String id) {
        logger.info("Post-delete event.");
    }

    /*
     Helper methods follow.
     */
    /**
     * Update the provided entity
     *
     * @param entity
     */
    /*
     private void updateResource(Entity entity) {
     ClientConfig config = new ClientConfig();
     Client client = ClientBuilder.newClient(config);
     URI uri = UriBuilder.fromUri("http://localhost:8080/iso8583event")
     .build();
     WebTarget target = client.target(uri);
     target.path(entity.getId()).request().accept(MediaType.APPLICATION_JSON)
     .put(javax.ws.rs.client.Entity.json(entity));
     }
     */
}
