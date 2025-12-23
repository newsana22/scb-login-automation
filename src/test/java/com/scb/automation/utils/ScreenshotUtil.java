package com.scb.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * ============================================================
 * Utility Class: ScreenshotUtil
 * ------------------------------------------------------------
 * Purpose:
 * - Capture screenshots from Selenium WebDriver
 * - Return screenshot as Base64 string
 *
 * Why Base64?
 * - No broken image issues
 * - Works in:
 *   ✔ UI mode
 *   ✔ Headless mode
 *   ✔ Azure DevOps pipeline
 * ============================================================
 */
public class ScreenshotUtil {

    /**
     * Capture screenshot in Base64 format
     *
     * @return Base64 encoded screenshot
     */
    public static String captureBase64() {

        try {
            WebDriver driver = DriverFactory.getDriver();

            if (driver == null) {
                return null;
            }

            return ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BASE64);

        } catch (Exception e) {
            System.out.println("Screenshot capture failed: " + e.getMessage());
            return null;
        }
    }
}
