package takeAway;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
public class ParallelExecution extends BaseUtilities {

   private OrderMealSteps orderMealSteps = new OrderMealSteps();
   private String propertyFile = "./src/test/resources/takeaway.properties";
   private String appURL = getConfigPropertyValue(propertyFile, "url");


    @BeforeTest
    @Parameters({"browser"})
    public void before(String browser) {
        String macDriverLocation = getConfigPropertyValue(propertyFile, "mac_driver_location");;
        String linuxDriverLocation = getConfigPropertyValue(propertyFile, "linux_driver_location");
        String windowsDriverLocation = getConfigPropertyValue(propertyFile, "windows_driver_location");
        String runInHeadlessMode = getConfigPropertyValue(propertyFile, "headless");

        switch (browser) {
            case "chrome":
                /**
                 * Driver setup for google chrome web browser
                 */
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

                try {
                    jExecutor = (JavascriptExecutor) driver;
                    driver.manage().window().maximize();
                    driverWait = new WebDriverWait(driver, 10);
                } catch (Exception ex) {
                    logger.info("The stack trace here happens when I try to maximize the screen");
                    logger.info(ex.getMessage());
                }
                break;

            case "firefox":

                /**
                 * Driver setup for firefox web browser
                 */
                String firefoxDriverPath = null;
                logger.info("Firefox ?: " + browser);
                if (this.getOsName().equalsIgnoreCase("Windows")) {
                    firefoxDriverPath = windowsDriverLocation + getConfigPropertyValue(propertyFile, "windows_driver_firefox");;
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

                try {

                    jExecutor = (JavascriptExecutor) driver;
                    driver.manage().window().maximize();
                    driverWait = new WebDriverWait(driver, 10);
                } catch (Exception ex) {
                    logger.info("The stack trace here happens when I try to maximize the screen");
                    logger.info(ex.getMessage());
                }
        }
    }

    @AfterSuite
    public void after() {
        if (driver != null) {
            driver.quit();

        }
    }

    @Test
    public void parallelTest() {
        driver.get(appURL);
        orderMealSteps.user_is_on_take_away_landing_page();
        orderMealSteps.user_can_see_time_to_order_food_message();
        orderMealSteps.user_enter_search_for_address(getConfigPropertyValue(propertyFile, "address"));
        orderMealSteps.search_results_popup_is_shown();
        orderMealSteps.user_select_address_from_search_results(getConfigPropertyValue(propertyFile, "address"));
        orderMealSteps.user_is_on_searched_address_page();
        orderMealSteps.user_can_listed_restaurants_list();
        orderMealSteps.user_select_restaurants_from_address_list();
        orderMealSteps.user_is_on_restaurant_details_page(getConfigPropertyValue(propertyFile, "restaurant_name"));
        orderMealSteps.user_select_first_menu_on_the_menu_list();
        orderMealSteps.user_select_menu_drink_selected();
        orderMealSteps.user_can_see_menu_total_price_to_pay();
        orderMealSteps.user_select_button_to_add_menu_to_cart();
        orderMealSteps.user_can_see_cart_total_price();
        orderMealSteps.user_select_cart_order_button();
        orderMealSteps.user_is_on_ready_to_eat_page();
        orderMealSteps.user_can_see_delivery_address_details_header();
        orderMealSteps.user_enter_delivery_address(getConfigPropertyValue(propertyFile, "delivery_address"));
        orderMealSteps.user_enter_delivery_postal_code(getConfigPropertyValue(propertyFile, "postal_code"));
        orderMealSteps.user_enter_delivery_city(getConfigPropertyValue(propertyFile, "city"));
        orderMealSteps.user_enter_delivery_person_name(getConfigPropertyValue(propertyFile, "person_name"));
        orderMealSteps.user_enter_email(getConfigPropertyValue(propertyFile, "email"));
        orderMealSteps.user_enter_delivery_phone_number("phone_number");
        orderMealSteps.user_enter_company_name("company_name");
        orderMealSteps.user_select_delivery_time(getConfigPropertyValue(propertyFile, "delivery_time"));
        orderMealSteps.user_enter_delivery_remarks(getConfigPropertyValue(propertyFile, "remarks"));
        orderMealSteps.user_select_save_delivery_remarks_for_next_order();
        orderMealSteps.user_select_pay_with_option("€ 19,00");
        orderMealSteps.user_select_recieve_discounts_loyalty_and_updates();
        orderMealSteps.user_select_order_and_pay_button();
        orderMealSteps.user_can_see_thank_you_for_your_order_message();
        orderMealSteps.user_can_see_copy_food_tracker_link();
        orderMealSteps.user_can_see_restaurant_order_from_name(getConfigPropertyValue(propertyFile, "restaurant_name"));
        orderMealSteps.user_can_see_ordered_meal_details();
        orderMealSteps.user_can_see_payment_success_reference_number();
    }
}
