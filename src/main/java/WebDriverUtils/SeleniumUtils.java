package WebDriverUtils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class SeleniumUtils {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(SeleniumUtils.class);

    /**
     * Constructor for SeleniumUtils
     *
     * @param driver WebDriver instance
     */
    public SeleniumUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Clicks on an element
     *
     * @param locator By locator of the element
     */
    public void click(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (TimeoutException e) {
            handleTimeoutException("Element not clickable within timeout: " + locator, e);
        }
    }

    /**
     * Sends keys to an element
     *
     * @param locator By locator of the element
     * @param text    Text to send
     */
    public void sendKeys(By locator, String text) {
        if (locator == null || text == null) {
            handleIllegalArgumentException("Locator or text is null");
        }
        try {
            WebElement element = waitForElement(ExpectedConditions.visibilityOfElementLocated(locator), "Element not visible: " + locator);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            handleError("Failed to clear and send keys to element: " + locator, e);
        }
    }

    /**
     * Gets text from an element
     *
     * @param locator By locator of the element
     * @return Text of the element
     */
    public String getText(By locator) {
        try {
            return waitForElement(ExpectedConditions.visibilityOfElementLocated(locator), "Element not visible: " + locator).getText();
        } catch (Exception e) {
            handleError("Error getting text from element: " + locator, e);
            return null;
        }
    }

    /**
     * Checks if an element is present
     *
     * @param locator By locator of the element
     * @return true if element is present, false otherwise
     */
    public boolean isElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Selects an option from a dropdown by visible text
     *
     * @param locator By locator of the dropdown element
     * @param text    Visible text of the option to select
     */
    public void selectByVisibleText(By locator, String text) {
        if (locator == null || text == null) {
            handleIllegalArgumentException("Locator or text is null");
        }
        try {
            Select select = new Select(wait.until(ExpectedConditions.presenceOfElementLocated(locator)));
            select.selectByVisibleText(text);
        } catch (Exception e) {
            handleError("Error selecting option from dropdown: " + locator, e);
        }
    }

    /**
     * Hovers over an element
     *
     * @param locator By locator of the element
     */
    public void hoverOverElement(By locator) {
        try {
            Actions actions = new Actions(driver);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            actions.moveToElement(element).perform();
        } catch (Exception e) {
            handleError("Error hovering over element: " + locator, e);
        }
    }

    /**
     * Performs drag and drop operation
     *
     * @param sourceLocator By locator of the source element
     * @param targetLocator By locator of the target element
     */
    public void dragAndDrop(By sourceLocator, By targetLocator) {
        try {
            Actions actions = new Actions(driver);
            WebElement source = wait.until(ExpectedConditions.presenceOfElementLocated(sourceLocator));
            WebElement target = wait.until(ExpectedConditions.presenceOfElementLocated(targetLocator));
            actions.dragAndDrop(source, target).perform();
        } catch (Exception e) {
            handleError("Error performing drag and drop", e);
        }
    }

    /**
     * Switches to a frame
     *
     * @param frameLocator By locator of the frame
     */
    public void switchToFrame(By frameLocator) {
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
        } catch (Exception e) {
            handleError("Error switching to frame: " + frameLocator, e);
        }
    }

    /**
     * Switches to default content
     */
    public void switchToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            handleError("Error switching to default content", e);
        }
    }

    /**
     * Accepts an alert
     */
    public void acceptAlert() {
        try {
            wait.until(ExpectedConditions.alertIsPresent()).accept();
        } catch (Exception e) {
            handleError("Error accepting alert", e);
        }
    }

    /**
     * Dismisses an alert
     */
    public void dismissAlert() {
        try {
            wait.until(ExpectedConditions.alertIsPresent()).dismiss();
        } catch (Exception e) {
            handleError("Error dismissing alert", e);
        }
    }

    /**
     * Gets text from an alert
     *
     * @return Text of the alert
     */
    public String getAlertText() {
        try {
            return wait.until(ExpectedConditions.alertIsPresent()).getText();
        } catch (Exception e) {
            handleError("Error getting alert text", e);
            return null;
        }
    }

    /**
     * Sends keys to an alert
     *
     * @param text Text to send to the alert
     */
    public void sendKeysToAlert(String text) {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.sendKeys(text);
        } catch (Exception e) {
            handleError("Error sending keys to alert", e);
        }
    }

    /**
     * Scrolls to an element
     *
     * @param locator By locator of the element
     */
    public void scrollToElement(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            handleError("Error scrolling to element: " + locator, e);
        }
    }

    /**
     * Waits for page to load completely
     */
    public void waitForPageLoad() {
        try {
            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            handleError("Page load timeout", e);
        }
    }

    /**
     * Gets attribute value of an element
     *
     * @param locator        By locator of the element
     * @param attributeName Name of the attribute
     * @return Value of the attribute
     */
    public String getAttributeValue(By locator, String attributeName) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator)).getAttribute(attributeName);
        } catch (Exception e) {
            handleError("Error getting attribute value: " + locator, e);
            return null;
        }
    }

    /**
     * Waits for an element to disappear
     *
     * @param locator By locator of the element
     */
    public void waitForElementToDisappear(By locator) {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            handleError("Element did not disappear: " + locator, e);
        }
    }

    /**
     * Clears an input field and sends keys
     *
     * @param locator By locator of the element
     * @param text    Text to send after clearing
     */
    public void clearAndSendKeys(By locator, String text) {
        if (locator == null || text == null) {
            handleIllegalArgumentException("Locator or text is null");
        }
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            handleError("Error clearing and sending keys to element: " + locator, e);
        }
    }

    /**
     * Executes JavaScript in the browser
     *
     * @param script The JavaScript to execute
     * @param args   Arguments to pass to the script (optional)
     * @return The result of the script execution
     */
    public Object executeJavaScript(String script, Object... args) {
        try {
            return ((JavascriptExecutor) driver).executeScript(script, args);
        } catch (Exception e) {
            handleError("Error executing JavaScript: " + script, e);
            return null;
        }
    }

    /**
     * Waits for an element with the given ExpectedCondition
     *
     * @param condition    ExpectedCondition to wait for
     * @param errorMessage Error message to log if condition fails
     * @return The WebElement once it is located and visible
     */
    private WebElement waitForElement(ExpectedCondition<WebElement> condition, String errorMessage) {
        try {
            return wait.until(condition);
        } catch (TimeoutException e) {
            handleError(errorMessage, e);
            return null;
        }
    }

    /**
     * Refreshes the current page
     */
    public void refreshPage() {
        try {
            driver.navigate().refresh();
        } catch (Exception e) {
            handleError("Error refreshing page", e);
        }
    }

    /**
     * Maximizes the current window
     */
    public void maximizeWindow() {
        try {
            driver.manage().window().maximize();
        } catch (Exception e) {
            handleError("Error maximizing window", e);
        }
    }

    /**
     * Takes a screenshot of the current screen and saves it to the specified file path.
     *
     * @param filePath File path to save the screenshot
     */
    public void takeScreenshot(String filePath) {
        try {
            // Take screenshot and save it to a file
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(filePath);
            ImageIO.write(ImageIO.read(screenshotFile), "png", destinationFile);

            System.out.println("Screenshot saved to: " + destinationFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error: Unable to save screenshot to " + filePath);
            e.printStackTrace();
        } catch (WebDriverException e) {
            System.err.println("WebDriverException: Unable to take screenshot");
            e.printStackTrace();
        }
    }
    /**
     * Quits the WebDriver instance
     */
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    
    public void openUrl(String url) {
        try {
            driver.get(url);
            System.out.println("Opened URL: " + url);
        } catch (Exception e) {
            System.err.println("Error opening URL: " + url);
            e.printStackTrace();
            // Handle the error as needed, such as throwing an exception or logging it
        }
    }

    
    /**
     * Gets the current page title
     * @return The current page title
     */
    public String getPageTitle() {
        try {
            return driver.getTitle();
        } catch (Exception e) {
            logger.error("Error getting page title", e);
            throw e;
        }
    }
   
    // Helper methods for error handling and logging
    private void handleIllegalArgumentException(String message) {
        logger.error(message);
        throw new IllegalArgumentException(message);
    }

    private void handleTimeoutException(String message, TimeoutException e) {
        logger.error(message, e);
        throw e;
    }

    private void handleError(String message, Exception e) {
        logger.error(message, e);
    }
}
