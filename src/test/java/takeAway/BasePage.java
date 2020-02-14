package takeAway;

import freemarker.log.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


public class BasePage {

    static WebDriver driver;
   JavascriptExecutor jExecutor;
    WebDriverWait driverWait;
     static Logger logger = Logger.getLogger(BasePage.class.getName());
     String propertyFile = "./src/test/resources/takeaway.properties";


//    protected WebDriver setupWebDriver() {
//
//
//
////        return driver;
//
//    }


    /**
     * Read data from properties file
     */
    public String getConfigPropertyValue(String propertyFileName, String propertyName) {
        String Value = null;
        try {
            FileInputStream fileIS = new FileInputStream(new File(propertyFileName));
            Properties prop = new Properties();
            prop.load(fileIS);

            Value = prop.getProperty(propertyName);
        } catch (IOException ex) {
            logger.info(ex.getMessage());
        }

        return Value;
    }

    /**
     * Convert path to absolute location
     */
    static String toAbsolutePath(String relativePath) {
        Path relPath = Paths.get(relativePath);
        Path absolutePath = null;
        if (!relPath.isAbsolute()) {
            Path base = Paths.get("");
            absolutePath = base.resolve(relPath).toAbsolutePath();
        }
        return absolutePath.normalize().toString();
    }

    /**
     * Get Operating System for mahcine running the test
     */
    public String getOsName() {
        String osType;
        String osName = "";

        osType = System.getProperty("os.name");

        if (osType.contains("Windows") || osType.contains("windows")) {
            osName = "Windows";
        } else if (osType.contains("Mac") || osType.contains("mac")) {
            osName = "Mac OS";
        } else if (osType.contains("Linux") || osType.contains("linux")) {
            osName = "Linux";
        }

        logger.info("os Type is ::: " + osType + "found os Name ::: " + osName);

        return osName;
    }

    /**
     * Wait for seconds while element not present
     */
    public void waitForElement(By selector) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    public void highLighterMethod(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 2px solid green;');", element);
    }

    /**
     * Wait for specified number of seconds
     */
    public void secondsDelay(int sec) {
        int timeInMilliSeconds;
        try {
            timeInMilliSeconds = sec * 1000;
            logger.info("##############################################");
            logger.info("Going for " + timeInMilliSeconds + " delay");
            logger.info("##############################################");
            Thread.sleep(timeInMilliSeconds);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    /**
     * Scroll to specific element on the page
     */
    public void scrollToElement( WebElement element) throws InterruptedException {
//        String key = "";
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].scrollIntoView(true);", element);
//        Thread.sleep(2000);

//        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView()", element);
    }
}