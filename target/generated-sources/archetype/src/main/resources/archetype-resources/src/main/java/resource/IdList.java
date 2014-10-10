#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * ${artifactId} : A proof of concept ReST / MongoDB service for 
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

package ${package}.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A Jackson serialised object used to marshal and un-marshal JSON object
 * representations containing a list of id's called 'rel'.
 * 
 * Normally obtained from the resource root URN like:
 * 
 * http://api.acme.co.nz/resource
 * 
 * The effective representation of this class returned in JSON will look 
 * something like:
 * 
 * {"rel":["7772b2bc-9ace-465d-9fe7-dd03543eaf92",
 * "79d5bb01-761e-4cea-a1ee-9548f8e166f4"]}
 * 
 * Id's from the list can then be used to construct a URN relative to the 
 * current similar to:
 * 
 * http://api.acme.co.nz/resource/7772b2bc-9ace-465d-9fe7-dd03543eaf92
 *
 * @author Michael Chester
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class IdList {

    private static final Logger logger = 
            Logger.getLogger(IdList.class.getName());

    @JsonProperty("rel")
    private ArrayList<String> idList;

    /**
     * Create an empty initial object to be populated through accessors.
     */
    public IdList() {
        super();
        logger.info("New IdList created.");
        idList = new ArrayList<>();
    }

    /**
     * Get the value of idList
     *
     * @return the value of idList
     */
    public ArrayList<String> getIdList() {
        return idList;
    }

    /**
     * Set the value of idList
     *
     * @param idList new value of idList
     */
    public void setIdList(ArrayList<String> idList) {
        this.idList = idList;
    }
    
    /**
     * Add an id to the idList
     *
     * @param id new id to add to idList
     */
    public void addId(String id) {
        idList.add(id);
    }

    /**
     * Create a JSON string representation of this object.
     * 
     * @return String in JSON format or null if an exception is caught.
     */
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writer().writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(IdList.class.getName())
                    .log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
