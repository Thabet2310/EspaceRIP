package SAONE;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import static java.lang.System.setProperty;

public class TestSaone_Excel {

    public static boolean elementExiste(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.isDisplayed(); // Vérifiez si l'élément est visible
        } catch (NoSuchElementException | ElementNotInteractableException e) {
            return false;
        }
    }

    @Test
    public static void main(String[] args) throws InterruptedException, IOException {
        setProperty("webdriver.chrome.driver", "C:\\Users\\t.thabet\\chromedriver\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Ouvrir la page de connexion (remplacez l'URL par votre propre URL)
        String loginPageUrl = "https://pprod-espace-rip.orange-business.com ";
        driver.get(loginPageUrl);
        Thread.sleep(2000);

        // Vérifier si la page intermédiaire s'affiche
        boolean pageIntermediairePresente = elementExiste(driver, By.id("details-button"));

        if (pageIntermediairePresente) {
            // Cliquer sur un élément pour continuer vers la page d'authentification
            driver.findElement(By.id("details-button")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("proceed-link")).click();
            Thread.sleep(2000);
        }

        // Saisir les identifiants
        String username = "rip.rrth";
        String password = "Rrth2019#";
        Thread.sleep(2000);

        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys(username);
        driver.findElement(By.id("submit-button")).click();
        Thread.sleep(1000);
        WebElement passwordInput = driver.findElement(By.id("currentPassword"));
        passwordInput.sendKeys(password);

        // Cliquer sur le bouton de connexion (remplacez le sélecteur par celui de votre bouton de connexion)
        WebElement loginButton = driver.findElement(By.id("submit-button"));
        loginButton.click();
        Thread.sleep(4000);

        // Charger le fichier Excel
        FileInputStream file = new FileInputStream("C:\\Users\\t.thabet\\Desktop\\TestAuto.xlsx");
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        // Itérer sur les lignes du fichier Excel
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            // Lire les données de chaque colonne
            String rubrique = row.getCell(0).getStringCellValue();
            String sousRubrique = row.getCell(1).getStringCellValue();
            String element = row.getCell(2).getStringCellValue();

            // Utiliser ces valeurs directement dans votre code

            String pathMenuTitle = "//button[contains(.,'" + rubrique + "')]";
            if (elementExiste(driver, By.xpath(pathMenuTitle))) {
                driver.findElement(By.xpath(pathMenuTitle)).click();
                Thread.sleep(2000);
                System.out.println("* " + rubrique);

                String pathSousRubrique = "//a[contains(.,'" + sousRubrique + "')]";
                boolean sousRubriqueExiste = elementExiste(driver, By.xpath(pathSousRubrique));
                if (sousRubriqueExiste) {
                    driver.findElement(By.xpath(pathSousRubrique)).click();
                    Thread.sleep(5000);

                    System.out.println("** " + sousRubrique + " , sont :  ");
                    String pathElement = "//a[contains(.,'" + element + "')]";
                    boolean elementExiste = elementExiste(driver, By.xpath(pathElement));
                    if (elementExiste) {
                        driver.findElement(By.xpath(pathElement)).click();
                        Thread.sleep(5000);

                        System.out.println("- " + element);

                        driver.navigate().back(); // Retour après avoir traité un élément
                        Thread.sleep(2000);
                    } else {
                        // Ne rien faire si l'élément n'existe pas
                    }

                    // Revenir en arrière pour retourner à la liste des sous-rubriques
                    driver.navigate().back();
                    Thread.sleep(4000);
                } else {
                    //Ne rien faire si la sous rubrique n'existe pas
                }
            } else {
                System.out.println("Rubrique non trouvée : " + rubrique);
            }
        }

        // Fermer le fichier Excel
        workbook.close();
    }
}
