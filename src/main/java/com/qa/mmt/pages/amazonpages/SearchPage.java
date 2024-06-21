package com.qa.mmt.pages.amazonpages;

import com.qa.mmt.base.BaseInitializer;
import com.qa.mmt.base.PageWebElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends PageWebElementActions {

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void clickSearchField() {

        try {
            byXpathAndExplictWaitOnElementClickable(commonElement.getProperty("SEARCH_FIELD"));
        } catch (Exception e) {
        }
        if (byXPathAndIsEnabled(commonElement.getProperty("SEARCH_FIELD")) && byXpathAndIsDisplayed(commonElement.getProperty("SEARCH_FIELD"))) {
            byXpathAndClick(commonElement.getProperty("SEARCH_FIELD"));
        }
    }


    public void setSearchProduct(String searchProduct) {

        if (byXPathAndIsEnabled(commonElement.getProperty("SEARCH_FIELD")) && byXpathAndIsDisplayed(commonElement.getProperty("SEARCH_FIELD"))) {
            byXpathAndEnterText(commonElement.getProperty("SEARCH_FIELD"),searchProduct);
        }
    }

    public void getProductListsAndSelectProduct(String inputSearchProduct)
    {
        try {
            byXpathAndExplictWaitOnElementVisibility(commonElement.getProperty("GET_PRODUCT_LIST"));
        } catch (Exception e) {
        }
        if (byXpathAndIsDisplayed(commonElement.getProperty("GET_PRODUCT_LIST"))) {

            List <WebElement> productList = driver.findElements(By.xpath(commonElement.getProperty("GET_PRODUCT_LIST")));
            BaseInitializer bi = new BaseInitializer();

            bi.logAndReport("Total number results found:"+productList.size());
            bi.logAndReportWithScreenshot("ProductList fetched...");
            for (WebElement productListElements : productList) {
                String suggestionText = productListElements.getAttribute("aria-label");
                if (suggestionText.equals(inputSearchProduct)) {
                    productListElements.click();
                    break;
                }
            }
        }
    }

    public void selectDeliveryDay()
    {
        try {
            byXpathAndExplictWaitOnElementVisibility(commonElement.getProperty("DELIVERY_DAY"));
        } catch (Exception e) {
        }
        if (!byXPathAndIsSelected(commonElement.getProperty("DELIVERY_DAY")))
        {
            byXpathAndClick(commonElement.getProperty("DELIVERY_DAY"));
        }

    }

    public void waitForCleartext() {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("CLEAR_PRODUCT_LIST"));
        } catch (Exception e) {
        }
    }

    public void getListOfMouses()
    {


            List <WebElement> productList = driver.findElements(By.xpath(commonElement.getProperty("GET_LIST_MOUSES")));
            BaseInitializer bi = new BaseInitializer();

            bi.logAndReport("Total number results found:"+productList.size());
            bi.logAndReportWithScreenshot("ProductList fetched...");
            for (WebElement productListElements : productList) {
                bi.logAndReport("List of all mouses:"+productListElements.getText());
            }

        }

        public void sortFeature()
        {
            byXpathAndScrollToElement(commonElement.getProperty("SORT_FILTER"));
            if (byXPathAndIsEnabled(commonElement.getProperty("SORT_FILTER"))) {
                byXpathAndClick(commonElement.getProperty("SORT_FILTER"));
                byXpathAndExplictWaitOnElementClickable(commonElement.getProperty("SORT_LOWTOHIGH"));
                byXpathAndClick(commonElement.getProperty("SORT_LOWTOHIGH"));
            }
        }


        public void clickSecondLinkProduct()
        {
            try {
                byXpathAndExplictWaitOnElementVisibility(commonElement.getProperty("CLICK_SECOND_PRODUCT"));
            } catch (Exception e) {
            }
            byXpathAndScrollToElement(commonElement.getProperty("CLICK_SECOND_PRODUCT"));
            if (byXpathAndIsDisplayed(commonElement.getProperty("CLICK_SECOND_PRODUCT"))) {
                byXpathAndClick(commonElement.getProperty("CLICK_SECOND_PRODUCT"));
            }

        }

    public void clickOffers()
    {
        try {
            byXpathAndExplictWaitOnElementVisibility(commonElement.getProperty("OFFERS"));
        } catch (Exception e) {
        }
        byXpathAndScrollToElement(commonElement.getProperty("OFFERS"));
        if (byXpathAndIsDisplayed(commonElement.getProperty("OFFERS"))) {
            byXpathAndClick(commonElement.getProperty("OFFERS"));
        }

    }

    public void getOffers()
    {
        try {
            byXpathAndExplictWaitOnElementVisibility(commonElement.getProperty("OFFERS_SHOW"));
        } catch (Exception e) {
        }
        if (byXpathAndIsDisplayed(commonElement.getProperty("OFFERS_SHOW"))) {
           BaseInitializer bi = new BaseInitializer();
           bi.logAndReportWithScreenshot("OFFERS Showing for Given Product");

        }
    }

    public void clickCLoseButton()
    {
        try {
            byCSSAndExplictWaitOnElementVisibility(commonElement.getProperty("CLOSE_BUTTON"));
        } catch (Exception e) {
        }
        byCSSAndClick(commonElement.getProperty("CLOSE_BUTTON"));
    }

    public String getPrice()
    {
        try {
            byCSSAndExplictWaitOnElementVisibility(commonElement.getProperty("GET_PRODUCT_PRICE"));
        } catch (Exception e) {
        }
        return byCssAndGetTextMessage(commonElement.getProperty("GET_PRODUCT_PRICE"));
    }

    public void selectQuantity(String valueSelect)
    {
        try {
            byXpathAndExplictWaitOnElement(commonElement.getProperty("SELECT_QUANTITY"));
        } catch (Exception e) {
        }
        byXpathAndSelectByValue(commonElement.getProperty("SELECT_QUANTITY"), valueSelect);
    }

    public void clickAddToCart()
    {
        try {
            byXpathAndExplictWaitOnElementClickable(commonElement.getProperty("ADD_TO_CART"));
        } catch (Exception e) {
        }
        if (byXpathAndIsDisplayed(commonElement.getProperty("ADD_TO_CART"))) {
            byXpathAndClick(commonElement.getProperty("ADD_TO_CART"));
        }
    }

    public String getCheckOutPrice()
    {
        try {
            byXpathAndExplictWaitOnElementVisibility(commonElement.getProperty("CHECKOUT_PRICE"));
        } catch (Exception e) {
        }
        return byXpathAndGetTextMessage(commonElement.getProperty("CHECKOUT_PRICE"));
    }


    public void processAndClickCheckout()
    {
        try {
            byXpathAndExplictWaitOnElementClickable(commonElement.getProperty("CHECKOUT_BUTTON"));
        } catch (Exception e) {
        }
        if (byXpathAndIsDisplayed(commonElement.getProperty("CHECKOUT_BUTTON"))) {
            byXpathAndClick(commonElement.getProperty("CHECKOUT_BUTTON"));
        }
    }
}
