package com.epam.docker;

import java.io.IOException;
import java.util.Properties;

public class PropertiesHolder {

    private static final PropertiesHolder INSTANCE = new PropertiesHolder();

    private static final String PROPERTY_FILE = "application.properties";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String URL = "url";
    public static final String DRIVER = "driver";

    private Properties properties;

    private PropertiesHolder() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertiesHolder getInstance() {
        return INSTANCE;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
