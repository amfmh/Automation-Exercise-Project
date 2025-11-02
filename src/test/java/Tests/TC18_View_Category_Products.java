package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_HomePage;
import Pages.P06_ProductsPage;
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

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC18_View_Category_Products {


    @BeforeMethod(alwaysRun = true)
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    public void viewCategoryProducts(){

        Assert.assertTrue(new P01_HomePage(getDriver()).verifyCategoriesAreVisible());
        LogsUtils.info("Verify that categories are visible on left side bar");

        new P01_HomePage(getDriver())
                .clickOnWomenCategory()
                .clickOnDressCategory();
        LogsUtils.info("Click on 'Women' category & Click on any category link under 'Women' category, for example: Dress");

        Assert.assertTrue(new P06_ProductsPage(getDriver()).verifyWomenDressCategoryPageIsVisible());
        Assert.assertEquals(new P06_ProductsPage(getDriver()).getTheTitleOfWomenDressCategoryPage(),"WOMEN - DRESS PRODUCTS");
        LogsUtils.info("Verify that category page is displayed and confirm text 'WOMEN - DRESS PRODUCTS'");

        new P06_ProductsPage(getDriver())
                .clickOnMenCategory()
                .clickOnMenTshirtsCategory();
        LogsUtils.info("On left side bar, click on any sub-category link of 'Men' category ==> Tshirts for example");

        Assert.assertTrue(new P06_ProductsPage(getDriver()).verifyMenTshirtsCategoryPageIsVisible());
        LogsUtils.info("Verify that user is navigated to that category page");


    }


    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }

}
