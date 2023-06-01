package testPOM;

import base.OzoneTestUtil;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import pages.SearchPage;

public class SuccessfulLoginTest extends OzoneTestUtil {

    //Gets the username & password from correctUser.csv
    @Test(dataProvider = "UserCredentials")
    public void successfulLogin(String username, String password) throws InvalidSelectorException {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        SearchPage searchPage = loginPage.login(username, password);
        Assert.assertNotNull(searchPage);

    }
}

