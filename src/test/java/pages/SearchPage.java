package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class SearchPage extends BasePage {

    //Elements
    @FindBy (className="cookiepopup-head")
    WebElement cookiePopup;

    @FindBy(className = "cookiepopup-closebutton")
    WebElement cookieClose;

    @FindBy(id = "search")
    private WebElement searchInput;

    @FindBy(className = "search-submit")
    private WebElement searchButton;

    @FindBy(id = "search-results")
    private WebElement searchResults;

    @FindBy(id = "isp_results_search_text")
    private WebElement searchResultTitle;

    @FindBy(css = "img[alt='The Green Mile'][isp_product_id='309859']")
    private WebElement firstResult;

    @FindBy(css = "img[alt='Aliens 1986 (Blu-Ray)'][isp_product_id='413286']")
    private WebElement secondResult;

    @FindBy(css = "a.button.checkout-color.add-to-cart")
    private  WebElement purchaseIcon;

    @FindBy(className = "notification")
    private WebElement notificationElement;


    //constructor
    public SearchPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }


    //methods
    public void SearchActionsItem1() throws InvalidElementStateException {

        cookiePopup.isDisplayed();
        cookieClose.click();


    }
    public void verifyFirstSearchResultTitle() {

        WebDriverWait sc = new WebDriverWait(driver,Duration.ofSeconds(10));
        sc.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.isDisplayed();
        searchInput.sendKeys("Stephen King");
        searchButton.click();

        String searchTerm = "Stephen King";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(searchResultTitle));
        String actualTitle = searchResultTitle.getText();
        Assert.assertEquals(actualTitle, searchTerm, "First search result title does not match the expected term.");

        firstResult.click();

        //Click on the purchase button
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(ExpectedConditions.elementToBeClickable(purchaseIcon)).click();

        //Wait for the slider(progress bar)
        WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
        By slideInLocator = By.className("iziToast-wrapper");
        wait.until(ExpectedConditions.visibilityOfElementLocated(slideInLocator));

        //Verify that the shopping card has one item added
        String expectedText= String.valueOf(1);
        String actualText = notificationElement.getText().trim();
        Assert.assertEquals(actualText, expectedText);

    }

    public void verifySecondSearchResultTitle() {

        searchInput.isDisplayed();
        searchInput.sendKeys("Alien");
        searchButton.click();

        String searchTerm = "Alien";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(searchResultTitle));
        String actualTitle = searchResultTitle.getText();
        Assert.assertEquals(actualTitle, searchTerm, "Second search result title does not match the expected term.");

        secondResult.click();

        //Click on the purchase button
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(ExpectedConditions.elementToBeClickable(purchaseIcon)).click();

        //Wait for the slider(progress bar)
        WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
        By slideInLocator = By.className("iziToast-wrapper");
        wait.until(ExpectedConditions.visibilityOfElementLocated(slideInLocator));

        //Verify that the shopping card has two items added
        String expectedText= String.valueOf(2);
        String actualText = notificationElement.getText().trim();
        Assert.assertEquals(actualText, expectedText);

    }
    }







