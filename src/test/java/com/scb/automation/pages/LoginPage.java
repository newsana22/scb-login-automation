package com.scb.automation.pages;

import com.scb.automation.utils.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for SCB Login Page
 * All locators are externalized in locators.properties
 */
public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // ---------------- Page Actions ----------------

    public void enterUsername(String username) {
        driver.findElement(
                By.xpath(LocatorReader.get("username.field"))
        ).sendKeys(username);
    }

    public void clickNext() {
        driver.findElement(
                By.xpath(LocatorReader.get("next.button"))
        ).click();
    }

    public void confirmSecurityImage() {
        driver.findElement(
                By.xpath(LocatorReader.get("security.checkbox"))
        ).click();
    }

    public void enterPassword(String password) {
        driver.findElement(
                By.xpath(LocatorReader.get("password.field"))
        ).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(
                By.xpath(LocatorReader.get("login.button"))
        ).click();
    }

    public String getErrorMessage() {
        return driver.findElement(
                By.xpath(LocatorReader.get("error.message"))
        ).getText();
    }
}