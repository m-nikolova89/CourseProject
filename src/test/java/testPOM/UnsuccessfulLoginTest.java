package testPOM;

import base.OzoneTestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.frameToBeAvailableAndSwitchToIt;

public class UnsuccessfulLoginTest extends OzoneTestUtil {

    @FindBy(id = "solver-button")
    private WebElement solverRecaptcha;

    @Test(dataProvider = "wrongUsers")
    public void testUnsuccessfulLogin(String username, String password) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();

        WebElement userNameInput = driver.findElement(By.id("email"));
        userNameInput.click();
        userNameInput.clear();
        userNameInput.sendKeys(username);

        WebElement passwordInput = driver.findElement(By.id("pass"));
        passwordInput.click();
        passwordInput.clear();
        passwordInput.sendKeys(password);

        // Wait for reCAPTCHA iframe and switch to it
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe[src^='https://www.google.com/recaptcha/api2/anchor']")));

        // Execute the JavaScript code
        String script = "var recaptchaCallback = function() {\n" +
                "  grecaptcha.render('el-646f7ffcc6354', {\n" +
                "    'sitekey': '6Ldhi0YUAAAAAL76XBM8Y3CahqIzjBpIOWEYXymd',\n" +
                "    'theme': 'light',\n" +
                "    'type': 'image',\n" +
                "    'size': 'normal',\n" +
                "    'callback': function(response) {\n" +
                "      if (response.length > 0) {\n" +
                "        document.getElementById('cb-646f7ffcc6354').setAttribute('value', 'checked');\n" +
                "        document.getElementById('cb-646f7ffcc6354').checked = true;\n" +
                "      }\n" +
                "    }\n" +
                "  });\n" +
                "};\n" +
                "recaptchaCallback();";
        try {
        WebDriverWait javaWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        //Execute the JavaScript code to click on the reCAPTCHA checkbox
        ((JavascriptExecutor) driver).executeScript("document.querySelector('.recaptcha-checkbox-border').click();");
        driver.switchTo().defaultContent();

        
            // Click on the login button using JavaScriptExecutor(for chrome)
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            WebElement loginButton = driver.findElement(By.id("send2"));
            executor.executeScript("arguments[0].click();", loginButton);
            loginButton.click();

        } catch (JavascriptException e) {
            System.out.println("Caught JavascriptException: " + e.getMessage());
        }

            // Explicitly wait for the error message to be present and visible
            WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement element = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".validation-advice")));

            WebElement errorMessage = driver.findElement(By.className("validation-advice"));
            Assert.assertTrue(errorMessage.isDisplayed());
        }

    @DataProvider(name = "wrongUsers")
    public Object[][] getUsers() {
        return new Object[][]{
                {"maya.nikolova89@gmail.com", ""},
                {"", "Marvel1989"},
                {"", ""}
        };
    }
}
