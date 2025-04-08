package RipDEMO;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static java.lang.System.setProperty;

public class CodeOptiReadFromexcel {

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


        // Champs à modifier selon le compte utilisateur
        String username = "ripripDEMO";
        String password = "TestProd2024#";
        String NomProfilUtilisateur = "GERS_OCC1";



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

        try {
            FileInputStream file = new FileInputStream(new File("C:\\Users\\t.thabet\\Desktop\\Automation\\SeleniumWebdriverJavaEspaceCollectivité\\EspaceCollectivite\\Menu.xlsx"));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);



            // Parcourir les lignes du fichier Excel
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                Row currentRow = sheet.getRow(i);
                Row nextRow = sheet.getRow(i + 1);


                Cell categorieCell = currentRow.getCell(0);
                Cell rubriqueCell = currentRow.getCell(1);
                Cell sousRubriqueCell = currentRow.getCell(2);
                Cell droitCell = currentRow.getCell(3);

                // Vérification si la catégorie est vide et si le champ suivant dans la colonne catégorie de la ligne suivante n'est pas vide
                if (categorieCell == null || categorieCell.getStringCellValue().isEmpty()) {
                    Cell nextCategorieCell = nextRow.getCell(0);
                    if (nextCategorieCell != null && !nextCategorieCell.getStringCellValue().isEmpty()) {
                        categorieCell = nextCategorieCell;
                        rubriqueCell = nextRow.getCell(1);
                        sousRubriqueCell = nextRow.getCell(2);
                        droitCell = nextRow.getCell(3);
                        i++; // Passer à la ligne suivante
                    }
                }

                // Vérification si la rubrique est vide et si le champ suivant dans la colonne rubrique de la ligne suivante n'est pas vide
                if (rubriqueCell == null || rubriqueCell.getStringCellValue().isEmpty()) {
                    Cell nextRubriqueCell = nextRow.getCell(1);
                    if (nextRubriqueCell != null && !nextRubriqueCell.getStringCellValue().isEmpty()) {
                        rubriqueCell = nextRubriqueCell;
                        sousRubriqueCell = nextRow.getCell(2);
                        droitCell = nextRow.getCell(3);
                        i++; // Passer à la ligne suivante
                    }
                }

                // Vérifier si les cellules ne sont pas vides
                if (categorieCell != null && rubriqueCell != null && sousRubriqueCell != null && droitCell != null) {
                    String categorie = categorieCell.getStringCellValue();
                    String rubrique = rubriqueCell.getStringCellValue();
                    String sousRubrique = sousRubriqueCell.getStringCellValue();
                    String droit = droitCell.getStringCellValue();

                    // Cliquer sur le lien de la catégorie
                    driver.findElement(By.xpath("//button[contains(.,'" + categorie + "')]")).click();
                    Thread.sleep(2000);

                    // Cliquer sur le lien de la rubrique
                    driver.findElement(By.linkText(rubrique)).click();
                    Thread.sleep(3000);

                    // Vérifier le droit d'accès à la sous-rubrique
                    if (droit.equals("Lecture") || droit.equals("Collaboration")) {
                        // Cliquer sur le lien de sous-rubrique
                        driver.findElement(By.linkText(sousRubrique)).click();
                        Thread.sleep(10000);

                        // Revenir en arrière pour revenir à la liste des sous-rubriques
                        driver.navigate().back();
                        Thread.sleep(3000);
                    } else {
                        System.out.println("Droit insuffisant pour accéder à la sous-rubrique : " + sousRubrique);
                    }
                } else {
                    System.out.println("Une ou plusieurs cellules sont vides dans la ligne " + currentRow.getRowNum());
                }
            }

            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Fermer le navigateur une fois tous les tests terminés
        driver.quit();
    }
}
