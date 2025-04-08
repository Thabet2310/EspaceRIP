package Bretagne;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class TESTALLURE {

    @Step("Vérifier si un élément existe")
    public static boolean elementExiste(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    @io.qameta.allure.Description("Test de connexion avec allure")
    public void testLogin() {
        // Initialisation du driver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://espace-rip.orange-business.com/");
        Allure.step("Page d'accueil ouverte");

        // Gestion de la page intermédiaire
        if (elementExiste(driver, By.id("details-button"))) {
            driver.findElement(By.id("details-button")).click();
            driver.findElement(By.id("proceed-link")).click();
            Allure.step("Page intermédiaire gérée");
        }

        // Remplissage des identifiants
        String username = "ripripDEMO";
        String password = "TestProd2024#";

        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys(username);
        Allure.step("Nom d'utilisateur saisi");

        driver.findElement(By.id("submit-button")).click();

        WebElement passwordInput = driver.findElement(By.id("currentPassword"));
        passwordInput.sendKeys(password);
        Allure.step("Mot de passe saisi");

        WebElement loginButton = driver.findElement(By.id("submit-button"));
        loginButton.click();
        Allure.step("Bouton de connexion cliqué");

        // Gestion du popup
        WebElement POPUPButton = driver.findElement(By.id("didomi-notice-disagree-button"));
        POPUPButton.click();
        Allure.step("Popup fermé");

        driver.quit();
    }
}
