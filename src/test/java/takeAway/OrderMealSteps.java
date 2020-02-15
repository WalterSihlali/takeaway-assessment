package takeAway;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import freemarker.log.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.NoSuchElementException;

public class OrderMealSteps extends BaseUtilities {

    private static Logger logger = Logger.getLogger(OrderMealSteps.class.getName());
    private String menuPrice;
    private String mealName;
    private String selectedDrink;

    @Given("^user launch takeaway web application$")
    public void user_launch_takeaway_web_app() {
        try {
            driver.get(appURL);
        } catch (WebDriverException ex) {
            logger.info(ex.getMessage());
        }
    }

    @Then("^user is on takeaway landing page$")
    public void user_is_on_take_away_landing_page() {
        try {
            secondsDelay(2);
            String landingPageTitle = driver.getTitle();
            Assert.assertEquals( getConfigPropertyValue(propertyFile, "landing_page_title"),landingPageTitle);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user can see time to order food message$")
    public void user_can_see_time_to_order_food_message() {
        try {
            String message = driver.findElement(By.className(PageObjects.LANDING_PAGE_HEADER)).getText();
            Assert.assertEquals(message, getConfigPropertyValue(propertyFile, "landing_page_message"));
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @When("^user search for address \"([^\"]*)\"$")
    public void user_enter_search_for_address(String address) {
        try {
            waitForElement(By.id(PageObjects.ADDRESS_SEARCH_AREA));
            WebElement addressField = driver.findElement(By.id(PageObjects.ADDRESS_SEARCH_AREA));
            highLighterMethod(driver, addressField);
            addressField.clear();
            addressField.sendKeys(address);
            secondsDelay(5);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @When("^user search for restaurant \"([^\"]*)\"$")
    public void user_enter_search_for_restaurant(String restaurant) {
        try {
            waitForElement(By.id(PageObjects.SEARCH_RESTAURANT));
            WebElement addressField = driver.findElement(By.id(PageObjects.SEARCH_RESTAURANT));
            highLighterMethod(driver, addressField);
            addressField.clear();
            addressField.sendKeys(restaurant);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @When("^user search for meal \"([^\"]*)\"$")
    public void user_enter_search_for_meals(String meal) {
        try {
            waitForElement(By.className(PageObjects.SEARCH_ICON));
            driver.findElement(By.className(PageObjects.SEARCH_ICON)).click();
            WebElement addressField = driver.findElement(By.className(PageObjects.SEARCH_MEAL));
            highLighterMethod(driver, addressField);
            addressField.clear();
            addressField.sendKeys(meal);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }


    @And("^user select address \"([^\"]*)\" from search results$")
    public void user_select_address_from_search_results(String address) {
        try {
            waitForElement(By.xpath(PageObjects.SEARCH_RESULTS_VALUE));
            WebElement searchResults = driver.findElement(By.xpath(PageObjects.SEARCH_RESULTS_VALUE));
            highLighterMethod(driver, searchResults);
            String searchResultsValue = searchResults.getText();

            if (searchResultsValue.contains(address)) {
                searchResults.click();
                waitForElement(By.xpath(PageObjects.SEARCH_RESULTS_MULTIPLE_LOCATION));
                WebElement multipleLocationsMessage = driver.findElement(By.xpath(PageObjects.SEARCH_RESULTS_MULTIPLE_LOCATION));
                if (multipleLocationsMessage.isDisplayed()) {
                    List<WebElement> addressLocations = driver
                            .findElement(By.className(PageObjects.POPUP_OPTIONS))
                            .findElements(By.xpath(PageObjects.ADDRESS_LOCATIONS));
                    addressLocations.get(0).click();
                }
            }

        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        } catch (NoAlertPresentException ex) {
            logger.info("No alert found on the page",ex);
        }
    }


    @Then("^search results popup is shown$")
    public void search_results_popup_is_shown() {
        try {
            int size = driver.findElements(By.xpath(PageObjects.ADDRESS_SUGGESTION)).size();
            Assert.assertEquals(1, size);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        } catch (NoAlertPresentException ex) {
            logger.info("No alert found on the page",ex);
        }
    }

    @When("^user select show address button$")
    public void user_select_show_address_button() {
        try {
            waitForElement(By.id(PageObjects.SHOW_DDRESS_RESULTS));
           driver.findElement(By.id(PageObjects.SHOW_DDRESS_RESULTS)).click();
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @Then("^invalid searched address message is shown$")
    public void invalid_searched_address_message_is_shown() {
        try {
            waitForElement(By.id(PageObjects.ADDRESS_ERROR));
            int size = driver.findElements(By.id(PageObjects.ADDRESS_ERROR)).size();
            Assert.assertEquals(1, size);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }
    @Then("^user is on searched address page$")
    public void user_is_on_searched_address_page() {
        try {
            secondsDelay(2);
            String pageTitle = driver.getTitle();
            Assert.assertEquals(pageTitle, getConfigPropertyValue(propertyFile, "order_address_page_title"));
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^address restaurants list is shown$")
    public void user_can_listed_restaurants_list() {
        try {
            waitForElement(By.className(PageObjects.LISTED_RESTAURANTS_NUMBER));
            String listedRestaurants = driver.findElement(By.className(PageObjects.LISTED_RESTAURANTS_NUMBER)).getText();
            int numberOfRestaurants = Integer.parseInt(listedRestaurants);
            Assert.assertTrue(numberOfRestaurants > 0);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        } catch (TimeoutException ex) {
            logger.info("Timed out waiting for the element",ex);
        }
    }

    @And("^searched restaurants not found$")
    public void user_can_found_searched_restaurant() {
        try {
            waitForElement(By.id(PageObjects.SEARCH_RESTAURANT_NOT_FOUND));
            int size = driver.findElements(By.id(PageObjects.SEARCH_RESTAURANT_NOT_FOUND)).size();
            Assert.assertEquals(1,size);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        } catch (TimeoutException ex) {
            logger.info("Timed out waiting for the element",ex);
        }
    }

    @When("^user select restaurant listed under address$")
    public void user_select_restaurants_from_address_list() {
        try {
            waitForElement(By.id(PageObjects.RESTAURANT_FROM_LIST));
            driver.findElement(By.id(PageObjects.RESTAURANT_FROM_LIST)).click();
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @Then("^user is on restaurant \"([^\"]*)\" details page$")
    public void user_is_on_restaurant_details_page(String restaurantName) {
        try {
            String name = driver.findElement(By.className(PageObjects.RESTAURANT_NAME)).getText();
            Assert.assertEquals(name, restaurantName.trim());
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @When("^user select first menu on the menu list$")
    public void user_select_first_menu_on_the_menu_list() {
        try {
            driver.findElement(By.id(PageObjects.RESTAURANT_MENU_ITEM)).click();
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @Then("^user can see selected drink details$")
    public void user_select_menu_drink_selected() {
        try {
            secondsDelay(2);
            Select select = new Select(driver.findElement(By.name(PageObjects.SELECTED_DRINK)));
            WebElement option = select.getFirstSelectedOption();
            selectedDrink = option.getText();
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @When("^user select cart order button$")
    public void user_select_cart_order_button() {
        try {
            driver.findElement(By.className(PageObjects.CART_ORDER_BUTTON)).click();
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @Then("^meal for purchase details are shown$")
    public void menu_details_are_shown() {
        try {
            waitForElement(By.className(PageObjects.MEAL_NAME));
            secondsDelay(1);
            mealName = driver.findElement(By.className(PageObjects.MEAL_NAME)).getText();
            String  mealPrice = driver.findElement(By.className(PageObjects.MEAL_PRICE)).getText();
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @Then("^user can see menu total price to pay$")
    public void user_can_see_menu_total_price_to_pay() {
        try {
            waitForElement(By.className(PageObjects.MEAL_PRICE_BUTTON));
            secondsDelay(1);
            String totalPrice = driver.findElement(By.className(PageObjects.MEAL_PRICE_BUTTON)).getText().replace("Add", "").trim();
            String currence = totalPrice.substring(0, 1);
            Assert.assertEquals(currence, "€");
            menuPrice = totalPrice;
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        } catch (TimeoutException ex) {
            logger.info("Timed out waiting for element",ex);
        }
    }

    @Then("^user can see cart total price$")
    public void user_can_see_cart_total_price() {
        try {
            waitForElement(By.className(PageObjects.CART_TOTAL));
            String cartTotal = driver.findElement(By.className(PageObjects.CART_TOTAL)).getText();
            Assert.assertEquals(menuPrice, cartTotal);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        } catch (TimeoutException ex) {
            logger.info("Timed out waiting for element",ex);
        }
    }

    @Then("^user is on ready to eat page$")
    public void user_is_on_ready_to_eat_page() {
        try {
            boolean isDisplayed = driver.findElement(By.className(PageObjects.ON_READY_TO_EAT_PAGE)).isDisplayed();
            Assert.assertTrue(isDisplayed);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user select button to add menu to cart$")
    public void user_select_button_to_add_menu_to_cart() {
        try {
            secondsDelay(3);
            driver.findElement(By.className(PageObjects.MEAL_PRICE_BUTTON)).click();
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user can see delivery address details header$")
    public void user_can_see_delivery_address_details_header() {
        try {
            int size = driver.findElements(By.className(PageObjects.ON_DELIVERY_PAGE)).size();
            Assert.assertEquals(1, size);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user enter delivery address \"([^\"]*)\"$")
    public void user_enter_delivery_address(String deliveryAddress) {
        try {
            WebElement addressEditText = driver.findElement(By.id(PageObjects.ADDRESS));
            highLighterMethod(driver, addressEditText);
            addressEditText.clear();
            addressEditText.sendKeys(deliveryAddress);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user enter delivery postal code \"([^\"]*)\"$")
    public void user_enter_delivery_postal_code(String postalCode) {
        try {
            waitForElement(By.id(PageObjects.POSTAL_CODE));
            WebElement postalCodeEditText = driver.findElement(By.id(PageObjects.POSTAL_CODE));
            highLighterMethod(driver, postalCodeEditText);
            postalCodeEditText.clear();
            postalCodeEditText.sendKeys(postalCode);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user enter delivery city \"([^\"]*)\"$")
    public void user_enter_delivery_city(String city) {
        try {
            WebElement cityEditText = driver.findElement(By.id(PageObjects.CITY));
            highLighterMethod(driver, cityEditText);
            cityEditText.clear();
            cityEditText.sendKeys(city);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user enter delivery person name \"([^\"]*)\"$")
    public void user_enter_delivery_person_name(String personName) {
        try {
            WebElement nameEditText = driver.findElement(By.id(PageObjects.NAME));
            highLighterMethod(driver, nameEditText);
            nameEditText.clear();
            nameEditText.sendKeys(personName);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user enter email \"([^\"]*)\"$")
    public void user_enter_email(String email) {
        try {
            WebElement emailEditText = driver.findElement(By.id(PageObjects.EMAIL));
            highLighterMethod(driver, emailEditText);
            emailEditText.clear();
            emailEditText.sendKeys(email);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user enter delivery phone number \"([^\"]*)\"$")
    public void user_enter_delivery_phone_number(String phoneNumber) {
        try {
            WebElement phoneEditText = driver.findElement(By.id(PageObjects.PHONE_NUMBER));
            highLighterMethod(driver, phoneEditText);
            phoneEditText.clear();
            phoneEditText.sendKeys(phoneNumber);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user enter company name \"([^\"]*)\"$")
    public void user_enter_company_name(String companyName) {
        try {
            WebElement companyEditText = driver.findElement(By.id(PageObjects.COMPANY_NAME));
            highLighterMethod(driver, companyEditText);
            companyEditText.clear();
            companyEditText.sendKeys(companyName);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @Then("^as soon as possible delivery time selected \"([^\"]*)\"$")
    public void user_select_delivery_time(String expectedDeliveryTime) {
        try {
            Select dropDown = new Select(driver.findElement(By.id(PageObjects.DELIVERY_TIME)));
            dropDown.selectByVisibleText(expectedDeliveryTime);

        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user enter delivery remarks \"([^\"]*)\"$")
    public void user_enter_delivery_remarks(String remarks) {
        try {
            WebElement remarksTextArea = driver.findElement(By.id(PageObjects.DELIVERY_REMARKS));
            highLighterMethod(driver, remarksTextArea);
            remarksTextArea.clear();
            remarksTextArea.sendKeys(remarks);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user select save delivery remarks for next order$")
    public void user_select_save_delivery_remarks_for_next_order() {
        try {
            driver.findElement(By.id(PageObjects.SAVE_DELIVERY_REMARKS)).click();
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user select pay with option \"([^\"]*)\"$")
    public void user_select_pay_with_option(String payWith) {
        try {
            scrollToElement(driver.findElement(By.id(PageObjects.PAY_WITH)));
            Select dropDown = new Select(driver.findElement(By.id(PageObjects.PAY_WITH)));
            dropDown.selectByVisibleText(payWith);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user select recieve discounts, loyalty and updates$")
    public void user_select_recieve_discounts_loyalty_and_updates() {
        try {
            driver.findElement(By.id(PageObjects.NEWS_LETTER_CHECKBOX)).click();
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user select order and pay button$")
    public void user_select_order_and_pay_button() {
        try {
            driver.findElement(By.className(PageObjects.ORDER_AND_PAY_BUTTON)).click();
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @Then("^user can see thank you for your order message$")
    public void user_can_see_thank_you_for_your_order_message() {
        try {
            secondsDelay(5);
            int size = driver.findElements(By.className(PageObjects.THANK_YOU_WE_RECIEVED_ORDER)).size();
            Assert.assertEquals(1, size);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }


    @And("^user can see copy food tracker link$")
    public void user_can_see_copy_food_tracker_link() {
        try {
            boolean isDisplayed = driver.findElement(By.className(PageObjects.COPY_FOOD_TRACKER_LINK)).isDisplayed();
            Assert.assertTrue(isDisplayed);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user can see restaurant order from name \"([^\"]*)\"$")
    public void user_can_see_restaurant_order_from_name(String restaurantName) {
        try {
            String name = driver.findElement(By.className(PageObjects.ORDER_RESTAURANT)).getText();
            Assert.assertEquals(name, restaurantName);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }

    @And("^user can see ordered meal details$")
    public void user_can_see_ordered_meal_details() {
        try {
            secondsDelay(1);
            scrollToElement(driver.findElement(By.className(PageObjects.SUCCESS_ORDER_MENU_NAME)));
            String orderedMealName = driver.findElement(By.className(PageObjects.SUCCESS_ORDER_MENU_NAME)).getText();
            String orderedDrinkName = driver.findElement(By.className(PageObjects.SUCCESS_ORDER_MENU_SIDES)).getText();
            secondsDelay(2);

            Assert.assertEquals(orderedMealName, mealName);
            Assert.assertTrue(selectedDrink.contains(orderedDrinkName));
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }

    }

    @Then("^user can see order success reference number$")
    public void user_can_see_payment_success_reference_number() {
        try {
            String referenceNumber = driver.findElement(By.className(PageObjects.SUCCESS_ORDER_REFERENCE)).getText();
            logger.info("Order reference number is : " + referenceNumber);
        } catch (NoSuchElementException ex) {
            logger.info("Element not found",ex);
        }
    }
}
