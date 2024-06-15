package com.qa.luma.utils;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import com.qa.luma.base.BaseInitializer;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class CaptureScreenshot extends BaseInitializer {

    private static final Logger log = LogManager.getLogger(CaptureScreenshot.class.getName());
    private static final String DATE_FORMAT = "-MMddyyyy-HHmmss";
    private static final String SCREENSHOT_FOLDER = setAutomationFilesPath() + "AutomationFiles\\Results\\ExecutionTestResults\\";
    private static final String FAILED_SCREENSHOT_PATH = SCREENSHOT_FOLDER + "FailedScreenShots\\";
    private static final String FULL_SCREENSHOT_PATH = SCREENSHOT_FOLDER + "FullScreenShots\\";
    private static final String PDF_SCREENSHOT_PATH = SCREENSHOT_FOLDER + "PdfScreenshots\\";

    public static boolean TAKE_SCREENSHOT, TAKE_SCREENSHOT_WORDFILE;

    private static String getCurrentDate() {
        return new SimpleDateFormat("MM-dd-yyyy").format(new Date());
    }

    private static String getTestClassName() {
        String testClassName = Reporter.getCurrentTestResult().getTestClass().getRealClass().getSimpleName();
        String[] arr = testClassName.split("_");
        return arr[0];
    }

    private static void captureScreenshotInternal(String path, String fileName, String pageName) {
        try {
            String testClassName = getTestClassName();
            String screenshotPath = path + "\\" + getCurrentDate() + "\\" + testClassName;
            Shutterbug.shootPage(BaseInitializer.driver, ScrollStrategy.WHOLE_PAGE, 500, true)
                    .withName(fileName).save(screenshotPath);
            Thread.sleep(1000);
            if (TAKE_SCREENSHOT_WORDFILE) {
                AddScreenshotsToWord.appendPictureToWord(testClassName + "-" + fileName, screenshotPath + "\\" + pageName + ".png", pageName);
            }
        } catch (Exception e) {
            log.error("Exception occurred while taking screenshot: " + e.getMessage());
        }
    }

    public static String captureFailedScreenshot(ITestResult result) {
        try {
            String screenshotPath = FAILED_SCREENSHOT_PATH + getTestClassName() + "\\";
            captureScreenshotInternal(screenshotPath, result.getName() + DATE_FORMAT, result.getName());
            return screenshotPath + result.getName() + DATE_FORMAT + ".png";
        } catch (Exception e) {
            log.error("Exception occurred while taking failed screenshot: " + e.getMessage());
        }
        return "";
    }

    public static void FullPageScreenshot(String pageName) {
        try {
            if (TAKE_SCREENSHOT) {
                String screenshotPath = FULL_SCREENSHOT_PATH + getTestClassName();
                captureScreenshotInternal(screenshotPath, pageName, pageName);
            }
        } catch (Exception e) {
            log.error("Exception occurred while taking full screenshot: " + e.getMessage());
        }
    }

    public void TakeFullScreenshots() {
        try {
            String testMethodName = Reporter.getCurrentTestResult().getMethod().getMethodName();
            String screenshotPath = PDF_SCREENSHOT_PATH + getCurrentDate() + "\\"
                    + Reporter.getCurrentTestResult().getTestClass().getRealClass().getSimpleName() + "\\";
            Shutterbug.shootPage(BaseInitializer.driver, ScrollStrategy.WHOLE_PAGE, 500, true)
                    .withName(testMethodName + DATE_FORMAT).save(screenshotPath);
            Thread.sleep(1000);
        } catch (Exception e) {
            log.error("Exception occurred while taking full screenshot: " + e.getMessage());
        }
    }

    public static String getBase64Screenshot() {
        try {
            return "data:image/png;base64," + ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            log.error("Exception occurred while attaching getBase64Screenshot to report: " + e.getMessage());
            return "";
        }
    }

    public static String ScreenCaptureAsBase64String() {
        try {
            File src = ((TakesScreenshot) BaseInitializer.driver).getScreenshotAs(OutputType.FILE);
            byte[] fileContent = FileUtils.readFileToByteArray(src);
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            log.error("Exception occurred while taking ScreenCaptureAsBase64String: " + e.getMessage());
            return "";
        }
    }
}
