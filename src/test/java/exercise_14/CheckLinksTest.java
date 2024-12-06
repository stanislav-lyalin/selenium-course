package exercise_14;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckLinksTest {

    private static final String URL = "http://localhost/litecart/admin/?app=countries&doc=countries";
    private static final String TITLE = "My Store";
    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void setup() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
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
        // login
        driver.get(URL);
        assertEquals(TITLE, driver.getTitle());
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        // open country page
        driver.findElement(By.xpath("//a[@class='button' and contains(@href,'edit_country')]")).click();
        // get all links
        List<WebElement> links = driver.findElements(By.xpath("//a[contains(@href,'http')][@target='_blank']/i[@class='fa fa-external-link']"));
        // cycle for each link
        for (WebElement link : links) {
            // click on link
            link.click();
            // wait for new window
            wait.until(driver -> driver.getWindowHandles().size() == 2);
            // get all window hundles
            Object[] allWindows = driver.getWindowHandles().toArray();
            // switch to new window
            driver.switchTo().window((String) allWindows[1]);
            // close new window
            driver.close();
            // switch to main window
            driver.switchTo().window((String) allWindows[0]);
        }
    }
}
