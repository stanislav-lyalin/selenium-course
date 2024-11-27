package exercise_10;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckPageTest {

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
    public void test() {

        driver.get(URL);

        // title
        String mainMenuTitle = driver.findElement(By.xpath("//div[@id='box-campaigns']/div/ul/li/a")).getAttribute("title");

        // price value
        String mainMenuRegularPriceValue = driver.findElement(By.xpath("//div[@id='box-campaigns']/div/ul/li/a/div[4]/s")).getText();
        String mainMenuCampaignPriceValue = driver.findElement(By.xpath("//div[@id='box-campaigns']/div/ul/li/a/div[4]/strong")).getText();

        // color
        String mainMenuRegularPriceColor = driver.findElement(By.xpath("//div[@id='box-campaigns']/div/ul/li/a/div[4]/s")).getCssValue("color");
        String mainMenuCampaignPriceColor = driver.findElement(By.xpath("//div[@id='box-campaigns']/div/ul/li/a/div[4]/strong")).getCssValue("color");

        // font
        assertEquals("line-through", driver.findElement(By.xpath("//div[@id='box-campaigns']/div/ul/li/a/div[4]/s")).getCssValue("text-decoration-line"));
        assertEquals("solid", driver.findElement(By.xpath("//div[@id='box-campaigns']/div/ul/li/a/div[4]/strong")).getCssValue("text-decoration-style"));
        String mainMenuRegularPriceFontWeight = driver.findElement(By.xpath("//div[@id='box-campaigns']/div/ul/li/a/div[4]/s")).getCssValue("font-weight");
        String mainMenuCampaignPriceColorFontWeight = driver.findElement(By.xpath("//div[@id='box-campaigns']/div/ul/li/a/div[4]/strong")).getCssValue("font-weight");

        // go to product page
        driver.findElement(By.xpath("//div[@id='box-campaigns']/div/ul/li/a")).click();

        // title
        String productPageTitle = driver.findElement(By.xpath("//a[@data-fancybox-group='product']/img")).getAttribute("title");

        // price value
        String productPageRegularPrice = driver.findElement(By.xpath("//div[@class='price-wrapper']/s")).getText();
        String productPageCampaignPrice = driver.findElement(By.xpath("//div[@class='price-wrapper']/strong")).getText();

        // color
        String productPageRegularPriceColor = driver.findElement(By.xpath("//div[@class='price-wrapper']/s")).getCssValue("color");
        String productPageCampaignPricezColor = driver.findElement(By.xpath("//div[@class='price-wrapper']/strong")).getCssValue("color");

        // font
        assertEquals("line-through", driver.findElement(By.xpath("//div[@class='price-wrapper']/s")).getCssValue("text-decoration-line"));
        assertEquals("solid", driver.findElement(By.xpath("//div[@class='price-wrapper']/strong")).getCssValue("text-decoration-style"));
        String productPageRegularPriceFontWeight = driver.findElement(By.xpath("//div[@class='price-wrapper']/s")).getCssValue("font-weight");
        String productPageCampaignPriceColorFontWeight = driver.findElement(By.xpath("//div[@class='price-wrapper']/strong")).getCssValue("font-weight");

        // title assertion
        assertEquals(mainMenuTitle, productPageTitle);

        // price value assertion
        assertEquals(mainMenuRegularPriceValue, productPageRegularPrice);
        assertEquals(mainMenuCampaignPriceValue, productPageCampaignPrice);

        // color assertion
        assertPriceColor("regular", mainMenuRegularPriceColor);
        assertPriceColor("campaign", mainMenuCampaignPriceColor);
        assertPriceColor("regular", productPageRegularPriceColor);
        assertPriceColor("campaign", productPageCampaignPricezColor);

        // font assertion
        Assertions.assertTrue(Integer.parseInt(mainMenuRegularPriceFontWeight) < Integer.parseInt(mainMenuCampaignPriceColorFontWeight));
        Assertions.assertTrue(Integer.parseInt(productPageRegularPriceFontWeight) < Integer.parseInt(productPageCampaignPriceColorFontWeight));
    }

    private void assertPriceColor(String priceType, String color) {
        List<String> rgba = new ArrayList<>(List.of(color.substring(5).replaceAll(" ", "").split(",")));
        switch (priceType) {
            case "regular" -> Assertions.assertTrue(rgba.get(0).equals(rgba.get(1)) && rgba.get(0).equals(rgba.get(2)));
            case "campaign" -> Assertions.assertTrue(rgba.get(1).equals("0") && rgba.get(2).equals("0"));
        }
    }
}
