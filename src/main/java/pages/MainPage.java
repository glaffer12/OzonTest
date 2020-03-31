package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {

    @FindBy(xpath = "//input[@name=\"search\"]")
    WebElement searchField;

    public void searchProduct(String productName) {
        searchField.click();
        searchField.sendKeys(productName);
        searchField.sendKeys(Keys.ENTER);
    }

}


