package SAONE;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static java.lang.System.setProperty;

public class TestSaone_Script0 {
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
        String[] rubriques = {" Mes essentiels" };

        for (String rubrique : rubriques) {
            // Entrer dans la rubrique
            String pathMenuTitle = "//button[contains(.,'" + rubrique + "')]";
            if (elementExiste(driver, By.xpath(pathMenuTitle))) {
                driver.findElement(By.xpath(pathMenuTitle)).click();
                Thread.sleep(2000);
                System.out.println("* " + rubrique);

                // Liste des sous-rubriques "Mon contrat Public",
                String[] sousRubriques = {"Mon contrat Public", "Mes contrats tiers", "Ma communication", "Mes offres & contrats usagers", "Mes documents de référence", "Ma sécurité chantiers", "Mon déploiement", "Ma vie de réseau", "Mes factures", "Mes réunions", "Mon reporting" }; //,"Ma sécurité chantiers", "Mes factures"

                for (String sousRubrique : sousRubriques) {
                    // Entrer dans la sous-rubrique
                    boolean sousRubriqueExiste = elementExiste(driver, (By.linkText(sousRubrique)));
                    if (sousRubriqueExiste) {
                        driver.findElement(By.linkText(sousRubrique)).click();
                        Thread.sleep(5000);


                        // Liste des éléments
                        String[] elements = {"Mon contrat public signé", "Mon acte de transfert", "Ma lettre de stabilité actionnaire", "Mon autorisation ARCEP", "Mes documents statutaires", "Mes attestations d'assurance" , "Mes avenants", "Mes autres documents structurants", "Mes courriers", "Mes contrats sous-traitance Orange SA", "Mes contrats sous-traitance Orange Concessions", "Mes contrats fournisseur Orange Wholesale France", "Mes contrats autres fournisseurs", "Mes illustrations", "Mes logos", "Mes maquettes d'outils de vente", "Mes textes et présentations", "Ma charte", "Mes réunions publiques", "Ma communication éligibilité", "Mes offres de référence", "Mes contrats usagers génériques", "Mes contrats usagers signés", "Mes processus", "Mes matériels", "Mes référentiels", "Mes CSPS", "Mes PGC", "Mes PPR", "Mes études", "Mes retours", "Mes DOE", "Mes Compte-Rendus d'Opérations", "Mes CRI", "Mes signalisations réseaux", "Mon exploitation & maintenance", "Récapitulatif Câblage Client final", "Mes demandes de subvention", "Mes redevances et contributions", "Mes factures usagers", "Mes comités hebdo", "Mes Comités Techniques", "Mes comités de suivi", "Mes KPI ARCEP", "Mes intentions de déploiement", "Mes tableaux de bord", "Mon insertion professionnelle", "Mes rapports d'activité", "Mes procès verbaux", "Mes rapports CAC et comptes sociaux" ,"Mes IPE & CPN"};
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
                        Thread.sleep(2000);

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
