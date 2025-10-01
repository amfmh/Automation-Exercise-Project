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
import java.util.List;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getPropertyValue;


@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC19_View_and_Cart_Brand_Products {

    private static List<String> ProductsPageNames ;
    private static List<String> PoloBrandProducts ;
    private static List<String> MadameBrandProducts ;


    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    public void View_and_Cart_Brand_Products(){


        new P01_HomePage(getDriver()).clickOnProductsButton();
        Assert.assertTrue(new P06_ProductsPage(getDriver()).verifyBrandsAreVisible());
        ProductsPageNames = new P06_ProductsPage(getDriver()).getProductNames();


        new P06_ProductsPage(getDriver()).clickOnPoloBrand();
        Assert.assertEquals(new P06_ProductsPage(getDriver()).getTheTitleOfPoloBrandPage(),"BRAND - POLO PRODUCTS");
        Assert.assertTrue(new P06_ProductsPage(getDriver()).verifyProductsListIsVisible());
        PoloBrandProducts = new P06_ProductsPage(getDriver()).getProductNames();

        for(int i=0 ; i<PoloBrandProducts.size() ; i++){
            for(int j=0 ; j<ProductsPageNames.size() ; j++){
                if(PoloBrandProducts.get(i) == ProductsPageNames.get(j)){
                    Assert.assertEquals(PoloBrandProducts.get(i), ProductsPageNames.get(j));
                }
            }
        }



        new P06_ProductsPage(getDriver()).clickOnMadameBrand();
        Assert.assertEquals(new P06_ProductsPage(getDriver()).getTheTitleOfMadameBrandPage(),"BRAND - MADAME PRODUCTS");
        Assert.assertTrue(new P06_ProductsPage(getDriver()).verifyProductsListIsVisible());
        MadameBrandProducts = new P06_ProductsPage(getDriver()).getProductNames();


        for(int i=0 ; i<MadameBrandProducts.size() ; i++){
            for(int j=0 ; j<ProductsPageNames.size() ; j++){
                if(MadameBrandProducts.get(i) == ProductsPageNames.get(j)){
                    Assert.assertEquals(MadameBrandProducts.get(i), ProductsPageNames.get(j));
                }
            }
        }

    }


    @AfterMethod
    public void quit() {
        quitDriver();
    }

}
