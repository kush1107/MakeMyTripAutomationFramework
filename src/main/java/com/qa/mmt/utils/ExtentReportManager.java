package com.qa.mmt.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.mmt.base.BaseInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class ExtentReportManager extends BaseInitializer {
    private static final Logger log = LogManager.getLogger(ExtentReportManager.class);
    private static ExtentReportManager instance;
    private static ExtentReports extent;
    private static ExtentTest test;
    private static String updatedReportFileName;
    public static String extendReportPath = setAutomationFilesPath() + "MMTAutomationFiles/Results/ExecutionTestResults/ExtentReports/";
    private static final String currentDate = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
    private static final String dateName = new SimpleDateFormat("-MM.dd.yyyy-HH.mm.ss").format(new Date());
    public static String testReportFile;
    public static String testReportFileName;

    private ExtentReportManager() {
        startExtentReport();
    }

    public static synchronized ExtentReportManager getInstance() {
        if (instance == null) {
            instance = new ExtentReportManager();
        }
        return instance;
    }

    private static void startExtentReport() {
        SimpleDateFormat dtf = new SimpleDateFormat("MM/dd/yyyy");
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(extendReportPath + "/" + currentDate + "/MMTExtentReport" + dateName + ".html");

        testReportFile = extendReportPath + "/" + currentDate + "/MMTExtentReport" + dateName + ".html";
        testReportFileName = "MMTExtentReport" + dateName + ".html";

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Username", System.getProperty("user.name"));

        htmlReporter.config().setDocumentTitle("MMT Functional Regression Tests");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setEncoding("UTF-8");
        LocalDateTime now = LocalDateTime.now();
        htmlReporter.config().setTimeStampFormat(dtf.format(Date.from(now.atZone(ZoneId.systemDefault()).toInstant())) + " HH:mm:ss");
    }

    public void createLogger(ITestResult result) {
        String testName = extractTestMethodName(result);
        test = extent.createTest(testName);
    }


    private String extractTestName(ITestResult result) {
        String[] arr = result.getInstanceName().split("\\.");
        return arr[arr.length - 1].split("_")[0];
    }

    public void logResult(ITestResult result) {
        try {
            Status status;
            String colorLabel;

            switch (result.getStatus()) {
                case ITestResult.FAILURE:
                    status = Status.FAIL;
                    colorLabel = "RED";
                    try {
                        ExcelUtils ex = new ExcelUtils();
                        ex.updateTestStatusForPassFail(module, "Failed");
                    } catch (Exception e) {
                        log.error("Test Case Status is not set to Failed in Excel");
                    }
                    logThrowable(result, status);
                    break;
                case ITestResult.SKIP:
                    status = Status.SKIP;
                    colorLabel = "ORANGE";
                    try {
                        ExcelUtils ex = new ExcelUtils();
                        ex.updateTestStatusForPassFail(module, "Skipped");
                    } catch (Exception e) {
                        log.error("Test Case Status is not set to Failed in Excel");
                    }
                    break;
                case ITestResult.SUCCESS:
                    status = Status.PASS;
                    colorLabel = "GREEN";
                    try {
                        ExcelUtils ex = new ExcelUtils();
                        ex.updateTestStatusForPassFail(module, "Passed");
                    } catch (Exception e) {
                        log.error("Test Case Status is not set to Failed in Excel");
                    }
                    break;
                default:
                    return;
            }

            test.log(status, MarkupHelper.createLabel(currentTestCaseName + " - Test Case " + status, ExtentColor.valueOf(colorLabel)));
            attachScreenshot(status.name());
        } catch (Exception e) {
            log.error("Error logging test result", e);
        }
    }

    private void logThrowable(ITestResult result, Status status) {
        test.log(status, String.valueOf(result.getThrowable()));
        try {
            String screenshot = CaptureScreenshot.getBase64Screenshot();
            test.log(status, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
            log.info("Screenshot captured for failure.");
        } catch (Exception e) {
            log.error("Error capturing screenshot", e);
        }
    }


    public void logSuccessMessage(String message) {
        test.log(Status.PASS, MarkupHelper.createLabel(message, ExtentColor.GREEN));
    }

    public void logFailMessage(String message) {
        test.log(Status.FAIL, MarkupHelper.createLabel(message, ExtentColor.RED));
    }


    public void createNodeForReports(String category, String node) {
        test.assignCategory(category);
        test = test.createNode("Verifying " + node);
        log.info("Created node for: " + node);
    }

    public void attachScreenshot(String status) {
        try {
            String screenshot = CaptureScreenshot.getBase64Screenshot();
            switch (status.toUpperCase()) {
                case "PASS":
                    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
                    break;
                case "FAIL":
                    test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
                    break;
                default:
                    test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
                    break;
            }
        } catch (Exception e) {
            log.error("Error attaching screenshot", e);
        }
    }



    public void endReport() {
        extent.flush();
    }

    private String extractTestMethodName(ITestResult result) {
        return result.getMethod().getMethodName();
    }
}
