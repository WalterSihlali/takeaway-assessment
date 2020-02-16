package takeAway;

import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

@RunWith(Cucumber.class)

@CucumberOptions(
        features = {"src/test/features"},
        tags = {"@regression"},
        format = {"pretty"},
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/takeway-report.html"}
        , monochrome = true)


public class TestRunner {

    private static Logger logger = Logger.getLogger(TestRunner.class.getName());

    @AfterClass
    public static void writeExtentReport() throws IOException {
        Reporter.loadXMLConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));
        copyLatestExtentReport();
        Reporter.loadXMLConfig("extent-config.xml");
    }

    /**
     * Copy report file from target to reports folder
     */
    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

        } finally {
            inputStream.close();
            outputStream.close();
        }
    }

    /**
     * Add time stamp to current report when new one is generated
     * Copy report file from target to reports folder
     */
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
        File destination = new File(System.getProperty("user.dir") + "/reports/takeway-report" + timestamp + ".html");
        copyFileUsingStream(source, destination);
    }

}