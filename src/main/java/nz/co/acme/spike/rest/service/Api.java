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

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import nz.co.acme.spike.rest.resource.Entity;
import nz.co.acme.spike.rest.resource.IdList;

/**
 * ReST service API interface definition.
 * 
 * This interface defines the JAX-RS properties of the implemented service.  It
 * separates the method implementation from the JAX-RS markup for clarity.
 *
 * @author Michael Chester
 */
@Path("/")
public interface Api {
    
    /**
     * Produces a list of ids that can be used to inquire after individual 
     * entities.
     *
     * @return JSON object containing an array of id's.
     */
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public IdList listIds();
    
    /**
     * Accepts a JSON representation of an entity, updates it's id with a new
     * value and commits the entity to the persistence layer.
     *
     * @param entity
     * @return A JSON representation of the persisted object.
     */
    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Entity create(Entity entity);
    
    /**
     * Return a copy of the entity persisted with the id in the given path
     * parameter.
     *
     * @param id  
     * @return A JSON representation of the entity or spring a 404 not found.
     */
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("{id : [0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}}")
    public Entity read(@PathParam("id") String id);
    
    /**
     * Update the entity persisted with the path parameter id to contain the 
     * detail of the provided entity.
     *
     * @param id
     * @param entity
     * @return A JSON representation of the updated persisted entity.
     */
    @PUT
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("{id : [0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}}")
    public Entity update(@PathParam("id") String id, Entity entity);
        
    /**
     * Delete from persistence the entities that have the id given in the path 
     * parameter. 
     *
     * @param id
     */
    @DELETE
    @Path("{id : [0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}}")
    public void delete(@PathParam("id") String id);
}
