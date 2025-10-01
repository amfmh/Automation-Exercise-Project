package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Utilities.Utility.findWebElement;

public class P04_AccountCreatedPage {
    private final WebDriver driver;
    private final By accountCreatedLabel = By.cssSelector("h2[data-qa='account-created']");
    private final By continueButton = By.cssSelector("a[data-qa='continue-button']");

    public P04_AccountCreatedPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean verifyAccountCreatedLabelExisted(){
        return findWebElement(driver,accountCreatedLabel).isDisplayed();
    }

    public P01_HomePage clickOnContinueButton(){
        Utility.clickingOnElement(driver, continueButton);
        return new P01_HomePage(driver);
    }
}
