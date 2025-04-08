package TestAuto;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;


import static java.lang.System.*;

@Test
public class TestEsCollRIP {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        // Spécifier le chemin vers le driver Chrome (chromedriver)
        setProperty("webdriver.chrome.driver", "C:\\Users\\t.thabet\\Desktop\\chromedriverwin64\\chromedriver.exe");        // Initialiser le navigateur Chrome
        WebDriver driver = new ChromeDriver();

        // Ouvrir la page de connexion (remplacez l'URL par votre propre URL)
        String loginPageUrl = "https://qualif-espace-rip.orange-business.com/";
        driver.get(loginPageUrl);
        Thread.sleep(2000);

        // parmetres avancés en cas de ne pas accéder à la page login
        driver.findElement(By.id("details-button")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("proceed-link")).click();
        Thread.sleep(2000);


        // Enter credentials
        String username = "ripripdemo";
        String password = "Demo2022#";
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


            String MenuTitle = "Mes essentiels";
            String pathMenuTitle = "//button[contains(.,'" + MenuTitle + "')]";
            driver.findElement(By.xpath(pathMenuTitle)).click();
            Thread.sleep(7000);

            String Rubrique = "Mon contrat Public";
            driver.findElement(By.linkText(Rubrique)).click();
            Thread.sleep(7000);

            String sousRubrique = "Mon acte de transfert";
            driver.findElement(By.linkText(sousRubrique)).click();
            Thread.sleep(7000);


            // Attendre quelques secondes pour voir les résultats (à titre indicatif)
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Fermer le navigateur
            driver.quit();
        }
    }
