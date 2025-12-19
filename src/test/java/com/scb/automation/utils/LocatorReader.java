package com.scb.automation.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class to read UI locators from locators.properties
 */
public class LocatorReader {

    private static Properties properties = new Properties();

    // Load locators only once when class is loaded
    static {
        try {
            InputStream inputStream =
                    LocatorReader.class
                            .getClassLoader()
                            .getResourceAsStream("locators.properties");

            if (inputStream == null) {
                throw new RuntimeException("locators.properties file not found");
            }

            properties.load(inputStream);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load locators.properties", e);
        }
    }

    // Fetch locator value by key
    public static String get(String key) {
        return properties.getProperty(key);
    }
}
