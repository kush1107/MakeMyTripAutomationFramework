package com.qa.luma.pages.commonpages;

import com.qa.luma.base.PageWebElementActions;
import org.openqa.selenium.WebDriver;

public class CommonPage extends PageWebElementActions {

    public static String credentialMessage;

    public CommonPage(WebDriver driver) {
        super(driver);
    }

    public void getAnyMessage(String msg)
    {
        try{
            credentialMessage = byXpathAndGetTextMessage(commonElement.getProperty("VALIDATION_MSG_PREFIX") + msg
                    + commonElement.getProperty("VALIDATION_MSG_SUFFIX"));
        }catch (Exception e){
            credentialMessage = null;
        }
    }


}
