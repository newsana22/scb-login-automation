package com.scb.automation.tests;

import com.scb.automation.pages.LoginPage;
import com.scb.automation.utils.ConfigReader;
import com.scb.automation.utils.ExcelUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

/**
 * Negative login test for SCB
 * Credentials are read from Excel using DataProvider
 * URL is read from config.properties
 */
public class LoginNegativeTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // URL from config.properties
        driver.get(ConfigReader.get("app.url"));

        loginPage = new LoginPage(driver);
    }

    @Test(dataProvider = "loginData")
    public void verifyInvalidLoginErrorMessage(String username, String password) {

        loginPage.enterUsername(username);
        loginPage.clickNext();
        loginPage.confirmSecurityImage();
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        // Assertion for negative login
        Assert.assertTrue(
                loginPage.getErrorMessage()
                        .contains("invalid Username or Password"),
                "Invalid login error message was not displayed"
        );
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return ExcelUtils.getLoginData();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
