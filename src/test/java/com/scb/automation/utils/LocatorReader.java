package com.scb.automation.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * LocatorReader
 * -------------------------
 * This utility class is responsible for reading UI element locators
 * from the locators.properties file.
 *
 * Example usage:
 *   String usernameField = LocatorReader.get("login.username");
 *
 * Why this class is needed:
 * - Keeps all UI locators outside Java code
 * - Makes maintenance easy when UI changes
 * - Avoids hardcoding XPath / CSS in test code
 */
public class LocatorReader {

    /**
     * Properties object to hold all key-value pairs
     * from locators.properties
     */
    private static Properties properties = new Properties();

    /**
     * Static block
     * -------------------------
     * This block runs ONLY ONCE when the class is loaded into memory.
     * It loads the locators.properties file from the classpath.
     *
     * This ensures:
     * - Locators are loaded a single time
     * - Faster access during test execution
     * - No repeated file reading
     */
    static {
        try {
            // Load locators.properties from src/test/resources
            InputStream inputStream =
                    LocatorReader.class
                            .getClassLoader()
                            .getResourceAsStream("locators.properties");

            // If file is not found, fail immediately
            if (inputStream == null) {
                throw new RuntimeException("locators.properties file not found");
            }

            // Load all locators into Properties object
            properties.load(inputStream);

        } catch (Exception e) {
            // Fail fast if locators file cannot be loaded
            throw new RuntimeException("Failed to load locators.properties", e);
        }
    }

    /**
     * get()
     * -------------------------
     * Generic method to fetch any locator value using its key.
     *
     * Example:
     *   LocatorReader.get("login.submit.button");
     *
     * @param key Locator key defined in locators.properties
     * @return Locator value (XPath / CSS / ID etc.)
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }
}
