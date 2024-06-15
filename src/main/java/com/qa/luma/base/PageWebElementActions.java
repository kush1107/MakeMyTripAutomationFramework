package com.qa.luma.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PageWebElementActions {

    private static final Logger log = LogManager.getLogger(PageWebElementActions.class.getName());
    public Properties commonElement;
    private Properties prop;
    public WebDriver driver;

    public PageWebElementActions(WebDriver driver) {
        this.driver = driver;
        BaseInitializer bt = new BaseInitializer();
        this.commonElement = bt.commonElements;
        this.prop = bt.prop;
    }

    public void byIdAndEnterText(String text, String idValue) {
        setSendKeys(By.id(idValue), text);
    }


    public void byNameAndEnterText(String text, String nameValue) {
        setSendKeys(By.name(nameValue), text);
    }

    public void byXpathAndEnterText(String xpathValue, String text) {
        setSendKeys(By.xpath(xpathValue), text);
    }

    public void byXpathAndEnterJSText(String xpathValue, String text) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement el = driver.findElement(By.xpath(xpathValue));
        js.executeScript("arguments[0].value = arguments[1];", el, text);
    }


    public void byIdAndClick(String idValue) {
        clickOnElement(By.id(idValue));
    }

    public void byNameAndClick(String nameValue) {
        clickOnElement(By.name(nameValue));
    }

    public void byXpathAndClick(String xpathValue) {
        clickOnElement(By.xpath(xpathValue));
    }

    public void byXpathScrollAndClick(String xpathValue) {
        WebElement ele = driver.findElement(By.xpath(xpathValue));
        scrollAndClickOnElement(ele);
    }

    public void byIdScrollAndClick(String idValue) {
        WebElement ele = driver.findElement(By.id(idValue));
        scrollAndClickOnElement(ele);
    }

    public void byLinkTextAndClick(String linkText) {
        clickOnElement(By.linkText(linkText));
    }

    public void byLinkTextAndHover(String linkText) {
        hoverOverElement(By.linkText(linkText));
    }

    public void byXpathAndHover(String xpath) {
        hoverOverElement(By.xpath(xpath));
    }

    public String byXpathAndGetTextMessage(String xpathValue) {
        byXpathAndExplictWaitOnElementVisibility(xpathValue);
        return getText(By.xpath(xpathValue));
    }

    public String byIdAndGetTextMessage(String idValue) {
        return getText(By.id(idValue));
    }

    public String byIdAndGetAttribute(String idValue, String attribute) {
        return driver.findElement(By.id(idValue)).getAttribute(attribute);
    }

    public String byXpathAndGetAttribute(String xpath, String attribute) {
        return driver.findElement(By.xpath(xpath)).getAttribute(attribute);
    }

    public boolean byIdAndIsSelected(String idLocator) {
        return driver.findElement(By.id(idLocator)).isSelected();
    }

    public boolean byIdAndIsEnabled(String idLocator) {
        WebElement ele = driver.findElement(By.id(idLocator));
        return isElementEnabled(ele);
    }

    public boolean byXPathAndIsEnabled(String xpath) {
        WebElement ele = driver.findElement(By.xpath(xpath));
        return isElementEnabled(ele);
    }

    public boolean byXPathAndExists(String xpath) {
        return driver.findElements(By.xpath(xpath)).size() > 0;
    }

    public boolean byIdAndExists(String id) {
        return driver.findElements(By.id(id)).size() > 0;
    }

    public boolean byIdAndIsDisplayed(String idLocator) {
        return isElementDisplayed(By.id(idLocator));
    }

    public boolean byXpathAndIsDisplayed(String xpathLocator) {
        return isElementDisplayed(By.xpath(xpathLocator));
    }

    public List<String> byIdAndGetDropdownOptions(String idLocator) {
        return getDropdownOptions(By.id(idLocator));
    }

    public int byIdAndGetDropdownSize(String idLocator) {
        return getDropdownSize(By.id(idLocator));
    }

    public int byXpathAndGetDropdownSize(String xpathLocator) {
        return getDropdownSize(By.xpath(xpathLocator));
    }

    public void byXpathAndExplictWaitOnElement(String elementwait) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementwait)));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementwait)));
    }

    public void byXpathAndExplictWaitOnElementVisibility(String elementwait) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementwait)));
    }

    public void byXpathAndExplictWaitOnElementInvisibility(String element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element)));
    }

    public void byXpathAndExplictWaitOnElementClickable(String element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
    }


    public boolean byXpathAndVerifyReadOnly(String xpath) {
        return driver.findElements(By.xpath(xpath + "[@readonly]")).size() > 0;
    }

    private void setSendKeys(By by, String text) {
        WebElement element = driver.findElement(by);
        sleep(250);
        if (!text.isEmpty()) {
            element.clear();
            element.sendKeys(text);
        }
    }

    private void clickOnElement(By by) {
        WebElement element = driver.findElement(by);
        sleep(500);
        element.click();
    }

    private void hoverOverElement(By by) {
        WebElement element = driver.findElement(by);
        Actions action = new Actions(driver);
        action.moveToElement(element)
                .pause(Duration.ofSeconds(3))
                .perform();
    }

    private String getText(By by) {
        WebElement element = driver.findElement(by);
        String text = element.getText();
        if (text == null || text.isEmpty()) {
            text = element.getAttribute("value");
        }
        return text;
    }

    private boolean isElementEnabled(WebElement element) {
        try {
            verifyElementPresentAndClickable(element);
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isElementDisplayed(By by) {
        WebElement element = driver.findElement(by);
        return element.isDisplayed();
    }

    private List<String> getDropdownOptions(By by) {
        WebElement element = driver.findElement(by);
        List<String> options = new ArrayList<>();
        Select select = new Select(element);
        List<WebElement> dropDown = select.getOptions();
        for (WebElement e : dropDown) {
            options.add(e.getText());
        }
        return options;
    }

    private int getDropdownSize(By by) {
        WebElement element = driver.findElement(by);
        Select select = new Select(element);
        List<WebElement> dropDown = select.getOptions();
        return dropDown.size();
    }


    private void verifyElementPresentAndClickable(WebElement ele) {
        WebDriverWait wait;
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        wait.until(ExpectedConditions.visibilityOf(ele));
        wait.until(ExpectedConditions.elementToBeClickable(ele));

    }

    private void sleep(long ms)
    {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    protected void scrollAndClickOnElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("arguments[0].scrollIntoView();", element);
            verifyElementPresentAndClickable(element);
            element.click();
        } catch (Exception e){
            js.executeScript("arguments[0].scrollIntoView();", element);
            js.executeScript("window.scrollBy(0,-400)");
            verifyElementPresentAndClickable(element);
            element.click();
        }
    }

}
