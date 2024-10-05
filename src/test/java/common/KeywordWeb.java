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
    private Select select;

    public KeywordWeb() {
    }

    public void openBrowser(String browser, String... url) {
        logger.info("Open browser");
        handleChromeNotifications();
        switch (browser.toUpperCase()) {
            case "CHROME":
                WebDriverManager.chromedriver().setup();
                System.setProperty("webdriver.chrome.driver","C:\\Users\\maia2\\.cache\\selenium\\chromedriver\\win64\\129.0.6668.89\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "EDGE":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
        }
        logger.info("open browser successfully " + browser);
        String rawUrl = url.length > 0 ? url[0] : "";
        if (rawUrl != null && !rawUrl.isEmpty()) {
            logger.info("go to url: " + rawUrl);
            driver.get(rawUrl);
        }
    }
    public void handleChromeNotifications() {
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--headless");
        ops.addArguments("--disable-notifications");
        ops.addArguments("--disable-extensions");
        ops.addArguments("disable-infobars");
//        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        driver = new ChromeDriver(ops);
    }

    public void setUp(String username, String accesskey, String baseURL){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("version", "109.0.5414.120");
        capabilities.setCapability("platform", "win10"); // If this cap isn't specified, it will just get any available one.
        capabilities.setCapability("build", "LambdaTestSampleApp");
        capabilities.setCapability("name", "LambdaTestJavaSample");
        capabilities.setCapability("network", true); // To enable network logs
        capabilities.setCapability("visual", true); // To enable step by step screenshot
        capabilities.setCapability("video", true); // To enable video recording
        capabilities.setCapability("console", true);
        try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + baseURL), capabilities);
        } catch (MalformedURLException e) {
            logger.info("Invalid grid URL");
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    public void resizeBrowser(int width, int height) {
        Dimension dimension = new Dimension(width, height);
        //Resize the current window to the given dimension
        driver.manage().window().setSize(dimension);
    }

    public void verifyUrl(String url){
        //verify url hiện tại với url truyền vào
        logger.info("Verify URL..." + url);
        Assert.assertTrue(getCurrentPageUrl().contains(url), "URL incorrect.");
    }

    public void verifyPageTitle(String title) {
        //verify title hiện tại với title truyền vào
        logger.info("Verify page title");
        Assert.assertTrue(driver.getTitle().equals(title));
    }

    public static void closeBrowser() {
        logger.info("close browser: ");
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle).quit();
        }
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPathElement1))).sendKeys(xPathElement2);
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
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPathElement1)));
        ele.clear();
        ele.sendKeys(xPathElement2);
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

    public void assertTrue(boolean status, String mess){
        Assert.assertTrue(status,mess);
    }
    public void assertFalse(boolean status, String mess){
        Assert.assertFalse(status,mess);
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

    public boolean verifyElementState(String element) {
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

    public void waitForJQueryLoad(Long timeoutWaitForPageLoad) {
        //chờ JQuery page load xong thì xử lý tiếp
        logger.info("wait for page load done");
        ExpectedCondition<Boolean> jqueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception error) {
                    return true;
                }
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutWaitForPageLoad));
            wait.until(jqueryLoad);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page load request for JQuery");
        }
    }
}
