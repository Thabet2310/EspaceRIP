package TestSaone_V0;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static java.lang.System.setProperty;

public class TestOSA1_SAONE {
    @Test
    public static boolean elementExiste(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.isDisplayed(); // Vérifiez si l'élément est visible
        } catch (NoSuchElementException | ElementNotInteractableException e) {
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        // Spécifier le chemin vers le driver Chrome (chromedriver)
        setProperty("webdriver.chrome.driver", "C:\\Users\\t.thabet\\chromedriver\\chromedriver\\chromedriver.exe");

        // Initialiser le navigateur Chrome
        WebDriver driver = new ChromeDriver();

        // Ouvrir la page de connexion (remplacez l'URL par votre propre URL)
        String loginPageUrl = "https://pprod-espace-rip.orange-business.com ";
        driver.get(loginPageUrl);
        Thread.sleep(2000);

        // Maximize the browser window
        driver.manage().window().maximize();


        //  ************************ Vérifier si la page intermédiaire s'affiche ***********************************

        boolean pageIntermediairePresente = elementExiste(driver, By.id("details-button"));

        if (pageIntermediairePresente) {
            // Cliquer sur un élément pour continuer vers la page d'authentification
            driver.findElement(By.id("details-button")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("proceed-link")).click();
            Thread.sleep(2000);
        }


        // ****************************************   Authentification *************************************************

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


        // ****************************************   Mes essentiels  *************************************************

        String C1 = " Mes essentiels";

        String pathMenuTitle = "//button[contains(.,'" + C1 + "')]";
        driver.findElement(By.xpath(pathMenuTitle)).click();
        Thread.sleep(2000);
        System.out.println("\033[34m* " + C1 + "\033[0m");

        // Mon contrat Public

        String R1C1 = "Mon contrat Public";

        // Accéder à la rubrique Mon contrat Public
        driver.findElement(By.linkText(R1C1)).click();
        Thread.sleep(3000);

        //   https://portail-collectivite-preprod.sso.francetelecom.fr/user/4/edit?destination=/admin/people%3Fuser%3D%26field_rip_target_id%3D%26status%3DAll%26role%3DAll%26permission%3DAll%26order%3Daccess%26sort%3Ddesc                        // Liste des éléments //"Mon acte de transfert", "Ma lettre de stabilité actionnaire", "Mon autorisation ARCEP", "Mes documents statutaires", "Mes attestations d'assurance" , "Mes avenants", "Mes autres documents structurants"

        //Accéder aux sous rubriques de la rubrique Mes essentiels

        String[] SR_R1C1 = {
                "Mon contrat public signé",
                "Mes avenants"
        };

        System.out.println("\u001B[33m** " + R1C1 + " :\u001B[0m");
        for (String element : SR_R1C1) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }

// Mes contrats tiers

        String R2C1 = "Mes contrats tiers";

        // Accéder à la rubrique Mes contrats tiers

        driver.findElement(By.linkText(R2C1)).click();
        Thread.sleep(3000);


        //Accéder aux sous rubriques de la rubrique Mes contrats tiers

        String[] SR_R2C1 = {
                "Mes contrats sous-traitance Orange SA",
        };

        System.out.println("\u001B[33m** " + R2C1 + " :\u001B[0m");
        for (String element : SR_R2C1) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }



        // ****************************************   Mon réseau  *************************************************


        String C2 = " Mon réseau";

        String pathMenuTitle1 = "//button[contains(.,'" + C2 + "')]";
        driver.findElement(By.xpath(pathMenuTitle1)).click();
        Thread.sleep(2000);
        System.out.println("\033[34m* " + C2 + "\033[0m");



        // Ma sécurité chantiers

        String R2C2 = "Ma sécurité chantiers";

        // Accéder à la rubrique Ma sécurité chantiers
        driver.findElement(By.linkText(R2C2)).click();
        Thread.sleep(3000);


        //Accéder aux sous rubriques de la rubrique Ma sécurité chantiers

        String[] SR_R2C2 = {
                "Mes CSPS",
                "Mes PGC",
                "Mes PPR"
        };

        System.out.println("\u001B[33m** " + R2C2 + " :\u001B[0m");
        for (String element : SR_R2C2) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }



        // Ma vie de réseau

        String R3C2 = "Ma vie de réseau";

        // Entrer dans la sous-rubrique Ma vie de réseau

        driver.findElement(By.linkText(R3C2)).click();
        Thread.sleep(3000);


        //Accéder aux sous rubriques de la rubrique Ma vie de réseau

        String[] SR_R3C2 = {
                "Mes DOE",
        };

        System.out.println("\u001B[33m** " + R3C2 + " :\u001B[0m");
        for (String element : SR_R3C2) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }



        // ****************************************   Mon Piloatge *************************************************


        String C3 = " Mon pilotage";

        String pathMenuTitle2 = "//button[contains(.,'" + C3 + "')]";
        driver.findElement(By.xpath(pathMenuTitle2)).click();
        Thread.sleep(2000);
        System.out.println("\033[34m* " + C3 + "\033[0m");



        // Mes réunions

        String R2C3 = "Mes réunions";

        // Accéder à la sous-rubrique Mes réunions

        driver.findElement(By.linkText(R2C3)).click();
        Thread.sleep(3000);

        //Accéder aux sous rubriques de la rubrique Mes réunions

        String[] SR_R2C3 = {
                "Mes Comités Techniques"
        };

        System.out.println("\u001B[33m** " + R2C3 + " :\u001B[0m");
        for (String element : SR_R2C3) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }


        // Mon reporting

        String R3C3 = "Mon reporting";

        // Accéder à la rubrique Mon reporting

        driver.findElement(By.linkText(R3C3)).click();
        Thread.sleep(3000);

        //Accéder aux sous rubriques de la rubrique Mon reporting

        String[] SR_R3C3 = {
                "Mes intentions de déploiement",
                "Mes IPE & CPN"
        };

        System.out.println("\u001B[33m** " + R3C3 + " :\u001B[0m");
        for (String element : SR_R3C3) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("\033[32m" + element + " OK\033[0m");

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31m" + element + " ,  KO\u001B[0m");
            }
        }


        // Revenir en arrière pour retourner à la liste des sous-rubriques
        driver.navigate().back();
        Thread.sleep(4000);


        // ****************************************** Déconnexion ******************************************

        driver.findElement(By.cssSelector("body > div > div > main > aside > div.profil-bloc > a")).click();


    }

}
