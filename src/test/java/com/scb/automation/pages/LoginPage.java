package com.scb.automation.pages;

import com.scb.automation.utils.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * ============================================================================
 * Page Object Class: LoginPage
 * ----------------------------------------------------------------------------
 * Purpose:
 * - Represents the SCB Login page
 * - Encapsulates all login-related UI actions
 *
 * Design Principles Used:
 * - Page Object Model (POM)
 * - No hardcoded locators
 * - Locators are read from locators.properties
 *
 * Benefits:
 * - Cleaner test classes
 * - Easy locator maintenance
 * - Reusable login actions
 * ============================================================================
 */
public class LoginPage {

    /**
     * WebDriver instance passed from test class
     * This driver is controlled by:
     * - Test class lifecycle
     * - DriverFactory (for screenshots & listeners)
     */
    private WebDriver driver;

    /**
     * Constructor
     *
     * @param driver WebDriver instance created in test setup
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // =========================================================================
    // Page Actions (Business Methods)
    // =========================================================================

    /**
     * Enters username into username input field
     *
     * @param username invalid/valid username from Excel
     */
    public void enterUsername(String username) {
        driver.findElement(
                By.xpath(LocatorReader.get("username.field"))
        ).sendKeys(username);
    }

    /**
     * Clicks on the "Next" button after entering username
     */
    public void clickNext() {
        driver.findElement(
                By.xpath(LocatorReader.get("next.button"))
        ).click();
    }

    /**
     * Confirms the security image checkbox
     *
     * SCB-specific behavior:
     * - User must confirm security image before entering password
     */
    public void confirmSecurityImage() {
        driver.findElement(
                By.xpath(LocatorReader.get("security.checkbox"))
        ).click();
    }

    /**
     * Enters password into password field
     *
     * @param password invalid password from Excel
     */
    public void enterPassword(String password) {
        driver.findElement(
                By.xpath(LocatorReader.get("password.field"))
        ).sendKeys(password);
    }

    /**
     * Clicks the Login button
     */
    public void clickLogin() {
        driver.findElement(
                By.xpath(LocatorReader.get("login.button"))
        ).click();
    }

    /**
     * Reads the error message displayed after invalid login
     *
     * @return error message text shown on UI
     */
    public String getErrorMessage() {
        return driver.findElement(
                By.xpath(LocatorReader.get("error.message"))
        ).getText();
    }
}
