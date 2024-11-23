package exercise_06;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckAllSectionsTest {

    private static final String URL = "http://localhost/litecart/admin/";
    private static final String TITLE = "My Store";
    private static final String MESSAGE = "You are now logged in as admin";
    WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();
    }

    @Test
    public void test() {
        login();
        checkAppearence();
        checkCatalog();
        checkCountries();
        checkCurrencies();
        checkCustomers();
        checkGeoZones();
        checkLanguages();
        checkModules();
        checkOrders();
        checkPages();
        checkReports();
        checkSettings();
        checkSlides();
        checkTax();
        checkTranslations();
        checkUsers();
        checkVQMods();
    }

    private void login() {
        driver.get(URL);
        assertEquals(TITLE, driver.getTitle());
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        assertEquals(MESSAGE, driver.findElement(By.xpath("//*[@id=\"notices\"]/div[2]")).getText());
    }

    private void checkAppearence() {
        checkSection("Template");
        checkSection("Template");
        checkSection("Logotype");
    }

    private void checkCatalog() {
        checkSection("Catalog");
        checkSection("Catalog");
        checkSection("Product Groups");
        checkSection("Option Groups");
        checkSection("Manufacturers");
        checkSection("Suppliers");
        checkSection("Suppliers");
        checkSection("Delivery Statuses");
        checkSection("Sold Out Statuses");
        checkSection("Quantity Units");
        checkSection("CSV Import/Export");
    }

    private void checkCountries() {
        checkSection("Countries");
    }

    private void checkCurrencies() {
        checkSection("Currencies");
    }

    private void checkCustomers() {
        checkSection("Customers");
        checkSection("CSV Import/Export");
        checkSection("Newsletter");
    }

    private void checkGeoZones() {
        checkSection("Geo Zones");
    }

    private void checkLanguages() {
        checkSection("Languages");
        checkSection("Languages");
        checkSection("Storage Encoding");
    }

    private void checkModules() {
        checkSection("Jobs");
        checkSection("Jobs");
        checkSection("Customer");
        checkSection("Shipping");
        checkSection("Payment");
        checkSection("Order Total");
        checkSection("Order Success");
        checkSection("Order Action");
    }

    private void checkOrders() {
        checkSection("Orders");
        checkSection("Orders");
        checkSection("Order Statuses");
    }

    private void checkPages() {
        checkSection("Pages");
    }

    private void checkReports() {
        checkSection("Monthly Sales");
        checkSection("Most Sold Products");
        checkSection("Most Shopping Customers");
    }

    private void checkSettings() {
        checkSection("Store Info");
        checkSection("Store Info");
        checkSection("Defaults");
        checkSection("General");
        checkSection("Listings");
        checkSection("Images");
        checkSection("Checkout");
        checkSection("Advanced");
        checkSection("Security");
    }

    private void checkSlides() {
        checkSection("Slides");
    }

    private void checkTax() {
        checkSection("Tax Classes");
        checkSection("Tax Classes");
        checkSection("Tax Rates");
    }

    private void checkTranslations() {
        checkSection("Search Translations");
        checkSection("Search Translations");
        checkSection("Scan Files");
        checkSection("CSV Import/Export");
    }

    private void checkUsers() {
        checkSection("Users");
    }

    private void checkVQMods() {
        checkSection("vQmods");
        checkSection("vQmods");
    }

    @AfterEach
    public void teardown() {
        driver.quit();
        driver = null;
    }

    private void checkSection(String name) {
        String href = name.replaceAll(" ", "_").toLowerCase();

        href = href.equals("csv_import/export") ? "csv" : href;

        href = href.equals("search_translations") ? "search" : href;

        href = href.equals("scan_files") ? "scan" : href;

        var jobs = Set.of("Customer", "Shipping", "Payment", "Order Total", "Order Success", "Order Action");
        name = jobs.contains(name) ? name + " Modules" : name;

        var settings = Set.of("Store Info", "Defaults", "General", "Listings", "Images", "Checkout", "Advanced", "Security");
        name = settings.contains(name) ? "Settings" : name;

        name = name.equals("Jobs") ? "Job Modules" : name;

        name = name.equals("Store Info") ? "Settings" : name;

        name = name.equals("Scan Files") ? "Scan Files For Translations" : name;

        var element = driver.findElement(By.cssSelector("a[href$='%s']".formatted(href)));
        element.click();
        assertEquals(name, driver.findElement(By.xpath("//*[@id=\"content\"]/h1")).getText());
    }
}
