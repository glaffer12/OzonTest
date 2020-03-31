package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.qameta.allure.Attachment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.DriverManager.getDriver;
import static utils.DriverManager.properties;

public class BaseSteps {

    public static HashMap<String, String> addedToCart = new HashMap<>();

    public static List<String> getMax(HashMap<String,String> map) {
        int max = 0;
        String name = "";
        List <String>  listMax= new ArrayList<>();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            if (Integer.parseInt(entry.getValue().replaceAll("[^0-9]",""))>max) {
                max = Integer.parseInt(entry.getValue().replaceAll("[^0-9]",""));
                name = entry.getKey();
            }
        }
        for (Map.Entry<String,String> entry : map.entrySet()) {
            if (Integer.parseInt(entry.getValue().replaceAll("[^0-9]",""))==max) {
               listMax.add(entry.getKey());
            }
        }
        return listMax;
    }

    @Before(value = "@first")
    public static void Before() {
        getDriver();
    }

    @Before(value="@notfirst")
    public static void ReloadPage() {
        getDriver().get(properties.getProperty("url"));
    }

    @After(value = "@last")
    public static void After() {
        getDriver().quit();
    }

    @Attachment(value = "В корзину добавлены товары: ")
    public static byte[] getBytes(String resourceName) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/resources", resourceName));
    }

}
