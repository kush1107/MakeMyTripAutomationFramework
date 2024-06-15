package com.qa.luma.pages.commonpages;

import com.qa.luma.base.PageWebElementActions;
import org.openqa.selenium.WebDriver;

public class LogoutPage extends PageWebElementActions {

    public LogoutPage(WebDriver driver)
    {
        super(driver);
    }

    private void clickSignOut()
    {
        if (byXPathAndIsEnabled(commonElement.getProperty("ARROW_BUTTON")) && byXpathAndIsDisplayed(commonElement.getProperty("ARROW_BUTTON"))) {
            byXpathAndClick(commonElement.getProperty("ARROW_BUTTON"));
            byXpathAndExplictWaitOnElementClickable(commonElement.getProperty("SIGN_OUT_BUTTON"));
            byXpathAndClick(commonElement.getProperty("SIGN_OUT_BUTTON"));
        }
    }
}
