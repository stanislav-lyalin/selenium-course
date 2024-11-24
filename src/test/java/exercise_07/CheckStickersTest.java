package exercise_07;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        List<WebElement> ducks = driver.findElements(By.cssSelector("a[href*='%s']".formatted("-duck-p-")));
        for (WebElement duck : ducks) {
            List<WebElement> stickers = new ArrayList<>(duck.findElements(By.cssSelector("[class^='sticker']")));
            assertEquals(1, stickers.size());
        }
    }
}
