package com.scb.automation.tests;

import com.scb.automation.pages.LoginPage;
import com.scb.automation.utils.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

/**
 * ============================================================
 * Test Class: LoginNegativeTest
 * ------------------------------------------------------------
 * Validates invalid login scenario for SCB
 * ============================================================
 */
public class LoginNegativeTest {

    private WebDriver driver;
    private LoginPage loginPage;

    /**
     * Setup before each test
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Enable headless for CI, works in local UI too
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);

        // Store driver for screenshots
        DriverFactory.setDriver(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(ConfigReader.get("app.url"));

        loginPage = new LoginPage(driver);
    }

    /**
     * Test: Invalid login validation
     */
    @Test(dataProvider = "loginData")
    public void verifyInvalidLoginErrorMessage(String username, String password) {

        loginPage.enterUsername(username);
        ExtentLogger.pass("Entered username");

        loginPage.clickNext();
        ExtentLogger.pass("Clicked Next");

        loginPage.confirmSecurityImage();
        ExtentLogger.pass("Confirmed security image");

        loginPage.enterPassword(password);
        ExtentLogger.pass("Entered password");

        loginPage.clickLogin();
        ExtentLogger.pass("Clicked Login");

        Assert.assertTrue(
                loginPage.getErrorMessage()
                        .contains("invalid Username or Password"),
                "Expected error message not displayed"
        );
        ExtentLogger.pass("Invalid Login error message assertion is successful");

    }

    /**
     * Data provider
     */
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return ExcelUtils.getLoginData();
    }

    /**
     * Cleanup after test
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }

        DriverFactory.unload();
    }
}
