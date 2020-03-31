package pages;

import annotation.FieldName;
import jdk.nashorn.internal.ir.WhileNode;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static utils.DriverManager.getDriver;

public abstract class BasePage {

    public BasePage(){
        PageFactory.initElements(getDriver(), this);
    }

    public void waiter() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void jsClick(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView();", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public void fillField(String fieldName, String value) throws Exception {
        WebElement element = getField(fieldName);
        scrollToElement(element);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
        element.sendKeys(value);
    }


    public WebElement getField(String name, String className) throws Exception {
        Class aClass = Class.forName(className);
        List<Field> fields = Arrays.asList(aClass.getFields());
        for (Field field : fields) {
            if (field.getAnnotation(FieldName.class).name().equalsIgnoreCase(name)) {
                return getDriver().findElement(By.xpath(field.getAnnotation(FindBy.class).xpath()));
            }
        }
        Assert.fail("Элемента \"" + name + " \" не существует");
        return null;
    }

    public abstract WebElement getField(String name) throws Exception;

}
