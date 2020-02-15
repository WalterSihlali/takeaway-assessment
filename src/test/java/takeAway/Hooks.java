package takeAway;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class Hooks extends BasePage {

    @Before
    public void setUp() {
        String macDriverLocation = "./src/test/resources//drivers/mac/";
        String linuxDriverLocation = "./src/test/resources//drivers/linux/";
        String windowsDriverLocation = "./src/test/resources/drivers/windows/";
        String browserName = getConfigPropertyValue(propertyFile, "browser");
        String runInHeadlessMode = getConfigPropertyValue(propertyFile, "headless");

        switch (browserName) {
            case "chrome":
                try{
                    /**
                     * Driver setup for google chrome web browser
                     */
                    String chromeDriverPath = null;

                    if (this.getOsName().equalsIgnoreCase("Windows")) { chromeDriverPath = windowsDriverLocation + "chromedriver.exe";
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
                    options.addArguments("window-size=1980,1080");
                    options.addArguments("--disable-extensions");
                    options.addArguments("--proxy-server='direct://'");
                    options.addArguments("--proxy-bypass-list=*");

                    if(runInHeadlessMode.equalsIgnoreCase("yes")) {
                        options.addArguments("--headless");
                    }
                    driver = new ChromeDriver(options);

                    try {
                        jExecutor = (JavascriptExecutor) driver;
                        driver.manage().window().maximize();
                        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
                        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
                    } catch (Exception ex) {
                        logger.info("The stack trace here happens when I try to maximize the screen");
                        logger.info(ex.getMessage());
                    }
                }catch (WebDriverException ex) {
                    logger.info("Unable to instantiate the chrome driver",ex);
                }
                break;

            case "firefox":
               try{
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
                       driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
                   } catch (Exception ex) {
                       logger.info("The stack trace here happens when I try to maximize the screen");
                       logger.info(ex.getMessage());
                   }
               } catch (WebDriverException ex) {
                   logger.info("Unable to instantiate firefox driver",ex);
               }
               break;
        }
    }

    @After
    public void tearDown() {
        if(driver != null) {
            driver.close();
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (driver != null) {
                logger.info("After features run " + driver);
                if(scenario.isFailed()) {
                    try {
                       captureScreenshot();
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                    }
                }
                driver.quit();
                driver = null;
            }
        } catch (WebDriverException ex) {
            logger.info(ex.getMessage());
        }
    }

}
