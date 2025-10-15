package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_HomePage;
import Pages.P06_ProductsPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getPropertyValue;
import static Utilities.Utility.*;
import static org.testng.Assert.assertEquals;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC09_SearchProduct {
    private final By searchedProductsLabel = By.cssSelector(".title.text-center");
    private final String SearchProductKey = DataUtils.getJsonData("SearchProductKey", "name");
    private final By productNamesSearchResults = By.xpath("//div[@class='productinfo text-center']/p");



    @BeforeMethod(alwaysRun = true)
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    public void VerifyAllProductsAndProductDetailPage() throws IOException {
        Assert.assertTrue(VerifyUrl(getDriver(), DataUtils.getPropertyValue("environment","HOME_URL")));
        LogsUtils.info("Verify that home page is visible successfully");


        new P01_HomePage(getDriver()).clickOnProductsButton();
        LogsUtils.info("Click on 'Products' button");

        Assert.assertTrue(new P06_ProductsPage(getDriver()).veryProductsPageVisibleByAllProductsLabel());
        LogsUtils.info("Verify user is navigated to ALL PRODUCTS page successfully");


        new P06_ProductsPage(getDriver()).
                fillSearchProductInput(SearchProductKey).
                clickOnSearchProductButton();
        LogsUtils.info("Enter product name in search input and click search button");

        assertEquals(findWebElement(getDriver(),searchedProductsLabel).getText(),"SEARCHED PRODUCTS");
        LogsUtils.info("Verify 'SEARCHED PRODUCTS' is visible");

        generalWait(getDriver()).until(ExpectedConditions.visibilityOfElementLocated(productNamesSearchResults));

        List<WebElement> SearchResults = new P06_ProductsPage(getDriver()).getProductNamesSearchResults();
        for(WebElement item : SearchResults) {

            Assert.assertTrue(item.getText().toLowerCase().contains(SearchProductKey.toLowerCase()));

        }
        LogsUtils.info("Verify all the products related to search are visible");

    }


    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }

}
