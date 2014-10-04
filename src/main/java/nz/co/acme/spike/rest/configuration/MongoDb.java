/**
 * JerseyMongoDB : A proof of concept ReST / MongoDB service for 
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

package nz.co.acme.spike.rest.configuration;

import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import nz.co.acme.spike.rest.persistence.Access;

/**
 * Set up and tear down the MongoDB connection.
 *
 * Application life cycle host for the MongoDB connection.  Sets up the 
 * MongoClient when the servlet context is initialised.  Closes the client when
 * the context is destroyed.  
 * 
 * As MongoDB's java driver client is thread safe 'mongoClient' is injected 
 * into the static field of the same name in the database 'Access' class.  The 
 * 'Access' class must only be instantiated following this injection.  This 
 * ordering is enforced by the servlet context startup order.
 * 
 * @author Michael Chester
 */
@WebListener
public class MongoDb implements ServletContextListener {

    private static final Logger logger = 
            Logger.getLogger(MongoDb.class.getName());

    /**
     * Initialise the servlet context with the MongoDB connection.
     * 
     * Note that the connection is directly injected into the persistence 
     * 'Access' class that class has no way of reaching the servlet context.
     * The connection is added to the servlet context so that it can be picked
     * up when it comes time to destroy the context.
     * 
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        try {
            MongoClient mongoClient = new MongoClient();
            servletContext.setAttribute("mongoClient", mongoClient);
            Access.setMongoClient(mongoClient);
            logger.info("Connected to MongoDB using default settings.");
        } catch (UnknownHostException exception) {
            logger.log(Level.SEVERE, "Unable to connect database.", exception);
        }
    }

    /**
     * Close the database connection as a part of servlet context destruction.
     * 
     * Retrieves the connection from the servlet context and destroys it.  Over
     * zealous containers may warn about possible memory leaks due to threads
     * that the client started not being shut down.  Generally this is just the 
     * container moaning that it does not manage all of its threads directly. In
     * most cases this warning is not actually valid.
     * 
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        MongoClient mongoClient
                = (MongoClient) servletContext.getAttribute("mongoClient");
        mongoClient.close();
        logger.info("Closed connection to MongoDB.");
    }

}
