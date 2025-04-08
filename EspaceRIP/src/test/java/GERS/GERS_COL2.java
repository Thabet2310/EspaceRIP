package GERS;

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


public class GERS_COL2 {
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


        // Champs à modifier selon le compte utilisateur
        String username = "ripripDEMO";
        String password = "TestProd2024#";
        String NomProfilUtilisateur = "GERS_COL2";


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
        Sheet sheet = workbook.createSheet( NomProfilUtilisateur );         //nOM DE FICHIER EXCEL

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Catégorie");
        headerRow.createCell(1).setCellValue("Rubrique");
        headerRow.createCell(2).setCellValue("Sous-rubrique");
        headerRow.createCell(3).setCellValue("Statut élément");
        headerRow.createCell(4).setCellValue("Statut iframe");

        int rowNum = 1;


        // Menuà modifier selon le compte utilisateur

        // Catégorie : Mes essentiels
        String C1 = "Mes essentiels"; // Categorie
        String[] R1C1 = {"Mon contrat Public", "Mes contrats tiers", "Mes offres & contrats usagers", "Ma communication"}; // Rubriques de catégorie 1
        String[][] SR_R1C1 = {
                // Sous-rubriques de rubrique 1 de catégorie 1
                {"Mon contrat public signé", "Mon acte de transfert", "Ma lettre de stabilité actionnaire", "Mon autorisation ARCEP", "Mes documents statutaires", "Mes attestations d'assurance",
                        "Mes avenants", "Mes DC4", "Mes notifications", "Mes autres documents structurants", "Mes courriers"  },

                // Sous-rubriques de rubrique 2 de catégorie 1
                {"Mes contrats sous-traitance Orange SA", "Mes contrats sous-traitance Orange Concessions", "Mes contrats fournisseur Orange Wholesale France", "Mes contrats autres fournisseurs"},

                // Sous-rubriques de rubrique 3 de catégorie 1
                {"Mes offres de référence", "Mes contrats usagers génériques"},

                // Sous-rubriques de rubrique 4 de catégorie 1
                {"Mes illustrations", "Mes logos", "Mes maquettes d'outils de vente", "Mes textes et présentations", "Ma charte", "Ma communication éligibilité"}

        };

        // Catégorie : Mon réseau
        String C2 = "Mon réseau"; // Categorie
        String[] R2C2 = {"Mes documents de référence", "Ma sécurité chantiers", "Mon déploiement", "Ma vie de réseau"}; // Rubriques de catégorie 2
        String[][] SR_R2C2 = {
                // Sous-rubriques de rubrique 1 de catégorie 2
                {"Mes processus", "Mes matériels", "Mes référentiels"},

                // Sous-rubriques de rubrique 2 de catégorie 2
                {"Mes CSPS", "Mes PGC", "Mes PPR"},

                // Sous-rubriques de rubrique 3 de catégorie 2
                {"Mes études", "Mes retours", "Mes PV réception"},

                // Sous-rubriques de rubrique 4 de catégorie 2
                {"Mes DOE", "Mes manquements Opérateurs Commerciaux", "Récapitulatif câblage client final", "Mes CRI"}

        };

        // Catégorie : Mon pilotage
        String C3 = "Mon pilotage"; // Categorie
        String[] R3C3 = {"Mes factures", "Mes réunions", "Mon reporting"}; // Rubriques de catégorie 3
        String[][] SR_R3C3 = {
                // Sous-rubriques de rubrique 1 de catégorie 3
                {"Mes demandes de subvention", "Mes redevances et contributions", "Mes factures usagers", "Mes éléments de facturation usagers"},

                // Sous-rubriques de rubrique 2 de catégorie 3
                {"Mes COTECHs", "Mes CA", "Mes COPIL", "Mes ateliers", "Mes réunions d'informations aux actionnaires", "Mes réunions CAC", "Mes autres réunions"},

                // Sous-rubriques de rubrique 3 de catégorie 3
                {"Mes KPI ARCEP", "Mes intentions de déploiement", "Mes IPE & CPN", "Mes tableaux de bord", "Mon insertion professionnelle", "Mes rapports d'activité", "Mes procès verbaux", "Mes rapports CAC et comptes sociaux"}
        };


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
/*
                            // Vérifier si le champ de recherche est cliquable
                            boolean champRechercheClickable = elementExiste(driver, By.id("champ-de-recherche-id")) && driver.findElement(By.id("champ-de-recherche-id")).isEnabled();

                            if (champRechercheClickable) {
                                System.out.println("\033[32miframe OK !\033[0m");
                            } else {
                                System.out.println("\033[31miframe KI.\033[0m");
                                captureEcran(driver);
                                statutIframe = "KO";
                            }
                            // Revenir au contexte par défaut
                            driver.navigate().back();
                            Thread.sleep(5000);
*/

///
                        //Localisation de champ de recherche
                        WebElement iframeElement = driver.findElement(By.id("content"));
                        driver.switchTo().frame(iframeElement);

                        // Vérifier si l'iframe est chargé avec succès
                        boolean iframeChargé = elementExiste(driver, By.cssSelector("#inplaceSearchDiv_WPQ2_lsinput"));
                        statutIframe = iframeChargé ? "OK" : "KO";

                        // Affichage du message avec le statut de l'iframe
                        System.out.println("Le statut de l'iframe pour " + sousRubrique + " est " + statutIframe);

                        // Si le statut de l'iframe est "KO", capture d'écran
                        if (statutIframe.equals("KO")) {
                            try {
                                // File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                                // FileUtils.copyFile(screenshotFile, new File("C:/Users/t.thabet/Desktop/Automation/SeleniumWebdriverJavaEspaceCollectivité/EspaceCollectivite/erreur_iframe.png"));

                                //Appel pour la fonction de capture ecran qui en dessous de code
                                captureEcran(driver, sousRubrique, NomProfilUtilisateur);
                            } catch (Exception e) {
                                System.out.println("Erreur lors de la capture d'écran de l'erreur d'iframe : " + e.getMessage());
                            }
                        }

                        // Revenir au contexte par défaut
                        driver.navigate().back();
                        Thread.sleep(5000);

                        // Après avoir interagi avec l'élément, vous pouvez revenir au contexte par défaut
                        driver.switchTo().defaultContent();

                        Thread.sleep(5000);

                        ///


                    }
                    else {
                        System.out.println("\u001B[31m" + sousRubrique + " ,  N'existe plus\u001B[0m");
                    }


                    // Ajout des données dans le fichier Excel
                    addDataToExcel(sheet, rowNum++, "", "", sousRubrique, statutElement, statutIframe);

                }
            }

        }

        // Enregistrer les données dans le fichier Excel après avoir parcouru toutes les catégories
        try (FileOutputStream fileOut = new FileOutputStream("Recette_" + NomProfilUtilisateur + ".xlsx")) {
            workbook.write(fileOut);
        }

        // Fermer le navigateur une fois tous les tests terminés
        driver.quit();
    }

    private static void addDataToExcel(Sheet sheet, int rowNum, String categorie, String rubrique, String sousRubrique, String statutElement, String statutIframe) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(categorie);
        row.createCell(1).setCellValue(rubrique);
        row.createCell(2).setCellValue(sousRubrique);
        row.createCell(3).setCellValue(statutElement);
        row.createCell(4).setCellValue(statutIframe);

    }

    private static void captureEcran(WebDriver driver, String sousRubrique, String NomProfilUtilisateur )  {
       /* try {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("C:/Users/t.thabet/Desktop/Automation/SeleniumWebdriverJavaEspaceCollectivité/EspaceCollectivite/CapturesError/" + sousRubrique + "_erreur_iframe.png"));
            System.out.println("Capture d'écran de l'erreur '"+ sousRubrique + "' d'iframe effectuée avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur lors de la capture d'écran de l'erreur d'iframe de '"+ sousRubrique + "' est: " + e.getMessage());
        } */
        try {
            // Capture de l'écran et sauvegarde dans un fichier avec le nom de la sous-rubrique
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("C:/Users/t.thabet/Desktop/Automation/SeleniumWebdriverJavaEspaceCollectivité/EspaceCollectivite/CapturesError/" + NomProfilUtilisateur + "_" + sousRubrique + "_erreur_iframe.png"));

            // Affichage du message de succès avec le nom du fichier créé
            //System.out.println("Capture d'écran de l'erreur '" + sousRubrique + "' d'iframe effectuée avec succès !");
        } catch (Exception e) {
            // Affichage du message d'erreur en cas d'échec avec le nom de la sous-rubrique
            System.out.println("Erreur lors de la capture d'écran de l'erreur d'iframe de '" + sousRubrique + "' : " + e.getMessage());
        }
    }
}

