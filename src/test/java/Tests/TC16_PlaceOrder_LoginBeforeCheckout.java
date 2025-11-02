package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.*;
import Utilities.DataUtils;
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
import static Utilities.DataUtils.getJsonData;
import static Utilities.DataUtils.getPropertyValue;
import static Utilities.Utility.VerifyUrl;


@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC16_PlaceOrder_LoginBeforeCheckout {

    private final  String EXISTED_EMAIL_ADDRESS = DataUtils.getJsonData("ExistingUser","email");
    private final  String EXISTED_PASSWORD = DataUtils.getJsonData("ExistingUser","password");
    private static List<String> yourDeliveryAddress;
    private static List<String> yourBillingAddress;
    private static List<String> productsNames_cartPage;
    private static List<String> productsPrices_cartPage;
    private static List<String> productsQuantities_cartPage;
    private static List<String> productsTotalPrices_cartPage;
    private static List<String> productsNames_CheckoutPage;
    private static List<String> productsPrices_CheckoutPage;
    private static List<String> productsQuantities_CheckoutPage;
    private static List<String> productsTotalPrices_CheckoutPage;


    @BeforeMethod(alwaysRun = true)
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }



    @Test
    public void placeOrderLoginBeforeCheckout() throws IOException {

        Assert.assertTrue(VerifyUrl(getDriver(), DataUtils.getPropertyValue("environment","HOME_URL")));
        LogsUtils.info("Verify that home page is visible successfully");


        new P01_HomePage(getDriver()).clickOnSignupLoginButton();
        LogsUtils.info("Click 'Signup / Login' button");


        new P02_LoginSignupPage(getDriver()).
                enterLoginEmailAddress(EXISTED_EMAIL_ADDRESS).
                enterLoginPassword(EXISTED_PASSWORD).
                clickOnLoginButton();
        LogsUtils.info("Fill email, password and click 'Login' button");

        Assert.assertTrue(new P01_HomePage(getDriver()).verifyLoggedInUsernameIsVisible());
        LogsUtils.info("Verify 'Logged in as username' at top");


        new P06_ProductsPage(getDriver()).
                addRandomProducts(3,34).
                clickOnCartButton();
        LogsUtils.info("Add products to cart & Click 'Cart' button");

        Assert.assertTrue(new P08_CartPage(getDriver()).verifyCartPageIsDisplayed());
        LogsUtils.info("Verify that cart page is displayed");

        productsNames_cartPage = new P08_CartPage(getDriver()).getProductsNames_CartPage();
        productsPrices_cartPage = new P08_CartPage(getDriver()).getPricesOfProducts_CartPage();
        productsQuantities_cartPage = new P08_CartPage(getDriver()).getQuantityOfProducts_CartPage();
        productsTotalPrices_cartPage = new P08_CartPage(getDriver()).getTotalPricesOfProducts_CartPage();

        new P08_CartPage(getDriver()).clickOnProceedToCheckoutButton();
        LogsUtils.info("Click Proceed To Checkout");

        yourDeliveryAddress = new P09_Checkoutpage(getDriver()).getDeliveryAddressData();
        yourBillingAddress = new P09_Checkoutpage(getDriver()).getBillingAddressData();
        Assert.assertEquals(yourDeliveryAddress.get(0), "YOUR DELIVERY ADDRESS");
        Assert.assertEquals(yourBillingAddress.get(0), "YOUR BILLING ADDRESS");
        for(int i=1 ; i<8 ; i++){
            Assert.assertEquals(yourDeliveryAddress.get(i) , yourBillingAddress.get(i));
        }

       String no1 = "Mr. " + getJsonData("AccountDetails","firstName") + " " + getJsonData("AccountDetails","lastName");
       String no2 = getJsonData("AccountDetails","company");
       String no3 = getJsonData("AccountDetails","address1");
       String no4 = getJsonData("AccountDetails","address2");
       String no5 = getJsonData("AccountDetails","city") + " " + getJsonData("AccountDetails","state") + " " + getJsonData("AccountDetails","zipcode");
       String no6 = getJsonData("AccountDetails","country");
       String no7 = getJsonData("AccountDetails","mobileNumber");

       Assert.assertEquals(yourDeliveryAddress.get(1) , no1);
       Assert.assertEquals(yourDeliveryAddress.get(2) , no2);
       Assert.assertEquals(yourDeliveryAddress.get(3) , no3);
       Assert.assertEquals(yourDeliveryAddress.get(4) , no4);
       Assert.assertEquals(yourDeliveryAddress.get(5) , no5);
       Assert.assertEquals(yourDeliveryAddress.get(6) , no6);
       Assert.assertEquals(yourDeliveryAddress.get(7) , no7);

       productsNames_CheckoutPage = new P09_Checkoutpage(getDriver()).getProductsNames_CheckoutPage();
       productsPrices_CheckoutPage = new P09_Checkoutpage(getDriver()).getPricesOfProducts_CheckoutPage();
       productsQuantities_CheckoutPage = new P09_Checkoutpage(getDriver()).getQuantityOfProducts_CheckoutPage();
       productsTotalPrices_CheckoutPage = new P09_Checkoutpage(getDriver()).getTotalPricesOfProducts_CheckoutPage();

       for(int i=0 ; i <= productsNames_CheckoutPage.size() ; i++){
           if(i == productsNames_CheckoutPage.size()){
               Assert.assertEquals(productsTotalPrices_CheckoutPage.get(i), productsTotalPrices_cartPage.get(i));
               break;
           }
           Assert.assertEquals(productsNames_CheckoutPage.get(i), productsNames_cartPage.get(i));
           Assert.assertEquals(productsPrices_CheckoutPage.get(i), productsPrices_cartPage.get(i));
           Assert.assertEquals(productsQuantities_CheckoutPage.get(i), productsQuantities_cartPage.get(i));
           Assert.assertEquals(productsTotalPrices_CheckoutPage.get(i), productsTotalPrices_cartPage.get(i));
       }
        LogsUtils.info("Verify Address Details and Review Your Order");


        new P09_Checkoutpage(getDriver()).enterCommentAndPlaceOrder("your order is ready").
                fillPaymentDetails();
        LogsUtils.info("Enter description in comment text area and click 'Place Order' & " +
                "Enter payment details: Name on Card, Card Number, CVC, Expiration date & " +
                "Click 'Pay and Confirm Order' button");

        Assert.assertTrue(new P10_PaymentPage(getDriver()).getSuccessMessageText());
        LogsUtils.info("Verify success message 'Your order has been placed successfully!'");

    }




    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }

}
