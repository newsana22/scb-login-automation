package com.scb.automation.utils;

import com.aventstack.extentreports.*;
import org.testng.*;

/**
 * ============================================================
 * TestNG Listener: TestListener
 * ------------------------------------------------------------
 * Responsibilities:
 * - Create Extent test
 * - Capture screenshots on:
 *   ✔ PASS
 *   ✔ FAIL
 * - Flush report after suite
 * ============================================================
 */
public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getExtent();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    /**
     * Expose ExtentTest for step-level logging
     */
    public static ExtentTest getTest() {
        return test.get();
    }

    /**
     * Before each test
     */
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest =
                extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    /**
     * On test success
     */
    @Override
    public void onTestSuccess(ITestResult result) {

        test.get().pass("Test Passed Successfully");

        String base64 = ScreenshotUtil.captureBase64();
        if (base64 != null) {
            test.get().addScreenCaptureFromBase64String(
                    base64,
                    "Final state after successful execution"
            );
        }
    }

    /**
     * On test failure
     */
    @Override
    public void onTestFailure(ITestResult result) {

        test.get().fail(result.getThrowable());

        String base64 = ScreenshotUtil.captureBase64();
        if (base64 != null) {
            test.get().addScreenCaptureFromBase64String(
                    base64,
                    "Failure Screenshot"
            );
        }
    }

    /**
     * On test skipped
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test Skipped");
    }

    /**
     * After suite execution
     */
    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
