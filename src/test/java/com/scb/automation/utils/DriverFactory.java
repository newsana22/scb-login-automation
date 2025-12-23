package com.scb.automation.utils;

import org.openqa.selenium.WebDriver;

/**
 * ============================================================
 * Class: DriverFactory
 * ------------------------------------------------------------
 * Purpose:
 * - Store WebDriver instance using ThreadLocal
 * - Required so TestNG Listener & Extent Logger
 *   can capture screenshots without passing driver everywhere
 *
 * Why ThreadLocal?
 * - Supports parallel execution
 * - Each test thread gets its own driver
 * ============================================================
 */
public class DriverFactory {

    // Thread-safe storage of WebDriver
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Store WebDriver for current thread
     */
    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    /**
     * Get WebDriver for current thread
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Remove WebDriver after test execution
     * Prevents memory leaks
     */
    public static void unload() {
        driver.remove();
    }
}
