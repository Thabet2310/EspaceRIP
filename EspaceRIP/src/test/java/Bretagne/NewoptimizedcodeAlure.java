package Bretagne;

import io.qameta.allure.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;

@Epic("Test de navigation") // Catégorie principale
@Feature("Génération de rapports Allure")
public class NewoptimizedcodeAlure {

    public static boolean elementExiste(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Test(description = "Test principal avec génération du rapport Allure")
    @Story("Connexion et navigation dans le système")
    @Severity(SeverityLevel.CRITICAL)
    public void testPrincipal() throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        Allure.step("Ouvrir le site Web et maximiser la fenêtre");
        driver.get("https://espace-rip.orange-business.com/");
        Thread.sleep(2000);
        driver.manage().window().maximize();

        Allure.step("Gérer la page intermédiaire (si présente)");
        if (elementExiste(driver, By.id("details-button"))) {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("details-button"))).click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("proceed-link"))).click();
            Thread.sleep(2000);
        }

        Allure.step("Se connecter avec des identifiants valides");
        String username = "ripripDEMO";
        String password = "TestProd2024#";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-button"))).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("currentPassword"))).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-button"))).click();
        Thread.sleep(4000);

        Allure.step("Refuser les cookies via le popup");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("didomi-notice-disagree-button"))).click();
        Thread.sleep(2000);

        Allure.step("Créer un fichier Excel pour stocker les résultats");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Bretagne_COL");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Catégorie");
        headerRow.createCell(1).setCellValue("Rubrique");
        headerRow.createCell(2).setCellValue("Sous-rubrique");
        headerRow.createCell(3).setCellValue("Statut");

        // Appeler les tests
        int[] rowNum = {1};
        testerRubriques(driver, sheet, rowNum, "Mes essentiels", "Mon contrat Public", new String[]{"Mon contrat public signé"});

        try (FileOutputStream fileOut = new FileOutputStream("Recette_Bretagne_COLAllure.xlsx")) {
            workbook.write(fileOut);
        }

        driver.quit();
    }

    @Step("Tester les rubriques pour {rubrique}")
    public void testerRubriques(WebDriver driver, Sheet sheet, int[] rowNum, String categorie, String rubrique, String[] sousRubriques) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Attendre que la catégorie soit visible et cliquable
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText(categorie))).click();
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("Catégorie non trouvée ou non cliquable : " + categorie);
        }




        if (!categorie.isEmpty()) {
            addDataToExcel(sheet, rowNum[0]++, categorie, "", "", "");
        }
        addDataToExcel(sheet, rowNum[0]++, "", rubrique, "", "");

        // Attendre que la rubrique soit visible et cliquable
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText(rubrique))).click();
            Thread.sleep(3000);

            for (String sousRubrique : sousRubriques) {
                boolean existe = elementExiste(driver, By.linkText(sousRubrique));
                addDataToExcel(sheet, rowNum[0]++, "", "", sousRubrique, existe ? "OK" : "KO");

                if (existe) {
                    wait.until(ExpectedConditions.elementToBeClickable(By.linkText(sousRubrique))).click();
                    Thread.sleep(5000);
                    driver.navigate().back();
                    Thread.sleep(2000);
                }
            }
        } catch (Exception e) {
            System.out.println("Rubrique non trouvée ou non cliquable : " + rubrique);
        }
    }

    private static void addDataToExcel(Sheet sheet, int rowNum, String categorie, String rubrique, String sousRubrique, String statut) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(categorie);
        row.createCell(1).setCellValue(rubrique);
        row.createCell(2).setCellValue(sousRubrique);
        row.createCell(3).setCellValue(statut);
    }
}
