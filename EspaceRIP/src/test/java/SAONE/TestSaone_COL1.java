package SAONE;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static java.lang.System.setProperty;

public class TestSaone_COL1 {
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
        setProperty("webdriver.chrome.driver", "C:\\Users\\t.thabet\\chromedriver\\chromedriver\\chromedriver.exe");        // Initialiser le navigateur Chrome
        WebDriver driver = new ChromeDriver();

        // Ouvrir la page de connexion (remplacez l'URL par votre propre URL)
        String loginPageUrl = "https://pprod-espace-rip.orange-business.com ";
        driver.get(loginPageUrl);
        Thread.sleep(2000);

        // Maximize the browser window
        driver.manage().window().maximize();

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

        // Liste des rubriques
        //Mes essentiel
        String[] rubriques = {" Mes essentiels", "Mon réseau", "Mon pilotage" };

        for (String rubrique : rubriques) {
            // Entrer dans la rubrique
            String pathMenuTitle = "//button[contains(.,'" + rubrique + "')]";
            if (elementExiste(driver, By.xpath(pathMenuTitle))) {
                driver.findElement(By.xpath(pathMenuTitle)).click();
                Thread.sleep(2000);
                System.out.println("* " + rubrique);

                // Liste des sous-rubriques "Mon contrat Public",
                String[] sousRubriques = {"Mon contrat Public","Mes offres & contrats usagers",
                        "Ma sécurité chantiers", "Ma vie de réseau",
                        "Mes factures", "Mes réunions", "Mon reporting" }; //,"Ma sécurité chantiers", "Mes factures"

                for (String sousRubrique : sousRubriques) {
                    // Entrer dans la sous-rubrique
                    boolean sousRubriqueExiste = elementExiste(driver, (By.linkText(sousRubrique)));
                    if (sousRubriqueExiste) {
                        driver.findElement(By.linkText(sousRubrique)).click();
                        Thread.sleep(5000);

                        //   https://portail-collectivite-preprod.sso.francetelecom.fr/user/4/edit?destination=/admin/people%3Fuser%3D%26field_rip_target_id%3D%26status%3DAll%26role%3DAll%26permission%3DAll%26order%3Daccess%26sort%3Ddesc                        // Liste des éléments //"Mon acte de transfert", "Ma lettre de stabilité actionnaire", "Mon autorisation ARCEP", "Mes documents statutaires", "Mes attestations d'assurance" , "Mes avenants", "Mes autres documents structurants"
                        String[] elements = {"Mon acte de transfert", "Ma lettre de stabilité actionnaire", "Mon autorisation ARCEP", "Mes documents statutaires", "Mes attestations d'assurance" , "Mes avenants", "Mes autres documents structurants","Mon contrat public signé", "Mes courriers",
                                "Mes offres de référence", "Mes contrats usagers génériques", "Mes contrats usagers signés",
                                "Mes CSPS", "Mes PGC", "Mes PPR",
                                "Mes signalisations réseaux",
                                "Mes redevances et contributions",
                                "Mes comités hebdo", "Mes Comités Techniques", "Mes comités de suivi",
                                "Mes intentions de déploiement", "Mes rapports CAC et comptes sociaux" ,"Mes IPE & CPN"};
                        System.out.println("** " + sousRubrique+ " , sont :  ");
                        for (String element : elements) {
                            boolean elementExiste = elementExiste(driver, By.linkText(element));

                            if (elementExiste) {
                                // Cliquer sur l'élément si il existe
                                driver.findElement(By.linkText(element)).click();
                                Thread.sleep(5000);

                                System.out.println("- " + element );

                                driver.navigate().back(); // Retour après avoir traité un élément
                                Thread.sleep(2000);
                            } else {
                                // Ne rien faire si l'élément n'existe pas
                                //System.out.println("L element : " + element + " , est KO  " );
                            }
                        }

                        // Revenir en arrière pour retourner à la liste des sous-rubriques
                        driver.navigate().back();
                        Thread.sleep(4000);

                    } else {
                        //Ne rien faire si la sous rubrique n'existe pas
                    }
                }
            } else {
                System.out.println("Rubrique non trouvée : " + rubrique);
            }
        }

    }

}
