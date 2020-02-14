package takeAway;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
public class ParallelExecution extends BasePage {

    OrderMealSteps orderMealSteps = new OrderMealSteps();

    @BeforeTest
    @Parameters({"browser"})
    public void before(String browser) {
        String macDriverLocation = "./src/test/resources//drivers/mac/";
        String linuxDriverLocation = "./src/test/resources//drivers/linux/";
        String windowsDriverLocation = "./src/test/resources/drivers/windows/";
        String runInHeadlessMode = getConfigPropertyValue(propertyFile, "headless");

        switch (browser) {
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
                    driverWait = new WebDriverWait(driver, 5);
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
        System.out.println("Sample test-method One. Thread id is: ");
        driver.get("https://www.thuisbezorgd.nl/en/");
        orderMealSteps.user_is_on_take_away_landing_page();
        orderMealSteps.user_enter_search_for_address("8888");
        orderMealSteps.user_can_see_time_to_order_food_message();
        orderMealSteps.search_results_popup_is_shown();
        orderMealSteps.user_select_address_from_search_results("8888");
        orderMealSteps.user_is_on_searched_address_page();
        orderMealSteps.user_can_listed_restaurants_list();
        orderMealSteps.user_select_restaurants_from_address_list();
        orderMealSteps.user_is_on_restaurant_details_page("");
        orderMealSteps.user_can_see_ordered_meal_details();
        orderMealSteps.user_select_first_menu_on_the_menu_list();
        orderMealSteps.user_select_menu_drink_selected();
        orderMealSteps.user_can_see_menu_total_price_to_pay();
        orderMealSteps.user_select_button_to_add_menu_to_cart();
        orderMealSteps.user_can_see_cart_total_price();
        orderMealSteps.user_select_cart_order_button();
        orderMealSteps.user_is_on_ready_to_eat_page();
        orderMealSteps.user_can_see_delivery_address_details_header();
        orderMealSteps.user_enter_search_for_address("add");
        orderMealSteps.user_enter_delivery_postal_code("co");
        orderMealSteps.user_enter_delivery_city("ci");
        orderMealSteps.user_enter_delivery_person_name("nam");
        orderMealSteps.user_enter_email("em");
        orderMealSteps.user_enter_delivery_phone_number("ph");
        orderMealSteps.user_enter_company_name("com");
        orderMealSteps.user_select_delivery_time("As soon as possible");
        orderMealSteps.user_enter_delivery_remarks("remark");
        orderMealSteps.user_select_save_delivery_remarks_for_next_order();
        orderMealSteps.user_select_pay_with_option("pay with");

        orderMealSteps.user_select_recieve_discounts_loyalty_and_updates();
        orderMealSteps.user_select_order_and_pay_button();
        orderMealSteps.user_can_see_thank_you_for_your_order_message();
        orderMealSteps.user_can_see_copy_food_tracker_link();

        orderMealSteps.user_can_see_restaurant_order_from_name("resta ord");
        orderMealSteps.user_can_see_ordered_meal_details();
        orderMealSteps.user_can_see_payment_success_reference_number();

    }

}
