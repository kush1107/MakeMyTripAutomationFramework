package com.qa.luma.customer.CustomerRegistration;

import com.qa.luma.LumaBaseTest;
import com.qa.luma.customer.CustomerBaseTest;
import com.qa.luma.data.excelData.CustomerData;
import com.qa.luma.data.excelData.LoginUserData;
import com.qa.luma.utils.ExtentReportManager;
import com.qa.luma.utils.IListenerTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(IListenerTest.class)
public class CustomerRegistrationTest extends LumaBaseTest {
    private static final ExtentReportManager extentReport = ExtentReportManager.getInstance();
    private CustomerBaseTest cbt = new CustomerBaseTest();
    private CustomerData cRecord;
    private LoginUserData lRecord;
    private int i;



    @BeforeTest
    private void initializeTest()
    {
        currentTestCaseName = "CustomerRegistrationTest";
        setUpDataFromExcel();
        cRecord = (CustomerData) cData.get(i);
        lRecord = (LoginUserData) lData.get(i);
        logMessage("Test Data loaded successfully...");

    }

    @Test(description = "Verify Customer Registration Flow")
    public void verifyCreateCustomerRegistrationTest()
    {
        extentReport.createNodeForReports("Customer Registration", "CreateCustomerRegistrationTest");
        launchApplicationURL(url);
        logAndReportWithScreenshot("Navigating to Registration Customer page...");
        cbt.clickCreateAccountLink();
        if (cbt.verifyCreateCustomerTitle()) {
            cbt.setFirstName(cRecord.getFirstName());
            cbt.setLastName(cRecord.getLastName());
            cbt.setEmail(cRecord.getEmailAddress());
            cbt.setRegistrationPassword(cRecord.getPassword());
            cbt.setRegistrationConfirmPassword(cRecord.getConfirmationPassword());
            cbt.clickCreateAccountButton();
        }
         validateAnyMessageWithScreenshots("Thank you for registering with Main Website Store");
        if (cbt.checkWelcomeUserProfile()) {
            logAndReport("Customer Registration done successfully...");
        }
    }
}
