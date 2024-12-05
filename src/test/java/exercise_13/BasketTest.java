package exercise_13;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasketTest {

    private static final String URL = "http://localhost/litecart/en/";
    WebDriver driver;
    WebDriverWait wait;
    int quantity;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterEach
    public void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void test() {
        // cycle to add items
        for (int i = 0; i < 3; i++) {
            try {
                // open main page
                driver.get(URL);
                // get current quantity
                quantity = Integer.parseInt(driver.findElement(By.xpath("//span[@class='quantity']")).getText());
                // assert current quantity
                Assertions.assertEquals(i, quantity);
                // click on product
                driver.findElement(By.xpath("//ul[@class='listing-wrapper products']/li[1]/a")).click();
                // check for mandatory option
                if (!driver.findElements(By.name("options[Size]")).isEmpty()) {
                    driver.findElement(By.name("options[Size]")).click();
                    driver.findElement(By.xpath("//option[@value='Small']")).click();
                }
            } finally {
                // add to basket
                driver.findElement(By.name("add_cart_product")).click();
                // wait for new quantity
                wait.until(ExpectedConditions.textToBe(By.xpath("//span[@class='quantity']"), String.valueOf(i + 1)));
            }
        }

        // go to basket
        driver.findElement(By.xpath("//a[contains(@href,'checkout')][@class='link']")).click();

        // cycle to remove items
        while (getSumQuantity() != 0) {
            try {
                // clock on first item
                driver.findElement(By.xpath("//ul[@class='shortcuts']/li[1]")).click();
                // wait for remove button to be clickable
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='remove_cart_item'][1]")));
                // click on remove button
                driver.findElement(By.xpath("//button[@name='remove_cart_item'][1]")).click();
            } catch (Exception ignore) {
                // check for remove button is present
                if (!driver.findElements(By.xpath("//button[@name='remove_cart_item']")).isEmpty()) {
                    // wait for remove button to be clickable
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='remove_cart_item'][1]")));
                    // click on remove button
                    driver.findElement(By.xpath("//button[@name='remove_cart_item'][1]")).click();
                }
            }
        }
    }

    private int getSumQuantity() {
        // check for element is present
        if (!driver.findElements(By.xpath("//div[@id='order_confirmation-wrapper']/table/tbody/tr[*]/td[@style='text-align: center;']")).isEmpty())
            // get sum quantity
            return driver.findElements(By.xpath("//div[@id='order_confirmation-wrapper']/table/tbody/tr[*]/td[@style='text-align: center;']"))
                    .stream()
                    .map(WebElement::getText)
                    .filter(text -> !text.isEmpty())
                    .mapToInt(Integer::parseInt)
                    .sum();
        // return 0 if element is not present
        return 0;
    }
}
