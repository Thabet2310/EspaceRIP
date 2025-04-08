package RipDEMO;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class testOptimisation  {

    // Classe représentant une sous-rubrique
    static class SousRubrique {
        private String name;

        public SousRubrique(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    // Classe représentant une rubrique
    static class Rubrique {
        private String name;
        private List<SousRubrique> sousRubriques = new ArrayList<>();

        public Rubrique(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void addSousRubrique(SousRubrique sousRubrique) {
            sousRubriques.add(sousRubrique);
        }

        public List<SousRubrique> getSousRubriques() {
            return sousRubriques;
        }
    }

    // Classe représentant une catégorie
    static class Category {
        private String name;
        private List<Rubrique> rubriques = new ArrayList<>();

        public Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void addRubrique(Rubrique rubrique) {
            rubriques.add(rubrique);
        }

        public List<Rubrique> getRubriques() {
            return rubriques;
        }
    }

    public static boolean elementExiste(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://pprod-espace-rip.orange-business.com/"); //https://espace-rip.orange-business.com/
        Thread.sleep(2000);

        // Maximiser la page
        driver.manage().window().maximize();

        boolean pageIntermediairePresente = elementExiste(driver, By.id("details-button"));

        if (pageIntermediairePresente) {
            driver.findElement(By.id("details-button")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("proceed-link")).click();
            Thread.sleep(2000);
        }

        String username = "thdb.rip.test";
        String password = "Thdb2023#";

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

        // Lecture des données à partir du fichier Excel
        FileInputStream excelFile = new FileInputStream(new File("C:\\Users\\t.thabet\\Desktop\\Automation\\SeleniumWebdriverJavaEspaceCollectivité\\EspaceCollectivite\\Menu.xlsx"));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);

        List<Category> categories = new ArrayList<>();
        Category currentCategory = null;
        Rubrique currentRubrique = null;

        for (Row row : sheet) {
            String categoryName = row.getCell(0).getStringCellValue();
            String rubriqueName = row.getCell(1).getStringCellValue();
            String sousRubriqueName = row.getCell(2).getStringCellValue();

            if (currentCategory == null || !currentCategory.getName().equals(categoryName)) {
                currentCategory = new Category(categoryName);
                categories.add(currentCategory);
            }

            if (currentRubrique == null || !currentRubrique.getName().equals(rubriqueName)) {
                currentRubrique = new Rubrique(rubriqueName);
                currentCategory.addRubrique(currentRubrique);
            }

            currentRubrique.addSousRubrique(new SousRubrique(sousRubriqueName));
        }

        workbook.close();

        // Navigation à travers votre application Web
        for (Category category : categories) {
            // Accéder à la catégorie
            String pathMenuTitle = "//button[contains(.,'" + category.getName() + "')]";
            driver.findElement(By.xpath(pathMenuTitle)).click();
            Thread.sleep(2000);

            for (Rubrique rubrique : category.getRubriques()) {
                // Accéder à la rubrique
                driver.findElement(By.linkText(rubrique.getName())).click();
                Thread.sleep(3000);

                for (SousRubrique sousRubrique : rubrique.getSousRubriques()) {
                    boolean elementExiste = elementExiste(driver, By.linkText(sousRubrique.getName()));
                    String statutElement = elementExiste ? "Existe" : "N'existe plus";
                    String statutIframe = "";

                    if (elementExiste) {
                        driver.findElement(By.linkText(sousRubrique.getName())).click();
                        Thread.sleep(10000);

                        System.out.println("\033[32m" + sousRubrique.getName() + " Existe\033[0m");

                        //Localisation de champ de recherche
                        WebElement iframeElement = driver.findElement(By.id("content"));
                        driver.switchTo().frame(iframeElement);

                        // Vérifier si l'iframe est chargé avec succès
                        boolean iframeChargé = elementExiste(driver, By.cssSelector("#inplaceSearchDiv_WPQ2_lsinput"));
                        statutIframe = iframeChargé ? "OK" : "KO";

                        // Affichage du message avec le statut de l'iframe
                        System.out.println("Le statut de l'iframe pour " + sousRubrique.getName() + " est " + statutIframe);

                        // Si le statut de l'iframe est "KO", capture d'écran
                        if (statutIframe.equals("KO")) {
                            try {
                                File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                                FileUtils.copyFile(screenshotFile, new File("erreur_iframe.png"));
                                System.out.println("Capture d'écran de l'erreur d'iframe effectuée avec succès !");
                            } catch (Exception e) {
                                System.out.println("Erreur lors de la capture d'écran de l'erreur d'iframe : " + e.getMessage());
                            }
                        }

                        // Après avoir interagi avec l'élément, revenir au contexte par défaut
                        driver.switchTo().defaultContent();

                        Thread.sleep(5000);

                    } else {
                        System.out.println("\u001B[31m" + sousRubrique.getName() + " ,  N'existe plus\u001B[0m");
                    }

                    // Ajouter les données à la feuille Excel
                    addDataToExcel(sheet, category.getName(), rubrique.getName(), sousRubrique.getName(), statutElement, statutIframe);
                }
                // Revenir en arrière pour passer au test de la prochaine rubrique
                driver.navigate().back();
                Thread.sleep(2000);
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream("Recette_testsearchBox_iframe.xlsx")) {
            workbook.write(fileOut);
        }

        driver.quit();
    }

    private static void addDataToExcel(Sheet sheet, String categoryName, String rubriqueName, String sousRubriqueName, String statutElement, String statutIframe) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        row.createCell(0).setCellValue(categoryName);
        row.createCell(1).setCellValue(rubriqueName);
        row.createCell(2).setCellValue(sousRubriqueName);
        row.createCell(3).setCellValue(statutElement);
        row.createCell(4).setCellValue(statutIframe);
    }
}