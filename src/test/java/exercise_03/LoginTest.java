package exercise_03;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    private static final String URL = "http://localhost/litecart/admin/";
    private static final String TITLE = "My Store";
    private static final String MESSAGE = "You are now logged in as admin";
    WebDriver driver;

    @BeforeEach
    public void setup() {
        // initialize driver
        driver = new ChromeDriver();
        // set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        // maximize window
        driver.manage().window().maximize();
    }

    @Test
    public void test() {
        // open page
        driver.get(URL);
        // assert page title
        assertEquals(TITLE, driver.getTitle());
        // fill username input field
        driver.findElement(By.name("username")).sendKeys("admin");
        // fill password input field
        driver.findElement(By.name("password")).sendKeys("admin");
        // click login button
        driver.findElement(By.name("login")).click();
        // assert message
        assertEquals(MESSAGE, driver.findElement(By.xpath("//*[@id=\"notices\"]/div[2]")).getText());
    }

    @AfterEach
    public void teardown() {
        // close driver
        driver.quit();
        driver = null;
    }
}
