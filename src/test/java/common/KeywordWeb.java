package common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Driver;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class KeywordWeb {
    private static final Logger logger = LoggerHelpers.getLogger();
    public static WebDriver driver;

    public void openBrowser(String browser, String... url) {
        logger.info("Open browser");
        switch (browser.toUpperCase()) {
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "EDGE":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                System.setProperty("webdriver.chrome.driver","C:\\Users\\maia2\\.cache\\selenium\\chromedriver\\win64\\129.0.6668.89\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
        }
        logger.info("open browser successfully " + browser);
        String rawUrl = url.length > 0 ? url[0] : "";
        if (rawUrl != null && !rawUrl.isEmpty()) {
            logger.info("go to url: " + rawUrl);
            driver.get(rawUrl);
        }
    }

    public void resizeBrowser(int width, int height) {
        Dimension dimension = new Dimension(width, height);
        //Resize the current window to the given dimension
        driver.manage().window().setSize(dimension);
    }

    public void reLoadPage() {
        logger.info("ReLoad Page...");
        driver.navigate().refresh();
    }

    public void verifyUrl(String url){
        //verify url hiện tại với url truyền vào
        logger.info("Verify URL..." + url);
        Assert.assertTrue(getCurrentPageUrl().contains(url), "URL incorrect.");
    }

    public void verifyPageTitle(String title) {
        //verify title hiện tại với title truyền vào
        logger.info("Verify page title");
        Assert.assertEquals(title, driver.getTitle());
    }

    public static void closeBrowser() {
        logger.info("close browser: ");
        if (driver != null) {
            driver.quit();
            driver = null;
        } else {
            System.out.println("Driver chưa được khởi tạo.");
        }
    }

    public void click(String element) {
        logger.info("click " + element);
        String xPathElement = PropertiesHelpers.getPropValue(element);
        if (xPathElement == null) {
            xPathElement = element;
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPathElement))).click();
    }

    public void sendKeys(String element, String content) {
        logger.info("send keys " + element + " with content " + content);
        String xPathElement1 = PropertiesHelpers.getPropValue(element);
        String xPathElement2 = PropertiesHelpers.getPropValue(content);
        if (xPathElement1 == null) {
            xPathElement1 = element;
        }
        if (xPathElement2 == null) {
            xPathElement2 = content;

        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPathElement1))).sendKeys(xPathElement2);
    }

    public void clearAndSendKeys(String element, String content) {
        logger.info("clear and send keys " + element + " with content " + content);
        String xPathElement1 = PropertiesHelpers.getPropValue(element);
        String xPathElement2 = PropertiesHelpers.getPropValue(content);
        if (xPathElement1 == null) {
            xPathElement1 = element;
        }
        if (xPathElement2 == null) {
            xPathElement2 = content;
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPathElement1)));
        ele.clear();
        ele.sendKeys(xPathElement2);
    }

    public void clearAndSendKeysWithBackspace(String element, String content) {
        logger.info("click" + element);
        String xPathElement1 = PropertiesHelpers.getPropValue(element);
        String xPathElement2 = PropertiesHelpers.getPropValue(content);
        if (xPathElement1 == null) {
            xPathElement1 = element;
        }
        if (xPathElement2 == null) {
            xPathElement2 = content;
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement elementBackspace = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPathElement1)));
        logger.info("clearAndSendKeysWithBackspace");
        elementBackspace.click();
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).perform();
        elementBackspace.sendKeys(xPathElement2);
    }

    public void executeJavaScript(String command) {
        logger.info("Executing JavaScript");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(command);
    }

    public void maximizeWindow() {
        logger.info("Maximizing browser window...");
        driver.manage().window().maximize();
    }

    public void navigateToUrl(String url) {
        logger.info("Navigating to URL..." + url);
        String xPathElement = PropertiesHelpers.getPropValue(url);
        if (xPathElement == null) {
            xPathElement = url;
        }
        driver.navigate().to(xPathElement);
    }

    public String getCurrentPageUrl() {
        logger.info("Navigating to URL...");
        return driver.getCurrentUrl();
    }

    public void simpleAssertEquals(String expected, String actual) {
        logger.info("compare from " + expected + " with " + actual);
        String xPathElement1 = PropertiesHelpers.getPropValue(expected);
        if (xPathElement1 == null) {
            xPathElement1 = expected;
        }
        String xPathElement2 = PropertiesHelpers.getPropValue(actual);
        if (xPathElement2 == null) {
            xPathElement2 = actual;
        }
        Assert.assertEquals(xPathElement2, xPathElement1);
    }


    public boolean verifyElementVisible(String element) {
        logger.info("verify Element Visible " + element);
        String xPathElement = PropertiesHelpers.getPropValue(element);
        if (xPathElement == null) {
            xPathElement = element;
        }
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPathElement))).isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void scrollToTheBottomPage(String element) {
        logger.info("scrollDownToElementWithJavaExecutor: ---------------------------");
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public void clickByJavaScript(String element) {
        logger.info("click element by javaExecutor");
        String xPathElement = PropertiesHelpers.getPropValue(element);
        if (xPathElement == null) {
            xPathElement = element;
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement elements = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPathElement)));
        JavascriptExecutor jse2 = (JavascriptExecutor)driver;
        jse2.executeScript("arguments[0].scrollIntoView()", elements);
        jse2.executeScript("arguments[0].click();", elements);
    }

    public boolean verifyElementIsSelected(String element) {
        logger.info("verify Element Is Selected " + element);
        String xPathElement = PropertiesHelpers.getPropValue(element);
        if (xPathElement == null) {
            xPathElement = element;
        }
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
             wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPathElement))).isSelected();
            return true;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void verifyOptionTotal(String element,int total){
        logger.info("click" + element);
        String xPathElement = PropertiesHelpers.getPropValue(element);
        if (xPathElement == null) {
            xPathElement = element;
        }
        Select select = new Select(driver.findElement(By.xpath(xPathElement)));
        Assert.assertEquals(total, select.getOptions().size());
    }

    public String getText(String element) {
        logger.info("Get text of " + element);
        String text = PropertiesHelpers.getPropValue(element);
        if (text == null) {
            text = element;
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(text))).getText();
    }

    public boolean verifyElementPresent(String element) {
        logger.info("verify Element Present " + element);
        String xPathElement = PropertiesHelpers.getPropValue(element);
        if (xPathElement == null) {
            xPathElement = element;
        }
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPathElement)));
            return true;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<WebElement> getListElementVisible(String element) {
        logger.info("verify List Element Visible " + element);
        String xPathElement = PropertiesHelpers.getPropValue(element);
        if (xPathElement == null) {
            xPathElement = element;
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        List<WebElement> webDriverList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xPathElement)));
        return webDriverList;
    }

    public List<WebElement> getListElementPresence(String element) {
        logger.info("verify List Element Presence " + element);
        String xPathElement = PropertiesHelpers.getPropValue(element);
        if (xPathElement == null) {
            xPathElement = element;
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        List<WebElement> webDriverList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xPathElement)));
        return webDriverList;
    }

    public void assertTrue(boolean status, String mess){
        Assert.assertTrue(status,mess);
    }

    public void verifyAttribute(String elementGetAttribute, String attribute, String expect) {
        logger.info("verifyAttribute " + elementGetAttribute + ": " + attribute + " == " + expect);
        String xPathElement1 = PropertiesHelpers.getPropValue(elementGetAttribute);
        String xPathElement2 = PropertiesHelpers.getPropValue(expect);
        if (xPathElement1 == null) {
            xPathElement1 = elementGetAttribute;
        }
        if (xPathElement2 == null) {
            xPathElement2 = expect;
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Assert.assertEquals(
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPathElement1))).getAttribute(attribute),
                xPathElement2);
    }

    public String getAttribute(String element, String attribute) {
        logger.info("get Attribute of" + element);
        String xPathElement = PropertiesHelpers.getPropValue(element);
        if (xPathElement == null) {
            xPathElement = element;
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement b = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPathElement)));
        String c = b.getAttribute(attribute);
        logger.info(c);
        return c;
    }

    public boolean verifyElementState(String element) {
        //trạng thái enabled/disables
        logger.info("verify Element State" + element);
        String xPathElement = PropertiesHelpers.getPropValue(element);
        if (xPathElement == null) {
            xPathElement = element;
        }
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPathElement))).isEnabled();
            return true;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void assertEquals(String expected, String actual) {
        logger.info("compare from " + expected + " with " + actual);
        String xPathElement1 = PropertiesHelpers.getPropValue(expected);
        String xPathElement2 = PropertiesHelpers.getPropValue(actual);
        if (xPathElement1 == null) {
            xPathElement1 = expected;
        }
        if (xPathElement2 == null) {
            xPathElement2 = actual;
        }
        String actualText = driver.findElement(By.xpath(xPathElement2)).getText();
        Assert.assertEquals(actualText, xPathElement1);
    }

    // Select
    public void selectOptionByText(String element, String text) {
        //chọn option với text đã truyền vào (dropdown tĩnh)
        logger.info("click" + element);
        String xPathElement = PropertiesHelpers.getPropValue(element);
        if (xPathElement == null) {
            xPathElement = element;
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Select select = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPathElement))));
        select.selectByVisibleText(text);
    }

    public void selectOptionByValue(String element, String value) {
        //chọn option với value đã truyền vào (dropdown tĩnh)
        logger.info("click" + element);
        String xPathElement = PropertiesHelpers.getPropValue(element);
        if (xPathElement == null) {
            xPathElement = element;
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Select select = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPathElement))));
        select.selectByValue(value);
    }

    public void selectOptionByIndex(String element, int index) {
        //chọn option với index đã truyền vào (dropdown tĩnh)
        logger.info("click" + element);
        String xPathElement = PropertiesHelpers.getPropValue(element);
        if (xPathElement == null) {
            xPathElement = element;
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Select select = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPathElement))));
        select.selectByIndex(index);
    }

    public void waitForJSLoad(Long timeoutWaitForPageLoad) {
        //chờ page load xong thì xử lý tiếp
        logger.info("wait for page load done");
        ExpectedCondition<Boolean> JSLoad = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutWaitForPageLoad));
            wait.until(JSLoad);
        }catch (Throwable error){
            Assert.fail("Timeout waiting for Page load request for JS");
        }
    }
}
