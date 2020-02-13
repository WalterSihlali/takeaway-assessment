package takeAway;

import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RunWith(Cucumber.class)

@CucumberOptions(
        features = {"src/test/features"},
//        tags = {"@chrome"},
        dryRun = false,
        format = {"pretty"},
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/takeway-report.html"}
        , monochrome = true)


public class ChromeTestRunner {
    private static Logger logger = Logger.getLogger(ChromeTestRunner.class);

    @AfterClass
    public static void writeExtentReport() throws IOException {
        Reporter.loadXMLConfig(new File(System.getProperty("user.dir")+ "/extent-config.xml"));
        copyLatestExtentReport();
        Reporter.loadXMLConfig("extent-config.xml");
    }

    /***EXTENT REPORT****************************************************************/
    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;

            while((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } finally {
            try{
                inputStream.close();
                outputStream.close();
            }
            catch (NullPointerException ex) {
                logger.info(ex.getMessage());
            }
        }
    }

    public static void copyLatestExtentReport() throws IOException {
        String timestamp = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss").format(Calendar.getInstance().getTime()).replaceAll(":", "-");
        File source = new File(System.getProperty("user.dir") + "/target/cucumber-reports/takeway-report.html");

        File file = new File(System.getProperty("user.dir") + "/reports");
        if(!file.exists()) {
            try {
                file.mkdir();
            } catch (Exception ex) {
               logger.info( ex.getLocalizedMessage());
            }
        }

        File destination = new File(System.getProperty("user.dir") + "/reports/takeway-report"+ timestamp + ".html");
        copyFileUsingStream(source, destination);
    }

}