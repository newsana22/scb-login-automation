package com.scb.automation.tests;

import com.scb.automation.pages.LoginPage;
import com.scb.automation.utils.ConfigReader;
import com.scb.automation.utils.ExcelUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

/**
 * ------------------------------------------------------------
 * Test Class: LoginNegativeTest
 *
 * Purpose:
 * - Validates negative login scenarios for SCB application
 * - Uses Excel for test data
 * - Reads application URL from config.properties
 *
 * Designed to work:
 * - Locally (developer machine)
 * - CI/CD Pipeline (Azure DevOps – headless Chrome)
 * ------------------------------------------------------------
 */
public class LoginNegativeTest {

    private WebDriver driver;
    private LoginPage loginPage;

    /**
     * ------------------------------------------------------------
     * @BeforeMethod
     * Runs BEFORE every test method
     *
     * Responsibilities:
     * 1. Setup correct ChromeDriver version
     * 2. Configure Chrome for pipeline (headless)
     * 3. Launch browser
     * 4. Open application URL
     * ------------------------------------------------------------
     */
    @BeforeMethod
    public void setUp() {

        // 1️ Automatically download & setup correct ChromeDriver
        WebDriverManager.chromedriver().setup();

        // 2️ ChromeOptions are REQUIRED for CI pipelines
        ChromeOptions options = new ChromeOptions();

        // Headless mode → No UI required (mandatory for pipeline)
        options.addArguments("--headless=new");

        // Security & stability flags for CI agents
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        // Fixed resolution so UI elements render correctly
        options.addArguments("--window-size=1920,1080");

        // 3️ Create ChromeDriver with options
        driver = new ChromeDriver(options);

        // 4️ Apply implicit wait for element loading
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // 5️ Navigate to application URL from config.properties
        driver.get(ConfigReader.get("app.url"));

        // 6️ Initialize LoginPage (Page Object)
        loginPage = new LoginPage(driver);
    }

    /**
     * ------------------------------------------------------------
     * Test: verifyInvalidLoginErrorMessage
     *
     * Description:
     * - Attempts login with invalid credentials
     * - Verifies correct error message is displayed
     *
     * Data Source:
     * - Excel file: testdata/LoginData.xlsx
     * ------------------------------------------------------------
     */
    @Test(dataProvider = "loginData")
    public void verifyInvalidLoginErrorMessage(String username, String password) {

        // Enter username
        loginPage.enterUsername(username);

        // Click Next button
        loginPage.clickNext();

        // Confirm security image (SCB specific flow)
        loginPage.confirmSecurityImage();

        // Enter invalid password
        loginPage.enterPassword(password);

        // Click Login
        loginPage.clickLogin();

        // Validate error message for invalid login
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("invalid Username or Password"),
                "Invalid login error message was NOT displayed"
        );
    }

    /**
     * ------------------------------------------------------------
     * @DataProvider
     *
     * Purpose:
     * - Supplies login credentials to test method
     * - Reads data from Excel using ExcelUtils
     * ------------------------------------------------------------
     */
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return ExcelUtils.getLoginData();
    }

    /**
     * ------------------------------------------------------------
     * @AfterMethod
     * Runs AFTER every test method
     *
     * Responsibility:
     * - Cleanly close browser
     * - Free system resources
     * ------------------------------------------------------------
     */
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
