package exercise_07;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class CheckStickersTest {

    private static final String URL = "http://localhost/litecart/en/";
    WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void checkStickers() {
        driver.get(URL);
        driver.findElements(By.xpath("//li[contains(@class,'product')]"))
                .forEach(duck -> Assertions.assertEquals(1, duck.findElements(By.cssSelector(".sticker")).size()));
    }
}
