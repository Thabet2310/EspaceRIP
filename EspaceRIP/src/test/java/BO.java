import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static java.lang.System.setProperty;

public class BO {
    @Test

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        // Spécifier le chemin vers le driver Chrome (chromedriver)
        setProperty("webdriver.chrome.driver", "C:\\Users\\t.thabet\\chromedriver\\chromedriver\\chromedriver.exe");        // Initialiser le navigateur Chrome
        WebDriver driver = new ChromeDriver();

        // Ouvrir la page de connexion (remplacez l'URL par votre propre URL)
        String loginPageUrl = "https://msi.sso.francetelecom.fr/monsi/index.html#/applications";
        driver.get(loginPageUrl);
        Thread.sleep(2000);

        // Saisir les identifiants
        String username = "rip.rrth";
        String password = "Rrth2019#";
        Thread.sleep(10000);

        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys(username);
        driver.findElement(By.id("submit-button")).click();
        Thread.sleep(2000);
        WebElement passwordInput = driver.findElement(By.id("currentPassword"));
        passwordInput.sendKeys(password);


        // Cliquer sur le bouton de connexion (remplacez le sélecteur par celui de votre bouton de connexion)
        WebElement loginButton = driver.findElement(By.id("submit-button"));
        loginButton.click();
        Thread.sleep(7000);


    }
}
