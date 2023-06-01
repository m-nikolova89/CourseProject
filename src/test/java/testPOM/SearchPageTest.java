package testPOM;

import base.OzoneTestUtil;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.InvalidSelectorException;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.SearchPage;

public class SearchPageTest extends OzoneTestUtil {

    //No need to login for this test
    @Test
    public void addItemsToCart () throws InvalidElementStateException {

        SearchPage searchPage = new SearchPage(driver);
        searchPage.SearchActionsItem1();
        searchPage.verifyFirstSearchResultTitle();
        searchPage.verifySecondSearchResultTitle();

    }

}
