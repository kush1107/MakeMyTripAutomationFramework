package com.qa.luma.customer;

import com.qa.luma.base.BaseInitializer;
import com.qa.luma.pages.commonpages.LoginPage;
import com.qa.luma.pages.commonpages.RegistrationPage;
import org.testng.SkipException;

public class CustomerBaseTest extends BaseInitializer {

    public void setValidCredentials(String username,String password)
    {
        LoginPage lp = new LoginPage(driver);
        lp.setUserID(username);
        lp.setPassword(password);
    }

    public void clickSignInLink()
    {
        LoginPage lp = new LoginPage(driver);
        lp.clickSignInLink();
    }

    public void clickSignInButton()
    {
        LoginPage lp = new LoginPage(driver);
        lp.clickSignButton();
    }

    public void verifyWelcomeUserProfile()
    {
        LoginPage lp = new LoginPage(driver);
        if (lp.verifyWelcomeUser()) {
            logAndReportWithScreenshot("User Logged-In successfully..");
        }
        else{
            if(lp.byXpathAndIsDisplayed(commonElements.getProperty("LOGIN_ERROR_MSG")))
            {
                logAndReportErrorWithScreenshot("Login Failed..Please try again");
                throw new SkipException("Login Failed..");
            }
        }
    }

    public void clickCreateAccountLink()
    {
        RegistrationPage rp = new RegistrationPage(driver);
        rp.clickCreateAccountLink();
    }

    public boolean verifyCreateCustomerTitle()
    {
        RegistrationPage rp = new RegistrationPage(driver);
        return  rp.verifyCreateNewCustomerTitle();
    }

    public void setFirstName(String firstName)
    {
        RegistrationPage rp = new RegistrationPage(driver);
        if (firstName!=null) {
            rp.setFirstName(firstName);
        }
    }

    public void setLastName(String lastName)
    {
        RegistrationPage rp = new RegistrationPage(driver);
        if (lastName!=null) {
            rp.setLastName(lastName);
        }
    }

    public void setEmail(String email)
    {
        RegistrationPage rp = new RegistrationPage(driver);
        if (email!=null) {
            rp.setEmailAddress(email);
        }
    }

    public void setRegistrationPassword(String password)
    {
        RegistrationPage rp = new RegistrationPage(driver);
        if (password!=null) {
            rp.setRegisterPassword(password);
        }
    }

    public void setRegistrationConfirmPassword(String password)
    {
        RegistrationPage rp = new RegistrationPage(driver);
        if (password!=null) {
            rp.setRegisterConfirmationPassword(password);
        }
    }

    public void clickCreateAccountButton()
    {
        RegistrationPage rp = new RegistrationPage(driver);
        rp.clickCreateAccountButton();
    }

    public boolean checkWelcomeUserProfile()
    {
        RegistrationPage rp = new RegistrationPage(driver);
        return  rp.verifyWelcomeUserProfile();
    }


}
