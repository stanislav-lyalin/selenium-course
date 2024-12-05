package exercise_11;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class RegistrationTest {

    private static final String URL = "http://localhost/litecart/en/";
    WebDriver driver;
    String email;
    String password;
    JavascriptExecutor js;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        js = (JavascriptExecutor) driver;
    }

    @AfterEach
    public void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void test() {
        registration();
        logout();
        login();
    }

    private void registration() {

        driver.get(URL);

        driver.findElement(By.xpath("//a[contains(@href,'create_account')]")).click();

        String firstName = RandomStringUtils.random(4, true, false);
        driver.findElement(By.name("firstname")).sendKeys(firstName);

        String lastName = RandomStringUtils.random(8, true, false);
        driver.findElement(By.name("lastname")).sendKeys(lastName);

        String address = RandomStringUtils.random(12, true, true);
        driver.findElement(By.name("address1")).sendKeys(address);

        String postCode = RandomStringUtils.random(5, false, true);
        driver.findElement(By.name("postcode")).sendKeys(postCode);

        String city = RandomStringUtils.random(5, true, false);
        driver.findElement(By.name("city")).sendKeys(city);

        email = RandomStringUtils.random(8, true, true) + "@test.com";
        driver.findElement(By.name("email")).sendKeys(email);

        String phone = RandomStringUtils.random(12, false, true);
        driver.findElement(By.name("phone")).sendKeys(phone);

        password = RandomStringUtils.random(12, true, true);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("confirmed_password")).sendKeys(password);

        // open drop-down list
        driver.findElement(By.xpath("//span[@class='select2-selection__arrow']")).click();
        // select country
        WebElement select = driver.findElement(By.xpath("//select[@name='country_code']"));
        js.executeScript("arguments[0].selectedIndex = 224; arguments[0].dispatchEvent(new Event('change'));", select);
        // close drop-down list
        driver.findElement(By.xpath("//span[@class='select2-selection__arrow']")).click();
        // get size of zone list
        int size = driver.findElements(By.xpath("//select[@name='zone_code']/*")).size();
        // choose random zone
        short randomZoneNumber = (short) (Math.random() * (size - 1) + 1);
        driver.findElement(By.xpath("//select[@name='zone_code']/option[%s]".formatted(randomZoneNumber))).click();
        // click on create account button
        driver.findElement(By.name("create_account")).click();
    }

    private void logout() {
        driver.findElement(By.xpath("//a[contains(@href,'logout')]")).click();
    }

    private void login() {
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
    }
}
