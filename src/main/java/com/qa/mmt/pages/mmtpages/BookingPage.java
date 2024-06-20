package com.qa.mmt.pages.mmtpages;

import com.qa.mmt.base.BaseInitializer;
import com.qa.mmt.base.PageWebElementActions;
import org.apache.commons.lang3.Validate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class BookingPage extends PageWebElementActions {

    public BookingPage(WebDriver driver) {
        super(driver);
    }


    public boolean verifyTrainBookingTitle()
    {
        try {
            byXpathAndExplictWaitOnElementVisibility(commonElement.getProperty("TRAIN_TICKET_BOOKING_TITLE"));
        } catch (Exception e) {
        }
        return byXpathAndIsDisplayed(commonElement.getProperty("TRAIN_TICKET_BOOKING_TITLE")) ? true : false;


    }

    public void setFromPlace(String fromPlace)
    {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("FROM_PLACE"));
        } catch (Exception e) {
        }
        if (byXPathAndIsEnabled(commonElement.getProperty("FROM_PLACE")) && byXpathAndIsDisplayed(commonElement.getProperty("FROM_PLACE"))) {
            byXpathAndClick(commonElement.getProperty("FROM_PLACE"));
            byXpathAndExplictWaitOnElementClickable(commonElement.getProperty("FROM_INPUT"));
            byXpathAndEnterText(commonElement.getProperty("FROM_INPUT"),fromPlace);
            byXpathAndExplictWaitOnElementClickable(commonElement.getProperty("SUGGESTION_PREFFIX")+fromPlace+commonElement.getProperty("SUGGESTION_SUFFIX"));
            byXpathAndClick(commonElement.getProperty("SUGGESTION_PREFFIX")+fromPlace+commonElement.getProperty("SUGGESTION_SUFFIX"));
        }
    }


    public void setToPlace(String toPlace)
    {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("TO_PLACE"));
        } catch (Exception e) {
        }
        if (byXpathAndIsDisplayed(commonElement.getProperty("TO_INPUT")) && byXPathAndIsEnabled(commonElement.getProperty("TO_INPUT"))) {
            byXpathAndExplictWaitOnElementClickable(commonElement.getProperty("TO_INPUT"));
            byXpathAndEnterText(commonElement.getProperty("TO_INPUT"),toPlace);
            byXpathAndExplictWaitOnElementClickable(commonElement.getProperty("SUGGESTION_PREFFIX")+toPlace+commonElement.getProperty("SUGGESTION_SUFFIX"));
            byXpathAndClick(commonElement.getProperty("SUGGESTION_PREFFIX")+toPlace+commonElement.getProperty("SUGGESTION_SUFFIX"));

        }else {
            if (!byXpathAndIsDisplayed(commonElement.getProperty("TO_INPUT")) && byXPathAndIsEnabled(commonElement.getProperty("TO_INPUT"))) {
                byXpathAndClick(commonElement.getProperty("TO_PLACE"));
                byXpathAndExplictWaitOnElementClickable(commonElement.getProperty("TO_INPUT"));
                byXpathAndEnterText(commonElement.getProperty("TO_INPUT"),toPlace);
                byXpathAndExplictWaitOnElementClickable(commonElement.getProperty("SUGGESTION_PREFFIX")+toPlace+commonElement.getProperty("SUGGESTION_SUFFIX"));
                byXpathAndClick(commonElement.getProperty("SUGGESTION_PREFFIX")+toPlace+commonElement.getProperty("SUGGESTION_SUFFIX"));
            }

        }
    }

    public void clickTravelDate()
    {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("TRAVEL_DATE"));
        } catch (Exception e) {
        }
        if (byXPathAndIsEnabled(commonElement.getProperty("TRAVEL_DATE")) && byXpathAndIsDisplayed(commonElement.getProperty("TRAVEL_DATE"))) {
            byXpathAndClick(commonElement.getProperty("TRAVEL_DATE"));
        }
    }

    public void setTravelDate(String inputMonth, String inputDate) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        if (inputMonth == null || inputMonth.isEmpty() || inputDate == null || inputDate.isEmpty()) {
            throw new IllegalArgumentException("Input month and date must be non-empty strings.");
        }

        try {
            while (true) {
                WebElement currentMonthElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@role='heading'])[1]")));
                String currentMonthText = currentMonthElement.getText();

                if (currentMonthText.equals(inputMonth)) {
                    break;
                } else {
                    WebElement nextMonthButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-label='Next Month']")));
                    nextMonthButton.click();
                }
            }

            List<WebElement> dateElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("(//div[@role='grid'])[1]//div[@class='DayPicker-Day']")));
            boolean dateFound = false;

            for (WebElement dateElement : dateElements) {
                if (dateElement.getText().equals(inputDate)) {
                    dateElement.click();
                    dateFound = true;
                    break;
                }
            }

            if (!dateFound) {
                throw new NoSuchElementException("The specified date: " + inputDate + " is not available in the current month view.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to set travel date to " + inputMonth + " " + inputDate, e);
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
  }


    public void selectTrainClass() {
        byXpathAndClick(commonElement.getProperty("TRAIN_CLASS"));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        byXpathAndClick(commonElement.getProperty("TRAIN_CLASS_CATEGORY"));
    }

    public void clickSearchButton ()
    {
        byXpathAndExplictWaitOnElementVisibility(commonElement.getProperty("SEARCH_BUTTON"));
        byXpathAndClick(commonElement.getProperty("SEARCH_BUTTON"));
    }

    public boolean verifyFilterText()
    {
        byXpathAndExplictWaitOnElementVisibility(commonElement.getProperty("FILTER_TEXT"));
        return byXpathAndIsDisplayed(commonElement.getProperty("FILTER_TEXT"));
    }


    public void getListDetial()
    {
        List <WebElement> ele =  driver.findElements(By.xpath("//div[@class='train-list']//div[@class='single-train-detail single-train-padding']"));

        for (WebElement ele2 : ele)
        {
            System.out.println(ele2.getText());
        }
    }

    public void trainSelect()
    {
        byXpathAndExplictWaitOnElementVisibility(commonElement.getProperty("TRAIN_SATABDI"));
        BaseInitializer bi  =  new BaseInitializer();
        bi.logAndReportWithScreenshot("Train Selected SuccessFully");
        byXpathAndScrollClick(commonElement.getProperty("TRAIN_SATABDI"));
    }

    public boolean VerifyPay()
    {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("PAYBUTTON"));
        } catch (Exception e) {
        }
        if (byXPathAndIsEnabled(commonElement.getProperty("PAYBUTTON")) && byXpathAndIsDisplayed(commonElement.getProperty("PAYBUTTON"))) {
            return true;
        }
        return false;
    }

    public void clickAddTraveller()
    {
        byXpathAndScrollClick(commonElement.getProperty("TRAVELLER_ADD"));
    }

    public void fillAddTraveller(String name,String age)
    {
        byXpathAndExplictWaitOnElement(commonElement.getProperty("TRAVELLER_ADD_ADD_BUTTON"));
        byXpathAndClick(commonElement.getProperty("TRAVELLER_NAME"));
        byXpathAndEnterText(commonElement.getProperty("TRAVELLER_NAME"),name);
        byXpathAndClick(commonElement.getProperty("TRAVELLER_AGE"));
        byXpathAndEnterText(commonElement.getProperty("TRAVELLER_AGE"),age);
        byXpathAndClick(commonElement.getProperty("TRAVELLER_GENDER"));
        byXpathAndExplictWaitOnElementVisibility(commonElement.getProperty("TRAVELLER_GENDER_MALE"));
        byXpathAndClick(commonElement.getProperty("TRAVELLER_GENDER_MALE"));
        byXpathAndClick(commonElement.getProperty("TRAVELLER_ADD_ADD_BUTTON"));
    }

    public void clickPayButtonAndValidateErrorMsg()
    {
        byXpathAndScrollClick(commonElement.getProperty("PAYBUTTON"));
        byXPathAndExists(commonElement.getProperty("VALIDATION_ERROR_MSG_IRCTC"));
        byXpathAndScrollToElement(commonElement.getProperty("VALIDATION_ERROR_MSG_IRCTC"));


    }

    public String getErMsgIRCTC()
    {
      String errorMSG =   byXpathAndGetTextMessage(commonElement.getProperty("VALIDATION_ERROR_MSG_IRCTC"));

      return errorMSG;
    }

    public void getPaymentDetails()
    {
        BaseInitializer bi = new BaseInitializer();
        byXpathAndScrollToElement(commonElement.getProperty("PAYBUTTON"));
       List<WebElement>  paymentDetails =  driver.findElements(By.xpath(commonElement.getProperty("PAYMENT_DETIALS")));

        for (WebElement paymentDetailsElement : paymentDetails)
        {
            bi.logAndReport(paymentDetailsElement.getText());
        }
        bi.logAndReportWithScreenshot("Payment Details Printed...");
    }


    public void getTotalPayment()
    {
        BaseInitializer bi = new BaseInitializer();

        bi.logAndReport("Total Amount to be Paid:"+byXpathAndGetTextMessage(commonElement.getProperty("PAYMENT_DETIALS_TOTAL")));
    }



}
