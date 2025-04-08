package Bretagne;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TEST411 {

    private static String lastCategorie = "";
    private static String lastRubrique = "";

    public static boolean elementExiste(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static void testerSousRubrique(WebDriver driver, Sheet sheet, int[] rowNum, String categorie, String rubrique, String sousRubrique, String statut) throws InterruptedException {
        boolean elementExiste = elementExiste(driver, By.linkText(sousRubrique));
        String statutFinal = elementExiste ? "OK" : "KO";

        // Ajouter les données dans le fichier Excel
        addDataToExcel(sheet, rowNum[0], categorie, rubrique, sousRubrique, statutFinal);
        rowNum[0]++; // Passer à la ligne suivante

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

    @Test
    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Lire le fichier Excel
        FileInputStream file = new FileInputStream("C:\\Users\\t.thabet\\Desktop\\Automation\\SeleniumWebdriverJavaEspaceCollectivité\\test.xlsx");
        Workbook excelWorkbook = new XSSFWorkbook(file);
        Sheet credentialsSheet = excelWorkbook.getSheet("Credentials");

        Row credentialsRow;
        int rowIndex = 1;  // Commence à la deuxième ligne (en ignorant l'en-tête)
        while ((credentialsRow = credentialsSheet.getRow(rowIndex)) != null) {
            if (credentialsRow.getCell(0) == null || credentialsRow.getCell(1) == null) {
                break;  // Arrêter si une cellule est vide (pas de credentials)
            }

            String User = credentialsRow.getCell(0).getStringCellValue();
            String Password = credentialsRow.getCell(1).getStringCellValue();
            String GU = credentialsRow.getCell(2).getStringCellValue();

            // Ouvrir le navigateur et se rendre sur la page de login
            driver.get("https://espace-rip.orange-business.com/");
            driver.findElement(By.id("username")).sendKeys(User);
            driver.findElement(By.id("submit-button")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("currentPassword")).sendKeys(Password);
            driver.findElement(By.id("submit-button")).click();
            Thread.sleep(4000);

            // Vérification du message d'erreur de connexion
            By errorMessageLocator = By.id("errorMessage480");
            if (isElementPresent(driver, errorMessageLocator)) {
                System.out.println("⚠️ Erreur : Identifiant ou mot de passe incorrect pour le groupe utilisateur " + GU + " !");
                driver.findElement(By.linkText("Changer d'identifiant")).click();
                Thread.sleep(1000);
                rowIndex++; // Passer à l'utilisateur suivant
                continue;
            }

            // Accéder à la feuille "DATA"
            Sheet excelSheet = excelWorkbook.getSheet("DATA");
            if (excelSheet == null) {
                throw new RuntimeException("La feuille 'DATA' n'a pas été trouvée dans le fichier Excel.");
            }

            // Trouver la colonne du GU dans DATA
            int guColumnIndex = findGUColumn(excelSheet, GU);
            if (guColumnIndex == -1) {
                System.out.println("⚠️ Erreur : Aucune colonne trouvée pour le GU " + GU);
                continue;
             else
             {
                    System.out.println("✅ Colonne trouvée pour le GU [" + GU + "] : " + guColumnIndex);
                }
            }

            // Création du fichier de résultats
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("TestResults");

            // Ajouter les en-têtes au fichier de résultats
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Catégorie");
            headerRow.createCell(1).setCellValue("Rubrique");
            headerRow.createCell(2).setCellValue("Sous-rubrique");
            headerRow.createCell(3).setCellValue("Statut");

            int[] rowNum = {1};

            String currentCategorie = "";
            String currentRubrique = "";

            // Tester les sous-rubriques
            for (Row row : excelSheet) {
                if (row.getRowNum() == 0) continue;

                String categorie = row.getCell(0).getStringCellValue();
                String rubrique = row.getCell(1).getStringCellValue();
                String sousRubrique = row.getCell(2).getStringCellValue();
                String statut = row.getCell(guColumnIndex).getStringCellValue();

                if (!categorie.equals(currentCategorie)) {
                    currentCategorie = categorie;
                    driver.findElement(By.xpath("//button[contains(.,'" + categorie + "')]")).click();
                    Thread.sleep(2000);
                }

                if (!rubrique.equals(currentRubrique)) {
                    currentRubrique = rubrique;
                    driver.findElement(By.linkText(rubrique)).click();
                    Thread.sleep(3000);
                }

                if (statut.equals("Lecture") || statut.equals("Collaboration")) {
                    testerSousRubrique(driver, sheet, rowNum, categorie, rubrique, sousRubrique, statut);
                }
            }

            // Enregistrer les résultats
            try (FileOutputStream fileOut = new FileOutputStream("TestResults_" + GU + ".xlsx")) {
                workbook.write(fileOut);
            }

            driver.findElement(By.linkText("Déconnexion")).click();
            Thread.sleep(4000);
            rowIndex++; // Passer à l'utilisateur suivant
        }

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

    private static int findGUColumn(Sheet sheet, String GU) {
        Row headerRow = sheet.getRow(0);
        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().equalsIgnoreCase(GU)) {
                return cell.getColumnIndex();
            }
        }
        return -1;
    }

    private static void addDataToExcel(Sheet sheet, int rowNum, String categorie, String rubrique, String sousRubrique, String statut) {
        Row row = sheet.createRow(rowNum);

        if (!categorie.equals(lastCategorie)) {
            row.createCell(0).setCellValue(categorie);
            lastCategorie = categorie;
        }

        if (!rubrique.equals(lastRubrique)) {
            row.createCell(1).setCellValue(rubrique);
            lastRubrique = rubrique;
        }

        row.createCell(2).setCellValue(sousRubrique);
        row.createCell(3).setCellValue(statut);
    }
}
