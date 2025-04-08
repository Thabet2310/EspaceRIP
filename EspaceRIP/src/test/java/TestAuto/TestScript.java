package TestAuto;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;

import static java.lang.System.setProperty;

public class TestScript {
    public static void main(String[] args) throws InterruptedException {
        setProperty("webdriver.chrome.driver", "C:\\Users\\t.thabet\\Desktop\\chromedriverwin64\\chromedriver.exe");        // Initialiser le navigateur Chrome
        WebDriver driver = new ChromeDriver();

        try {
            FileInputStream fis = new FileInputStream("D:\\testData.xlsx");
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0); // Supposons que les données sont dans la première feuille

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String username = row.getCell(0).getStringCellValue();
                String password = row.getCell(1).getStringCellValue();

                // Exécutez le test avec les données lues
                runTest(driver, username, password);
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver.quit();
    }

    public static void runTest(WebDriver driver, String username, String password) throws InterruptedException {
        driver.get("https://qualif-espace-rip.orange-business.com/");
        // parmetres avancés
        driver.findElement(By.id("details-button")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("proceed-link")).click();
        Thread.sleep(2000);

        // Utilisez les données
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("submit-button")).click();
        Thread.sleep(5000);

        driver.findElement(By.id("currentPassword")).sendKeys(password);
        driver.findElement(By.id("login_button")).click();
        Thread.sleep(10000);

        // Effectuez d'autres vérifications ou actions si nécessaire
    }
}