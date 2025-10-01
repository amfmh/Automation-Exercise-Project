package Tests;


import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_HomePage;
import Pages.P02_LoginSignupPage;
import Utilities.DataUtils;
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
import static Utilities.Utility.VerifyUrl;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC04_LogoutUser {

    private final  String EXISTED_EMAIL_ADDRESS = DataUtils.getJsonData("ExistingUser","email");
    private final  String EXISTED_PASSWORD = DataUtils.getJsonData("ExistingUser","password");


    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    public void logoutUser() throws IOException {
        Assert.assertTrue(VerifyUrl(getDriver(),DataUtils.getPropertyValue("environment","HOME_URL")));


        new P01_HomePage(getDriver()).clickOnSignupLoginButton();
        Assert.assertTrue(new P02_LoginSignupPage(getDriver()).verifyLoginToYourAccountLabelExisted());


        new P02_LoginSignupPage(getDriver()).
                enterLoginEmailAddress(EXISTED_EMAIL_ADDRESS).
                enterLoginPassword(EXISTED_PASSWORD).
                clickOnLoginButton();
        Assert.assertTrue(new P01_HomePage(getDriver()).verifyLoggedInUsernameIsVisible());


        new P01_HomePage(getDriver()).clickOnLogoutButton();
        Assert.assertTrue(VerifyUrl(getDriver(),DataUtils.getPropertyValue("environment","LOGIN_SIGNUP_URL")));
    }



    @AfterMethod
    public void quit() {
        quitDriver();
    }

}
