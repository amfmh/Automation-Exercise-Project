package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.*;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
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
@Feature("User")
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


    @BeforeMethod(alwaysRun = true)
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test(description = "Test Case 1: Register User")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Register User")
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Click on 'Signup / Login' button
            5. Verify 'New User Signup!' is visible
            6. Enter name and email address
            7. Click 'Signup' button
            8. Verify that 'ENTER ACCOUNT INFORMATION' is visible
            9. Fill details: Title, Name, Email, Password, Date of birth
            10. Select checkbox 'Sign up for our newsletter!'
            11. Select checkbox 'Receive special offers from our partners!'
            12. Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
            13. Click 'Create Account button'
            14. Verify that 'ACCOUNT CREATED!' is visible
            15. Click 'Continue' button
            16. Verify that 'Logged in as username' is visible
            17. Click 'Delete Account' button
            18. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button""")
    public void registerUser() throws IOException {
        Assert.assertTrue(VerifyUrl(getDriver(),DataUtils.getPropertyValue("environment","HOME_URL")));
        LogsUtils.info("Home page is opened");

        new P01_HomePage(getDriver()).
                clickOnSignupLoginButton();
        LogsUtils.info("Signup / Login button is clicked");
        Assert.assertTrue(VerifyUrl(getDriver(),DataUtils.getPropertyValue("environment","LOGIN_SIGNUP_URL")));
        LogsUtils.info("page 'New User Signup!' is visible");

        new P02_LoginSignupPage(getDriver()).
                enterSignupUsername(SIGNUP_USERNAME).
                enterSignupEmailAddress(SIGNUP_EMAIL_ADDRESS).
                clickOnSignupButton();
        Assert.assertTrue(new P03_SignupPage(getDriver()).verifyEnterAccountInformationLabelExisted());
        LogsUtils.info("Verify that 'ENTER ACCOUNT INFORMATION' is visible");

        new P03_SignupPage(getDriver()).
                fillAccountDetails(PASSWORD, FIRSTNAME, LASTNAME, COMPANY, ADDRESS1, ADDRESS2, STATE, CITY, ZIPCODE, MOBILE_NUMBER).
                clickOnCreateAccountButton();
        Assert.assertTrue(new P04_AccountCreatedPage(getDriver()).verifyAccountCreatedLabelExisted());
        LogsUtils.info("Verify that 'ACCOUNT CREATED!' is visible");

        new P04_AccountCreatedPage(getDriver()).clickOnContinueButton();
        Assert.assertTrue(new P01_HomePage(getDriver()).verifyLoggedInUsernameIsVisible());
        LogsUtils.info("Verify that 'Logged in as username' is visible");

        new P01_HomePage(getDriver()).clickOnDeleteAccountButton();
        Assert.assertTrue(new P05_AccountDeletedPage(getDriver()).verifyAccountDeletedLabelExisted());
        LogsUtils.info("Verify that 'ACCOUNT DELETED!' is visible");

        new P05_AccountDeletedPage(getDriver()).clickOnContinueButton();
        LogsUtils.info("'continue' button is clicked and the website is returned to the home page");

    }

    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }

}
