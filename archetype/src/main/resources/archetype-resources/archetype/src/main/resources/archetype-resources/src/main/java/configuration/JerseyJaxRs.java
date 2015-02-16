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

package ${symbol_dollar}{package}.configuration;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configuration for JAX-RS.
 * 
 * This class:
 *  - Sets the root path for the service to '/'.
 *  - Registers the 'Service' class as the JAX-RS service. Note that the name
 *    of the resource is given by the archive 'context.xml' content.
 * 
 * @author Michael Chester
 */
@ApplicationPath("/")
public class JerseyJaxRs extends ResourceConfig {

    private static final Logger logger = 
            Logger.getLogger(JerseyJaxRs.class.getName());

    /**
     * JerseyJaxRs registers the Jersey JAX-RS service(s).  
     * 
     * In this implementation the code explicitly registers each service as 
     * Jersey's class path scanning code can generate annoying issues by trying
     * to register abstract classes it finds with JAX-RS annotations.
     * 
     * @throws UnknownHostException
     */
    public JerseyJaxRs() throws UnknownHostException {
        register(${symbol_dollar}{package}.service.Service.class);
        logger.log(Level.INFO, "Registered service {0} with Jersey.",
                        ${symbol_dollar}{package}.service.Service.class.getName());
    }

}
