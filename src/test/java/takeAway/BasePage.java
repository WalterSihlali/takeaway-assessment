package takeAway;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
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

    protected static WebDriver driver;
    protected JavascriptExecutor jExecutor;
    protected WebDriverWait driverWait;
    private static Logger logger = Logger.getLogger(BasePage.class);
    protected String propertyFile = "./src/test/resources/takeaway.properties";


    protected WebDriver setupWebDriver() {
        String macDriverLocation = "./src/test/resources//drivers/mac/";
        String linuxDriverLocation = "./src/test/resources//drivers/linux/";
        String windowsDriverLocation = "./src/test/resources/drivers/windows/";
        String browserName = getConfigPropertyValue(propertyFile, "browser");
        BasicConfigurator.configure();

        switch (browserName) {
            case "chrome":
                /**
                 * Driver setup for google chrome web browser
                 */
                String chromeDriverPath = null;

                if (this.getOsName().equalsIgnoreCase("Windows")) {
                    chromeDriverPath = windowsDriverLocation + "chromedriver.exe";
                } else if (this.getOsName().equalsIgnoreCase("Mac OS")) {
                    chromeDriverPath = macDriverLocation + "chromedriver";
                } else if (this.getOsName().equalsIgnoreCase("Linux")) {
                    chromeDriverPath = linuxDriverLocation + "chromedriver";
                }
                logger.info("This is the chrome driver path is :::: " + chromeDriverPath);

                String absoluteChromeDriverPath = toAbsolutePath(chromeDriverPath);
                logger.info("This is the chrome driver real path is :::: " + absoluteChromeDriverPath);

                System.setProperty("webdriver.chrome.driver", absoluteChromeDriverPath);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("test-type");
                options.addArguments("--disable-extensions");
                driver = new ChromeDriver(options);

                try {
                    jExecutor = (JavascriptExecutor) driver;
                    driver.manage().window().maximize();
                    driverWait = new WebDriverWait(driver, 5);
                } catch (Exception e) {
                    logger.info("The stack trace here happens when I try to maximize the screen");
                    logger.info(e.getStackTrace());
                }
                break;

            case "firefox":

                /**
                 * Driver setup for firefox web browser
                 */
                String firefoxDriverPath = null;
                logger.info("Firefox ?: " + browserName);
                if (this.getOsName().equalsIgnoreCase("Windows")) {
                    firefoxDriverPath = windowsDriverLocation + "geckodriver.exe";
                } else if (this.getOsName().equalsIgnoreCase("Mac OS")) {
                    firefoxDriverPath = macDriverLocation + "geckodriver";
                } else if (this.getOsName().equalsIgnoreCase("Linux")) {
                    firefoxDriverPath = linuxDriverLocation + "geckodriver";
                }
                logger.info("This is the firefox driver path is :::: " + firefoxDriverPath);

                String absoluteFirefoxDriverPath = toAbsolutePath(firefoxDriverPath);
                logger.info("This is the chrome driver real path is :::: " + absoluteFirefoxDriverPath);

                System.setProperty("webdriver.gecko.driver", absoluteFirefoxDriverPath);
                driver = new FirefoxDriver();

                try {

                    jExecutor = (JavascriptExecutor) driver;
                    driver.manage().window().maximize();
                    driverWait = new WebDriverWait(driver, 5);
                } catch (Exception ex) {
                    logger.info("The stack trace here happens when I try to maximize the screen");
                    logger.info(ex.getStackTrace());
                }
        }


        return driver;

    }


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
        } catch (IOException e) {
            logger.info(e.getStackTrace());
        }

        return Value;
    }

    /**
     * Convert path to absolute location
     */
    private static String toAbsolutePath(String relativePath) {
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
        WebDriverWait wait = new WebDriverWait(driver, 5);
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
            logger.info(e.getStackTrace());
        }
    }
    /**
     * Scroll to specific element on the page
     */
    public void scrollToElement(WebDriver driver, WebElement element) throws InterruptedException {
        String key = "";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(2000);
    }
}