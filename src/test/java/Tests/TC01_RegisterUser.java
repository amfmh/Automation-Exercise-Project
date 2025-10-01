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

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getPropertyValue;
import static Utilities.Utility.VerifyUrl;



@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC01_RegisterUser {

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


    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void registerUser() throws IOException {
        Assert.assertTrue(VerifyUrl(getDriver(),DataUtils.getPropertyValue("environment","HOME_URL")));


        new P01_HomePage(getDriver()).
                clickOnSignupLoginButton();
        Assert.assertTrue(VerifyUrl(getDriver(),DataUtils.getPropertyValue("environment","LOGIN_SIGNUP_URL")));


        new P02_LoginSignupPage(getDriver()).
                enterSignupUsername(SIGNUP_USERNAME).
                enterSignupEmailAddress(SIGNUP_EMAIL_ADDRESS).
                clickOnSignupButton();
        Assert.assertTrue(new P03_SignupPage(getDriver()).verifyEnterAccountInformationLabelExisted());


        new P03_SignupPage(getDriver()).
                fillAccountDetails(PASSWORD, FIRSTNAME, LASTNAME, COMPANY, ADDRESS1, ADDRESS2, STATE, CITY, ZIPCODE, MOBILE_NUMBER).
                clickOnCreateAccountButton();
        Assert.assertTrue(new P04_AccountCreatedPage(getDriver()).verifyAccountCreatedLabelExisted());


        new P04_AccountCreatedPage(getDriver()).clickOnContinueButton();
        Assert.assertTrue(new P01_HomePage(getDriver()).verifyLoggedInUsernameIsVisible());


        new P01_HomePage(getDriver()).clickOnDeleteAccountButton();
        Assert.assertTrue(new P05_AccountDeletedPage(getDriver()).verifyAccountDeletedLabelExisted());


        new P05_AccountDeletedPage(getDriver()).clickOnContinueButton();

    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }

}
