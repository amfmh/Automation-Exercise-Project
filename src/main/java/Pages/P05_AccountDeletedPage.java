package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Utilities.Utility.findWebElement;

public class P05_AccountDeletedPage {
    private final WebDriver driver;
    private final By accountDeletedLabel = By.xpath("//b[text()='Account Deleted!']");
    private final By continueButton = By.cssSelector("a[data-qa='continue-button']");

    public P05_AccountDeletedPage(WebDriver driver){
        this.driver = driver;
    }

    public boolean verifyAccountDeletedLabelExisted(){
        return findWebElement(driver, accountDeletedLabel).isDisplayed();
    }

    public P01_HomePage clickOnContinueButton(){
        Utility.clickingOnElement(driver, continueButton);
        return new P01_HomePage(driver);
    }
}
