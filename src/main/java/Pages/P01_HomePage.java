package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.Set;

import static Utilities.Utility.*;
import static Utilities.Utility.scrolling;

public class P01_HomePage {

    private final WebDriver driver;
    private final By SignupLoginButton = By.xpath("//a[contains(text(), 'Signup')]");
    private final By loggedInUsername = By.xpath("//ul[contains(@class,'navbar-nav')] //li[10] //a //b");
    private final By deleteAccountButton = By.xpath("//ul[contains(@class,'navbar-nav')] //a[contains(@href,'/delete_account')]");
    private final By logoutButton = By.cssSelector("a[href = '/logout']");
    private final By productsButton = By.cssSelector("a[href='/products']");
    private final By categories = By.id("accordian");
    private final By womenCategory = By.cssSelector("a[href = '#Women']");
    private final By womenDressCategory = By.cssSelector("a[href='/category_products/1']");
    private final By cartButton = By.cssSelector("ul a[href='/view_cart']");



    public P01_HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean verifyHomeUrl ( String expectedValue ) { return driver.getCurrentUrl().equals(expectedValue); }

    public P02_LoginSignupPage clickOnSignupLoginButton() {
        Utility.clickingOnElement(driver, SignupLoginButton);
        return new P02_LoginSignupPage(driver);
    }

    public boolean verifyLoggedInUsernameIsVisible(){
        return findWebElement(driver,loggedInUsername).isDisplayed();
    }

    public P05_AccountDeletedPage clickOnDeleteAccountButton(){
        Utility.clickingOnElement(driver, deleteAccountButton);
        return new P05_AccountDeletedPage(driver);
    }

    public P02_LoginSignupPage clickOnLogoutButton(){
        Utility.clickingOnElement(driver, logoutButton);
        return new P02_LoginSignupPage(driver);
    }

    public P06_ProductsPage clickOnProductsButton(){
        Utility.clickingOnElement(driver, productsButton);
        return new P06_ProductsPage(driver);
    }

    public boolean verifyCategoriesAreVisible(){
        return findWebElement(driver,categories).isDisplayed();
    }

    public P01_HomePage clickOnWomenCategory(){
        clickingOnElement(driver,womenCategory);
        return this;
    }

    public P06_ProductsPage clickOnDressCategory(){
        clickingOnElement(driver,womenDressCategory);
        return new P06_ProductsPage(driver);
    }

    public P08_CartPage clickOnCartButton(){
        clickingOnElement(driver, cartButton);
        return new P08_CartPage(driver);
    }
    public P07_ProductDetailsPage clickOnViewButtonOfAnyProduct(int numberOfProductsNeeded , int totalNumberOfProducts){
        Set<Integer> randomNumbers = generateUniqueNumber(numberOfProductsNeeded, totalNumberOfProducts);
        for (int random : randomNumbers) {
            By viewProductButtonForAnyProduct = By.cssSelector("a[href = '/product_details/" + random + "']");

            // The next two lines to remove any ads in products page
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.querySelectorAll('.ad, .popup, .overlay').forEach(el => el.remove());");

            scrolling(driver , viewProductButtonForAnyProduct);
            Actions actions = new Actions(driver);
            actions.moveToElement(findWebElement(driver, viewProductButtonForAnyProduct)).perform();
            LogsUtils.info("randomNumber " + random);
            clickingOnElement(driver, viewProductButtonForAnyProduct);
        }
        return new P07_ProductDetailsPage(driver);
    }
}
