package OrangeConcession;

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

public class TestOCC_COL1_ExcelSheet {

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
        Thread.sleep(4000);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("OCC_COL1");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Catégorie");
        headerRow.createCell(1).setCellValue("Rubrique");
        headerRow.createCell(2).setCellValue("Sous-rubrique");
        headerRow.createCell(3).setCellValue("Statut");

        int rowNum = 1;

        // *********** Mes essentiels ***********

        String C1 = " Mes essentiels";
        String R1C1 = "Mon contrat Public";
        String[] SR_R1C1 = {
                "Mon contrat public signé",
                "Mes attestations d'assurance",
                "Mes avenants",
                "Mes autres documents structurants",
                "Mes courriers"
        };

        addDataToExcel(sheet, rowNum++, C1, "", "", "");
        addDataToExcel(sheet, rowNum++, "", R1C1, "", "");


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
            String statut = elementExiste ? "OK" : "KO";

            addDataToExcel(sheet, rowNum++, "", "", element, statut);

            if (elementExiste) {
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back();
                Thread.sleep(2000);
            }
            else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }


        // ****** Mes contrats tiers ********

        String R2C1 = "Mes contrats tiers";
        String[] SR_R2C1 = {
                "Mes contrats sous-traitance Orange SA",
                "Mes contrats sous-traitance Orange Concessions",
                "Mes contrats fournisseur Orange Wholesale France",
                "Mes contrats autres fournisseurs"
        };

        addDataToExcel(sheet, rowNum++, "", R2C1, "", "");


        // Accéder à la rubrique Mes offres & contrats usagers
        driver.findElement(By.linkText(R2C1)).click();
        Thread.sleep(3000);

        System.out.println("\u001B[33m** " + R2C1 + " :\u001B[0m");

        for (String element : SR_R2C1) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));
            String statut = elementExiste ? "OK" : "KO";

            addDataToExcel(sheet, rowNum++, "", "", element, statut);

            if (elementExiste) {
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back();
                Thread.sleep(2000);
            }
            else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }

      //  ******* Mes offres et contarts usagers ******

        String R3C1 = "Mes offres & contrats usagers";
        String[] SR_R3C1 = {
                "Mes offres de référence",
                "Mes contrats usagers génériques",
                "Mes contrats usagers signés"
        };

        addDataToExcel(sheet, rowNum++, "", R3C1, "", "");


        // Accéder à la rubrique Mes offres & contrats usagers
        driver.findElement(By.linkText(R3C1)).click();
        Thread.sleep(3000);

        System.out.println("\u001B[33m** " + R3C1 + " :\u001B[0m");

        for (String element : SR_R3C1) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));
            String statut = elementExiste ? "OK" : "KO";

            addDataToExcel(sheet, rowNum++, "", "", element, statut);

            if (elementExiste) {
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back();
                Thread.sleep(2000);
            }
            else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }




        //                               *********** Mon réseau ***********

        String C2 = " Mon réseau";

        addDataToExcel(sheet, rowNum++, C2, "", "", "");

        //Acceder à la catégorie Mon réseau

        String pathMenuTitle1 = "//button[contains(.,'" + C2 + "')]";
        driver.findElement(By.xpath(pathMenuTitle1)).click();
        Thread.sleep(2000);

        System.out.println("\033[34m* " + C2 + "\033[0m");


        // *********** Mes documents de référence *****************


          String R1C2 = "Mes documents de référence";
           String[] SR_R1C2 = {
                "Mes processus",
                "Mes matériels",
                "Mes référentiels"
        };

        addDataToExcel(sheet, rowNum++, "", R1C2, "", "");

        driver.findElement(By.linkText(R1C2)).click();
        Thread.sleep(3000);

        System.out.println("\u001B[33m** " + R1C2 + " :\u001B[0m");

        for (String element : SR_R1C2) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));
            String statut = elementExiste ? "OK" : "KO";

            addDataToExcel(sheet, rowNum++, "", "", element, statut);

            if (elementExiste) {
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back();
                Thread.sleep(2000);
            }
            else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }




        // *********** Ma sécurité chantiers *****************

        String R2C2 = "Ma sécurité chantiers";
        String[] SR_R2C2 = {
                "Mes CSPS",
                "Mes PGC",
                "Mes PPR"
        };

        addDataToExcel(sheet, rowNum++, "", R2C2, "", "");

        driver.findElement(By.linkText(R2C2)).click();
        Thread.sleep(3000);

        System.out.println("\u001B[33m** " + R2C2 + " :\u001B[0m");

        for (String element : SR_R2C2) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));
            String statut = elementExiste ? "OK" : "KO";

            addDataToExcel(sheet, rowNum++, "", "", element, statut);

            if (elementExiste) {
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back();
                Thread.sleep(2000);
            }
            else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }

        // ************** Mon déploiment ***************

        String R3C2 = "Mes déploiements";
        String[] SR_R3C2 = {
                "Mes études",
                "Mes retours",
                "Mes PV réception"
        };

        addDataToExcel(sheet, rowNum++, "", R3C2, "", "");

        driver.findElement(By.linkText(R3C2)).click();
        Thread.sleep(3000);

        System.out.println("\u001B[33m** " + R3C2 + " :\u001B[0m");

        for (String element : SR_R3C2) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));
            String statut = elementExiste ? "OK" : "KO";

            addDataToExcel(sheet, rowNum++, "", "", element, statut);

            if (elementExiste) {
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back();
                Thread.sleep(2000);
            }
            else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }


        // ************** Ma vie de réseau ***************

        String R4C2 = "Ma vie de réseau";
        String[] SR_R4C2 = {
                "Mes DOE",
                "Mes CRI OI",
                "Actifs - suivi de trafic",
                "Exploitation et maintenance",
                "Récapitulatif Câblage Client final"
        };

        addDataToExcel(sheet, rowNum++, "", R4C2, "", "");

        driver.findElement(By.linkText(R4C2)).click();
        Thread.sleep(3000);

        System.out.println("\u001B[33m** " + R4C2 + " :\u001B[0m");

        for (String element : SR_R4C2) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));
            String statut = elementExiste ? "OK" : "KO";

            addDataToExcel(sheet, rowNum++, "", "", element, statut);

            if (elementExiste) {
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back();
                Thread.sleep(2000);
            }
            else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }



        //                               *********** Mon pilotage ***********



        String C3 = " Mon pilotage";

        String R1C3 = "Mes factures";
        String[] SR_R1C3 = {
                "Mes demandes de subvention",
                "Mes redevances et contributions",
                "Mes certifications de facture",
                "Mes factures usagers",
                "Mes éléments de facturation usagers",
                "Factures Orange concessions"
        };

        addDataToExcel(sheet, rowNum++, C3, "", "", "");
        addDataToExcel(sheet, rowNum++, "", R1C3, "", "");


        //Acceder à la catégorie Mon réseau

        String pathMenuTitle2 = "//button[contains(.,'" + C3 + "')]";
        driver.findElement(By.xpath(pathMenuTitle2)).click();
        Thread.sleep(2000);

        System.out.println("\033[34m* " + C3 + "\033[0m");


        // ******************** Mes factures  *************

        driver.findElement(By.linkText(R1C3)).click();
        Thread.sleep(3000);

        System.out.println("\u001B[33m** " + R1C3 + " :\u001B[0m");

        for (String element : SR_R1C3) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));
            String statut = elementExiste ? "OK" : "KO";

            addDataToExcel(sheet, rowNum++, "", "", element, statut);

            if (elementExiste) {
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back();
                Thread.sleep(2000);
            }
            else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }



        //      ********************* Mon partenaire industriel  ************************


        String R2C3 = "Mon partenaire industriel";
        String[] SR_R2C3 = {
                "Mon contrat industriel",
               " Mes fiches label",
                "Mon contrat services informatiques",
                "Mes performances applicatives",
                "Mes archives Datavision",
                "Mon contrat services soutien comptable et juridique"
        };

        addDataToExcel(sheet, rowNum++, "", R2C3, "", "");

        driver.findElement(By.linkText(R2C3)).click();
        Thread.sleep(3000);

        System.out.println("\u001B[33m** " + R2C3 + " :\u001B[0m");

        for (String element : SR_R2C3) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));
            String statut = elementExiste ? "OK" : "KO";

            addDataToExcel(sheet, rowNum++, "", "", element, statut);

            if (elementExiste) {
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back();
                Thread.sleep(2000);
            }
            else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }


             //      ********************* Mon reporting  ************************

        String R3C3 = "Mon reporting";
        String[] SR_R3C3 = {
                "Mes KPI ARCEP",
                "Mes intentions de déploiement",
                "Mes tableaux de bord",
                "Mes rapports d'activité",
                "Mes rapports CAC et comptes sociaux",
        };

        addDataToExcel(sheet, rowNum++, "", R3C3, "", "");

        driver.findElement(By.linkText(R3C3)).click();
        Thread.sleep(3000);

        System.out.println("\u001B[33m** " + R3C3 + " :\u001B[0m");

        for (String element : SR_R3C3) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));
            String statut = elementExiste ? "OK" : "KO";

            addDataToExcel(sheet, rowNum++, "", "", element, statut);

            if (elementExiste) {
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back();
                Thread.sleep(2000);
            }
            else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream("Recette_OCC_COL1.xlsx")) {
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
