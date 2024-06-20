package com.qa.mmt.pages.commonpages;

import com.qa.mmt.base.PageWebElementActions;
import org.openqa.selenium.WebDriver;

public class MenuPage extends PageWebElementActions {

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    public void clickTrainBookingLink() {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("TRAIN_TICKET_BOOKING"));
        } catch (Exception e) {
        }
        if (byXPathAndIsEnabled(commonElement.getProperty("TRAIN_TICKET_BOOKING")) && byXpathAndIsDisplayed(commonElement.getProperty("TRAIN_TICKET_BOOKING"))) {
            byXpathAndClick(commonElement.getProperty("TRAIN_TICKET_BOOKING"));
        }
    }
}
