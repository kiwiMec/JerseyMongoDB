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

package ${package}.rest.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class LinkList {

    private static final Logger logger = 
            Logger.getLogger(LinkList.class.getName());
    
    private final List<Link> list;

    /**
     *
     */
    public LinkList() {
        super();
        list = new ArrayList<>();
        logger.info("");
    }
            
    /**
     *
     * @return
     */
    public List<Link> getList() {
        logger.info("");
        return list;
    }
    
    /**
     *
     * @param link
     */
    public void addLink(Link link) {
        logger.info("");
        list.add(link);
    }
    
    /**
     *
     * @return
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
