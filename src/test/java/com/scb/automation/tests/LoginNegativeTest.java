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

public class LoginNegativeTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {

        // Resolve correct ChromeDriver
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // REQUIRED for Azure DevOps / CI
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Open URL once
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

        Assert.assertTrue(
                loginPage.getErrorMessage().toLowerCase().contains("invalid"),
                "Invalid login error message was not displayed"
        );
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return ExcelUtils.getLoginData();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
