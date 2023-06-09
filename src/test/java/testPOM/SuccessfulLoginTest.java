package testPOM;

import base.OzoneTestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.SearchPage;

import java.time.Duration;

public class SuccessfulLoginTest extends OzoneTestUtil {

    // Gets the username & password from correctUser.csv
    @Test(dataProvider = "UserCredentials")
    public void successfulLogin(String username, String password) throws InvalidSelectorException {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();

        SearchPage searchPage = loginPage.login(username, password);

        // Execute the JavaScript script to include the reCAPTCHA script dynamically
        String script = "var scriptTag = document.createElement('script');\n" +
                "scriptTag.src = 'https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit';\n" +
                "scriptTag.async = true;\n" +
                "document.head.appendChild(scriptTag);\n";

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript(script);

        //ImplicitWait to Manually Verify Recaptcha Image challenge (if present)
        // and to click on the login button
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        // Wait for the account page welcome message to be displayed
        WebElement welcomeMsg = driver.findElement(By.className("my-account"));
        Assert.assertTrue(welcomeMsg.isDisplayed(), "Element is not displayed.");
    }
}
