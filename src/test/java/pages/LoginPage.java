package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.frameToBeAvailableAndSwitchToIt;

public class LoginPage extends BasePage {

    //Elements
    @FindBy (className="cookiepopup-head")
    WebElement cookiePopup;

    @FindBy(className = "cookiepopup-closebutton")
    WebElement cookieClose;

    @FindBy(css = "a.icon-link.user-icon-link")
    WebElement loginIcon;

    @FindBy(id = "email")
    private WebElement userNameInput;

    @FindBy(id = "pass")
    private WebElement passwordInput;

    @FindBy(id = "send2")
    private WebElement loginButton;

    @FindBy(id = "main")
    private WebElement account;

    @FindBy(css = "a.icon-link.clever-link1.user-icon-link.loggedIn")
    private WebElement profileLink;


    //constructor
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    //Methods
    public void openLoginPage() {

        cookiePopup.isDisplayed();
        cookieClose.click();
        loginIcon.click();

    }

    public SearchPage login(String username, String password) throws InvalidSelectorException {

        userNameInput.click();
        userNameInput.clear();
        userNameInput.sendKeys(username);

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

        String script2 = "(function(){var w=window,C='___grecaptcha_cfg'" +
                ",cfg=w[C]=w[C]||{},N='grecaptcha';var gr=w[N]=w[N]||{};" +
                "gr.ready=gr.ready||function(f){(cfg['fns']=cfg['fns']||[]).push(f);" +
                "};w['__recaptcha_api']='https://www.google.com/recaptcha/api2/';" +
                "(cfg['render']=cfg['render']||[]).push('explicit');" +
                "(cfg['onload']=cfg['onload']||[]).push('onloadCallback');" +
                "w['__google_recaptcha_client']=true;var d=document,po=d.createElement('script');" +
                "po.type='text/javascript';po.async=true;" +
                "po.src='https://www.gstatic.com/recaptcha/releases/CDFvp7CXAHw7k3HxO47Gm1O9/recaptcha__bg.js';" +
                "po.crossOrigin='anonymous';po.integrity='sha384-z9N8cuZT+YsJSSTMiXye2mipHco/IMMog0l6FWphYPW4IFJPCQw3r5ohVHsmX+ZH';" +
                "var e=d.querySelector('script[nonce]'),n=e&&(e['nonce']||e.getAttribute('nonce'));" +
                "if(n){po.setAttribute('nonce',n);}var s=d.getElementsByTagName('script')[0];s.parentNode.insertBefore(po, s);})()";

        // Execute the JavaScript code to click on the reCAPTCHA checkbox
        ((JavascriptExecutor) driver).executeScript("document.querySelector('.recaptcha-checkbox-border').click();");

        ((JavascriptExecutor) driver).executeScript(script2);

        driver.switchTo().defaultContent();

        // Wait for the login button to be visible
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait2.until(ExpectedConditions.visibilityOf(loginButton));
        loginButton.click();

        // Click on the login button using JavaScriptExecutor
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", loginButton);


        // Wait for the account and profile link to be displayed
        WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait3.until(ExpectedConditions.visibilityOfAllElements(account));
        account.isDisplayed();

        // Perform mouse hover on the profile icon
        Actions actions2 = new Actions(driver);
        actions2.moveToElement(profileLink).perform();

        // Wait for the dropdown menu to be visible
        WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement dropdownMenu = wait4.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".loggedIn-dropdown")));

        // Find the logout link within the dropdown menu
        WebElement logoutLink = dropdownMenu.findElement(By.cssSelector("span[onclick*='logout']"));

        // Click on the logout link
        logoutLink.click();

        return new SearchPage(driver);
    }


}


