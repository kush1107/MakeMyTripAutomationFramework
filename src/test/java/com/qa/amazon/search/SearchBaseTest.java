package com.qa.amazon.search;

import com.qa.mmt.base.BaseInitializer;
import com.qa.mmt.pages.amazonpages.SearchPage;


public class SearchBaseTest extends BaseInitializer {

    public void clickSearchField() {

        SearchPage sp = new SearchPage(driver);
        sp.clickSearchField();

    }


    public void setSearchProduct(String searchProduct) {

        SearchPage sp = new SearchPage(driver);
        sp.setSearchProduct(searchProduct);
    }

    public void getProductListsAndSelectProduct(String inputSearchProduct) {

        SearchPage sp = new SearchPage(driver);
        sp.getProductListsAndSelectProduct(inputSearchProduct);
    }

    public void selectDeliveryDay() {
        SearchPage sp = new SearchPage(driver);
        sp.selectDeliveryDay();

    }

    public void waitForCleartext() {
        SearchPage sp = new SearchPage(driver);
        sp.waitForCleartext();
    }


    public void getListOfMouses()
    {


        SearchPage sp = new SearchPage(driver);
        sp.getListOfMouses();
    }


    public void clickSecondLinkProduct()
    {

        SearchPage sp = new SearchPage(driver);
        sp.clickSecondLinkProduct();

    }

    public void sortFeature()
    {
        SearchPage sp = new SearchPage(driver);
        sp.sortFeature();
    }


    public void clickOffers()
    {
        SearchPage sp = new SearchPage(driver);
        sp. clickOffers();

    }

    public void getOffers()
    {
        SearchPage sp = new SearchPage(driver);
        sp.getOffers();
    }

    public void clickCLoseButton()
    {
        SearchPage sp = new SearchPage(driver);
        sp.clickCLoseButton();
    }

    public String getPrice()
    {
        SearchPage sp = new SearchPage(driver);
        return sp.getPrice();
    }


    public void selectQuantity(String valueSelect)
    {
        SearchPage sp = new SearchPage(driver);
        sp.selectQuantity(valueSelect);
    }


    public void clickAddToCart()
    {

        SearchPage sp = new SearchPage(driver);
        sp.clickAddToCart();
    }

    public String getCheckOutPrice()
    {
        SearchPage sp = new SearchPage(driver);
        return sp.getCheckOutPrice();
    }


    public void processAndClickCheckout()
    {
        SearchPage sp = new SearchPage(driver);
        sp.processAndClickCheckout();
    }









}
