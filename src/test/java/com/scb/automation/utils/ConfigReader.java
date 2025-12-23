package com.scb.automation.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigReader is a utility class used to read values
 * from config.properties file.
 *
 * This file is kept inside src/test/resources so that
 * Maven and Azure pipeline can load it from classpath.
 */
public class ConfigReader {

    /**
     * Properties object that will hold all key-value
     * pairs from config.properties file.
     *
     * Static so that it is shared across the entire test run.
     */
    private static Properties properties = new Properties();

    /**
     * Static block:
     * ----------------
     * This block runs ONLY ONCE when the class is loaded into memory.
     *
     * Purpose:
     * - Load config.properties file
     * - Store all values into the Properties object
     *
     * Why static block?
     * - We don’t want to reload the file for every test
     * - Faster execution
     * - Cleaner design
     */
    static {
        try {
            // Load config.properties from classpath
            InputStream inputStream =
                    ConfigReader.class
                            .getClassLoader()
                            .getResourceAsStream("config.properties");

            // Safety check: file must exist
            if (inputStream == null) {
                throw new RuntimeException("config.properties file not found in classpath");
            }

            // Load all key-value pairs into Properties object
            properties.load(inputStream);

        } catch (Exception e) {
            // Fail fast if config file cannot be loaded
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    /**
     * Generic getter method to fetch any property value.
     *
     * Example usage:
     * ConfigReader.get("app.url");
     *
     * @param key Property name from config.properties
     * @return Property value as String
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }
}
