package exercise_06;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckSectionsTest {

    private static final String URL = "http://localhost/litecart/admin/";
    private static final String TITLE = "My Store";
    private static final String MESSAGE = "You are now logged in as admin";
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
        login();
        checkSections();
    }

    private void login() {
        driver.get(URL);
        assertEquals(TITLE, driver.getTitle());
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        assertEquals(MESSAGE, driver.findElement(By.xpath("//*[@id=\"notices\"]/div[2]")).getText());
    }

    private void checkSections() {
        // find all menu links
        List<String> menuLinks = driver.findElements(By.xpath("//li[(contains(@id,'app-'))]/a"))
                .stream()
                .map(link -> link.getAttribute("href"))
                .filter(Objects::nonNull)
                .filter(href -> !href.isEmpty())
                .toList();
        // cycle for each menu link
        for (String menulink : menuLinks) {
            // clink on menu link
            driver.get(menulink);
            // assert title
            Assertions.assertFalse(driver.findElement(By.xpath("//td[@id='content']/h1")).getText().isEmpty());
            // find all section links
            List<String> sectionLinks = new ArrayList<>();
            driver.findElements(By.xpath("//li[(contains(@id,'doc-'))]/a"))
                    .stream()
                    .map(link -> link.getAttribute("href"))
                    .filter(Objects::nonNull)
                    .filter(href -> !href.isEmpty())
                    .forEach(sectionLinks::add);
            // cycle for each section link
            for (String allLink : sectionLinks) {
                // click on section link
                driver.get(allLink);
                // assert title
                Assertions.assertFalse(driver.findElement(By.xpath("//td[@id='content']/h1")).getText().isEmpty());
            }
        }
    }
}
