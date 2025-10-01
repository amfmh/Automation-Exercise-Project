package Pages;

import Utilities.DataUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Utilities.Utility.findWebElement;

public class P03_SignupPage {

    private final WebDriver driver;
    private final By enterAccountInformationLabel = By.xpath("//h2 //b[text()='Enter Account Information']");
    private final By titleMrRadioButton = By.id("id_gender1");
    private final By passwordInput = By.id("password");
    private final By daysSelect = By.id("days");
    private final By monthsSelect = By.id("months");
    private final By yearsSelect = By.id("years");
    private final By newsletterCheckbox = By.id("newsletter");
    private final By specialOffersCheckbox = By.id("optin");
    private final By firstNameInput = By.id("first_name");
    private final By lastNameInput = By.id("last_name");
    private final By companyInput = By.id("company");
    private final By address1Input = By.id("address1");
    private final By address2Input = By.id("address2");
    private final By countrySelect = By.id("country");
    private final By stateInput = By.id("state");
    private final By cityInput = By.id("city");
    private final By zipcodeInput = By.id("zipcode");
    private final By mobileNumberInput = By.id("mobile_number");
    private final By createAccountButton = By.cssSelector("button[data-qa='create-account']");



    public P03_SignupPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean verifyEnterAccountInformationLabelExisted(){
        return findWebElement(driver,enterAccountInformationLabel).isDisplayed();
    }


    public P03_SignupPage fillAccountDetails(String passwordText, String firstNameText, String lastNameText, String companyText, String address1Text,
                                                 String address2Text, String stateText, String cityText, String zipcodeText, String mobileNumberText){
        Utility.clickingOnElement(driver, titleMrRadioButton);
        Utility.sendData(driver, passwordInput, passwordText);
        Utility.selectingFromDropDown(driver, daysSelect, DataUtils.getJsonData("AccountDetails", "day"));
        Utility.selectingFromDropDown(driver, monthsSelect, DataUtils.getJsonData("AccountDetails", "month"));
        Utility.selectingFromDropDown(driver, yearsSelect, DataUtils.getJsonData("AccountDetails", "year"));
        Utility.clickingOnElement(driver, newsletterCheckbox);
        Utility.clickingOnElement(driver, specialOffersCheckbox);
        Utility.sendData(driver, firstNameInput, firstNameText);
        Utility.sendData(driver, lastNameInput, lastNameText);
        Utility.sendData(driver, companyInput, companyText);
        Utility.sendData(driver, address1Input, address1Text);
        Utility.sendData(driver, address2Input, address2Text);
        Utility.selectingFromDropDown(driver, countrySelect, DataUtils.getJsonData("AccountDetails", "country"));
        Utility.sendData(driver, stateInput, stateText);
        Utility.sendData(driver, cityInput, cityText);
        Utility.sendData(driver, zipcodeInput, zipcodeText);
        Utility.sendData(driver, mobileNumberInput, mobileNumberText);
        return this;
    }

    public P04_AccountCreatedPage clickOnCreateAccountButton(){
        Utility.clickingOnElement(driver, createAccountButton);
        return new P04_AccountCreatedPage(driver);
    }



}
