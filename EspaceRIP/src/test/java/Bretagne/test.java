package Bretagne;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class test {

    @Test
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://espace-rip.orange-business.com/");
        driver.manage().window().maximize();

        // Connexion
        Thread.sleep(2000);
        driver.findElement(By.id("username")).sendKeys("ridffipDEMO");
        driver.findElement(By.id("submit-button")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("currentPassword")).sendKeys("TestProd2024#");
        driver.findElement(By.id("submit-button")).click();
        Thread.sleep(4000);

        // ✅ Vérification du message d'erreur de connexion
        By errorMessageLocator = By.id("errorMessage480");

        if (isElementPresent(driver, errorMessageLocator)) {
            System.out.println("⚠️ Erreur : Identifiant ou mot de passe incorrect !");
            driver.quit();  // Fermer le navigateur si l'authentification échoue
            return;         // Sortir du test
        }

        // *** Gestion de l'iframe et clic sur le bouton ***
        try {
            // Passer dans l'iframe "myFrame"
            driver.switchTo().frame("myFrame");
            Thread.sleep(2000);

            // Cliquer sur le bouton dans l'iframe
            driver.findElement(By.id("boutonGaucheSoonExpired")).click();
            Thread.sleep(2000);

            // Revenir au contexte principal
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            System.out.println("Erreur : Impossible de cliquer sur le bouton dans l'iframe.");
            e.printStackTrace();
        }

        // Gérer la popup "Didomi"
        try {
            Thread.sleep(2000);
            WebElement didomiButton = driver.findElement(By.id("didomi-notice-disagree-button"));
            didomiButton.click();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Aucune popup Didomi détectée.");
        }

        // Déconnexion
        driver.findElement(By.linkText("Déconnexion")).click();
        Thread.sleep(2000);

        // Fermer le navigateur
        driver.quit();
    }
    public static boolean isElementPresent(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
