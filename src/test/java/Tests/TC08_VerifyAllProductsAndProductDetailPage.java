package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_HomePage;
import Pages.P06_ProductsPage;
import Pages.P07_ProductDetailsPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getPropertyValue;
import static Utilities.Utility.VerifyUrl;


@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC08_VerifyAllProductsAndProductDetailPage {



    @BeforeMethod
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


        new P01_HomePage(getDriver()).clickOnProductsButton();
        Assert.assertTrue(new P06_ProductsPage(getDriver()).veryProductsPageVisibleByAllProductsLabel());


        Assert.assertTrue(new P06_ProductsPage(getDriver()).verifyProductsListIsVisible());

        new P06_ProductsPage(getDriver()).clickOnViewProductButtonOfFirstProduct();

        Assert.assertTrue(new P07_ProductDetailsPage(getDriver()).verifyProductNameIsVisible());
        Assert.assertTrue(new P07_ProductDetailsPage(getDriver()).verifyProductCategoryIsVisible());
        Assert.assertTrue(new P07_ProductDetailsPage(getDriver()).verifyProductPriceIsVisible());
        Assert.assertTrue(new P07_ProductDetailsPage(getDriver()).verifyProductAvailabilityIsVisible());
        Assert.assertTrue(new P07_ProductDetailsPage(getDriver()).verifyProductConditionIsVisible());
        Assert.assertTrue(new P07_ProductDetailsPage(getDriver()).verifyProductBrandIsVisible());

    }


    @AfterMethod
    public void quit() {
        quitDriver();
    }

}
