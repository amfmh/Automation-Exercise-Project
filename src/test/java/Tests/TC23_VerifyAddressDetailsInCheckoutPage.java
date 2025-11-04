package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.*;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
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
public class TC23_VerifyAddressDetailsInCheckoutPage {
    private final String SIGNUP_USERNAME = "name" + Utility.getTimeStamp();
    private final String SIGNUP_EMAIL_ADDRESS = "email" + Utility.getTimeStamp() + "@gmail.com";
    private final String PASSWORD = "pass" + Utility.getTimeStamp();
    private final String FIRSTNAME = DataUtils.getJsonData("AccountDetails", "firstName");
    private final String LASTNAME = DataUtils.getJsonData("AccountDetails", "lastName");
    private final String COMPANY = DataUtils.getJsonData("AccountDetails", "company");
    private final String ADDRESS1 = DataUtils.getJsonData("AccountDetails", "address1");
    private final String ADDRESS2 = DataUtils.getJsonData("AccountDetails", "address2");
    private final String STATE = DataUtils.getJsonData("AccountDetails", "state");
    private final String CITY = DataUtils.getJsonData("AccountDetails", "city");
    private final String ZIPCODE = DataUtils.getJsonData("AccountDetails", "zipcode");
    private final String MOBILE_NUMBER = DataUtils.getJsonData("AccountDetails", "mobileNumber");
    private static List<String> yourDeliveryAddress;
    private static List<String> yourBillingAddress;




    @BeforeMethod(alwaysRun = true)
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    public void verifyAddressDetailsInCheckoutPage() throws IOException {


        Assert.assertTrue(VerifyUrl(getDriver(), DataUtils.getPropertyValue("environment","HOME_URL")));
        LogsUtils.info("Verify that home page is visible successfully");

        new P01_HomePage(getDriver()).clickOnSignupLoginButton();
        LogsUtils.info("Click 'Signup / Login' button");

        new P02_LoginSignupPage(getDriver()).
                enterSignupUsername(SIGNUP_USERNAME).
                enterSignupEmailAddress(SIGNUP_EMAIL_ADDRESS).
                clickOnSignupButton();
        new P03_SignupPage(getDriver()).
                fillAccountDetails(PASSWORD, FIRSTNAME, LASTNAME, COMPANY, ADDRESS1, ADDRESS2, STATE, CITY, ZIPCODE, MOBILE_NUMBER).
                clickOnCreateAccountButton();
        LogsUtils.info("Fill all details in Signup and create account");

        Assert.assertTrue(new P04_AccountCreatedPage(getDriver()).verifyAccountCreatedLabelExisted());
        new P04_AccountCreatedPage(getDriver()).clickOnContinueButton();
        LogsUtils.info("Verify 'ACCOUNT CREATED!' and click 'Continue' button");

        Assert.assertTrue(new P01_HomePage(getDriver()).verifyLoggedInUsernameIsVisible());
        LogsUtils.info("Verify ' Logged in as username' at top");

        new P01_HomePage(getDriver()).clickOnProductsButton();
        new P06_ProductsPage(getDriver()).
                addRandomProducts(3,34).
                clickOnCartButton();
        LogsUtils.info("Add products to cart & Click 'Cart' button");

        Assert.assertTrue(new P08_CartPage(getDriver()).verifyCartPageIsDisplayed());
        LogsUtils.info("Verify that cart page is displayed");


        new P08_CartPage(getDriver()).clickOnProceedToCheckoutButton();
        LogsUtils.info("Click Proceed To Checkout");

        yourDeliveryAddress = new P09_Checkoutpage(getDriver()).getDeliveryAddressData();
        yourBillingAddress = new P09_Checkoutpage(getDriver()).getBillingAddressData();
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
        LogsUtils.info("Verify that the delivery address is same address filled at the time registration of account" +
                "Verify that the billing address is same address filled at the time registration of account");

        new P01_HomePage(getDriver()).clickOnDeleteAccountButton();
        LogsUtils.info("Click 'Delete Account' button");

        Assert.assertTrue(new P05_AccountDeletedPage(getDriver()).verifyAccountDeletedLabelExisted());
        new P05_AccountDeletedPage(getDriver()).clickOnContinueButton();
        LogsUtils.info("Verify 'ACCOUNT DELETED!' and click 'Continue' button");

    }


    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }


}
