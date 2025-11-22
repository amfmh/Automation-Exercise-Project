package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_HomePage;
import Pages.P06_ProductsPage;
import Pages.P07_ProductDetailsPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import io.qameta.allure.*;
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


@Epic("Regression Tests")
@Feature("Verify")
@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC08_VerifyAllProductsAndProductDetailPage {



    @BeforeMethod(alwaysRun = true)
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test(description = "Test Case 8: Verify All Products and product detail page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Verify All Products and product detail page")
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Click on 'Products' button
            5. Verify user is navigated to ALL PRODUCTS page successfully
            6. The products list is visible
            7. Click on 'View Product' of first product
            8. User is landed to product detail page
            9. Verify that detail detail is visible: product name, category, price, availability, condition, brand""")
    public void VerifyAllProductsAndProductDetailPage() throws IOException {
        Assert.assertTrue(VerifyUrl(getDriver(), DataUtils.getPropertyValue("environment","HOME_URL")));
        LogsUtils.info("Verify that home page is visible successfully");

        new P01_HomePage(getDriver()).clickOnProductsButton();
        LogsUtils.info("Click on 'Products' button");

        Assert.assertTrue(new P06_ProductsPage(getDriver()).veryProductsPageVisibleByAllProductsLabel());
        LogsUtils.info("Verify user is navigated to ALL PRODUCTS page successfully");


        Assert.assertTrue(new P06_ProductsPage(getDriver()).verifyProductsListIsVisible());
        LogsUtils.info("The products list is visible");

        new P06_ProductsPage(getDriver()).clickOnViewProductButtonOfFirstProduct();
        LogsUtils.info("Click on 'View Product' of first product");

        Assert.assertTrue(VerifyUrl(getDriver(), DataUtils.getPropertyValue("environment","FIRST_PRODUCT_DETAILS_URL")));
        LogsUtils.info("User is landed to product detail page");


        Assert.assertTrue(new P07_ProductDetailsPage(getDriver()).verifyProductNameIsVisible());
        Assert.assertTrue(new P07_ProductDetailsPage(getDriver()).verifyProductCategoryIsVisible());
        Assert.assertTrue(new P07_ProductDetailsPage(getDriver()).verifyProductPriceIsVisible());
        Assert.assertTrue(new P07_ProductDetailsPage(getDriver()).verifyProductAvailabilityIsVisible());
        Assert.assertTrue(new P07_ProductDetailsPage(getDriver()).verifyProductConditionIsVisible());
        Assert.assertTrue(new P07_ProductDetailsPage(getDriver()).verifyProductBrandIsVisible());
        LogsUtils.info("Verify that detail detail is visible: product name, category, price, availability, condition, brand");

    }


    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }

}
