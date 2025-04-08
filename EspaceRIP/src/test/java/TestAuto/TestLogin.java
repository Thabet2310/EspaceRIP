import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileNotFoundException;
/*
public class TestLogin {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        // Initialisation du webdriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\t.thabet\\Desktop\\chromedriverwin64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Accéder à la page de connexion
        driver.get("https://qualif-espace-rip.orange-business.com/");
        LoginPage loginPage = new LoginPage(driver);

        // parmetres avancés
        driver.findElement(By.id("details-button")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("proceed-link")).click();
        Thread.sleep(2000);

        // Remplir les champs de connexion
        loginPage.enterUsername("ripripdemo");
        Thread.sleep(2000);

        // Cliquer sur le bouton Suivant
        loginPage.clickLoginButton();
        Thread.sleep(2000);

        loginPage.enterPassword("Demo2022#");
        Thread.sleep(2000);

        // Cliquer sur le bouton de connexion
        loginPage.clickLoginButton();

        // Autres actions...

        // Fermeture du navigateur
        driver.quit();
    }
}*/