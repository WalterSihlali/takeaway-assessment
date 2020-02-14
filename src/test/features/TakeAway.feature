Feature: Restaurant application

  Scenario Outline: Case 1 Scenario
    Given user launch takeaway web application
    Then user is on takeaway landing page
    And user can see time to order food message
    When user search for address "<address>"
    Then search results popup is shown
    When user select address "<address>" from search results
    Then user is on searched address page
    And address restaurants list is shown
    When user select restaurant listed under address
    Then user is on restaurant "<restaurant name>" details page
    And meal for purchase details are shown
    When user select first menu on the menu list
    And user can see selected drink details
    Then user can see menu total price to pay
    When user select button to add menu to cart
    Then user can see cart total price
    When user select cart order button
    Then user is on ready to eat page
    And user can see delivery address details header
    And user enter delivery address "<delivery address>"
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
     | browser| address | restaurant name           | delivery address | city     | postal code | person name | email              | phone number | company name | delivery time       | remarks                          | pay with |
     |broswer | 8888    | TEST Restaurant Selenium  | main street 2415 | Enschede | 8888AA      | TestUSer    | testuser@test.test | 1234567890   | Takeaway.com | As soon as possible | Leave the order at the reception | € 19,00  |

    #Your postcode is invalid or incomplete (a valid and complete postcode should look like this: 1017AB). Check and correct the postcode then try again.

 # defetc as data not being save when use come to re-order


  Scenario Outline: Case 2 Scenario
    Given user launch takeaway web application
    Then user is on takeaway landing page
    And user can see time to order food message
    When user search for address "<address>"
    Then search results popup is shown
    When user select address "<address>" from search results
    Then user is on searched address page
    And address restaurants list is shown
    When user select restaurant listed under address
    Then user is on restaurant "<restaurant name>" details page
    When user select first menu on the menu list
    Then meal for purchase details are shown
    And user can see selected drink details
    And user can see menu total price to pay
    When user select button to add menu to cart
    Then user can cart total price
    When user select cart order button
    And user is on ready to eat page
    Then as soon as possible delivery time selected "<delivery time>"
    When user select order and pay button
    Then user can see thank you for your order message
    And user select button to add menu to cart
    And user can cart total price
    When user select cart order button
    And user can see copy food tracker link
    And user can see restaurant order from name "<restaurant name>"
    And user can see order success reference number

    Examples:
     |browser | address | restaurant name          | delivery time       |
    |browser  | 8888    | TEST Restaurant Selenium | As soon as possible |

