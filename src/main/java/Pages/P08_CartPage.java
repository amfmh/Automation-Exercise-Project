package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static Utilities.Utility.*;

public class P08_CartPage {
    private final WebDriver driver;
    private final By shoppingCartRouteLabel = By.cssSelector("li.active");
    private final By ProceedToCheckoutButton = By.xpath("//a[contains(@class,'check_out')]");
    private final By registerLoginButton = By.cssSelector("div.modal-content a[href='/login']");




    public P08_CartPage(WebDriver driver) {this.driver = driver;}


    public boolean verifyCartPageIsDisplayed(){
        return findWebElement(driver,shoppingCartRouteLabel).isDisplayed();
    }

    public List<String> getFirstAndSecondProductsNames_CartPage(){
        List<String> productNamesList_CartPage = new ArrayList<>(2);
        for(int i=0 ; i<=1 ; i++){
            By getProductNames_CartPage = By.cssSelector("tr#product-"+ (i+1) + " " + "a[href = '/product_details/"+ (i+1) +"']");
            productNamesList_CartPage.add(i, findWebElement(driver,getProductNames_CartPage).getText());
        }
        return productNamesList_CartPage;
    }


    public List<String> getFirstAndSecondProductPrices_CartPage(){
        List<String> productPricesList_CartPage = new ArrayList<>(2);
        for(int i=0 ; i<=1 ; i++){
            By getProductPrices_CartPage = By.cssSelector("tr#product-" + (i+1) + " " + "td.cart_price p");
            String fullText = Utility.getText(driver,getProductPrices_CartPage);
            String productPrice = String.valueOf(Integer.parseInt(fullText.replace("Rs. ","")));
            productPricesList_CartPage.add( i , productPrice );
        }
        return productPricesList_CartPage;
    }


    public List<String> getProductsNames_CartPage(){
        By productName = By.cssSelector("h4 a");
        List<WebElement> productNames = driver.findElements(productName);
        List<String> productsNames_CartPage = new ArrayList<>();
        for(int i=0 ; i < productNames.size(); i++){
            By getProductName = By.xpath("(//h4 //a)[" + (i+1) + "]");
            productsNames_CartPage.add(i, findWebElement(driver, getProductName).getText());
        }
        return productsNames_CartPage;
    }


    public List<String> getPricesOfProducts_CartPage(){
        By prices = By.className("cart_price");
        List<WebElement> productsPrices = driver.findElements(prices);
        List<String> productsPricesList = new ArrayList<>();
        for(int i=0 ; i < productsPrices.size() ; i++){
            By getProductPrice_CartPage = By.xpath("(//td[@class = 'cart_price'])[" + (i+1) + "]");
            String fullText = Utility.getText(driver, getProductPrice_CartPage);
            String productPrice = String.valueOf(Integer.parseInt(fullText.replace("Rs. ", "")));
            productsPricesList.add(i, productPrice);
        }
        return productsPricesList;
    }


    public List<String> getQuantityOfProducts_CartPage(){
        By quantity = By.xpath("//td[@class='cart_quantity'] //button");
        List<WebElement> quantities = driver.findElements(quantity);
        List<String> productsQuantities = new ArrayList<>();
        for(int i=0 ; i < quantities.size() ; i++){
                By productQuantity = By.xpath("(//td[@class='cart_quantity'] //button)[" + (i + 1) + "]");
                productsQuantities.add(i, findWebElement(driver, productQuantity).getText());
        }
        return productsQuantities;
    }



    public List<String> getTotalPricesOfProducts_CartPage(){
        int sumOfProductsTotalPrices = 0;
        By totalPrice = By.cssSelector(".cart_total_price");
        List<WebElement> productsTotalPrices = driver.findElements(totalPrice);
        List<String> productsTotalPricesList_CartPage = new ArrayList<>();
        for(int i=0 ; i < productsTotalPrices.size() ; i++){
            By getProductTotalPrices_CartPage = By.xpath("(//p[@class='cart_total_price'])[" + (i+1) + "]");
            String fullText = Utility.getText(driver,getProductTotalPrices_CartPage);
            String productTotalPrice = String.valueOf(Integer.parseInt(fullText.replace("Rs. ","")));
            sumOfProductsTotalPrices = sumOfProductsTotalPrices + Integer.parseInt(fullText.replace("Rs. ",""));
            productsTotalPricesList_CartPage.add( i , productTotalPrice );
        }
        productsTotalPricesList_CartPage.add(String.valueOf(sumOfProductsTotalPrices));
        return productsTotalPricesList_CartPage;
    }



    public P09_Checkoutpage clickOnProceedToCheckoutButton(){
        clickingOnElement(driver,ProceedToCheckoutButton);
        return new P09_Checkoutpage(driver);
    }

    public P02_LoginSignupPage clickOnRegisterLoginButton(){
        clickingOnElement(driver, registerLoginButton);
        return new P02_LoginSignupPage(driver);
    }

}
