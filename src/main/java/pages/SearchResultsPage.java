package pages;

import annotation.FieldName;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import steps.BaseSteps;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static utils.DriverManager.getDriver;

public class SearchResultsPage extends BasePage {

    @FindBy(xpath = "//div[@data-widget=\"searchResultsFilters\"]/aside/div[.//div[@unit=\"[object Object]\"]]")
    @FieldName(name = "фильтры с ползунком")
    List<WebElement> fromToFilters;

    @FindBy(xpath = "//div[@data-widget=\"searchResultsFilters\"]/aside/div[.//div[@value]]")
    @FieldName(name = "чекбоксы")
    List<WebElement> checkboxFilters;

    @FindBy(xpath = "//div[@data-widget=\"searchResultsFilters\"]/aside/div[.//span[@class=\"show\"]]")
    @FieldName(name = "чекмарки")
    List<WebElement> checkmarkFilters;

    @FindBy(xpath ="//div[contains(@class,\"widget-search-result-container\")]/div/div[@class]")
    @FieldName(name = "результаты поиска")
    List<WebElement> searchResults;

    @FindBy(xpath = "//a[@data-widget=\"cart\"]")
    @FieldName(name = "корзина")
    WebElement cart;

    @FindBy(xpath = "//a[@data-widget=\"cart\"]/span[contains(@class,\"f-caption--bold\")]")
    @FieldName(name = "количество товаров в корзине")
    WebElement numberOfProductsInCart;

    @Override
    public WebElement getField(String name) throws Exception {
        return getField(name, "pages.SearchResultsPage");
    }

    public void setFromToFilterValue (String filterName, String filterParamName, String value) {
        for (WebElement element : fromToFilters) {
            if(element.findElement(By.xpath("./div[text()]")).getText().contains(filterName)) {
                fillField(element.findElement(By.xpath(".//div[./label[text()=\"" + filterParamName + "\"]]//input")), value);
                element.findElement(By.xpath("//div[./label[text()=\"" + filterParamName + "\"]]//input")).sendKeys(Keys.ENTER);
                waiter();
                waitWhileFilterLoaded();
                break;
            }
        }
    }

    public void setCheckbox(String checkboxName) {
        for (WebElement element : checkboxFilters) {
            if(element.findElement(By.xpath(".//span")).getText().contains(checkboxName)) {
                scrollToElement(element.findElement(By.xpath(".//span")));
                jsClick(element.findElement(By.xpath(".//div[./input]")));
                waiter();
                waitWhileFilterLoaded();
                break;
            }
        }
    }

    public void waitWhileFilterLoaded() {
        /*try {
            scrollToElement(searchResults.get(0));
            waitElementToBeClickable(searchResults.get(0).findElement(By.xpath("//div[./div[contains(text(),\"В корзину\")]]")));
        } catch (NoSuchElementException e) {
            Assert.fail("По данным параметрам не найдено ни одного товара.");
        }*/
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setCheckmark(String filterName, String checkmarkName) {
        for (WebElement element : checkmarkFilters) {
            if (element.findElement(By.xpath("./div[@class and not(@label)]")).getText().contains(filterName)) {
                scrollToElement(element);
                try {
                    getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                    element.findElement(By.xpath(".//span[contains(text(),\"Посмотреть все\")]")).click();
                    waiter();
                    getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                } catch (NoSuchElementException e) {}
                jsClick( element.findElement(By.xpath(".//label[.//span[contains(text(),\"" + checkmarkName + "\")]]")));
                waiter();
                waitWhileFilterLoaded();
                break;
            }
        }
    }

    public void addToCartByCondition(String numberOfProducts, String conditionName) {
        getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        int number = Integer.parseInt(numberOfProducts);
        System.out.println(number);
        int count=0;
        switch(conditionName) {
            case "чётных" :
                count=1;
                break;
            case"нечётных" :
                count=0;
                break;
        }
        while(number>0) {
            try {
                scrollToElement(searchResults.get(count));
                waitElementToBeClickable(searchResults.get(count).findElement(By.xpath(".//div[./div[contains(text(),\"В корзину\")]]")));
                searchResults.get(count).findElement(By.xpath(".//div[./div[contains(text(),\"В корзину\")]]")).click();
                BaseSteps.addedToCart.put(searchResults.get(count).findElement(By.xpath(".//div[./span]//a[contains(@class,\"tile-hover-target\")]")).getText(),searchResults.get(count).findElement(By.xpath(".//span[contains(text(),\"₽\")]")).getText());
                waitWhileProductAdded();
                count = count +2;
                number--;
                System.out.println(number);
            } catch (NoSuchElementException e) {
                count = count + 2;
                System.out.println(number);
            }
        }
        System.out.println(BaseSteps.addedToCart.toString());
        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void addToCartByCondition(String conditionName) {
        getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        int count=0;
        switch(conditionName) {
            case "чётные" :
                count=1;
                break;
            case"нечётные" :
                count=0;
                break;
        }
        System.out.println(searchResults.size());
            while(count<searchResults.size()) {
            try {
                scrollToElement(searchResults.get(count).findElement(By.xpath(".//div[./div[contains(text(),\"В корзину\")]]")));
                waitElementToBeClickable(searchResults.get(count).findElement(By.xpath(".//div[./div[contains(text(),\"В корзину\")]]")));
                searchResults.get(count).findElement(By.xpath(".//div[./div[contains(text(),\"В корзину\")]]")).click();
                BaseSteps.addedToCart.put(searchResults.get(count).findElement(By.xpath(".//a[contains(@class,\"a4s8 tile-hover-target\")]")).getText(),searchResults.get(count).findElement(By.xpath(".//span[contains(text(),\"₽\")]")).getText());
                waitWhileProductAdded();
                count = count + 2;
            } catch (NoSuchElementException e) {
                count = count + 2;
            }
        }
        System.out.println(BaseSteps.addedToCart.toString());
        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void goToCart() throws Exception {
            cart.click();
    }

    public void waitWhileProductAdded() {
        while(true) {
            if(BaseSteps.addedToCart.size()==(Integer.parseInt(numberOfProductsInCart.getText()))) break;
            else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
