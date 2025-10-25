package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_HomePage;
import Pages.P07_ProductDetailsPage;
import Pages.P08_CartPage;
import Utilities.LogsUtils;
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
import static Utilities.Utility.VerifyUrl;


@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC13_VerifyProductQuantityInCart {

    private static List<String> productsQuantities_CartPage;


    @BeforeMethod(alwaysRun = true)
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    public void verifyProductQuantityInCart() throws IOException {

        Assert.assertTrue(VerifyUrl(getDriver(), getPropertyValue("environment","HOME_URL")));
        LogsUtils.info("Verify that home page is visible successfully");

        new P01_HomePage(getDriver()).clickOnViewButtonOfAnyProduct(1,43);
        LogsUtils.info("Click 'View Product' for any product on home page");

        Assert.assertEquals(getDriver().getTitle(),"Automation Exercise - Product Details");
        LogsUtils.info("Verify product detail is opened");

        new P07_ProductDetailsPage(getDriver()).
                increaseQuantity("4").
                clickOnAddToCartButton().
                clickOnViewCartButton();
        LogsUtils.info("Increase quantity to 4 & Click 'Add to cart' button & Click 'View Cart' button");

        productsQuantities_CartPage = new P08_CartPage(getDriver()).getQuantityOfProducts_CartPage();
        for(int i=0 ; i<productsQuantities_CartPage.size() ; i++) Assert.assertEquals(productsQuantities_CartPage.get(i), "4");
        LogsUtils.info("Verify that product is displayed in cart page with exact quantity");

    }




    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }

}
