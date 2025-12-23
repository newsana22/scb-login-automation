package com.scb.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * ============================================================
 * Class: ExtentManager
 * ------------------------------------------------------------
 * Purpose:
 * - Initialize ExtentReports only once
 * - Avoid duplicate report creation
 * ============================================================
 */
public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtent() {

        if (extent == null) {

            ExtentSparkReporter reporter =
                    new ExtentSparkReporter("reports/ExtentReport.html");

            reporter.config().setReportName("SCB Automation Test Report");
            reporter.config().setDocumentTitle("SCB Login Automation");

            extent = new ExtentReports();
            extent.attachReporter(reporter);

            extent.setSystemInfo("Project", "SCB Login Automation");
            extent.setSystemInfo("Executed By", "Sana Ansari");
            extent.setSystemInfo("Execution Mode", "UI / Headless / CI");
        }

        return extent;
    }
}
