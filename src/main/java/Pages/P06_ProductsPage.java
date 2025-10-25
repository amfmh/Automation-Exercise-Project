package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static Utilities.Utility.*;

public class P06_ProductsPage {
    private final WebDriver driver;
    private final By allProductsLabel = By.cssSelector(".title.text-center");
    private final By productsList = By.cssSelector("div.product-image-wrapper");
    private final By viewProductOfFirstProductButton = By.cssSelector("a[href = '/product_details/1']");
    private final By searchProductInput = By.id("search_product");
    private final By searchProductButton = By.id("submit_search");
    private final By productNamesSearchResults = By.xpath("//div[@class='productinfo text-center']/p");
    private final By firstProductCard = By.xpath("(//div[@class = 'single-products'])[1]");
    private final By addToCartButtonForFirstProductOverlay = By.xpath("//div[@class = 'product-overlay'] //a[@data-product-id='1']");
    private final By secondProductCard = By.xpath("(//div[@class = 'single-products'])[2]");
    private final By addToCartButtonForSecondProductOverlay = By.xpath("//div[@class = 'product-overlay'] //a[@data-product-id='2']");
    private final By continueShoppingButton = By.cssSelector("button[data-dismiss='modal']");
    private final By viewCartButton = By.xpath("//div[@class = 'modal-content'] //a");
    private final By cartButton = By.cssSelector("a[href='/view_cart'] i.fa-shopping-cart");
    private final By womenDressCategoryPagePath = By.xpath("//li[text() = 'Women > Dress']");
    private final By titleOfWomenDressCategoryPage = By.cssSelector("h2.text-center");
    private final By menCategory = By.cssSelector("a[href = '#Men']");
    private final By menTshirtsCategory = By.cssSelector("a[href='/category_products/3']");
    private final By MenTshirtsCategoryPagePath = By.xpath("//li[text() = 'Men > Tshirts']");
    private final By brands = By.className("brands-name");
    private final By poloBrand = By.cssSelector("a[href='/brand_products/Polo']");
    private final By TitleTextCenter = By.cssSelector(".title.text-center");
    private final By madameBrand = By.cssSelector("a[href='/brand_products/Madame']");



    public P06_ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean veryProductsPageVisibleByAllProductsLabel(){
        return findWebElement(driver, allProductsLabel).isDisplayed();
    }


    public boolean verifyProductsListIsVisible(){
        return findWebElement(driver, productsList).isDisplayed();
    }

    public P07_ProductDetailsPage clickOnViewProductButtonOfFirstProduct(){
        // To eliminate any advertisements at the bottom of the page
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelectorAll('iframe').forEach(el => el.remove());");

        clickingOnElement(driver, viewProductOfFirstProductButton);
        return new P07_ProductDetailsPage(driver);
    }


    /*public P07_ProductDetailsPage clickOnViewButtonOfAnyProduct(int numberOfProductsNeeded , int totalNumberOfProducts){
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
    }*/


    public P06_ProductsPage fillSearchProductInput(String searchKey) {
        sendData(driver, searchProductInput, searchKey);
        return this;
    }
    public P06_ProductsPage clickOnSearchProductButton(){
        clickingOnElement(driver, searchProductButton);
        return this;
    }

    public List<WebElement> getProductNamesSearchResults() {

        List<WebElement> itemsList = driver.findElements(productNamesSearchResults);
        return itemsList;
    }

    public List<String> getProductNames() {
        List<String> itemsnamesList = new ArrayList<>();
        List<WebElement> itemsList = driver.findElements(productNamesSearchResults);
        for(int i=0 ; i<itemsList.size() ; i++){
            itemsnamesList.add(i, itemsList.get(i).getText());
        }
        return itemsnamesList;
    }

    public List<String> getFirstAndSecondProductsNames_ProductsPage(){
        List<String>productNamesList_ProductsPage = new ArrayList<>(2);
        for(int i=0 ; i<=1 ; i++){
            By getProductNames_ProductsPage = By.xpath("(//div[@class = 'productinfo text-center'] //p)[" + (i+1) + "]");
            productNamesList_ProductsPage.add( i , findWebElement(driver,getProductNames_ProductsPage).getText() );
        }
        return productNamesList_ProductsPage;
    }

    public List<String> getFirstAndSecondProductPrices_ProductsPage(){
        List<String>productPricesList_ProductsPage = new ArrayList<>(2);
        for(int i=0 ; i<=1 ; i++){
            By getProductPrices_ProductsPage = By.xpath("(//div[@class='productinfo text-center'] //h2)[" + (i+1) + "]");
            String fullText = getText(driver,getProductPrices_ProductsPage);
            String productPrice = String.valueOf(Integer.parseInt(fullText.replace("Rs. ","")));
            productPricesList_ProductsPage.add( i , productPrice );
        }
        return productPricesList_ProductsPage;
    }

    public P06_ProductsPage clickOnAddToCartButtonForFirstProduct(){
        scrolling(driver , firstProductCard);
        Actions actions = new Actions(driver);
        actions.moveToElement(findWebElement(driver, firstProductCard)).perform();
        clickingOnElement(driver, addToCartButtonForFirstProductOverlay);
        return this;
    }

    public P06_ProductsPage clickOnContinueShoppingButton(){
        clickingOnElement(driver, continueShoppingButton);
        return this;
    }

    public P06_ProductsPage clickOnAddToCartButtonForSecondProduct(){
        scrolling(driver , secondProductCard);
        Actions actions = new Actions(driver);
        actions.moveToElement(findWebElement(driver, secondProductCard)).perform();
        clickingOnElement(driver, addToCartButtonForSecondProductOverlay);
        return this;
    }

    public P08_CartPage clickOnViewCartButton(){
        clickingOnElement(driver, viewCartButton);
        return new P08_CartPage(driver);
    }


    public P06_ProductsPage addRandomProducts(int numberOfProductsNeeded , int totalNumberOfProducts){
        Set<Integer> randomNumbers = generateUniqueNumber(numberOfProductsNeeded, totalNumberOfProducts); //3 > 2,4,1
        for (int random : randomNumbers) {
            By cardForAllProducts = By.xpath("(//div[@class = 'single-products'])["+random+"]");

            // The next two lines to remove any ads in products page
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.querySelectorAll('.ad, .popup, .overlay').forEach(el => el.remove());");

            scrolling(driver , cardForAllProducts);
            Actions actions = new Actions(driver);
            actions.moveToElement(findWebElement(driver, cardForAllProducts)).perform();
            LogsUtils.info("randomNumber " + random);
            By addToCartButtonForAllProductsOverlay = By.xpath("(//div[@class='product-overlay']//a[contains(@class,add-to-cart)])["+random+"]"); //dynamic Locator
            clickingOnElement(driver, addToCartButtonForAllProductsOverlay);
            clickingOnElement(driver, continueShoppingButton);
        }
        return this;
    }


    public P08_CartPage clickOnCartButton(){
        clickingOnElement(driver,cartButton);
        return new P08_CartPage(driver);
    }


    public boolean verifyWomenDressCategoryPageIsVisible(){
        return findWebElement(driver,womenDressCategoryPagePath).isDisplayed();
    }


    public String getTheTitleOfWomenDressCategoryPage(){
        return findWebElement(driver,titleOfWomenDressCategoryPage).getText();
    }

    public P06_ProductsPage clickOnMenCategory(){
        clickingOnElement(driver,menCategory);
        return this;
    }


    public P06_ProductsPage clickOnMenTshirtsCategory(){
        clickingOnElement(driver,menTshirtsCategory);
        return this;
    }


    public boolean verifyMenTshirtsCategoryPageIsVisible(){
        return findWebElement(driver,MenTshirtsCategoryPagePath).isDisplayed();
    }


    public boolean verifyBrandsAreVisible(){
        return findWebElement(driver,brands).isDisplayed();
    }


    public P06_ProductsPage clickOnPoloBrand(){
        clickingOnElement(driver,poloBrand);
        return this;
    }


    public String getTheTitleOfPoloBrandPage(){
        return findWebElement(driver,TitleTextCenter).getText();
    }


    public P06_ProductsPage clickOnMadameBrand(){
        clickingOnElement(driver,madameBrand);
        return this;
    }


    public String getTheTitleOfMadameBrandPage(){
        return findWebElement(driver,TitleTextCenter).getText();
    }

}
