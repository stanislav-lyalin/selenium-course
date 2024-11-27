package exercise_09;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckGeoZonesSortingTest {

    private static final String URL = "http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones";
    private static final String TITLE = "My Store";
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
    public void test() {
        login();
        checkGeoZonesSorting();
    }

    private void login() {
        driver.get(URL);
        assertEquals(TITLE, driver.getTitle());
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    private void checkGeoZonesSorting() {
        List<String> links = driver.findElements(By.xpath("//tr[contains(@class,'row')]/td[3]/a"))
                .stream()
                .map(row -> row.getAttribute("href"))
                .filter(Objects::nonNull)
                .filter(href -> !href.isEmpty())
                .toList();

        for (String link : links) {
            driver.get(link);
            List<String> actualList = driver.findElements(By.xpath("//select[contains(@name,'zone_code')]/option[contains(@selected,'selected')]"))
                    .stream()
                    .map(zone -> zone.getAttribute("text"))
                    .filter(Objects::nonNull)
                    .filter(text -> !text.isEmpty())
                    .toList();

            List<String> expectedList = new ArrayList<>(actualList);
            Collections.sort(expectedList);
            assertEquals(expectedList, actualList);
        }
    }
}
