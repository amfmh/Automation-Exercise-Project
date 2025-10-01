package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static Utilities.Utility.*;

public class P09_Checkoutpage {
    private final WebDriver driver;
    private final By comment = By.cssSelector("textarea[name='message']");
    private final By placeOrderButton = By.cssSelector("a[href='/payment']");



    public P09_Checkoutpage(WebDriver driver) {
        this.driver = driver;
    }



    public List<String> getDeliveryAddressData(){
        List<WebElement> addressDeliveryElements = driver.findElements(By.xpath("//ul[contains(@id, 'address_delivery')]//li"));
        List<String> addressDeliveryData = new ArrayList<>();
        for(int i=0 ; i < addressDeliveryElements.size() ; i++){
            addressDeliveryData.add(addressDeliveryElements.get(i).getText());
        }
        return addressDeliveryData;
    }


    public List<String> getBillingAddressData(){
        List<WebElement> addressBillingElements = driver.findElements(By.xpath("//ul[contains(@id, 'address_invoice')]//li"));
        List<String> addressBillingData = new ArrayList<>();
        for(int i=0 ; i < addressBillingElements.size() ; i++){
            addressBillingData.add(addressBillingElements.get(i).getText());
        }
        return addressBillingData;
    }


    public List<String> getProductsNames_CheckoutPage(){
        By productName = By.cssSelector("h4 a");
        List<WebElement> productNames = driver.findElements(productName);
        List<String> productsNames_CheckoutPage = new ArrayList<>();
        for(int i=0 ; i < productNames.size(); i++){
            By getProductName = By.xpath("(//h4 //a)[" + (i+1) + "]");
            productsNames_CheckoutPage.add(i, findWebElement(driver, getProductName).getText());
        }
        return productsNames_CheckoutPage;
    }


    public List<String> getPricesOfProducts_CheckoutPage(){
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


    public List<String> getQuantityOfProducts_CheckoutPage(){
        By quantity = By.xpath("//td[@class='cart_quantity'] //button");
        List<WebElement> quantities = driver.findElements(quantity);
        List<String> productsQuantities = new ArrayList<>();
        for(int i=0 ; i < quantities.size() ; i++){
            By productQuantity = By.xpath("(//td[@class='cart_quantity'] //button)[" + (i + 1) + "]");
            productsQuantities.add(i, findWebElement(driver, productQuantity).getText());
        }
        return productsQuantities;
    }


    public List<String> getTotalPricesOfProducts_CheckoutPage(){
        By totalPrice = By.cssSelector(".cart_total_price");
        List<WebElement> productsTotalPrices = driver.findElements(totalPrice);
        List<String> productsTotalPricesList_CheckoutPage = new ArrayList<>();
        for(int i=0 ; i < productsTotalPrices.size() ; i++){
            By getProductTotalPrices_CartPage = By.xpath("(//p[@class='cart_total_price'])[" + (i+1) + "]");
            String fullText = Utility.getText(driver,getProductTotalPrices_CartPage);
            String productTotalPrice = String.valueOf(Integer.parseInt(fullText.replace("Rs. ","")));
            productsTotalPricesList_CheckoutPage.add( i , productTotalPrice );
        }
        return productsTotalPricesList_CheckoutPage;
    }


    public P10_PaymentPage enterCommentAndPlaceOrder(String text){
        sendData(driver, comment, text);
        clickingOnElement(driver, placeOrderButton);
        return new P10_PaymentPage(driver);
    }
}
