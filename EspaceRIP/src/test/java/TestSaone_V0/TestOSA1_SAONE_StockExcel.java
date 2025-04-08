package TestSaone_V0;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileOutputStream;
import java.io.IOException;

import static java.lang.System.setProperty;

public class TestOSA1_SAONE_StockExcel {

    public static boolean elementExiste(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        setProperty("webdriver.chrome.driver", "C:\\Users\\t.thabet\\chromedriver\\chromedriver\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.get("https://pprod-espace-rip.orange-business.com");
        Thread.sleep(2000);

        driver.manage().window().maximize();

        boolean pageIntermediairePresente = elementExiste(driver, By.id("details-button"));

        if (pageIntermediairePresente) {
            driver.findElement(By.id("details-button")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("proceed-link")).click();
            Thread.sleep(2000);
        }

        String username = "rip.rrth";
        String password = "Rrth2019#";
        Thread.sleep(2000);

        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys(username);
        driver.findElement(By.id("submit-button")).click();
        Thread.sleep(1000);
        WebElement passwordInput = driver.findElement(By.id("currentPassword"));
        passwordInput.sendKeys(password);

        WebElement loginButton = driver.findElement(By.id("submit-button"));
        loginButton.click();
        Thread.sleep(4000);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Rapport");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Catégorie");
        headerRow.createCell(1).setCellValue("Rubrique");
        headerRow.createCell(2).setCellValue("Sous-rubrique");
        headerRow.createCell(3).setCellValue("Statut");

        int rowNum = 1;

        String C1 = " Mes essentiels";
        String R1C1 = "Mon contrat Public";
        String[] SR_R1C1 = {"Mon contrat public signé", "Mes avenants"};

        addDataToExcel(sheet, rowNum++, C1, "", "", "");
        addDataToExcel(sheet, rowNum++, "", R1C1, "", "");


        //Acceder à la catégorie

        String pathMenuTitle = "//button[contains(.,'" + C1 + "')]";
        driver.findElement(By.xpath(pathMenuTitle)).click();
        Thread.sleep(2000);

        // Accéder à la rubrique Mon contrat Public
        driver.findElement(By.linkText(R1C1)).click();
        Thread.sleep(3000);

        for (String element : SR_R1C1) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));
            String statut = elementExiste ? "OK" : "KO";

            addDataToExcel(sheet, rowNum++, "", "", element, statut);

            if (elementExiste) {
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);
                driver.navigate().back();
                Thread.sleep(2000);
            }
        }

        // ... Ajouter d'autres données pour les autres rubriques ...

        try (FileOutputStream fileOut = new FileOutputStream("rapport.xlsx")) {
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
