package Bretagne;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;

public class TestBOEspaceCO {
    public static void main(String[] args) throws InterruptedException, IOException {
        //setProperty("webdriver.chrome.driver", "C:\\Users\\t.thabet\\chromedriver\\chromedriver\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://msi.sso.francetelecom.fr/monsi/index.html#/applications");
        Thread.sleep(2000);

        driver.manage().window().maximize();

        String username = "BYGG2709";
        String password = "Ali@1952T!123";
        Thread.sleep(2000);

        WebElement usernameInput = driver.findElement(By.id("user"));
        usernameInput.sendKeys(username);
        Thread.sleep(1000);
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys(password);

        WebElement loginButton = driver.findElement(By.id("linkValidForm"));
        loginButton.click();
        Thread.sleep(4000);

        driver.get("https://portail-collectivite-preprod.sso.francetelecom.fr/?check_logged_in=1");

        WebElement ContentButton = driver.findElement(By.linkText("Contenu"));
        ContentButton.click();

        driver.findElement(By.linkText("Ajouter du contenu")).click();
        Thread.sleep(2000);

        driver.findElement(By.linkText("Contenu externe")).click();
        Thread.sleep(2000);

        driver.findElement(By.id("edit-title-0-value")).sendKeys("test");
        Thread.sleep(2000);

        // Localiser l'élément <select> de Type
        WebElement dropdown = driver.findElement(By.id("edit-field-type"));

        // Créer une instance de Select
        Select select = new Select(dropdown);

        // Sélectionner l'option par texte visible
        select.selectByVisibleText("Lien");

        //driver.findElement(By.id("edit-field-type")).click();
        Thread.sleep(2000);

        //Choisir de RIP

        // Localiser l'élément <select> de RIP
        WebElement dropdown2 = driver.findElement(By.id("edit-field-rip"));

        // Créer une instance de Select
        Select select2 = new Select(dropdown2);

        // Sélectionner l'option par texte visible
        select2.selectByVisibleText("RIP DEMO");

// Description
        driver.findElement(By.id("edit-field-description-0-value")).sendKeys("testtestteststest");
        Thread.sleep(2000);

        driver.findElement(By.id("edit-field-url-0-uri")).sendKeys("http://example.com");
        Thread.sleep(2000);

        //driver.findElement(By.linkText("edit-field-nouvel-onglet-value")).click();

        driver.findElement(By.id("edit-submit")).click();
        Thread.sleep(5000);
    }}
