package com.scb.automation.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.*;

/**
 * ============================================================
 * TestNG Listener: TestListener
 * ------------------------------------------------------------
 * Responsibilities:
 * - Initialize Extent HTML report
 * - Log test PASS / FAIL / SKIP
 * - Attach screenshots for:
 *   ✔ Success
 *   ✔ Failure
 * - Flush report after execution
 * ============================================================
 */
public class TestListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    /**
     * Runs once before test suite starts
     */
    @Override
    public void onStart(ITestContext context) {

        ExtentSparkReporter reporter =
                new ExtentSparkReporter("reports/ExtentReport.html");

        reporter.config().setReportName("SCB Automation Test Report");
        reporter.config().setDocumentTitle("SCB Login Automation");

        extent = new ExtentReports();
        extent.attachReporter(reporter);

        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Executed By", "Sana Ansari");
    }

    /**
     * Runs before each test method
     */
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest =
                extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    /**
     * Runs when test PASSES
     */
    @Override
    public void onTestSuccess(ITestResult result) {

        // Capture Base64 screenshot
        String base64Screenshot =
                ScreenshotUtil.captureScreenshotBase64();

        // Attach screenshot to Extent report
        test.get().pass("Test Passed Successfully");

        if (base64Screenshot != null) {
            test.get().addScreenCaptureFromBase64String(
                    base64Screenshot,
                    "Final state after successful execution"
            );
        }
    }

    /**
     * Runs when test FAILS
     */
    @Override
    public void onTestFailure(ITestResult result) {

        // Log failure reason
        test.get().fail(result.getThrowable());

        // Capture Base64 screenshot
        String base64Screenshot =
                ScreenshotUtil.captureScreenshotBase64();

        // Attach screenshot to Extent report
        if (base64Screenshot != null) {
            test.get().addScreenCaptureFromBase64String(
                    base64Screenshot,
                    "Failure Screenshot"
            );
        }
    }

    /**
     * Runs when test is SKIPPED
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test Skipped");
    }

    /**
     * Runs once after suite execution
     */
    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
