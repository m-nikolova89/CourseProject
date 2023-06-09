package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.frameToBeAvailableAndSwitchToIt;

public class LoginPage extends BasePage {

    // Elements
    @FindBy(className = "cookiepopup-head")
    private WebElement cookiePopup;

    @FindBy(className = "cookiepopup-closebutton")
    private WebElement cookieClose;

    @FindBy(className = "responsive-logo")
    private WebElement ozoneLogo;

    @FindBy(css = "a.clever-link1")
    private WebElement loginIcon;

    @FindBy(id = "email")
    private WebElement userNameInput;

    @FindBy(id = "pass")
    private WebElement passwordInput;

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Methods
    public void openLoginPage() {

        cookiePopup.isDisplayed();
        cookieClose.click();
        ozoneLogo.isDisplayed();
        loginIcon.click();
    }

    public SearchPage login(String username, String password) throws InvalidSelectorException {
        userNameInput.click();
        userNameInput.clear();
        userNameInput.sendKeys(username);

        passwordInput.click();
        passwordInput.clear();
        passwordInput.sendKeys(password);

        //reCAPTCHA Code
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Wait for reCAPTCHA iframe and switch to it
        wait.until(frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe[src^='https://www.google.com/recaptcha/api2/anchor']")));

        WebDriverWait javaWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        //Execute the JavaScript code to click on the reCAPTCHA checkbox
        ((JavascriptExecutor) driver).executeScript("document.querySelector('.recaptcha-checkbox-border').click();");


        // Execute the JavaScript script to solve reCAPTCHA after the checkbox is clicked
        String verificationScript = "var verifyCallback = function(response) {\n" +
                "    if (response.length > 0) {\n" +
                "        document.getElementById('cb-6482b33206671').value = 'checked';\n" +
                "        document.getElementById('cb-6482b33206671').checked = true;\n" +
                "    }\n" +
                "};\n" +
                "var onloadCallback = function() {\n" +
                "    grecaptcha.render('el-6482b33206671', {\n" +
                "        'sitekey': '6Ldhi0YUAAAAAL76XBM8Y3CahqIzjBpIOWEYXymd',\n" +
                "        'theme': 'light',\n" +
                "        'type': 'image',\n" +
                "        'size': 'normal',\n" +
                "        'callback': verifyCallback\n" +
                "    });\n" +
                "};\n" +
                "if (typeof grecaptcha !== 'undefined') {\n" +
                "    onloadCallback();\n" +
                "} else {\n" +
                "    var scriptTag = document.createElement('script');\n" +
                "    scriptTag.src = 'https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit';\n" +
                "    scriptTag.async = true;\n" +
                "    document.head.appendChild(scriptTag);\n" +
                "}";

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript(verificationScript);

        return new SearchPage(driver);
    }
}
