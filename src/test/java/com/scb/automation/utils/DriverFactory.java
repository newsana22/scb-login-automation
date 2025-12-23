package com.scb.automation.utils;

import org.openqa.selenium.WebDriver;

/**
 * Stores WebDriver using ThreadLocal
 * Required so Listener can access driver for screenshots
 */
public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void unload() {
        driver.remove();
    }
}
