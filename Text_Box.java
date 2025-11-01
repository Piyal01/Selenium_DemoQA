
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.github.javafaker.Faker;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Text_Box {
    static  WebDriver driver; //
    @BeforeAll //this method will call first

    public static void demoqa(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headed");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }
    @DisplayName("Get website title")
    @Test
    public void gettitle(){
        driver.get("https://demoqa.com/");
        String Actualtitle=driver.getTitle();
        System.out.println(Actualtitle);
        String  Expectedtitle="DEMOQA";
        Assertions.assertTrue(Actualtitle.equals(Expectedtitle));
        Assertions.assertEquals(Expectedtitle,Actualtitle);
    }
    @Test
    //interact with web element(submit form)
    public void submitform(){
        //using faker library we can generate random cridentials
        driver.get("https://demoqa.com/text-box");
        Faker faker = new Faker();
        String name= faker.name().fullName();
        String email= faker.internet().emailAddress();
        String currentAddress= faker.address().streetAddress();
        String permanentAddress= faker.address().fullAddress();

        driver.findElement(By.id("userName")).sendKeys(name);
        driver.findElement(By.id("userEmail")).sendKeys(email);
        driver.findElement(By.id("currentAddress")).sendKeys(currentAddress);
        driver.findElement(By.id("permanentAddress")).sendKeys(permanentAddress);
        scroll();
        driver.findElement(By.id("submit")).click();

        List<WebElement> resultElem= driver.findElements(By.tagName("p"));
        String actualName= resultElem.get(0).getText();
        String actualEmail= resultElem.get(1).getText();
        String actualCurrentAddress= resultElem.get(2).getText();
        String actualPermanentAddress= resultElem.get(3).getText();

        String expectedName= "Name:"+name;
        String expectedEmail= "Email:"+email;
        String expectedCurrentAddress= "Current Address :"+currentAddress;
        String expectedPermanentAddress= "Permananet Address :"+permanentAddress;

        Assertions.assertEquals(expectedName, actualName);
        Assertions.assertEquals(expectedEmail, actualEmail);
        Assertions.assertEquals(expectedCurrentAddress, actualCurrentAddress);
        Assertions.assertEquals(expectedPermanentAddress, actualPermanentAddress);

    }
    public void scroll(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500)"); // scrolls down 500px

    }
    @Test

    public void clickButton(){
        driver.get("https://demoqa.com/buttons");
        List<WebElement> buttonElements = driver.findElements(By.cssSelector("[type=button]"));
        Actions actions = new Actions(driver);
        actions.doubleClick(buttonElements.get(1)).perform();
        actions.contextClick(buttonElements.get(2)).perform();
        actions.click(buttonElements.get(3)).perform();
    }
    @Test
    public void handlealertbutton() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        //  Simple alert
        driver.findElement(By.id("alertButton")).click();
        driver.switchTo().alert().accept();

        // Timer alert (use explicit wait)
        driver.findElement(By.id("timerAlertButton")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500);");

        // Confirm alert - accept
        driver.findElement(By.id("confirmButton")).click();
        driver.switchTo().alert().accept();

        // Confirm alert - dismiss
        wait.until(ExpectedConditions.elementToBeClickable(By.id("confirmButton")));
        driver.findElement(By.id("confirmButton")).click();
        driver.switchTo().alert().dismiss();
    }

//   @AfterAll
//   public void closebrowser(){
//       driver.close(); //driver remains in the backend close the tab but the driver remain alive
//      driver.quit();
//   }
}
