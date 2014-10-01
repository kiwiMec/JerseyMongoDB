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

package nz.co.acme.spike.rest.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A Jackson serialised object used to marshal and un-marshal JSON object
 * representations containing a representation of an entity persisted or to
 * be persisted or updated.
 *
 * This is the entity representation to be persisted as a resource.  The key
 * for maintenance through the entity life cycle is the id field.  It is defined
 * as a UUID and maintained by the service class.
 * 
 * @author Michael Chester
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class Entity {

    private static final Logger logger = 
            Logger.getLogger(Entity.class.getName());

    /**
     * Create an empty initial object to be populated through accessors.
     */
    public Entity() {
        super();
        logger.info("New Entity created.");
    }

    /**
     * Create an object with the provided initial values.
     *
     * @param id
     * @param name
     * @param msisdn
     */
    public Entity(String id, String name, String msisdn) {
        super();
        this.id = id;
        this.name = name;
        this.mobile = msisdn;
        logger.info("New initialised Entity created.");
    }

    private String id;

    /**
     * Get the value of id.
     *
     * @return the value of id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the value of id.
     *
     * @param id new value of id.
     */
    public void setId(String id) {
        this.id = id;
    }

    private String name;

    /**
     * Get the value of name.
     *
     * @return the value of name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name.
     *
     * @param name new value of name.
     */
    public void setName(String name) {
        this.name = name;
    }

    private String mobile;

    /**
     * Get the value of mobile.
     *
     * @return the value of mobile.
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Set the value of mobile.
     *
     * @param mobile new value of mobile.
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Create a JSON string representation of this object.
     * 
     * @return String in JSON format.
     */
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writer().writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Entity.class.getName())
                    .log(Level.SEVERE, null, ex);
            return "{}";
        }
    }

}
