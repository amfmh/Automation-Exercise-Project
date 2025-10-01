package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Utilities.Utility.*;

public class P07_ProductDetailsPage {
    private final WebDriver driver;
    private final By productName = By.cssSelector("div[class='product-information'] h2");
    private final By productCategory = By.cssSelector("div.product-information p");
    private final By productPrice = By.cssSelector("div[class='product-information'] span span");
    private final By productAvailability = By.cssSelector("div.product-information p:nth-of-type(2)");
    private final By productCondition = By.cssSelector("div.product-information p:nth-of-type(3)");
    private final By productBrand = By.cssSelector("div.product-information p:nth-of-type(4)");
    private final By quantityInput = By.id("quantity");
    private final By addToCartButton = By.cssSelector("button[class='btn btn-default cart']");
    private final By viewCartButton = By.cssSelector("a[href='/view_cart'] u");




    public P07_ProductDetailsPage(WebDriver driver) {

        this.driver = driver;

    }

    public boolean verifyProductNameIsVisible(){
        return findWebElement(driver, productName).isDisplayed();
    }

    public boolean verifyProductCategoryIsVisible(){
        return findWebElement(driver, productCategory).isDisplayed();
    }

    public boolean verifyProductPriceIsVisible(){
        return findWebElement(driver, productPrice).isDisplayed();
    }

    public boolean verifyProductAvailabilityIsVisible(){
        return findWebElement(driver, productAvailability).isDisplayed();
    }

    public boolean verifyProductConditionIsVisible(){
        return findWebElement(driver, productCondition).isDisplayed();
    }

    public boolean verifyProductBrandIsVisible(){
        return findWebElement(driver, productBrand).isDisplayed();
    }

    public P07_ProductDetailsPage increaseQuantity(String quantity){
        findWebElement(driver,quantityInput).clear();
        sendData(driver,quantityInput,quantity);
        return this;
    }

    public P07_ProductDetailsPage clickOnAddToCartButton(){
        Utility.clickingOnElement(driver,addToCartButton);
        return this;
    }

    public P08_CartPage clickOnViewCartButton(){
        Utility.clickingOnElement(driver,addToCartButton);
        return new P08_CartPage(driver);
    }


}
