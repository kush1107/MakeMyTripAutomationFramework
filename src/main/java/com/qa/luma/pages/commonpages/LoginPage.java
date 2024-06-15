package com.qa.luma.pages.commonpages;

import com.qa.luma.base.BaseInitializer;
import com.qa.luma.base.PageWebElementActions;
import org.openqa.selenium.WebDriver;

public class LoginPage extends PageWebElementActions {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void clickSignInLink() {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("SIGN_IN_LINK"));
        } catch (Exception e) {
        }
        if (byXPathAndIsEnabled(commonElement.getProperty("SIGN_IN_LINK")) && byXpathAndIsDisplayed(commonElement.getProperty("SIGN_IN_LINK"))) {
            byXpathAndClick(commonElement.getProperty("SIGN_IN_LINK"));
        }
    }

    public void setUserID(String username) {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("USER_ID"));
        } catch (Exception e) {
        }
        if (byXPathAndIsEnabled(commonElement.getProperty("USER_ID")) && byXpathAndIsDisplayed(commonElement.getProperty("USER_ID"))) {
            byXpathAndEnterText(commonElement.getProperty("USER_ID"), username);
        }
    }

    public void setPassword(String password) {
        if (byXPathAndIsEnabled(commonElement.getProperty("PASSWORD")) && byXpathAndIsDisplayed(commonElement.getProperty("PASSWORD"))) {
            byXpathAndEnterText(commonElement.getProperty("PASSWORD"), password);
        }
    }

    public void clickSignButton() {
        if (byXPathAndIsEnabled(commonElement.getProperty("SIGIN_BUTTON")) && byXpathAndIsDisplayed(commonElement.getProperty("SIGIN_BUTTON"))) {
            byXpathAndClick(commonElement.getProperty("SIGIN_BUTTON"));
        }
    }

    public boolean verifyWelcomeUser() {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("USER_LOGIN_WELCOME"));
            if (byXpathAndIsDisplayed(commonElement.getProperty("USER_LOGIN_WELCOME"))) {
                return true;
            }
        } catch (Exception e) {
            BaseInitializer bi = new BaseInitializer();
            bi.logAndReportErrorWithScreenshot(e.getMessage());
        }
        return false;
    }




    public void validationMsg(String msg) {
        {
            BaseInitializer bi = new BaseInitializer();
            try {
                msg = byXpathAndGetTextMessage(commonElement.getProperty("VALIDATION_MSG_PREFIX")+msg+commonElement.getProperty("VALIDATION_MSG_SUFFIX"));
            } catch (Exception e) {
                msg =null;
            }
            if (msg!=null || !msg.isEmpty()) {
                bi.logAndReportWithScreenshot(msg);
            } else {
                bi.logAndReportErrorWithScreenshot(msg + " - Message not displayed.");
            }
        }
    }

}
