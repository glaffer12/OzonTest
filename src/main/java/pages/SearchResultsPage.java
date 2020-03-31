package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import steps.BaseSteps;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static utils.DriverManager.getDriver;

public class SearchResultsPage extends BasePage {

    @FindBy(xpath = "//div[@data-widget=\"searchResultsFilters\"]/aside/div[.//div[@unit=\"[object Object]\"]]")
    List<WebElement> fromToFilters;

    @FindBy(xpath = "//div[@data-widget=\"searchResultsFilters\"]/aside/div[.//div[@value]]")
    List<WebElement> checkboxFilters;

    @FindBy(xpath = "//div[@data-widget=\"searchResultsFilters\"]/aside/div[.//span[@class=\"show\"]]")
    List<WebElement> checkmarkFilters;

    @FindBy(xpath ="//div[@class=\"widget-search-result-container an2\"]/div/div[@class]")
    List<WebElement> searchResults;

    @FindBy(xpath = "//a[@data-widget=\"cart\"]")
    WebElement cart;

    @FindBy(xpath = "//a[@data-widget=\"cart\"]/span[contains(@class,\"f-caption--bold\")]")
    WebElement numberOfProductsInCart;

    public void setFromToFilterValue (String filterName, String filterParamName, String value) {
        for (WebElement element : fromToFilters) {
            if(element.findElement(By.xpath("./div[text()]")).getText().contains(filterName)) {
                fillField(element.findElement(By.xpath(".//div[./label[text()=\"" + filterParamName + "\"]]//input")), value);
                element.findElement(By.xpath("//div[./label[text()=\"" + filterParamName + "\"]]//input")).sendKeys(Keys.ENTER);
                break;
            }
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getDriver().navigate().refresh();
    }

    public void setCheckbox(String checkboxName) {
        for (WebElement element : checkboxFilters) {
            if(element.findElement(By.xpath(".//span")).getText().contains(checkboxName)) {
                scrollToElement(element.findElement(By.xpath(".//span")));
                element.findElement(By.xpath(".//div[./input]")).click();
                break;
            }
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getDriver().navigate().refresh();
    }

    public void setCheckmark(String filterName, String checkmarkName) {
        for (WebElement element : checkmarkFilters) {
            if (element.findElement(By.xpath("./div[@class and not(@label)]")).getText().contains(filterName)) {
                scrollToElement(element);
                try {
                    getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                    element.findElement(By.xpath(".//span[contains(text(),\"Посмотреть все\")]")).click();
                    getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                } catch (NoSuchElementException e) {}
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element.findElement(By.xpath(".//label[.//span[contains(text(),\"" + checkmarkName + "\")]]")));
                break;
            }
        }
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getDriver().navigate().refresh();
    }

    /*public void addToCartByPosition(int[] positionNumbers) {
        for (int positionNumber : positionNumbers) {
            searchResults.get(positionNumber).findElement(By.xpath(".//div[@class=\"a4o8 ui-a6\"]/button[@class=\"ui-a9\"]")).click();
            BaseSteps.addedToCart.add(searchResults.get(positionNumber).findElement(By.xpath("//a[@class=\"a3b3 tile-hover-target\"]")).getText());
            waitWhileProductAdded();
        }
    }*/

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
                scrollToElement(searchResults.get(count).findElement(By.xpath(".//div[./div[contains(text(),\"В корзину\")]]")));
                searchResults.get(count).findElement(By.xpath(".//div[./div[contains(text(),\"В корзину\")]]")).click();
                BaseSteps.addedToCart.put(searchResults.get(count).findElement(By.xpath(".//a[contains(@class,\"a4s8 tile-hover-target\")]")).getText(),searchResults.get(count).findElement(By.xpath(".//span[contains(text(),\"₽\")]")).getText());
                waitWhileProductAdded();
                count = count +2;
                number--;
            } catch (NoSuchElementException e) {
                count = count + 2;
            }
        }
        System.out.println(BaseSteps.addedToCart.toString());
        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            while(count<searchResults.size()-1) {
            try {
                scrollToElement(searchResults.get(count).findElement(By.xpath(".//div[./div[contains(text(),\"В корзину\")]]")));
                searchResults.get(count).findElement(By.xpath(".//div[./div[contains(text(),\"В корзину\")]]")).click();
                BaseSteps.addedToCart.put(searchResults.get(count).findElement(By.xpath(".//a[contains(@class,\"a4s8 tile-hover-target\")]")).getText(),searchResults.get(count).findElement(By.xpath(".//span[contains(text(),\"₽\")]")).getText());
                waitWhileProductAdded();
                /*try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                count = count + 2;
            } catch (NoSuchElementException e) {
                count = count + 2;
            }
        }
        System.out.println(BaseSteps.addedToCart.toString());
        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void goToCart() {
        cart.click();
    }

    public void waitWhileProductAdded() {
        while(true) {
            if(BaseSteps.addedToCart.size()==(Integer.parseInt(numberOfProductsInCart.getText()))) break;
            else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //div[@class="bv7" and ./div[contains(text(),"Оперативная память")]]//div[@class="b2v" and ./label[contains(text(),"от")]]//input

}
