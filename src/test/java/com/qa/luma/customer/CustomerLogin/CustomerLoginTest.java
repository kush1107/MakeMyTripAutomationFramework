package com.qa.luma.customer.CustomerLogin;

import com.qa.luma.LumaBaseTest;
import com.qa.luma.customer.CustomerBaseTest;
import com.qa.luma.data.excelData.CustomerData;
import com.qa.luma.data.excelData.LoginUserData;
import com.qa.luma.utils.ExtentReportManager;
import com.qa.luma.utils.IListenerTest;
import org.testng.annotations.*;

@Listeners(IListenerTest.class)
public class CustomerLoginTest extends LumaBaseTest {
    private static final ExtentReportManager extentReport = ExtentReportManager.getInstance();
    private CustomerBaseTest cbt = new CustomerBaseTest();
    private CustomerData cRecord;
    private LoginUserData lRecord;
    private int i;
    private static String invalidUsername,invalidPassword;



    @BeforeClass
    private void initializeTest()
    {
        currentTestCaseName = "CustomerLoginTest";

    }

    @AfterClass
    private void setTestCaseName()
    {
        IListenerTest.testName = "CustomerLoginTests";
    }

    @Test(description = "Verify login with valid credentials",priority = 1)
    public void LoginWithValidCredentialsTest()
    {
        extentReport.createNodeForReports("Customer Login", "LoginWithValidCredentialsTest");
        launchApplicationURL(url);
        logAndReportWithScreenshot("Navigating to login page...");
        cbt.clickSignInLink();
        cbt.setValidCredentials(username,password);
        logAndReportWithScreenshot("Entered valid credentials.");
        cbt.clickSignInButton();
        cbt.verifyWelcomeUserProfile();

    }

    @Test(description = "Verify login with both invalid credentials",priority = 2)
    public void LoginWithInValidCredentialsTest()
    {
        invalidUsername ="Automation@gmail.com";
        invalidPassword = "Test@1107";

        extentReport.createNodeForReports("Customer Login", "LoginWithInValidCredentialsTest");
        launchApplicationURL(url);
        logAndReportWithScreenshot("Navigating to login page...");
        cbt.clickSignInLink();
        cbt.setValidCredentials(invalidUsername,invalidPassword);
        cbt.clickSignInButton();
        validateAnyMessageWithScreenshots("The account sign-in was incorrect or your account is disabled temporarily");

    }

    @Test(description = "Verify login with invalid email and valid password",priority = 3)
    public void LoginWithInValidEmailAndValidPasswordTest()
    {
        invalidUsername ="Automation@gmail.com";

        extentReport.createNodeForReports("Customer Login", "LoginWithInValidEmailAndValidPasswordTest");
        launchApplicationURL(url);
        logAndReportWithScreenshot("Navigating to login page...");
        cbt.clickSignInLink();
        cbt.setValidCredentials(invalidUsername,password);
        cbt.clickSignInButton();
        validateAnyMessageWithScreenshots("The account sign-in was incorrect or your account is disabled temporarily");

    }

    @Test(description = "Verify login with invalid email and valid password",priority = 4)
    public void LoginWithValidEmailAndInValidPasswordTest()
    {
        invalidPassword ="Test@1107";

        extentReport.createNodeForReports("Customer Login", "LoginWithValidEmailAndInValidPasswordTest");
        launchApplicationURL(url);
        logAndReportWithScreenshot("Navigating to login page...");
        cbt.clickSignInLink();
        cbt.setValidCredentials(invalidUsername,password);
        cbt.clickSignInButton();
        validateAnyMessageWithScreenshots("The account sign-in was incorrect or your account is disabled temporarily");

    }



}
