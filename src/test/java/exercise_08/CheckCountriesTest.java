package exercise_08;

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

public class CheckCountriesTest {

    private static final String URL = "http://localhost/litecart/admin/?app=countries&doc=countries";
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
        checkCountriesSorting();
        checkGeoZonesSorting();
    }

    private void login() {
        driver.get(URL);
        assertEquals(TITLE, driver.getTitle());
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    private void checkCountriesSorting() {
        List<String> actualList = driver.findElements(By.cssSelector("a[href*='country_code']"))
                .stream()
                .map(country -> country.getAttribute("text"))
                .filter(Objects::nonNull)
                .filter(text -> !text.isEmpty())
                .toList();

        List<String> expectedList = new ArrayList<>(actualList);
        Collections.sort(expectedList);
        assertEquals(expectedList, actualList);
    }

    private void checkGeoZonesSorting() {
        List<String> links = driver.findElements(By.xpath("//tr[contains(@class,'row') and not(td=0)]/td[5]/a"))
                .stream()
                .map(row -> row.getAttribute("href"))
                .filter(Objects::nonNull)
                .filter(href -> !href.isEmpty())
                .toList();

        for (String link : links) {
            driver.get(link);
            List<String> actualList = driver.findElements(By.xpath("//td[3]/input"))
                    .stream()
                    .map(zone -> zone.getAttribute("value"))
                    .filter(Objects::nonNull)
                    .filter(value -> !value.isEmpty())
                    .toList();
            List<String> expectedList = new ArrayList<>(actualList);
            Collections.sort(expectedList);
            assertEquals(expectedList, actualList);
        }
    }
}
