package Tests;


import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_HomePage;
import Pages.P02_LoginSignupPage;
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
public class TC03_LoginUserWithIncorrectEmailAndPassword {

    private final String INCORRECT_LOGIN_EMAIL_ADDRESS = "email" + Utility.getTimeStamp() + "@gmail.com";
    private final String INCORRECT_LOGIN_PASSWORD = "pass" + Utility.getTimeStamp();



    @BeforeMethod(alwaysRun = true)
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    public void LoginUserWithIncorrectEmailAndPassword() throws IOException {
        Assert.assertTrue(VerifyUrl(getDriver(), DataUtils.getPropertyValue("environment","HOME_URL")));
        LogsUtils.info("Verify that home page is visible successfully");


        new P01_HomePage(getDriver()).clickOnSignupLoginButton();
        LogsUtils.info("Click on 'Signup / Login' button");
        Assert.assertTrue(new P02_LoginSignupPage(getDriver()).verifyLoginToYourAccountLabelExisted());
        LogsUtils.info("Verify 'Login to your account' is visible");


        new P02_LoginSignupPage(getDriver()).
                enterLoginEmailAddress(INCORRECT_LOGIN_EMAIL_ADDRESS).
                enterLoginPassword(INCORRECT_LOGIN_PASSWORD).
                clickOnLoginButton();
        LogsUtils.info("Enter incorrect email address and password & Click 'login' button");
        Assert.assertTrue(new P02_LoginSignupPage(getDriver()).verifyIncorrectLoginInputDataValidationMessageIsViible());
        LogsUtils.info("Verify error 'Your email or password is incorrect!' is visible");


    }


    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }

}
