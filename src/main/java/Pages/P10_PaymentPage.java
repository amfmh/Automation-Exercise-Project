package Pages;

import org.openqa.selenium.By;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;

import static Utilities.DataUtils.getJsonData;
import static Utilities.Utility.*;

public class P10_PaymentPage {
    private final WebDriver driver;
    private final By nameOnCardInput = By.cssSelector("input[data-qa='name-on-card']");
    private final By cardNumberInput = By.cssSelector("input[data-qa='card-number']");
    private final By cvcInput = By.cssSelector("input[data-qa='cvc']");
    private final By expirationMonthInput = By.cssSelector("input[data-qa='expiry-month']");
    private final By expirationYearInput = By.cssSelector("input[data-qa='expiry-year']");
    private final By payAndConfirmOrderButton = By.cssSelector("button[data-qa='pay-button']");
    private final By successMessage = By.xpath("//p[text() = 'Congratulations! Your order has been confirmed!']");
    private final By downloadInvoiceButton = By.cssSelector("a.btn.btn-default.check_out");
    private final By continueButton = By.cssSelector(".btn.btn-primary");


    public P10_PaymentPage(WebDriver driver) {
        this.driver = driver;
    }


    public P10_PaymentPage fillPaymentDetails(){
        sendData(driver, nameOnCardInput, getJsonData("PaymentDetails","nameOnCard"));
        sendData(driver, cardNumberInput, getJsonData("PaymentDetails","cardNumber"));
        sendData(driver, cvcInput, getJsonData("PaymentDetails","cvc"));
        sendData(driver, expirationMonthInput, getJsonData("PaymentDetails","expirationMonth"));
        sendData(driver, expirationYearInput, getJsonData("PaymentDetails","expirationYear"));

        clickingOnElement(driver, payAndConfirmOrderButton);


        return this;
    }

    public boolean getSuccessMessageText(){

        return generalWait(driver).until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();

    }


    public P10_PaymentPage clickOnDownloadInvoiceButton(){
        //clickingOnElement(driver, downloadInvoiceButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", findWebElement(driver, downloadInvoiceButton));
        return this;
    }

    public static boolean fileExists(String filePath) throws InterruptedException {
        Thread.sleep(3000);
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }


    public P01_HomePage clickOnContinueButton(){
        clickingOnElement(driver, continueButton);
        return new P01_HomePage(driver);
    }



}
