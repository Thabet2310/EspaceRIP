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


// ce code lire depuis un fichier excel avec succés
public class Deepseek3 {

    public static boolean elementExiste(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static void testerSousRubrique(WebDriver driver, Sheet sheet, int[] rowNum, String sousRubrique) throws InterruptedException {
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

    @Test
    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://espace-rip.orange-business.com/");
        Thread.sleep(2000);

        driver.manage().window().maximize();

        // Lire les credentials depuis le fichier Excel
        FileInputStream file = new FileInputStream("C:\\Users\\t.thabet\\Desktop\\Automation\\SeleniumWebdriverJavaEspaceCollectivité\\test.xlsx");
        Workbook excelWorkbook = new XSSFWorkbook(file);
        Sheet credentialsSheet = excelWorkbook.getSheet("Credentials");

        String User = "";
        String Password = "";

        // Lire la première ligne (en supposant que les credentials sont dans la première ligne)
        Row credentialsRow = credentialsSheet.getRow(1); // La première ligne est l'en-tête, donc on commence à la deuxième ligne
        if (credentialsRow != null) {
            User = credentialsRow.getCell(0).getStringCellValue();
            Password = credentialsRow.getCell(1).getStringCellValue();
        }

        driver.findElement(By.id("username")).sendKeys(User);
        driver.findElement(By.id("submit-button")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("currentPassword")).sendKeys(Password);
        driver.findElement(By.id("submit-button")).click();
        Thread.sleep(4000);

        driver.findElement(By.id("didomi-notice-disagree-button")).click();
        Thread.sleep(2000);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("TestResults");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Catégorie");
        headerRow.createCell(1).setCellValue("Rubrique");
        headerRow.createCell(2).setCellValue("Sous-rubrique");
        headerRow.createCell(3).setCellValue("Statut");

        int[] rowNum = {1};

        Sheet excelSheet = excelWorkbook.getSheet("DATA"); // Utiliser la feuille "DATA" pour les tests
        if (excelSheet == null) {
            throw new RuntimeException("La feuille 'DATA' n'a pas été trouvée dans le fichier Excel.");
        }

        String currentCategorie = "";
        String currentRubrique = "";

        for (Row row : excelSheet) {
            if (row.getRowNum() == 0) continue; // Ignorer l'en-tête

            String categorie = row.getCell(0).getStringCellValue();
            String rubrique = row.getCell(1).getStringCellValue();
            String sousRubrique = row.getCell(2).getStringCellValue();
            String statut = row.getCell(3).getStringCellValue();

            // Vérifier si la catégorie a changé
            if (!categorie.equals(currentCategorie)) {
                currentCategorie = categorie;
                driver.findElement(By.xpath("//button[contains(.,'" + categorie + "')]")).click();
                Thread.sleep(2000);
            }

            // Vérifier si la rubrique a changé
            if (!rubrique.equals(currentRubrique)) {
                currentRubrique = rubrique;
                driver.findElement(By.linkText(rubrique)).click();
                Thread.sleep(3000);
            }

            // Tester la sous-rubrique si le statut est "Lecture" ou "Collaboration"
            if (statut.equals("Lecture") || statut.equals("Collaboration")) {
                testerSousRubrique(driver, sheet, rowNum, sousRubrique);
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream("TestResults.xlsx")) {
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