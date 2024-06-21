package com.qa.mmt.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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

    public void byCSSAndClick(String CSS) {
        clickOnElement(By.cssSelector(CSS));
    }

    public void byXpathAndClickUsingJs(String xpathValue) {
        clickOnElementUsingJs(By.xpath(xpathValue));
    }

    private void clickOnElementUsingJs(By by) {
        WebElement element = driver.findElement(by);
        sleep(500);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", element);
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

    public String byCssAndGetTextMessage(String xpathValue) {
        byCSSAndExplictWaitOnElementVisibility(xpathValue);
        return getText(By.cssSelector(xpathValue));
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

    public boolean byXPathAndIsSelected(String xpath) {
        return driver.findElement(By.xpath(xpath)).isSelected();
    }

    public boolean byIdAndIsEnabled(String idLocator) {
        WebElement ele = driver.findElement(By.id(idLocator));
        return isElementEnabled(ele);
    }

    public boolean byXPathAndIsEnabled(String xpath) {
        WebElement ele = driver.findElement(By.xpath(xpath));
        return isElementEnabled(ele);
    }

    public boolean byCSSAndIsEnabled(String css) {
        WebElement ele = driver.findElement(By.cssSelector(css));
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

    public void byCSSAndExplictWaitOnElementVisibility(String elementwait) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(elementwait)));
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

    protected void scrollToAndClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {;
        }

        element.click();
    }

    protected void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {;
        }

    }

    public void byXpathAndScrollToElement(String xpath)
    {
        WebElement ele = driver.findElement(By.xpath(xpath));
        scrollToElement(ele);
    }

    public void byXpathAndScrollClick(String xpath)
    {
        WebElement ele = driver.findElement(By.xpath(xpath));
        scrollToAndClick(ele);

    }

    public  void switchToOtherWindow(String mainWindow) {
        sleep(1500);
        Set<String> allWindowHandles = driver.getWindowHandles();
        if (allWindowHandles.size() != 2) {
            throw new NoSuchWindowException("Only the main window is present. Please ensure another window is opened.");
        }

        // Find the handle of the other window
        String otherWindowHandle = null;
        for (String handle : allWindowHandles) {
            if (!handle.equals(mainWindow)) {
                otherWindowHandle = handle;
                break;
            }
        }

        sleep(1500);
        // Switch to the other window
        driver.switchTo().window(otherWindowHandle);
    }

    public void byXpathAndSelectByValue(String xpathValue, String value) {
        WebElement xpath = driver.findElement(By.xpath(xpathValue));
        selectFromDropDownByValue(xpath, value);
    }

    protected void selectFromDropDownByValue(WebElement element, String value) {
        verifyElementPresentAndClickable(element);
        if (!value.isEmpty()) {
            sleep(500);
        }
        new Select(element).selectByValue(value);
    }

    public static Integer extractNumber(String text) {
        int rupeeIndex = text.indexOf("₹");
        int dotIndex = text.indexOf(".");

        if (rupeeIndex != -1 && dotIndex > rupeeIndex) {
            String numberString = text.substring(rupeeIndex + 1, dotIndex);
            try {
                return Integer.parseInt(numberString);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
