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

package ${symbol_dollar}{package}.events;

import java.util.logging.Logger;
import ${symbol_dollar}{package}.resource.Entity;
import ${symbol_dollar}{package}.resource.IdList;

/**
 * Hooks for business logic event bubbling.
 * 
 * In this class are methods to handle business logic.  Event code should be 
 * asynchronous so that the ReST call can return before the client's connection
 * times out.
 *
 * @author Michael Chester
 */
public class Events {

    private static final Logger logger = 
            Logger.getLogger(Events.class.getName());

    /**
     * Events POJO creation method.
     */
    public Events() {
        logger.info("New Events created.");
    }
    
    /**
     * Initial event as the listIds method executes.
     */
    public void preListIds() {
        logger.info("Pre-listId event.");
    }
    
    /**
     * Final event before the listIds method returns.
     * 
     * @param idList the idList recovered from persistence.
     * @return the idList to return to the ReST client.
     */
    public IdList postListIds(IdList idList) {
        logger.info("Post-listId event.");
        return idList;
    }
    
    /**
     * Initial event as the create method executes.
     * 
     * @param entity the entity provided by the client pre-persistence.
     * @return the entity to persist.
     */
    public Entity preCreate(Entity entity) {
        logger.info("Pre-create event.");
        return entity;
    }
    
    /**
     * Final event before the create method returns.
     * 
     * @param entity initial entity provided by the client.
     * @param createdEntity entity persisted.
     * @return entity to return to the client.
     */
    public Entity postCreate(Entity entity, Entity createdEntity) {
        logger.info("Post-create event.");
        return createdEntity;
    }
    
    /**
     * Initial event as the read method executes.
     * 
     * @param id the id of the entity the client has requested.
     * @return the id of the entity to retrieve.
     */
    public String preRead(String id) {
        logger.info("Pre-read event.");
        return id;
    }
    
    /**
     * Final event before the read method returns.
     * 
     * @param id the id of the entity the client has requested.
     * @param entity the entity retrieved.
     * @return the entity to return to the client.
     */
    public Entity postRead(String id, Entity entity) {
        logger.info("Post-read event.");
        return entity;
    }
    
    /**
     * Initial event as the update method executes.
     * 
     * @param id the id of the entity the client has requested to be updated.
     * @param entity the updated entity as provided by the client.
     * @return the entity to update with.
     */
    public Entity preUpdate(String id, Entity entity) {
        logger.info("Pre-update event.");
        return entity;
    }
    
    /**
     * Final event before the update method returns.
     * 
     * @param id the id of the entity the client has requested to be updated.
     * @param entity the updated entity as provided by the client.
     * @param updated the entity as updated.
     * @return the entity to return to the client.
     */
    public Entity postUpdate(String id, Entity entity, Entity updated) {
        logger.info("Post-update event.");
        return updated;
    }   
    
    /**
     * Initial event as the delete method executes.
     * 
     * @param id the id of the event to delete as provided by the client.
     * @return the id of the event to delete.
     */
    public String preDelete(String id) {
        logger.info("Pre-delete event.");
        return id;
    }
    
    /**
     * Final event before the delete method returns.
     * 
     * @param id the id of the event to delete as provided by the client.
     */
    public void postDelete(String id) {
        logger.info("Post-delete event.");
    }   
    
}
