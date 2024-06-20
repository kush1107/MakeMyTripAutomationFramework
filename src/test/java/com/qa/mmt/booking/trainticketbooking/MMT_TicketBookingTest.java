package com.qa.mmt.booking.trainticketbooking;

import com.qa.mmt.MMTBaseTest;
import com.qa.mmt.booking.BookingBaseTest;
import com.qa.mmt.data.excelData.BookingData;
import com.qa.mmt.utils.ExtentReportManager;
import com.qa.mmt.utils.IListenerTest;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(IListenerTest.class)
public class MMT_TicketBookingTest extends MMTBaseTest {
    private static final ExtentReportManager extentReport = ExtentReportManager.getInstance();
    private BookingBaseTest bbt = new BookingBaseTest();
    private BookingData bRecord;
    private int i;


    @BeforeTest
    private void initializeTest() {
        currentTestCaseName = "TrainTicketBookingTest";
        setUpDataFromExcel();
        bRecord = (BookingData) bData.get(i);
        logMessage("Test Data loaded successfully...");

    }

    @Test(description = "Verify train ticket booking Flow")
    public void MMTTrainTicketBookingTest() {
        extentReport.createNodeForReports("Train Ticket Booking", "TrainTicketBookingTest");
        launchApplicationURL(url);
        logAndReportWithScreenshot("Navigating to Train Ticket Booking page...");
        bbt.clickTrainLink();
        if (bbt.verifyTrainBookingTitle()) {
            logAndReportWithScreenshot("Landed on Train Ticket Booking page page...");
            bbt.setFromPlace(bRecord.getFromPlace());
            bbt.setToPlace(bRecord.getToPlace());
            bbt. clickTravelDate();
            bbt.setTravelDate(bRecord.getTravelDateMonth(),bRecord.getTravelDateDay());
            bbt.selectTrainClass();
            logAndReportWithScreenshot("Entered train ticket booking details...");
            bbt.clickSearchButton();
            if(bbt.verifyFilterText())
            {
                logAndReportWithScreenshot("Landing on  train listing details...");
                bbt.getTrainList();
                bbt.selectTrain();
            }
            if(bbt.VerifyPay()){
                bbt.clickAddTraveller();
                bbt.fillAddTraveller("Test","24");
                logAndReportWithScreenshot("Adding Traveller successFully");
            }

            bbt.clickPayButtonAndValidateIRCTCErrorMsg();
            bbt.getErroMsgIRCTC();
            Assert.assertTrue( bbt.getErroMsgIRCTC().contains("Please enter IRCTC Username"));

            logAndReportWithScreenshot("Total Payment Summary..");
            bbt.getPaymentDetails();
            bbt.getTotalPayment();



        }


    }
}
