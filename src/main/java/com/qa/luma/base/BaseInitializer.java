package com.qa.luma.base;

import com.qa.luma.data.excelData.CustomerData;
import com.qa.luma.pages.commonpages.LoginPage;
import com.qa.luma.utils.CaptureScreenshot;
import com.qa.luma.utils.ExtentReportManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static org.testng.FileAssert.fail;

public class BaseInitializer {
    public static final Logger log = LogManager.getLogger(BaseInitializer.class.getName());
    public static ExtentReportManager reports = ExtentReportManager.getInstance();
    // added stepName to use in log result

    public static Properties prop = null;
    public static Properties commonElements = null;
    public static WebDriver driver = null;
    public static String automationFilesPath;

    //Set Properties
    public static String browser,url,username,password,environment,module;


    public static long IMPLICIT_WAIT = 5;
    public static long EXPLICIT_WAIT = 60;
    public static String pageName;
    public static String currentTestCaseName;


    public static List<?> dataIterator;
    public static List<?> cData,lData,pData;

    public static String fullPageScreenshotFlag,wordFilePageScreenshotFlag;
    public static Path downloadFilepath,fileUpload;
    private static final String currentDate = new SimpleDateFormat("MM-dd-yyyy").format(new Date());



    public static String LUMA_TestSpecificRecordData;

    /**
     * This method used to set automationFiles path and which is set by environmental variables.
     */
    public static String setAutomationFilesPath() {
        automationFilesPath = System.getenv("AUTOMATION_FILES_PATH") + File.separator;
        return automationFilesPath;
    }

    /**
     * This method sets Environment for automation testing.
     */
    public String setEnvironment() {
        environment = getProperty("ENVIRONMENT");
        if (environment == null || environment.isEmpty()) {
            logAndReportError("Config Properties file : Please set environment for the test..");
        }
        return environment;
    }

    /**
     * This method sets Excel sheet path for automation testing.
     */
    public void setExcelPaths() {
        LUMA_TestSpecificRecordData = setAutomationFilesPath() + prop.getProperty("LUMATestSpecificRecordData");
    }

    /**
     * This method sets Browser for automation testing.
     */
    public String setBrowser() {
        browser = getProperty("BROWSER");
        if (browser == null || browser.isEmpty()) {
            logAndReportError("Config Properties file : Please set browser for the test..");
        }
        return browser;
    }

    /**
     * This method sets URL for automation testing.
     */
    public String setUrl() {
        url = getProperty("URL");
        if (url == null || url.isEmpty()) {
            logAndReportError("Config Properties file : Please set browser for the test..");
        }
        return url;
    }
    /**
     * This method sets Module for automation testing.
     */
    public String setModule() {
        module = getProperty("MODULE");
        if (module == null || module.isEmpty()) {
            logAndReportError("Config Properties file : Please set browser for the test..");
        }
        return module;
    }

    /**
     * This method sets Username for credentials.
     */
    public String setUsername() {
         username = getProperty("USERNAME");
        if (username == null || username.isEmpty()) {
            logAndReportError("Config Properties file : Please set username for the test..");
        }
        return username;
    }

    /**
     * This method sets Password for credentials.
     */
    public String setPassword() {
         password = getProperty("PASSWORD");
        if (password == null || password.isEmpty()) {
            logAndReportError("Config Properties file : Please set password for the test..");
        }
        return password;
    }


    /**
     * This method used to take full page screenshot based on flag value.
     */
    public void setFullPageScreenShotFlag() {
        fullPageScreenshotFlag = getProperty("FULLPAGE_SCREENSHOT_FLAG");
        if (fullPageScreenshotFlag == null || fullPageScreenshotFlag.isEmpty()) {
            fullPageScreenshotFlag = prop.getProperty("positiveFlowScrnShotsFlag");
        }
        if(fullPageScreenshotFlag.equalsIgnoreCase("Y")) {
            CaptureScreenshot.TAKE_SCREENSHOT=true;
        } else {
            CaptureScreenshot.TAKE_SCREENSHOT=false;
        }
    }

    public void setAttachScreenShotToWordFileFlag() {
        wordFilePageScreenshotFlag = System.getProperty("WORDFILE_SCREENSHOT_FLAG");
        if (wordFilePageScreenshotFlag == null || wordFilePageScreenshotFlag.isEmpty()) {
            wordFilePageScreenshotFlag = prop.getProperty("WORDFILE_SCREENSHOT_FLAG");
        }
        if(wordFilePageScreenshotFlag.equalsIgnoreCase("Y")) {
            CaptureScreenshot.TAKE_SCREENSHOT_WORDFILE=true;
        } else {
            CaptureScreenshot.TAKE_SCREENSHOT_WORDFILE=false;
        }
    }

    private void loadPropertyFile(Properties properties, String filePath) {
        try (FileInputStream file = new FileInputStream(filePath)) {
            properties.load(file);
        } catch (IOException e) {
            log.info("Error occurred while reading property file at " + filePath + ": " + e.getMessage());
        }
    }

    public void readPropertyAndSetup() {
        prop = new Properties();
        loadPropertyFile(prop, setAutomationFilesPath() + "LumaAutomationFiles\\Resources\\properties\\config.properties");

        commonElements = new Properties();
        loadPropertyFile(commonElements, ".\\src\\test\\resources\\properties\\WebElements.properties");

        try {
            setExcelPaths();
        } catch (Exception e) {
            logAndReportError("Error loading excel file path properties..");
        }
        url = setUrl();
        module = setModule();
        username = setUsername();
        password = setPassword();
        browser = setBrowser().toLowerCase();

    }


    public void setChromeBrowser() {
        downloadFilepath = Paths.get(setAutomationFilesPath() +"AutomationFiles" + File.separator + "Results" + File.separator
                + "downloadFiles" + File.separator + currentDate + File.separator);
        try {
            Files.createDirectories(downloadFilepath);
        } catch (IOException e) {
            logAndReportError("Failed to create directory!" + e.getMessage());
        }
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadFilepath.toString());
        // PDF download
        prefs.put("plugins.plugins_disabled", new String[] {"Chrome PDF Viewer"});
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("plugins.always_open_pdf_externally", true);
        ChromeOptions opt = new ChromeOptions();
        opt.setExperimentalOption("prefs", prefs);
        if (Boolean.parseBoolean(prop.getProperty("WINDOW_SIZE_FLAG").toLowerCase())) {
            opt.addArguments("--start-maximized");
        } else {
            opt.addArguments("--window-size=1920,1080");
        }
        if (Boolean.parseBoolean(prop.getProperty("HEADLESS_FLAG").toLowerCase())) {
            opt.addArguments("--headless=new");
        }
        opt.setAcceptInsecureCerts(true);
        opt.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(opt);
    }

    public void setFirefoxBrowser() {
        downloadFilepath = Paths.get(setAutomationFilesPath() +"LumaAutomationFiles" + File.separator + "Results" + File.separator
                + "feeHandLogFiles" + File.separator + File.separator + currentDate + File.separator +
                Reporter.getCurrentTestResult().getTestClass().getRealClass().getSimpleName() + File.separator);
        try {
            Files.createDirectories(downloadFilepath);
        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
        }
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.dir", downloadFilepath.toString());
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;application/pdf;application/octet-stream;");
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("pdfjs.disabled", true);

        // PDF download
        profile.setPreference("plugin.scan.plid.all",false);
        profile.setPreference("plugin.scan.Acrobat","99.0");

        FirefoxOptions option = new FirefoxOptions();
        option.setProfile(profile);
        if (Boolean.parseBoolean(prop.getProperty("HEADLESSBROWSERFLAG").toLowerCase())) {
            option.addArguments("--headless=new");
        }
        option.addArguments("--window-size=1920,1080");
        driver = new FirefoxDriver(option);
    }

    protected void launchBrowserAndOpenApplication() {
        switch (browser) {
            case "chrome":
                setChromeBrowser();
                break;
            case "firefox":
                setFirefoxBrowser();
                break;
            case "ie":
                driver = new InternetExplorerDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                log.info("browser is invalid" + browser);
                fail("browser is invalid" + browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(EXPLICIT_WAIT));

    }

    public void launchApplicationURL(String url)
    {
        logAndReport("Launching " + browser + " browser..");
        driver.manage().deleteAllCookies();
        driver.get(url);
        logAndReport("Automation Scripts execution started on URL : " + url);
        PageWebElementActions pwa = new PageWebElementActions(driver);
        pwa.byXpathAndExplictWaitOnElementVisibility(commonElements.getProperty("SIGN_IN_LINK"));
        logAndReportWithScreenshot("Application Launched successfully...");
    }



    public static String getProperty(String propValue) {
        return prop.getProperty(propValue);
    }

    public void logMessage(String message) {
        log.info(message);
    }

    public void logAndReport(String message) {
        log.info(message);
        reports.logSuccessMessage(message);
    }

    public void logAndReportWithScreenshot(String message) {
        log.info(message);
        reports.logSuccessMessage(message);
        reports.attachScreenshot("PASS");
    }

    public void logAndReportWarning(String message) {
        log.info(message);
        reports.logPartiallyPassMessage(message);
    }

    public void logAndReportWarningWithScreenshot(String message) {
        log.info(message);
        reports.logPartiallyPassMessage(message);
        reports.attachScreenshot("WARNING");
    }

    public void logAndReportError(String message) {
        log.error(message);
        reports.logFailMessage(message);
    }

    public void logAndReportErrorWithScreenshot(String message) {
        log.error(message);
        reports.logFailMessage(message);
        reports.attachScreenshot("FAIL");
    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void verifyElementPresent(WebElement ele) {
        new WebDriverWait(driver,Duration.ofSeconds(EXPLICIT_WAIT)).until(ExpectedConditions.visibilityOf(ele));
    }

    public void takeScreenShot(String path){
        sleep(2000);
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(path));
            File rename = new File(path);
            boolean flag = scrFile.renameTo(rename);
            if (flag) {
                System.out.println("File Successfully Rename");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Screenshot taken successfully");
    }


    /**
     * This method is used to login to the already opened Single Sign On application by passing the credentials as parameters.
     */
    public void loginToApp(String username, String password) {
        CaptureScreenshot.FullPageScreenshot("SignOnPage");
        LoginPage login = new LoginPage(driver);
        login.clickSignInLink();
        login.setUserID(username);
        login.setPassword(password);
        login.clickSignButton();
        if (login.verifyWelcomeUser()) {
            logAndReportWithScreenshot("Login into the Application successfully.");
        }
        sleep(1000);
    }

    @BeforeSuite
    public void setUpApplication() {
        readPropertyAndSetup();
        launchBrowserAndOpenApplication();
    }



}
