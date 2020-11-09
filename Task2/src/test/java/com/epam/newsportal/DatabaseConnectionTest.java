package com.epam.newsportal;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionTest {

    @Test
    public void testDatabaseConnection() {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
            Class.forName(properties.getProperty("data.source.driver_class"));
            Connection connection = DriverManager.getConnection(
                    properties.getProperty("data.source.url"),
                    properties.getProperty("data.source.username"),
                    properties.getProperty("data.source.password"));
            Assert.assertNotNull(connection);
        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
