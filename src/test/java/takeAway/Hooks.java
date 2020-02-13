package takeAway;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks extends DriverSetUp {

    @Before
    public void setUp() {

            driver = setupWebDriver();

    }

    @After
    public void tearDown() {
        if(driver != null) {
            driver.close();
        }
    }

}
