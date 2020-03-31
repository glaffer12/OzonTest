package steps;

import cucumber.api.java.ru.Когда;
import pages.MainPage;

public class MainPageSteps {

    MainPage mainPage = new MainPage();

    @Когда("выполнен поиск товара \"(.+)\"")
    public void выполнен_поиск_товара(String productName) {
        mainPage.searchProduct(productName);
    }
}
