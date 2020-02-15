Feature: As a user I want to be able to order a meal from my favourite restaurant near my location on takeaway.com

#  @regression @positive-scenario
  Scenario Outline: Order food from takeway.com
    Given user launch takeaway web application
    Then user is on takeaway landing page
    And user can see time to order food message
    When user search for address "<address>"
    Then search results popup is shown
    When user select address "<address>" from search results
    Then user is on searched address page
    And address restaurants list is shown
    When user search for restaurant "<restaurant name>"
    Then address restaurants list is shown
    When user select restaurant listed under address
    Then user is on restaurant "<restaurant name>" details page
    When user search for meal "<meal name>"
    And meal for purchase details are shown
    When user select first menu on the menu list
    And user can see selected drink details
    Then user can see menu total price to pay
    When user select button to add menu to cart
    Then user can see cart total price
    When user select cart order button
    Then user is on ready to eat page
    And user can see delivery address details header
    When user enter delivery address "<delivery address>"
    And user enter delivery postal code "<postal code>"
    And user enter delivery city "<city>"
    And user enter delivery person name "<person name>"
    And user enter email "<email>"
    And user enter delivery phone number "<phone number>"
    And user enter company name "<company name>"
    Then as soon as possible delivery time selected "<delivery time>"
    When user enter delivery remarks "<remarks>"
    And user select save delivery remarks for next order
    And user select pay with option "<pay with>"
    And user select recieve discounts, loyalty and updates
    And user select order and pay button
    Then user can see thank you for your order message
    And user can see copy food tracker link
    And user can see restaurant order from name "<restaurant name>"
    And user can see ordered meal details
    And user can see order success reference number

    Examples:
      | address | restaurant name          | meal name   | delivery address | city     | postal code | person name | email              | phone number | company name | delivery time       | remarks                          | pay with |
      | 8888    | TEST Restaurant Selenium | Duck Breast | main street 2415 | Enschede | 8888AA      | TestUSer    | testuser@test.test | 1234567890   | Takeaway.com | As soon as possible | Leave the order at the reception | € 19,00  |

 # defetc as data not being save when use come to re-order

  @regression @positive-scenario
  Scenario Outline: Re-order food from takeway.com
    Given user launch takeaway web application
    Then user is on takeaway landing page
    And user can see time to order food message
    When user search for address "<address>"
    Then search results popup is shown
    When user select address "<address>" from search results
    Then user is on searched address page
    When user search for restaurant "<restaurant name>"
    Then address restaurants list is shown
    When user select restaurant listed under address
    Then user is on restaurant "<restaurant name>" details page
    When user search for meal "<meal name>"
    And meal for purchase details are shown
    When user select first menu on the menu list
    When user select first menu on the menu list
    Then meal for purchase details are shown
    And user can see selected drink details
    And user can see menu total price to pay
    When user select button to add menu to cart
    Then user can see cart total price
    When user select cart order button
    And user is on ready to eat page
    Then as soon as possible delivery time selected "<delivery time>"
    When user select order and pay button
    Then user can see thank you for your order message
    And user can see copy food tracker link
    And user can see restaurant order from name "<restaurant name>"
    And user can see ordered meal details
    And user can see order success reference number

    Examples:
      | address | restaurant name          |meal name| delivery time       |
      | 8888    | TEST Restaurant Selenium | Duck Breas        |As soon as possible |


#  @regression @negative-scenario
  Scenario Outline: Search for restaurant not near address
    Given user launch takeaway web application
    Then user is on takeaway landing page
    And user can see time to order food message
    When user search for address "<address>"
    Then search results popup is shown
    When user select address "<address>" from search results
    Then user is on searched address page
    And address restaurants list is shown
    When user search for restaurant "<restaurant name>"
    Then searched restaurants not found

    Examples:
      | address | restaurant name |
      | 8888    | Roccomamas      |

#  @regression @negative-scenario
  Scenario Outline: Search for address not in takeaway regions
    Given user launch takeaway web application
    Then user is on takeaway landing page
    And user can see time to order food message
    When user search for address "<address>"
    And user select show address button
    Then invalid searched address message is shown

    Examples:
      | address |
      | 2194    |


