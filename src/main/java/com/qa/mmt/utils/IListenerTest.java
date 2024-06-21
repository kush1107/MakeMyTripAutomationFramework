package com.qa.mmt.utils;

import com.qa.mmt.base.BaseInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;

public class IListenerTest implements ITestListener, ISuiteListener {
    private static final Logger log = LogManager.getLogger(IListenerTest.class.getName());
    private ExtentReportManager extentReportManager;
    public static String testName;
    public static String suiteName;

    @Override
    public void onStart(ISuite suite) {
        extentReportManager = ExtentReportManager.getInstance();
        suiteName = suite.getName();
        log.info(suiteName + " suite started");
    }

    @Override
    public void onFinish(ISuite suite) {
        extentReportManager.endReport();
        log.info(suiteName + " suite finished");
        log.info("===========================================================================");
    }

    @Override
    public void onStart(ITestContext context) {
        log.info(context.getSuite().getName() + " context started");
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info(context.getSuite().getName() + " context finished");
        log.info("===========================================================================");
    }

    @Override
    public void onTestStart(ITestResult result) {
        extentReportManager.createLogger(result);
        testName = returnModifiedTestName(result);
        log.info(testName + " test started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            extentReportManager.logResult(result);
        } catch (Exception e) {
            log.error("Error logging test success result", e);
        }
        log.info(testName + " test ended");
        cleanupDriver();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            extentReportManager.logResult(result);
        } catch (Exception e) {
            log.error("Error logging test failure result", e);
        }
        log.error("Exception occurred while testing " + testName, result.getThrowable());
        log.info("===========================================================================");
        cleanupDriver();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        try {
            extentReportManager.logResult(result);
        } catch (Exception e) {
            log.error("Error logging test skipped result", e);
        }
        log.info(testName + " test skipped");
        cleanupDriver();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Optional: Implement if needed
    }

    private void cleanupDriver() {
        if (BaseInitializer.driver != null) {
            BaseInitializer.driver.quit();
            BaseInitializer.driver = null;
        }
    }

    private String returnModifiedTestName(ITestResult result) {
        String[] arr = result.getInstanceName().split("\\.");
        if (arr.length > 7) {
            String testName = arr[arr.length - 7];
            arr = testName.split("_");
            return arr[0];
        }
        return result.getMethod().getMethodName(); // fallback to method name
    }
}
