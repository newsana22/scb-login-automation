package com.scb.automation.utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

/**
 * ============================================================
 * Utility: ExtentLogger
 * ------------------------------------------------------------
 * Purpose:
 * - Step-level logging with screenshots
 * - Used inside test methods
 * ============================================================
 */
public class ExtentLogger {

    /**
     * Log PASS step with screenshot
     */
    public static void pass(String stepDescription) {

        String base64 = ScreenshotUtil.captureBase64();

        TestListener.getTest().log(
                Status.PASS,
                stepDescription,
                MediaEntityBuilder
                        .createScreenCaptureFromBase64String(base64)
                        .build()
        );
    }

    /**
     * Log FAIL step with screenshot
     */
    public static void fail(String stepDescription, Throwable t) {

        String base64 = ScreenshotUtil.captureBase64();

        TestListener.getTest().log(
                Status.FAIL,
                stepDescription,
                MediaEntityBuilder
                        .createScreenCaptureFromBase64String(base64)
                        .build()
        ).fail(t);
    }
}
