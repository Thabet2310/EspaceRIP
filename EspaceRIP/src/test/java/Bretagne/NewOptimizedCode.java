package Bretagne;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;

public class NewOptimizedCode {

    public static boolean elementExiste(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static void testerRubriques(WebDriver driver, Sheet sheet, int[] rowNum, String categorie, String rubrique, String[] sousRubriques) throws InterruptedException {
        // Ajouter la catégorie et la rubrique dans Excel
        if (!categorie.isEmpty()) {
            addDataToExcel(sheet, rowNum[0]++, categorie, "", "", "");
        }
        addDataToExcel(sheet, rowNum[0]++, "", rubrique, "", "");

        // Accéder à la rubrique
        driver.findElement(By.linkText(rubrique)).click();
        Thread.sleep(3000);

        System.out.println("\u001B[33m** " + rubrique + " :\u001B[0m");

        // Tester chaque sous-rubrique
        for (String sousRubrique : sousRubriques) {
            boolean elementExiste = elementExiste(driver, By.linkText(sousRubrique));
            String statut = elementExiste ? "OK" : "KO";

            addDataToExcel(sheet, rowNum[0]++, "", "", sousRubrique, statut);

            if (elementExiste) {
                driver.findElement(By.linkText(sousRubrique)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + sousRubrique + " OK\033[0m");

                driver.navigate().back();
                Thread.sleep(2000);
            } else {
                System.out.println("\u001B[31m" + sousRubrique + " ,  KO\u001B[0m");
            }
        }
    }

    @Test
    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://espace-rip.orange-business.com/");
        Thread.sleep(2000);

        driver.manage().window().maximize();

        if (elementExiste(driver, By.id("details-button"))) {
            driver.findElement(By.id("details-button")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("proceed-link")).click();
            Thread.sleep(2000);
        }

        String username = "ripripDEMO";
        String password = "TestProd2024#";
        Thread.sleep(2000);

        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("submit-button")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("currentPassword")).sendKeys(password);
        driver.findElement(By.id("submit-button")).click();
        Thread.sleep(4000);

        driver.findElement(By.id("didomi-notice-disagree-button")).click();
        Thread.sleep(2000);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Bretagne_COL");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Catégorie");
        headerRow.createCell(1).setCellValue("Rubrique");
        headerRow.createCell(2).setCellValue("Sous-rubrique");
        headerRow.createCell(3).setCellValue("Statut");

        int[] rowNum = {1};

        // Définir les données pour les tests
        String[][][] categories = {
                {   // Catégorie 1 : Mes essentiels
                        {" Mes essentiels", "Mon contrat Public", "Mon contrat public signé"},
                        {"", "Mes contrats tiers", "Mes contrats sous-traitance Orange Concessions"},
                        {"", "Mes offres & contrats usagers", "Mes offres de référence"},
                        {"", "Ma communication", "Mes maquettes d'outils de vente"}
                },
                {   // Catégorie 2 : Mon réseau
                        {" Mon réseau", "Mes documents de référence", "Mes processus"},
                        {"", "Ma sécurité chantiers", "Mes PPR"},
                        {"", "Mon déploiement", "Mes PV réception"},
                        {"", "Ma vie de réseau", "Mon exploitation et maintenance"}
                },
                {   // Catégorie 3 : Mon pilotage
                        {" Mon pilotage", "Mes factures", "Mes demandes de subvention"},
                        {"", "Mes réunions", "Mes comités de pilotage"},
                        {"", "Mon reporting", "Mes KPI ARCEP"}
                }
        };

        for (String[][] categorieData : categories) {
            for (String[] rubriqueData : categorieData) {
                String categorie = rubriqueData[0];
                String rubrique = rubriqueData[1];
                String[] sousRubriques = rubriqueData[2].split(",");

                if (!categorie.isEmpty()) {
                    driver.findElement(By.xpath("//button[contains(.,'" + categorie + "')]")).click();
                    Thread.sleep(2000);
                    System.out.println("\033[34m* " + categorie + "\033[0m");
                }

                testerRubriques(driver, sheet, rowNum, categorie, rubrique, sousRubriques);
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream("Recette_Bretagne_NewOpticode.xlsx")) {
            workbook.write(fileOut);
        }

        driver.quit();
    }

    private static void addDataToExcel(Sheet sheet, int rowNum, String categorie, String rubrique, String sousRubrique, String statut) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(categorie);
        row.createCell(1).setCellValue(rubrique);
        row.createCell(2).setCellValue(sousRubrique);
        row.createCell(3).setCellValue(statut);
    }
}

