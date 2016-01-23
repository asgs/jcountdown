package org.adoptopenjdk.jcountdown.control;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class PropertiesProducer {
    
    private static final String PROPERTIES_FILE = "mongo.properties";
    
    private Properties properties = new Properties();
    
    public static final String DATABASE_NAME        = "database.name";
    public static final String PORT                 = "database.port";
    public static final String HOST                 = "database.host";
    public static final String USERNAME             = "database.username";
    public static final String PASSWORD             = "database.password";

    
    @Produces
    public MongoConfiguration produceMongoConfiguration(){
            
        // Set the default property values
        properties.put(DATABASE_NAME, "jcountdown");
        properties.put(PORT, 27017);
        properties.put(HOST, "localhost");
        properties.put(USERNAME, ""); // TODO: set username. See Wiki for more info on how to set users in mongo db
        properties.put(PASSWORD, ""); // TODO: set password. See wiki for more info on how to set users in mongo db
        
        // Now try and read in any property overrides
        final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE);
        if (in == null) {
            return new MongoConfiguration(properties);
        }
        try {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties", e);
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                // don't care
            }
        }
    
        return new MongoConfiguration(properties);
    }
}
