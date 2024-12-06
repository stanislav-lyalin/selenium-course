package exercise_17;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckLogTest {

    private static final String URL = "http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1";
    private static final String TITLE = "My Store";
    WebDriver driver;

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
        // login
        driver.get(URL);
        assertEquals(TITLE, driver.getTitle());
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        // get all links
        List<String> links = driver.findElements(By.xpath("//tr[@class='row']/td[3]/a[contains(@href,'product')]"))
                .stream()
                .map(row -> row.getAttribute("href"))
                .filter(Objects::nonNull)
                .filter(href -> !href.isEmpty())
                .toList();
        // cycle for each link
        for (String link : links) {
            driver.get(link);
            driver.manage().logs().get("browser").forEach(l -> {
                System.out.println(l.getMessage());
                Assertions.assertTrue(l.getMessage().isEmpty());
            });
        }
    }
}
