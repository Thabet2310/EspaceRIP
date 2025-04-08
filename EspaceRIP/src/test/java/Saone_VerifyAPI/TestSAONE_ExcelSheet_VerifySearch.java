package Saone_VerifyAPI;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileOutputStream;
import java.io.IOException;

import static java.lang.System.setProperty;

public class TestSAONE_ExcelSheet_VerifySearch {

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

        driver.get("https://espace-rip.orange-business.com/");
        Thread.sleep(2000);

        driver.manage().window().maximize();

        boolean pageIntermediairePresente = elementExiste(driver, By.id("details-button"));

        if (pageIntermediairePresente) {
            driver.findElement(By.id("details-button")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("proceed-link")).click();
            Thread.sleep(2000);
        }

        String username = "ripripDEMO";
        String password = "Demo2023#";
        Thread.sleep(2000);

        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys(username);
        driver.findElement(By.id("submit-button")).click();
        Thread.sleep(1000);
        WebElement passwordInput = driver.findElement(By.id("currentPassword"));
        passwordInput.sendKeys(password);

        WebElement loginButton = driver.findElement(By.id("submit-button"));
        loginButton.click();
        Thread.sleep(8000);



        // *********** Mes essentiels ***********

        String C1 = " Mes essentiels";
        String R1C1 = "Mon contrat Public";
        String SR_R1C1 = "Mon acte de transfert";




        //Acceder à la catégorie

        String pathMenuTitle = "//button[contains(.,'" + C1 + "')]";
        driver.findElement(By.xpath(pathMenuTitle)).click();
        Thread.sleep(2000);

        System.out.println("\033[34m* " + C1 + "\033[0m");

        // Accéder à la rubrique Mon contrat Public
        driver.findElement(By.linkText(R1C1)).click();
        Thread.sleep(3000);

        driver.findElement(By.linkText(SR_R1C1)).click();
        Thread.sleep(7000);


        driver.findElement(By.id("QCB1_Button1")).click();

        Thread.sleep(3000);

        driver.quit();
    }

}
