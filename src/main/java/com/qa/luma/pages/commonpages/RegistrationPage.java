package com.qa.luma.pages.commonpages;

import com.qa.luma.base.PageWebElementActions;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;

public class RegistrationPage extends PageWebElementActions {

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void clickCreateAccountLink() {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("CREATE_ACCOUNT_LINK"));
        } catch (Exception e) {
        }
        if (byXPathAndIsEnabled(commonElement.getProperty("CREATE_ACCOUNT_LINK")) && byXpathAndIsDisplayed(commonElement.getProperty("CREATE_ACCOUNT_LINK"))) {
            byXpathAndClick(commonElement.getProperty("CREATE_ACCOUNT_LINK"));
        }
    }

    public boolean verifyCreateNewCustomerTitle() {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("CREATE_NEW_CUSTOMER_TITLE"));
        } catch (Exception e) {
        }
        if (byXpathAndIsDisplayed(commonElement.getProperty("CREATE_NEW_CUSTOMER_TITLE"))) {
           return true;
        }
        return false;
    }

    public void setFirstName(String firstName) {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("FIRST_NAME"));
        } catch (Exception e) {
        }
        if (byXPathAndIsEnabled(commonElement.getProperty("FIRST_NAME")) && byXpathAndIsDisplayed(commonElement.getProperty("FIRST_NAME"))) {
            byXpathAndEnterText(commonElement.getProperty("FIRST_NAME"), firstName);
        }
    }

    public void setLastName(String lastName) {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("LAST_NAME"));
        } catch (Exception e) {
        }
        if (byXPathAndIsEnabled(commonElement.getProperty("LAST_NAME")) && byXpathAndIsDisplayed(commonElement.getProperty("LAST_NAME"))) {
            byXpathAndEnterText(commonElement.getProperty("LAST_NAME"), lastName);
        }
    }

    public void setEmailAddress(String email) {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("EMAIL_ID"));
        } catch (Exception e) {
        }
        if (byXPathAndIsEnabled(commonElement.getProperty("EMAIL_ID")) && byXpathAndIsDisplayed(commonElement.getProperty("EMAIL_ID"))) {
            byXpathAndEnterText(commonElement.getProperty("EMAIL_ID"), email);
        }
    }

    public void setRegisterPassword(String password) {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("REGISTER_PASSWORD"));
        } catch (Exception e) {
        }
        if (byXPathAndIsEnabled(commonElement.getProperty("REGISTER_PASSWORD")) && byXpathAndIsDisplayed(commonElement.getProperty("REGISTER_PASSWORD"))) {
            byXpathAndEnterText(commonElement.getProperty("REGISTER_PASSWORD"), password);
        }
    }

    public void setRegisterConfirmationPassword(String confirmationPassword) {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("REGISTER_CONFIRMATION_PASSWORD"));
        } catch (Exception e) {
        }
        if (byXPathAndIsEnabled(commonElement.getProperty("REGISTER_CONFIRMATION_PASSWORD")) && byXpathAndIsDisplayed(commonElement.getProperty("REGISTER_CONFIRMATION_PASSWORD"))) {
            byXpathAndEnterText(commonElement.getProperty("REGISTER_CONFIRMATION_PASSWORD"), confirmationPassword);
        }
    }

    public void clickCreateAccountButton() {
        if (byXPathAndIsEnabled(commonElement.getProperty("CREATE_ACCOUNT_BUTTON")) && byXpathAndIsDisplayed(commonElement.getProperty("CREATE_ACCOUNT_BUTTON"))) {
            byXpathAndClick(commonElement.getProperty("CREATE_ACCOUNT_BUTTON"));
        }
    }

    public boolean verifyWelcomeUserProfile()
    {
        LoginPage lp = new LoginPage(driver);
        if (lp.verifyWelcomeUser()) {
            return true;
        }
        return false;
    }
}
