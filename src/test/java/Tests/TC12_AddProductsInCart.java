package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_HomePage;
import Pages.P06_ProductsPage;
import Pages.P08_CartPage;
import Utilities.LogsUtils;

import io.qameta.allure.*;
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


@Epic("Regression Tests")
@Feature("Cart")
@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC12_AddProductsInCart {

    private static List<String>productsNames_ProductsPage;
    private static List<String>productsNames_CartPage;

    private static List<String>productsPrices_ProductsPage;
    private static List<String>productsPrices_CartPage;
    private static List<String>productsQuantities_CartPage;
    private static List<String>productsTotalPrices_CartPage;


    @BeforeMethod(alwaysRun = true)
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test(description = "Test Case 12: Add Products in Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Add Products in Cart")
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Click 'Products' button
            5. Hover over first product and click 'Add to cart'
            6. Click 'Continue Shopping' button
            7. Hover over second product and click 'Add to cart'
            8. Click 'View Cart' button
            9. Verify both products are added to Cart
            10. Verify their prices, quantity and total price""")
    public void addProductsInCart() throws IOException {

        Assert.assertTrue(VerifyUrl(getDriver(), getPropertyValue("environment","HOME_URL")));
        LogsUtils.info("Verify that home page is visible successfully");


        /*new P01_HomePage(getDriver()).
                clickOnProductsButton().
                clickOnAddToCartButtonForFirstProduct().
                clickOnContinueShoppingButton().
                clickOnAddToCartButtonForSecondProduct().
                clickOnViewCartButton();*/


        new P01_HomePage(getDriver()).clickOnProductsButton();
        LogsUtils.info("Click 'Products' button");

        productsNames_ProductsPage = new P06_ProductsPage(getDriver()).getFirstAndSecondProductsNames_ProductsPage();
        productsPrices_ProductsPage = new P06_ProductsPage(getDriver()).getFirstAndSecondProductPrices_ProductsPage();
        new P06_ProductsPage(getDriver()).
                clickOnAddToCartButtonForFirstProduct().
                clickOnContinueShoppingButton().
                clickOnAddToCartButtonForSecondProduct().
                clickOnViewCartButton();
        LogsUtils.info("Hover over first product and click 'Add to cart' & Click 'Continue Shopping' button " +
                "& Hover over second product and click 'Add to cart' & Click 'View Cart' button");

        productsNames_CartPage = new P08_CartPage(getDriver()).getFirstAndSecondProductsNames_CartPage();
        productsPrices_CartPage = new P08_CartPage(getDriver()).getFirstAndSecondProductPrices_CartPage();
        productsQuantities_CartPage = new P08_CartPage(getDriver()).getQuantityOfProducts_CartPage();
        productsTotalPrices_CartPage = new P08_CartPage(getDriver()).getTotalPricesOfProducts_CartPage();


        for(int i=0 ; i<=1 ; i++){
            Assert.assertEquals(productsNames_CartPage.get(i),productsNames_ProductsPage.get(i));
            Assert.assertEquals(productsPrices_ProductsPage.get(i),productsPrices_CartPage.get(i));
            Assert.assertEquals(productsQuantities_CartPage.get(i),"1");
            Assert.assertEquals(productsTotalPrices_CartPage.get(i),productsPrices_CartPage.get(i));
        }
        LogsUtils.info("Verify both products are added to Cart & Verify their prices, quantity and total price");

    }


    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }

}
