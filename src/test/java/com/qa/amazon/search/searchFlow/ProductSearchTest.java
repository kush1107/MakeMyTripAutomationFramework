package com.qa.amazon.search.searchFlow;

import com.qa.amazon.AmazonBaseTest;
import com.qa.amazon.search.SearchBaseTest;
import com.qa.mmt.base.PageWebElementActions;
import com.qa.mmt.utils.ExtentReportManager;
import com.qa.mmt.utils.IListenerTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.assertj.core.api.SoftAssertions;


@Listeners(IListenerTest.class)
public class ProductSearchTest extends AmazonBaseTest {

    private static final ExtentReportManager extentReport = ExtentReportManager.getInstance();
    private SearchBaseTest sbt = new SearchBaseTest();
    private int i;


    @BeforeTest
    private void initializeTest() {
        currentTestCaseName = "AmazonProductSearchTest";

    }

    @Test(description = "Verify train ticket booking Flow")
    public void AmazonProductSearchTest() {
        extentReport.createNodeForReports("Amazon Search", "AmazonProductSearchTest");
        launchApplicationURL_Amazon("https://www.amazon.in/");
        logAndReportWithScreenshot("Navigating to Train Ticket Booking page...");
         sbt.clickSearchField();
         sbt.setSearchProduct("mouse");
        logAndReportWithScreenshot("Searched product ..");
         sbt.getProductListsAndSelectProduct("mouse wireless");
        logAndReportWithScreenshot("Clicked on searched product ..");
         sbt.selectDeliveryDay();
        logAndReportWithScreenshot("Delivery Type seelcted");
         sbt.waitForCleartext();
         sbt.getListOfMouses();
         sbt.sortFeature();
        logAndReportWithScreenshot("Feature selected - Low to high");
        String mainwindow =  driver.getWindowHandle();
         sbt.clickSecondLinkProduct();
        PageWebElementActions pwa = new PageWebElementActions(driver);
        pwa.switchToOtherWindow(mainwindow);
        String mouse_priceUnit = sbt.getPrice();
        int mouse_price = Integer.parseInt(mouse_priceUnit);
        logAndReport("Price of 1 unit :"+mouse_priceUnit);
        sbt.clickOffers();
        sbt.getOffers();
        sbt.clickCLoseButton();
        sleep(3000);
        int units=2;
        sbt.selectQuantity(String.valueOf(units));
        int total_price_expected = mouse_price*units;
        sbt.clickAddToCart();
        int TotalPrice = pwa.extractNumber(sbt.getCheckOutPrice());
        logAndReportWithScreenshot("Checkout Process..");
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(TotalPrice).isEqualTo(total_price_expected);
        sbt.processAndClickCheckout();
        softly.assertAll();

    }


}
