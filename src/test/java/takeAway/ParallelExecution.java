package takeAway;

import org.junit.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class ParallelExecution extends BaseUtilities {

    private String propertyFile = "./src/test/resources/takeaway.properties";
    private String appURL = getConfigPropertyValue(propertyFile, "url");


    @Parameters({"browser"})
    @BeforeTest
    public void before(String browser) {
        String macDriverLocation = getConfigPropertyValue(propertyFile, "mac_driver_location");
        ;
        String linuxDriverLocation = getConfigPropertyValue(propertyFile, "linux_driver_location");
        String windowsDriverLocation = getConfigPropertyValue(propertyFile, "windows_driver_location");
        String runInHeadlessMode = getConfigPropertyValue(propertyFile, "headless");

       switch(browser) {
               /**
                * Driver setup for google chrome web browser
                */
           case "chrome" :
               String chromeDriverPath = null;

               if (this.getOsName().equalsIgnoreCase("Windows")) {
                   chromeDriverPath = macDriverLocation + getConfigPropertyValue(propertyFile, "windows_driver_chrome");
               } else if (this.getOsName().equalsIgnoreCase("Mac OS")) {
                   chromeDriverPath = macDriverLocation + getConfigPropertyValue(propertyFile, "mac_driver_chrome");
               } else if (this.getOsName().equalsIgnoreCase("Linux")) {
                   chromeDriverPath = macDriverLocation + getConfigPropertyValue(propertyFile, "linux_driver_chrome");
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

               if (runInHeadlessMode.equalsIgnoreCase("yes")) {
                   options.addArguments("--headless");
               }
               driver = new ChromeDriver(options);
               driver.manage().window().maximize();
               driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
               break;

           case "firefox":

                /**
                 * Driver setup for firefox web browser
                 */
                String firefoxDriverPath = null;
                logger.info("Firefox ?: " + browser);
                if (this.getOsName().equalsIgnoreCase("Windows")) {
                    firefoxDriverPath = windowsDriverLocation + getConfigPropertyValue(propertyFile, "windows_driver_firefox");
                    ;
                } else if (this.getOsName().equalsIgnoreCase("Mac OS")) {
                    firefoxDriverPath = macDriverLocation + getConfigPropertyValue(propertyFile, "mac_driver_firefox");
                } else if (this.getOsName().equalsIgnoreCase("Linux")) {
                    firefoxDriverPath = linuxDriverLocation + getConfigPropertyValue(propertyFile, "linux_driver_firefox");
                }
                logger.info("This is the firefox driver path is :::: " + firefoxDriverPath);

                String absoluteFirefoxDriverPath = toAbsolutePath(firefoxDriverPath);
                logger.info("This is the chrome driver real path is :::: " + absoluteFirefoxDriverPath);

                System.setProperty("webdriver.gecko.driver", absoluteFirefoxDriverPath);
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                break;
        }
    }

    @AfterSuite
    public void after() {
        if (driver != null) {
            driver.quit();

        }
    }

@Test
public void testParallelChromeTest() {
        driver.get(appURL);
        System.out.println("Test Case One with Thread Id:- "
                + Thread.currentThread().getId());
            String landingPageTitle = driver.getTitle();
            Assert.assertEquals(getConfigPropertyValue(propertyFile, "landing_page_title"), landingPageTitle);
            secondsDelay(4);
    }
}
