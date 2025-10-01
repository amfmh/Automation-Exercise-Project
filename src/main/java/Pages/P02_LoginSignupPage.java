package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Utilities.Utility.findWebElement;

public class P02_LoginSignupPage {

    private final WebDriver driver;
    private final By signupUsernameInput = By.xpath("//input[@type='text']");
    private final By signupEmailAddressInput = By.xpath("//form[@action='/signup'] //input[@name='email']");
    private final By SignupButton = By.xpath("//button[text()='Signup']");
    private final By loginToYourAccountLabel = By.cssSelector("div[class='login-form'] h2");
    private final By loginEmailAddressInput = By.cssSelector("input[data-qa='login-email']");
    private final By loginPasswordInput = By.cssSelector("input[data-qa='login-password']");
    private final By loginButton = By.cssSelector("button[data-qa='login-button']");
    private final By INCORRECT_LOGIN_INPUT_DATA_VALIDATION_MESSAGE = By.cssSelector("form[action = '/login'] p");


    public P02_LoginSignupPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean verifyLoginSignupUrl ( String expectedValue ) {
        return driver.getCurrentUrl().equals(expectedValue);
    }

    public P02_LoginSignupPage enterSignupUsername(String usernameText) {
        Utility.sendData(driver, signupUsernameInput, usernameText);
        return this;
    }

    public P02_LoginSignupPage enterSignupEmailAddress(String emailText) {
        Utility.sendData(driver, signupEmailAddressInput, emailText);
        return this;
    }

    public P03_SignupPage clickOnSignupButton() {
        Utility.clickingOnElement(driver, SignupButton);
        return new P03_SignupPage(driver);
    }

    public boolean verifyLoginToYourAccountLabelExisted(){
        return findWebElement(driver,loginToYourAccountLabel).isDisplayed();
    }

    public P02_LoginSignupPage enterLoginEmailAddress(String emailText) {
        Utility.sendData(driver, loginEmailAddressInput, emailText);
        return this;
    }

    public P02_LoginSignupPage enterLoginPassword(String emailText) {
        Utility.sendData(driver, loginPasswordInput, emailText);
        return this;
    }

    public P03_SignupPage clickOnLoginButton() {
        Utility.clickingOnElement(driver, loginButton);
        return new P03_SignupPage(driver);
    }


    public boolean verifyIncorrectLoginInputDataValidationMessageIsViible(){
        return findWebElement(driver,INCORRECT_LOGIN_INPUT_DATA_VALIDATION_MESSAGE).isDisplayed();
    }

}
