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
 * - Return screenshot in Base64 format (BEST for ExtentReports)
 *
 * Why Base64?
 * - Avoids broken image issues in Extent HTML report
 * - Works in:
 *   ✔ UI mode
 *   ✔ Headless mode
 *   ✔ Azure DevOps pipelines
 * ============================================================
 */
public class ScreenshotUtil {

    /**
     * Captures screenshot and returns Base64 string
     *
     * @return Base64 screenshot (String)
     */
    public static String captureScreenshotBase64() {

        try {
            // Get current active WebDriver instance
            WebDriver driver = DriverFactory.getDriver();

            // Capture screenshot in Base64 format
            return ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BASE64);

        } catch (Exception e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
}
