package steps;

import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import pages.CartPage;

import java.io.IOException;

public class CartPageSteps {

    CartPage cartPage = new CartPage();

    @Тогда("в корзине присутствуют все добавленные продукты")
    public void в_корзине_присутствуют_все_добвленные_продукты() throws IOException {
        cartPage.checkIfAllProductsPresent();
        BaseSteps.getBytes("example.txt");
    }

    @Тогда("отображается текст В корзине находится \"(.+)\"")
    public void отображается_текст_В_корзине_находится(String text) {
        cartPage.checkIsInCartPresent(text);
    }

    @Когда("из корзины удалены все добавленные ранее продукты")
    public void из_корзины_удалены_все_добавленные_ранее_продукты() {
        cartPage.deleteAllProducts();
    }

    @Тогда("отображается текст Корзина пуста")
    public void отображается_текст_Корзина_пуста() {
        cartPage.checkIsCartEmpty();
    }


}
