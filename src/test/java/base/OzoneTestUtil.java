package base;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OzoneTestUtil {
    public WebDriver driver;
    public String appURL, browser;
    public int implicitWait;


    @AfterMethod
    public void tearDown() {

        driver.quit();

    }

    @BeforeMethod
    public void setupDriverAndOpenTestAddress() {

        readConfig("src/test/resources/config.properties");
        setupWebDriver();

        //driver.manage().window().maximize() is for running the tests in full screen mode
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        driver.get(appURL);
    }



    @DataProvider(name = "UserCredentials")
    public Object[][] readUserFromCsv() throws IOException, CsvValidationException {
        // Read the CSV file
        String csvFilePath = "src/test/resources/correctUser.csv";
        List<String[]> csvData = readCSV(csvFilePath);

        // Convert the CSV data to a two-dimensional object array
        Object[][] testData = new Object[csvData.size()][2];
        for (int i = 0; i < csvData.size(); i++) {
            String[] row = csvData.get(i);
            String username = row[0]; // Assuming the first column contains the username
            String password = row[1]; // Assuming the second column contains the password
            testData[i][0] = username;
            testData[i][1] = password;
        }

        return testData;
    }


    private List<String[]> readCSV(String filePath) throws IOException, CsvValidationException {
        List<String[]> data = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                data.add(line);
            }
        }

        return data;
    }
    //Read config file
    private void readConfig(String confFile) {

        try {
            FileInputStream fileInputStream = new FileInputStream(confFile);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            appURL = properties.getProperty("testURL");
            browser = properties.getProperty("browser");
            implicitWait = Integer.parseInt(properties.getProperty("implicitWait"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    private void setupWebDriver() {

        switch (browser) {
            case "chrome":
                driver = setupChromeDriver();
                break;
            case "firefox":
                driver = setupFirefoxDriver();
                break;
        }

    }

    private WebDriver setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    private WebDriver setupFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }

}



