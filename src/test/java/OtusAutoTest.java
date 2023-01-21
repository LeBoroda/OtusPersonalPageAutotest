import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class OtusAutoTest {
    private WebDriver driver;
    private WebDriverWait wait;
    @BeforeAll
    public static void init() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void otusTest() {
        driver.get(System.getProperty("base.url"));
        loginToOtus();
        openAccount();

        clearAndInput(By.cssSelector("input[name='fname']"), "Стажер");
        clearAndInput(By.cssSelector("input[name='fname_latin']"), "Trainee");
        clearAndInput(By.cssSelector("input[name='lname']"), "Тестировщик");
        clearAndInput(By.cssSelector("input[name='lname_latin']"), "Tester");
        clearAndInput(By.cssSelector("input[name='blog_name']"), "СТОЖОР");
        clearAndInput(By.cssSelector("input[name='date_of_birth']"), "13.10.1888");

        dropDownMenuClick(By.cssSelector("input[name='country']+div"), By.cssSelector("button[title=Казахстан]"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(By.cssSelector("input[name='city']"), "disabled", "disabled")));
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(By.cssSelector("input[name='country']"), "disabled", "disabled")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[name='city']+div")));
        dropDownMenuClick(By.cssSelector("input[name='city']+div"), By.cssSelector("button[title=Аксай"));
        dropDownMenuClick(By.cssSelector("input[name=english_level]+div"), By.cssSelector("button[title='Супер продвинутый (Mastery)']"));
        driver.findElement(By.cssSelector("#id_ready_to_relocate_1+span")).click();
        driver.findElement(By.cssSelector("input[value=full]+span")).click();
        driver.findElement(By.cssSelector("input[value=flexible]+span")).click();
        addContacts(By.cssSelector("input[name*='contact-0']~.lk-cv-block__input"), By.cssSelector("button[title='Тelegram']"),
                By.cssSelector("input[name=contact-0-value]"), "@ZUPATESTER");
        driver.findElement(By.cssSelector("input[name=contact-0-preferable]+span")).click();
        driver.findElement(By.cssSelector("button[class*=custom-select-add]")).click();
        addContacts(By.cssSelector("input[name*='contact-1']~.lk-cv-block__input"), By.xpath("//*[@name='contact-1-id']//following::button[@title='WhatsApp']"),
                By.cssSelector("#id_contact-1-value"), "+12345678901");
        dropDownMenuClick(By.cssSelector("div[class='select select_full']"), By.cssSelector("#id_gender>option[value=f]"));
        clearAndInput(By.cssSelector("#id_company"), "Barmaley Inc.");
        clearAndInput(By.cssSelector("#id_work"), "Big boss");
        driver.findElement(By.cssSelector("button[title='Сохранить и заполнить позже']")).click();

        driver.manage().deleteAllCookies();
        driver.get(System.getProperty("base.url"));
        loginToOtus();
        openAccount();

        Assertions.assertEquals("Стажер", driver.findElement(By.cssSelector("input[name='fname']")).getAttribute("value"));
        Assertions.assertEquals("Trainee", driver.findElement(By.cssSelector("input[name='fname_latin']")).getAttribute("value"));
        Assertions.assertEquals("Тестировщик", driver.findElement(By.cssSelector("input[name='lname']")).getAttribute("value"));
        Assertions.assertEquals("Tester", driver.findElement(By.cssSelector("input[name='lname_latin']")).getAttribute("value"));
        Assertions.assertEquals("СТОЖОР", driver.findElement(By.cssSelector("input[name='blog_name']")).getAttribute("value"));
        Assertions.assertEquals("13.10.1888", driver.findElement(By.cssSelector("input[name='date_of_birth']")).getAttribute("value"));
        Assertions.assertEquals("Казахстан", driver.findElement(By.cssSelector("div[data-ajax-slave*=by_country]")).getText());
        Assertions.assertEquals("Аксай", driver.findElement(By.cssSelector("div[class*=slave-city]")).getText());
        Assertions.assertEquals("Супер продвинутый (Mastery)", driver.findElement(By.cssSelector("input[name=english_level]+div")).getText());
        Assertions.assertEquals("Да", driver.findElement(By.cssSelector("#id_ready_to_relocate_1+span")).getText());
        Assertions.assertFalse(false, driver.findElement(By.cssSelector("input[value=full]+span")).getAttribute("checked"));
        Assertions.assertTrue(true, driver.findElement(By.cssSelector("input[value=flexible]+span")).getAttribute("checked"));
        Assertions.assertEquals("Тelegram", driver.findElement(By.cssSelector("input[name*='contact-0']~.lk-cv-block__input")).getText());
        Assertions.assertEquals("@ZUPATESTER", driver.findElement(By.cssSelector("input[name=contact-0-value]")).getAttribute("value"));
        Assertions.assertTrue(true, driver.findElement(By.cssSelector("input[name=contact-0-preferable]+span")).getAttribute("checked"));
        Assertions.assertEquals("WhatsApp", driver.findElement(By.cssSelector("input[name*='contact-1']~.lk-cv-block__input")).getText());
        Assertions.assertEquals("+12345678901", driver.findElement(By.cssSelector("#id_contact-1-value")).getAttribute("value"));
        Assertions.assertFalse(false, driver.findElement(By.cssSelector("input[name=contact-1-preferable]+span")).getAttribute("checked"));
        Assertions.assertEquals("f", driver.findElement(By.cssSelector("#id_gender>option[value=f]")).getAttribute("value"));
        Assertions.assertEquals("Barmaley Inc.", driver.findElement(By.cssSelector("#id_company")).getAttribute("value"));
        Assertions.assertEquals("Big boss", driver.findElement(By.cssSelector("#id_work")).getAttribute("value"));

    }

    public void loginToOtus() {
        driver.findElement(By.cssSelector(".header3__button-sign-in")).click();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[class ^=new-input][name=email][type=text]")));
        clearAndInput(By.cssSelector("input[class ^=new-input][name=email][type=text]"), System.getProperty("login"));
        clearAndInput(By.cssSelector("input[class ^=new-input][name=password][type=password]"), System.getProperty("password"));
        driver.findElement(By.cssSelector("button[class $=new-button_md][type=submit]")).submit();
    }

    public void openAccount() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".header3__user-info-name")));
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.cssSelector(".header3__user-info-name"))).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("a[href*=personal]"))));
        driver.findElement(By.cssSelector("a[href*=personal]")).click();
    }

    public void clearAndInput(By locator, String line) {
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(line);
    }

    public void dropDownMenuClick(By menuLocator, By buttonLocator) {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(menuLocator)).click().perform();
        driver.findElement(buttonLocator).click();
    }

    public void addContacts(By menuLocator, By buttonLocator, By inputFieldLocator, String contact) {
        dropDownMenuClick(menuLocator, buttonLocator);
        clearAndInput(inputFieldLocator, contact);
    }

    @AfterEach
    public void close() {
        if (driver != null)
            driver.quit();
    }
}
