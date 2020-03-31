package pages;

import jdk.nashorn.internal.ir.WhileNode;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static utils.DriverManager.getDriver;

public class BasePage {

    public BasePage(){
        PageFactory.initElements(getDriver(), this);
    }

    public void waitWhileValueChanges(WebElement element) {
        String actualValue = element.getText();
        int count = 0;
        while(true && count<20) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(element.getText().equals(actualValue)) break;
            else  {
                actualValue = element.getText();
                count++;
            }
        }
    }

    private boolean isElementPresent(By by) {
        try {
            getDriver().findElement(by);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView();", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickElement(WebElement element) {
        scrollToElement(element);
        element.click();
    }

    public WebElement waitElementToBeClickable(WebElement element) {
        new WebDriverWait(getDriver(),10).until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    public boolean isElementPresent(WebElement element) {
        try {
            getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
        finally {
            getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
    }

    public void fillField(WebElement element, String value) {
        scrollToElement(element);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
        element.sendKeys(value);
    }

    public void waitWhileFieldIsEmpty(WebElement element) {
        while(true) {
            if (element.getText().equals("")) break;
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
