package pages;

import annotation.FieldName;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import steps.BaseSteps;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import static utils.DriverManager.getDriver;

public class CartPage extends BasePage {


    @FindBy(xpath = "//button[.//div[contains(text(),\"Удалить\")]]")
    @FieldName(name = "подтвердить удаление")
    WebElement confirmRemoving;

    @FindBy(xpath="//span[contains(text(),\"Удалить выбранные\")]")
    @FieldName(name = "удалить выбранные")
    WebElement removeSelected;

    @FindBy(xpath = "//h1[contains(text(),\"Корзина пуста\")]")
    @FieldName(name = "корзина пуста")
    WebElement emptyCart;

    @Override
    public WebElement getField(String name) throws Exception {
        return getField(name, "pages.CartPage");
    }

    public void checkIfAllProductsPresent() {
        for (Map.Entry<String, String> pair : BaseSteps.addedToCart.entrySet())
            try {
                getDriver().findElement(By.xpath("//span[text()=\"" + pair.getKey() + "\"]"));
                System.out.println(pair.getKey() + " присутсвует в корзине.");
            } catch (NoSuchElementException e) {
                System.out.println(pair.getKey() + " не присутсвует в корзине.");
            }
        try {
            writeMapDateToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeMapDateToFile() throws IOException {
        PrintWriter out = new PrintWriter(new FileOutputStream("src/main/resources/example.txt"));
        String stroka;
        int count=1;
        for (Map.Entry<String,String> entry : BaseSteps.addedToCart.entrySet()) {
            stroka = count +") " +entry.getKey() + ", стоимость - " + entry.getValue();
            count++;
            out.printf(stroka + "\n");
        }

        out.printf("\nТовар(ы) с наибольшей стоимостью:\n");
        for(String key : BaseSteps.getMax(BaseSteps.addedToCart)) {
            out.printf(key + ", стоимость - " + BaseSteps.addedToCart.get(key) + "\n");
        }
        out.close();
    }

    public void checkIsInCartPresent(String text) {
        try {
            getDriver().findElement(By.xpath("//div[./span[contains(text(),\"Ваша корзина\")] and ./span[contains(text(),\"" + text + "\")]]"));
            System.out.println("Количество товаров в корзине отображается корректно");
        } catch (NoSuchElementException e) {
            System.out.println("Количество товаров в корзине отображается некорректно");
        }
    }

    public void deleteAllProducts() {
        removeSelected.click();
        confirmRemoving.click();
        BaseSteps.addedToCart.clear();
    }

    public void checkIsCartEmpty() {
        isElementPresent(emptyCart);
        System.out.println("Корзина пуста");
    }

}
