package SoneExcelSheetTest;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static java.lang.System.setProperty;

public class SearshBox {

    // Déclaration d'une variable pour stocker le nom de la rubrique actuelle
    private static String currentRubrique = "";

    public static boolean elementExiste(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        setProperty("webdriver.chrome.driver", "C:\\Users\\t.thabet\\Desktop\\Automation\\SeleniumWebdriverJavaEspaceCollectivité\\EspaceCollectivite\\chromedriver.exe");
        Thread.sleep(2000);

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
        String password = "TestProd2024#";

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
        Sheet sheet = workbook.createSheet("SAONE_COL");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Catégorie");
        headerRow.createCell(1).setCellValue("Rubrique");
        headerRow.createCell(2).setCellValue("Sous-rubrique");
        headerRow.createCell(3).setCellValue("Statut élément");
        headerRow.createCell(4).setCellValue("Statut iframe");

        int rowNum = 1;

        // *********** Mes essentiels ***********

        String C1 = " Mes essentiels";
        String R1C1 = "Mon contrat Public";
        String[] SR_R1C1 = {
                "Mon acte de transfert", "Mon acte de transfert"
        };

        addDataToExcel(sheet, rowNum++, C1, R1C1, "", "", "");

        //Acceder à la catégorie

        String pathMenuTitle = "//button[contains(.,'" + C1 + "')]";
        driver.findElement(By.xpath(pathMenuTitle)).click();
        Thread.sleep(2000);

        System.out.println("\033[34m* " + C1 + "\033[0m");

        // Accéder à la rubrique Mon contrat Public
        driver.findElement(By.linkText(R1C1)).click();
        Thread.sleep(3000);

        System.out.println("\u001B[33m** " + R1C1 + " :\u001B[0m");

        for (String element : SR_R1C1) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));
            String statutElement = elementExiste ? "Existe" : "N'existe plus";
            String statutIframe = "";

            if (elementExiste) {
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(10000);

                System.out.println("\033[32m" + element + " Existe\033[0m");

                //Localisation de champ de recherche
                WebElement iframeElement = driver.findElement(By.id("content"));
                driver.switchTo().frame(iframeElement);

                // Vérifier si l'iframe est chargé avec succès
                boolean iframeChargé = elementExiste(driver, By.cssSelector("#inplaceSearchDiv_WPQ2_lsinput"));
                statutIframe = iframeChargé ? "OK" : "KO";

                // Affichage du message avec le statut de l'iframe
                System.out.println("Le statut de l'iframe pour " + element + " est " + statutIframe);

                // Si le statut de l'iframe est "KO", capture d'écran
                if (statutIframe.equals("KO")) {
                    try {
                        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                        FileUtils.copyFile(screenshotFile, new File("C:/Users/t.thabet/Desktop/Automation/SeleniumWebdriverJavaEspaceCollectivité/EspaceCollectivite/erreur_iframe.png"));
                        System.out.println("Capture d'écran de l'erreur d'iframe effectuée avec succès !");
                    } catch (Exception e) {
                        System.out.println("Erreur lors de la capture d'écran de l'erreur d'iframe : " + e.getMessage());
                    }
                }

                // Après avoir interagi avec l'élément, vous pouvez revenir au contexte par défaut
                driver.switchTo().defaultContent();

                Thread.sleep(5000);

            } else {
                System.out.println("\u001B[31m" + element + " ,  N'existe plus\u001B[0m");
            }

            // Utilisation du nom de la rubrique actuelle lors de l'ajout des données à la feuille Excel
            addDataToExcel(sheet, rowNum++, "", currentRubrique, element, statutElement, statutIframe);

            // Revenir en arrière pour passer au test de la deuxième sous-rubrique
            driver.navigate().back();
            Thread.sleep(2000);
        }

        try (FileOutputStream fileOut = new FileOutputStream("Recette_testsearchBox_iframe.xlsx")) {
            workbook.write(fileOut);
        }

        driver.quit();
    }

    private static void addDataToExcel(Sheet sheet, int rowNum, String categorie, String rubrique, String sousRubrique, String statutElement, String statutIframe) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(categorie);
        row.createCell(1).setCellValue(rubrique);
        row.createCell(2).setCellValue(sousRubrique);
        row.createCell(3).setCellValue(statutElement);
        row.createCell(4).setCellValue(statutIframe);
    }}