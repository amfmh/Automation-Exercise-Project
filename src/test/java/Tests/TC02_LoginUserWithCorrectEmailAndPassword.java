package Tests;


import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_HomePage;
import Pages.P02_LoginSignupPage;
import Pages.P05_AccountDeletedPage;
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
@Feature("User")
@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC02_LoginUserWithCorrectEmailAndPassword {

    private final  String EXISTED_EMAIL_ADDRESS = DataUtils.getJsonData("ExistingUser","email");
    private final  String EXISTED_PASSWORD = DataUtils.getJsonData("ExistingUser","password");

    @BeforeMethod(alwaysRun = true)
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test(description = "Test Case 2: Login User with correct email and password")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login User with correct email and password")
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Click on 'Signup / Login' button
            5. Verify 'Login to your account' is visible
            6. Enter correct email address and password
            7. Click 'login' button
            8. Verify that 'Logged in as username' is visible""")
    public void LoginUserWithCorrectEmailAndPassword() throws IOException {
        Assert.assertTrue(VerifyUrl(getDriver(),DataUtils.getPropertyValue("environment","HOME_URL")));
        LogsUtils.info("Verify that home page is visible successfully");

        new P01_HomePage(getDriver()).clickOnSignupLoginButton();
        LogsUtils.info("Click on 'Signup / Login' button");

        Assert.assertTrue(new P02_LoginSignupPage(getDriver()).verifyLoginToYourAccountLabelExisted());
        LogsUtils.info("Verify 'Login to your account' is visible");


        new P02_LoginSignupPage(getDriver()).
                enterLoginEmailAddress(EXISTED_EMAIL_ADDRESS).
                enterLoginPassword(EXISTED_PASSWORD).
                clickOnLoginButton();
        LogsUtils.info("Enter correct email address and password & Click 'login' button");
        Assert.assertTrue(new P01_HomePage(getDriver()).verifyLoggedInUsernameIsVisible());
        LogsUtils.info("Verify 'Login to your account' is visible");


        new P01_HomePage(getDriver()).clickOnDeleteAccountButton();
        LogsUtils.info("Click 'Delete Account' button");

        Assert.assertTrue(new P05_AccountDeletedPage(getDriver()).verifyAccountDeletedLabelExisted());
        LogsUtils.info("Verify that 'ACCOUNT DELETED!' is visible");

    }


    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }

}
