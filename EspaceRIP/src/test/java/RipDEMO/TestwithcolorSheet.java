package RipDEMO;

import org.apache.commons.io.FileUtils;
        import org.apache.poi.ss.usermodel.*;
        import org.apache.poi.xssf.usermodel.XSSFWorkbook;
        import org.openqa.selenium.*;
        import org.openqa.selenium.chrome.ChromeDriver;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;

        import static java.lang.System.setProperty;

public class TestwithcolorSheet {
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


        //Creation d'un fichier excel pour le stockage des données de test
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("SAONE_COL");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Catégorie");
        headerRow.createCell(1).setCellValue("Rubrique");
        headerRow.createCell(2).setCellValue("Sous-rubrique");
        headerRow.createCell(3).setCellValue("Statut élément");
        headerRow.createCell(4).setCellValue("Statut iframe");

        int rowNum = 1;

        // Catégorie : Mes essentiels
        String C1 = "Mes essentiels"; // Categorie
        String[] R1C1 = {"Mon contrat Public", "Mes contrats tiers"}; // Rubriques de catégorie 1
        String[][] SR_R1C1 = {{"Mon contrat public signé", ""}, // Sous-rubriques de rubrique 1 de catégorie 1
                {"", ""}}; // Sous-rubriques de rubrique 2 de catégorie 1

        // Catégorie : Mon réseau
        String C2 = "Mon réseau"; // Categorie
        String[] R2C2 = {"Ma sécurité chantiers", "Mes documents de référence"}; // Rubriques de catégorie 2
        String[][] SR_R2C2 = {{"Mes CSPS", ""}, // Sous-rubriques de rubrique 1 de catégorie 2
                {"", ""}}; // Sous-rubriques de rubrique 2 de catégorie 2

        // Catégorie : Mon pilotage
        String C3 = "Mon pilotage"; // Categorie
        String[] R3C3 = {"Mes factures", "Mes réunions"}; // Rubriques de catégorie 3
        String[][] SR_R3C3 = {{"Mes demandes de subvention", ""}, // Sous-rubriques de rubrique 1 de catégorie 3
                {"", ""}}; // Sous-rubriques de rubrique 2 de catégorie 3



        // Tester les catégories, rubriques et sous-rubriques

        for (int i = 0; i < 3; i++) {
            String categorie = null;
            String[] rubriques = null;
            String[][] sousRubriques = null;

            // Sélectionner la catégorie, les rubriques et les sous-rubriques appropriées
            switch (i) {
                case 0:
                    categorie = C1;
                    rubriques = R1C1;
                    sousRubriques = SR_R1C1;
                    break;
                case 1:
                    categorie = C2;
                    rubriques = R2C2;
                    sousRubriques = SR_R2C2;
                    break;
                case 2:
                    categorie = C3;
                    rubriques = R3C3;
                    sousRubriques = SR_R3C3;
                    break;
            }

            // Accéder à la catégorie
            String pathMenuTitle = "//button[contains(.,'" + categorie + "')]";
            driver.findElement(By.xpath(pathMenuTitle)).click();
            Thread.sleep(2000);

            System.out.println("\033[34m* " + categorie + "\033[0m");

            addDataToExcel(sheet, rowNum++, categorie, "", "", "", "");


            for (int j = 0; j < rubriques.length; j++) {
                String rubrique = rubriques[j];
                String[] sousRubriqueArray = sousRubriques[j];
                // Stockage de données de test
                addDataToExcel(sheet, rowNum++, "", rubrique, "", "", "");


                // Accéder à la rubrique
                driver.findElement(By.linkText(rubrique)).click();
                Thread.sleep(3000);

                System.out.println("\u001B[33m** " + rubrique + " :\u001B[0m");

                for (String sousRubrique : sousRubriqueArray) {
                    boolean elementExiste = elementExiste(driver, By.linkText(sousRubrique));
                    String statutElement = elementExiste ? "OK" : "KO";
                    String statutIframe = "";

                    if (elementExiste) {
                        driver.findElement(By.linkText(sousRubrique)).click();
                        Thread.sleep(10000);

                        System.out.println("\033[32m" + sousRubrique + " Existe\033[0m");

                        // Vérifier si le champ de recherche est cliquable
                        boolean champRechercheClickable = elementExiste(driver, By.id("champ-de-recherche-id")) && driver.findElement(By.id("champ-de-recherche-id")).isEnabled();

                        if (champRechercheClickable) {
                            System.out.println("\033[32mLe champ de recherche est cliquable, donc l'iframe existe !\033[0m");
                        } else {
                            System.out.println("\033[31mLe champ de recherche n'est pas cliquable, donc l'iframe n'existe plus.\033[0m");
                            captureEcran(driver);
                            statutIframe = "KO";
                        }

                        // Revenir au contexte par défaut
                        driver.navigate().back();
                        Thread.sleep(5000);
                    }
                    else {
                        System.out.println("\u001B[31m" + sousRubrique + " ,  N'existe plus\u001B[0m");
                    }


                    // Ajout des données dans le fichier Excel
                    CellStyle style = statutElement.equals("KO") ? createGreenStyle(workbook) : createRedStyle(workbook);

                    addDataToExcel(sheet, rowNum++, "", "", sousRubrique, statutElement, statutIframe, style);

                }
            }

        }

        // Enregistrer les données dans le fichier Excel après avoir parcouru toutes les catégories
        try (FileOutputStream fileOut = new FileOutputStream("Recette_GERS_iframe.xlsx")) {
            workbook.write(fileOut);
        }

        // Fermer le navigateur une fois tous les tests terminés
        driver.quit();
    }

    private static void addDataToExcel(Sheet sheet, int i, String categorie, String s, String s1, String s2, String s3) {
    }

    private static void addDataToExcel(Sheet sheet, int rowNum, String categorie, String rubrique, String sousRubrique, String statutElement, String statutIframe, CellStyle style) {

        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(categorie);
        row.createCell(1).setCellValue(rubrique);
        row.createCell(2).setCellValue(sousRubrique);
        row.createCell(3).setCellValue(statutElement);
        row.createCell(4).setCellValue(statutIframe);

        // Appliquer le style à la cellule
        row.getCell(3).setCellStyle(style); // Pour la colonne "Statut élément"
        row.getCell(4).setCellStyle(style); // Pour la colonne "Statut iframe"
    }

    private static void captureEcran(WebDriver driver) {
        try {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("C:/Users/t.thabet/Desktop/Automation/SeleniumWebdriverJavaEspaceCollectivité/EspaceCollectivite/erreur_iframe.png"));
            System.out.println("Capture d'écran de l'erreur d'iframe effectuée avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur lors de la capture d'écran de l'erreur d'iframe : " + e.getMessage());
        }
    }

    // Créer un style pour le texte rouge (KO)
    private static CellStyle createRedStyle(Workbook workbook) {
        CellStyle redStyle = workbook.createCellStyle();
        Font redFont = workbook.createFont();
        redFont.setColor(IndexedColors.RED.getIndex());
        redStyle.setFont(redFont);
        return redStyle;
    }

    // Créer un style pour le texte vert (OK)
    private static CellStyle createGreenStyle(Workbook workbook) {
        CellStyle greenStyle = workbook.createCellStyle();
        Font greenFont = workbook.createFont();
        greenFont.setColor(IndexedColors.GREEN.getIndex());
        greenStyle.setFont(greenFont);
        return greenStyle;
    }
}

