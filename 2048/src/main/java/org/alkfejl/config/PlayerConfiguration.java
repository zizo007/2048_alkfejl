package org.alkfejl.config;

import java.io.IOException;
import java.util.Properties;

public class PlayerConfiguration {

    private static final Properties properties = new Properties();


    static{
        try{
            properties.load(PlayerConfiguration.class.getResourceAsStream("/application.properties"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static String getValue(String key){
        return properties.getProperty(key);
    }
}
