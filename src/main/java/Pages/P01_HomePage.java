package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Utilities.Utility.clickingOnElement;
import static Utilities.Utility.findWebElement;

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

}
