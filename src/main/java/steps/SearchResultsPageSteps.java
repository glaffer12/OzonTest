package steps;

import cucumber.api.java.ru.Когда;
import pages.SearchResultsPage;

public class SearchResultsPageSteps {

    SearchResultsPage searchResultsPage = new SearchResultsPage();

    @Когда("установлено значение поля \"(.+)\" *(от|до) \"(.+)\"")
    public void установлено_значение_поля(String filterName, String filterParamName, String value) {
        searchResultsPage.setFromToFilterValue(filterName, filterParamName, value);
    }

    @Когда("выполнено нажатие на чекбокс \"(.+)\"")
    public void выполнено_нажатие_на_чекбокс(String checkboxName) {
        searchResultsPage.setCheckbox(checkboxName);
    }

    @Когда("для параметра \"(.+)\" выбрано значение \"(.+)\"")
    public void установлено_значение_поля(String filterName, String checkmarkName) {
        searchResultsPage.setCheckmark(filterName,checkmarkName);
    }

    @Когда("в корзину добавлены первые \"(.+)\" *(чётных|нечётных) элементов")
    public void в_корзину_добавлены(String numberOfProducts, String conditionName) {
        searchResultsPage.addToCartByCondition(numberOfProducts, conditionName);
    }

    @Когда("в корзину добавлены все *(чётные|нечётные) элементы")
    public void в_корзину_добавлены(String conditionName) {
        searchResultsPage.addToCartByCondition(conditionName);
    }

    @Когда("выполнен переход в корзину")
    public void выполнен_переход_в_корзину() throws Exception {
        searchResultsPage.goToCart();
    }


}
