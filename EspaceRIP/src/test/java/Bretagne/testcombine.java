package Bretagne;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.*;
import java.util.*;

public class testcombine {
    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        FileInputStream file = new FileInputStream("C:\\Users\\t.thabet\\Desktop\\Automation\\SeleniumWebdriverJavaEspaceCollectivité\\test.xlsx");
        Workbook excelWorkbook = new XSSFWorkbook(file);
        Sheet credentialsSheet = excelWorkbook.getSheet("Credentials");
        Sheet testSheet = excelWorkbook.getSheet("DATA");

        if (testSheet == null) {
            throw new RuntimeException("La feuille 'TestEspCo' est introuvable.");
        }

        // Préparation du fichier de résultats combinés
        Workbook resultWorkbook = new XSSFWorkbook();
        Sheet resultSheet = resultWorkbook.createSheet("TestResults");

        Row headerRow = resultSheet.createRow(0);
        headerRow.createCell(0).setCellValue("Catégorie");
        headerRow.createCell(1).setCellValue("Rubrique");
        headerRow.createCell(2).setCellValue("Sous-rubrique");

        List<String> GUList = new ArrayList<>();
        int colIndex = 3;

        // Lire la liste des GU
        for (int i = 3; i < testSheet.getRow(0).getPhysicalNumberOfCells(); i++) {
            String GU = testSheet.getRow(0).getCell(i).getStringCellValue();
            GUList.add(GU);
            headerRow.createCell(colIndex++).setCellValue(GU);
        }

        int resultRowNum = 1;

        // Boucle sur chaque GU (utilisateur)
        for (int rowIndex = 1; rowIndex < credentialsSheet.getPhysicalNumberOfRows(); rowIndex++) {
            Row credentialsRow = credentialsSheet.getRow(rowIndex);
            if (credentialsRow == null || credentialsRow.getCell(0) == null || credentialsRow.getCell(1) == null) {
                break;
            }

            String User = credentialsRow.getCell(0).getStringCellValue();
            String Password = credentialsRow.getCell(1).getStringCellValue();
            String GU = credentialsRow.getCell(2).getStringCellValue();

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
                continue;
            }

            // Tester chaque sous-rubrique
            for (int rowNum = 1; rowNum < testSheet.getPhysicalNumberOfRows(); rowNum++) {
                Row testRow = testSheet.getRow(rowNum);
                if (testRow == null) continue;

                String categorie = testRow.getCell(0).getStringCellValue();
                String rubrique = testRow.getCell(1).getStringCellValue();
                String sousRubrique = testRow.getCell(2).getStringCellValue();
                String permission = testRow.getCell(rowIndex + 2).getStringCellValue(); // Vérifier permission du GU

                if (!permission.equals("Lecture") && !permission.equals("Collaboration")) {
                    continue;
                }

                Row resultRow = resultSheet.getRow(rowNum);
                if (resultRow == null) {
                    resultRow = resultSheet.createRow(rowNum);
                    resultRow.createCell(0).setCellValue(categorie);
                    resultRow.createCell(1).setCellValue(rubrique);
                    resultRow.createCell(2).setCellValue(sousRubrique);
                }

                boolean statutTest = testSousRubrique(driver, sousRubrique);
                resultRow.createCell(rowIndex + 2).setCellValue(statutTest ? "OK" : "KO");
            }
            Thread.sleep(4000);

            driver.findElement(By.linkText("Déconnexion")).click();
            Thread.sleep(4000);
        }

        // Sauvegarde du fichier combiné
        try (FileOutputStream fileOut = new FileOutputStream("TestResults.xlsx")) {
            resultWorkbook.write(fileOut);
        }

        driver.quit();
    }

    public static boolean isElementPresent(WebDriver driver, By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean testSousRubrique(WebDriver driver, String sousRubrique) {
        try {
            WebElement element = driver.findElement(By.linkText(sousRubrique));
            if (element.isDisplayed()) {
                element.click();
                Thread.sleep(2000);
                driver.navigate().back();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
