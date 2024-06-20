package com.qa.mmt.booking;

import com.qa.mmt.base.BaseInitializer;
import com.qa.mmt.pages.commonpages.MenuPage;
import com.qa.mmt.pages.mmtpages.BookingPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;

import java.awt.print.Book;
import java.util.List;

public class BookingBaseTest extends BaseInitializer {


    public void clickTrainLink() {
        MenuPage mp = new MenuPage(driver);
        mp.clickTrainBookingLink();
    }


    public boolean verifyTrainBookingTitle() {
        BookingPage bp = new BookingPage(driver);
        return bp.verifyTrainBookingTitle();

    }


    public void setFromPlace(String fromPlace) {
        BookingPage bp = new BookingPage(driver);
        bp.setFromPlace(fromPlace);
    }


    public void setToPlace(String toPlace) {
        BookingPage bp = new BookingPage(driver);
        bp.setToPlace(toPlace);
    }

    public void setTravelDate(String inputmonth, String inputdate) {
        BookingPage bp = new BookingPage(driver);
        bp.setTravelDate(inputmonth, inputdate);
    }

    public void clickTravelDate() {
        BookingPage bp = new BookingPage(driver);
        bp.clickTravelDate();
    }


    public void selectTrainClass() {
        BookingPage bp = new BookingPage(driver);
        bp.selectTrainClass();
    }

    public void clickSearchButton() {
        BookingPage bp = new BookingPage(driver);
        bp.clickSearchButton();
    }

    public boolean verifyFilterText() {
        BookingPage bp = new BookingPage(driver);
        return bp.verifyFilterText();
    }

    public void getTrainList()
    {
        BookingPage bp = new BookingPage(driver);
        bp.getListDetial();
    }

    public void selectTrain() {
        BookingPage bp = new BookingPage(driver);
        bp.trainSelect();
    }


    public boolean VerifyPay()
    {
        BookingPage bp = new BookingPage(driver);
        return bp.VerifyPay();
    }

    public void clickAddTraveller()
    {
        BookingPage bp = new BookingPage(driver);
        bp.clickAddTraveller();
    }
    public void fillAddTraveller(String name,String age)
    {

        BookingPage bp = new BookingPage(driver);
        bp.fillAddTraveller(name,age);
    }


    public void clickPayButtonAndValidateIRCTCErrorMsg()
    {
        BookingPage bp = new BookingPage(driver);
        bp.clickPayButtonAndValidateErrorMsg();
    }

    public String getErroMsgIRCTC()
    {
        BookingPage bp = new BookingPage(driver);
        return bp.getErMsgIRCTC();
    }

    public void getPaymentDetails()
    {
        BookingPage bp = new BookingPage(driver);
        bp.getPaymentDetails();
    }

    public void getTotalPayment()
    {

        BookingPage bp = new BookingPage(driver);
        bp.getTotalPayment();
    }

}
