/**
 * JerseyMongoDB : A proof of concept ReST / MongoDB service for educational
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
package nz.co.acme.spike.rest.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A Jackson serialised object used to marshal and un-marshal JSON object
 * representations containing a representation of an entity persisted or to be
 * persisted or updated.
 *
 * This is the entity representation to be persisted as a resource. The key for
 * maintenance through the entity life cycle is the id field. It is defined as a
 * UUID and maintained by the service class.
 *
 * @author Michael Chester
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class Entity {

    private static final Logger logger
            = Logger.getLogger(Entity.class.getName());

    /**
     * Create an empty initial object to be populated through accessors.
     */
    public Entity() {
        super();
        this.links = new ArrayList<>();
        logger.info("New Entity created.");
    }

    /**
     * Create an object with the provided initial values.
     *
     * @param id
     * @param created
     * @param updated
     * @param state
     * @param name
     * @param mobile
     * @param links
     */
    public Entity(String id, String created, String updated,
            String state, String name, String mobile, List<Link> links) {
        super();
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.state = state;
        this.name = name;
        this.mobile = mobile;
        this.links = links;
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

    private String created;

    /**
     * Get the value of created
     *
     * @return the value of created
     */
    public String getCreated() {
        return created;
    }

    /**
     * Set the value of created
     *
     * @param created new value of created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    private String updated;

    /**
     * Get the value of updated
     *
     * @return the value of updated
     */
    public String getUpdated() {
        return updated;
    }

    /**
     * Set the value of updated
     *
     * @param updated new value of updated
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    private String state;

    /**
     * Get the value of state
     *
     * @return the value of state
     */
    public String getState() {
        return state;
    }

    /**
     * Set the value of state
     *
     * @param state new value of state
     */
    public void setState(String state) {
        this.state = state;
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

    private List<Link> links;

    /**
     * Get the value of links
     *
     * @return the value of links
     */
    public List<Link> getLinks() {
        return links;
    }

    /**
     * Set the value of links
     *
     * @param links new value of links
     */
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    /**
     * Create a JSON string representation of this object.
     *
     * @return String in JSON format or null if an exception is caught.
     */
    @Override
    public String toString() {
        String string = null;
        try {
            string = new ObjectMapper().writer().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.severe(e.toString());
        }
        return string;
    }

}
