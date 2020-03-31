package pages;

import annotation.FieldName;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {

    @FindBy(xpath = "//input[@name=\"search\"]")
    @FieldName(name = "поле поиска")
    WebElement searchField;

    @Override
    public WebElement getField(String name) throws Exception {
        return getField(name, "pages.MainPage");
    }

    public void searchProduct(String productName) {
        searchField.click();
        searchField.sendKeys(productName);
        searchField.sendKeys(Keys.ENTER);
    }

}


