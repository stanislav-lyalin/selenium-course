package exercise_12;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddNewProductTest {

    private static final String URL = "http://localhost/litecart/admin/";
    private static final String TITLE = "My Store";
    private static final String MESSAGE = "You are now logged in as admin";
    WebDriver driver;
    String name;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void test() {
        login();
        addNewProduct();
        fillGeneralTab();
        fillInformationTab();
        fillPricesTab();
        checkNewProduct();
    }

    private void login() {
        driver.get(URL);
        assertEquals(TITLE, driver.getTitle());
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        assertEquals(MESSAGE, driver.findElement(By.xpath("//*[@id=\"notices\"]/div[2]")).getText());
    }

    private void addNewProduct() {
        // go to catalog
        driver.findElement(By.xpath("//li[@id='app-']/a[contains(@href,'catalog')]")).click();
        // add new product
        driver.findElement(By.xpath("//td[@id='content']/div[*]/a[@class='button'][contains(@href,'edit_product')]")).click();
    }

    private void fillGeneralTab() {
        // status
        driver.findElement(By.xpath("//label/input[@name='status'][@value='1']")).click();
        // name
        name = RandomStringUtils.random(5, true, false);
        driver.findElement(By.name("name[en]")).sendKeys(name);
        // code
        String code = RandomStringUtils.random(5, false, true);
        driver.findElement(By.name("code")).sendKeys(code);
        // quantity
        driver.findElement(By.name("quantity")).sendKeys("1");
        // upload image
        File file = new File("src/test/java/exercise_12/my_duck.png");
        driver.findElement(By.name("new_images[]")).sendKeys(file.getAbsolutePath());
        // dates
        driver.findElement(By.name("date_valid_from")).sendKeys("01.01.2022");
        driver.findElement(By.name("date_valid_to")).sendKeys("31.12.2025");
    }

    private void fillInformationTab() {
        // open information tab
        driver.findElement(By.xpath("//a[contains(@href,'#tab-information')]")).click();
        // manufacturer
        driver.findElement(By.xpath("//select[@name='manufacturer_id']/option[@value='1']")).click();
        // keywords
        driver.findElement(By.name("keywords")).sendKeys("test");
        // short description
        driver.findElement(By.name("short_description[en]")).sendKeys("test");
        // description
        driver.findElement(By.name("description[en]")).sendKeys("test");
        // head title
        driver.findElement(By.name("head_title[en]")).sendKeys("test");
        // meta description
        driver.findElement(By.name("meta_description[en]")).sendKeys("test");
    }

    private void fillPricesTab() {
        // open prices tab
        driver.findElement(By.xpath("//a[contains(@href,'#tab-prices')]")).click();
        // purchase price
        driver.findElement(By.name("purchase_price")).sendKeys("1");
        // currency
        driver.findElement(By.xpath("//select[@name='purchase_price_currency_code']/option[@value='EUR']")).click();
        // price
        driver.findElement(By.name("prices[EUR]")).sendKeys("10");
        // save
        driver.findElement(By.name("save")).click();
    }

    private void checkNewProduct() {
        List<String> ducks = driver.findElements(By.xpath("//tr[@class='row']/td[3]/a"))
                .stream()
                .map(WebElement::getText)
                .filter(text -> !text.isEmpty())
                .toList();
        Assertions.assertTrue(ducks.contains(name));
    }
}
