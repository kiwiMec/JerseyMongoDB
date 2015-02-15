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
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author michael
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class Link {

    private static final Logger logger
            = Logger.getLogger(Link.class.getName());

    private String charset;
    private String href;
    private String rel;
    private String title;
    private String type;
    private String verb;

    /**
     *
     */
    public Link() {
        super();
    }

    /**
     *
     * @param charset
     * @param href
     * @param rel
     * @param title
     * @param type
     * @param verb
     */
    public Link(String charset, String href, String rel, String title,
            String type, String verb) {
        this.charset = charset;
        this.href = href;
        this.rel = rel;
        this.title = title;
        this.type = type;
        this.verb = verb;
        logger.info("");
    }

    /**
     *
     * @return
     */
    public String getCharset() {
        logger.info("");
        return charset;
    }

    /**
     *
     * @param charset
     */
    public void setCharset(String charset) {
        logger.info("");
        this.charset = charset;
    }

    /**
     *
     * @return
     */
    public String getHref() {
        logger.info("");
        return href;
    }

    /**
     *
     * @param href
     */
    public void setHref(String href) {
        logger.info("");
        this.href = href;
    }

    /**
     *
     * @return
     */
    public String getRel() {
        logger.info("");
        return rel;
    }

    /**
     *
     * @param rel
     */
    public void setRel(String rel) {
        logger.info("");
        this.rel = rel;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        logger.info("");
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        logger.info("");
        this.title = title;
    }

    /**
     *
     * @return
     */
    public String getType() {
        logger.info("");
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        logger.info("");
        this.type = type;
    }

    /**
     *
     * @return
     */
    public String getVerb() {
        logger.info("");
        return verb;
    }

    /**
     *
     * @param verb
     */
    public void setVerb(String verb) {
        logger.info("");
        this.verb = verb;
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
