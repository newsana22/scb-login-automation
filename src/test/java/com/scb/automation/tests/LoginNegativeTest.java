package com.scb.automation.tests;

import com.scb.automation.pages.LoginPage;
import com.scb.automation.utils.ConfigReader;
import com.scb.automation.utils.ExcelUtils;
import com.scb.automation.utils.DriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

/**
 * ============================================================================
 * Test Class: LoginNegativeTest
 * ----------------------------------------------------------------------------
 * Purpose:
 * 1. Validate negative login scenarios for SCB application
 * 2. Read test data from Excel (DataProvider)
 * 3. Read environment configuration from config.properties
 * 4. Support both:
 *    - Local execution
 *    - Azure DevOps CI pipeline (Headless Chrome)
 *
 * Reporting & Failure Handling:
 * - Extent HTML report generation
 * - Screenshots automatically captured on failure
 * - Reports & screenshots published as pipeline artifacts
 * ============================================================================
 */
public class LoginNegativeTest {

    protected WebDriver driver;
    protected LoginPage loginPage;

    /**
     * ============================================================================
     * @BeforeMethod
     * ----------------------------------------------------------------------------
     * Runs BEFORE each test method
     *
     * Responsibilities:
     * 1. Setup correct ChromeDriver using WebDriverManager
     * 2. Configure ChromeOptions for CI/CD (headless execution)
     * 3. Launch browser
     * 4. Store driver in DriverFactory (IMPORTANT for screenshots)
     * 5. Navigate to application URL
     * 6. Initialize Page Object
     * ============================================================================
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        // Step 1: Automatically resolve correct ChromeDriver version
        WebDriverManager.chromedriver().setup();

        // Step 2: Configure ChromeOptions (mandatory for Azure pipelines)
        ChromeOptions options = new ChromeOptions();

        // Headless execution (no browser UI)
        options.addArguments("--headless=new");

        // Stability flags for CI agents
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        // Fixed resolution so UI elements render correctly
        options.addArguments("--window-size=1920,1080");

        // Step 3: Launch Chrome browser
        driver = new ChromeDriver(options);

        // Step 4: Store driver in DriverFactory
        // This allows TestNG Listener to access driver for screenshots
        DriverFactory.setDriver(driver);

        // Step 5: Apply implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Step 6: Navigate to application URL from config.properties
        driver.get(ConfigReader.get("app.url"));

        // Step 7: Initialize Login Page Object
        loginPage = new LoginPage(driver);
    }

    /**
     * ============================================================================
     * Test Case: verifyInvalidLoginErrorMessage
     * ----------------------------------------------------------------------------
     * Description:
     * - Attempts login using invalid credentials
     * - Verifies appropriate error message is displayed
     *
     * Test Data Source:
     * - Excel file: src/test/resources/testdata/LoginData.xlsx
     *
     * Validation:
     * - Error message must contain "invalid Username or Password"
     * ============================================================================
     */
    @Test(dataProvider = "loginData")
    public void verifyInvalidLoginErrorMessage(String username, String password) {

        // Step 1: Enter username
        loginPage.enterUsername(username);

        // Step 2: Click Next
        loginPage.clickNext();

        // Step 3: Confirm security image (SCB-specific flow)
        loginPage.confirmSecurityImage();

        // Step 4: Enter invalid password
        loginPage.enterPassword(password);

        // Step 5: Click Login
        loginPage.clickLogin();

        // Step 6: Validate error message
        Assert.assertTrue(
                loginPage.getErrorMessage()
                        .contains("invalid Username or Password"),
                "Invalid login error message was NOT displayed"
        );
    }

    /**
     * ============================================================================
     * @DataProvider
     * ----------------------------------------------------------------------------
     * Purpose:
     * - Supplies login credentials to test method
     * - Reads data from Excel using ExcelUtils
     *
     * Returns:
     * - Object[][] containing username & password combinations
     * ============================================================================
     */
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return ExcelUtils.getLoginData();
    }

    /**
     * ============================================================================
     * @AfterMethod
     * ----------------------------------------------------------------------------
     * Runs AFTER each test method
     *
     * Responsibilities:
     * 1. Quit browser
     * 2. Remove driver from ThreadLocal storage
     *
     * NOTE:
     * - Screenshot capture is handled by TestNG Listener
     * ============================================================================
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }

        // Remove driver from ThreadLocal to avoid memory leaks
        DriverFactory.unload();
    }
}
