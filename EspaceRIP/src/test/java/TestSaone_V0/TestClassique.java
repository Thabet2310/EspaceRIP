package TestSaone_V0;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static java.lang.System.setProperty;

public class TestClassique {
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
        System.out.println("* " + C1);

        // Mon contrat Public

        String R1C1 = "Mon contrat Public";

        // Accéder à la rubrique Mon contrat Public
        driver.findElement(By.linkText(R1C1)).click();
        Thread.sleep(3000);

        //   https://portail-collectivite-preprod.sso.francetelecom.fr/user/4/edit?destination=/admin/people%3Fuser%3D%26field_rip_target_id%3D%26status%3DAll%26role%3DAll%26permission%3DAll%26order%3Daccess%26sort%3Ddesc                        // Liste des éléments //"Mon acte de transfert", "Ma lettre de stabilité actionnaire", "Mon autorisation ARCEP", "Mes documents statutaires", "Mes attestations d'assurance" , "Mes avenants", "Mes autres documents structurants"

        //Accéder aux sous rubriques de la rubrique Mes essentiels

        String[] SR_R1C1 = {
                "Mon contrat public signé",
                "Mon acte de transfert",
                "Ma lettre de stabilité actionnaire",
                "Mon autorisation ARCEP",
                "Mes documents statutaires",
                "Mes attestations d'assurance",
                "Mes avenants",
                "Mes autres documents structurants",
                "Mes courriers",
        };

        System.out.println("** " + R1C1 + " , sont :  ");
        for (String element : SR_R1C1) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("- " + element);

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31mL'élément : " + element + " , est KO\u001B[0m");
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
                "Mes contrats sous-traitance Orange Concessions",
                "Mes contrats fournisseur Orange Wholesale France",
                "Mes contrats autres fournisseurs"
        };

        System.out.println("** " + R2C1 + " : ");
        for (String element : SR_R2C1) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("- " + element);

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31mL'élément : " + element + " , est KO\u001B[0m");
            }
        }


        // Mes offres & contrats usagers

        String R3C1 = "Mes offres & contrats usagers";

        // Accéder à la rubrique Mes offres & contrats usagers
        driver.findElement(By.linkText(R3C1)).click();
        Thread.sleep(3000);


        //Accéder aux sous rubriques de la rubrique Mes offres & contrats usagers

        String[] SR_R3C1 = {
                "Mes offres de référence",
                "Mes contrats usagers génériques",
                "Mes contrats usagers signés"
        };

        System.out.println("** " + R3C1 + " , sont :  ");
        for (String element : SR_R3C1) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("- " + element);

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31mL'élément : " + element + " , est KO\u001B[0m");
            }
        }


        // Ma communication

        String R4C1 = "Ma communication";

        // Accéder à la sous-rubrique Ma communication
        driver.findElement(By.linkText(R4C1)).click();
        Thread.sleep(3000);

        //Accéder aux sous rubriques de la rubrique Ma communication

        String[] SR_R4C1 = {
                "Mes illustrations",
                "Mes logos",
                "Mes maquettes d'outils de vente",
                "Mes textes et présentations",
                "Ma charte",
                "Mes réunions publiques",
                "Ma communication éligibilité"
        };

        System.out.println("** " + R4C1 + " , sont :  ");
        for (String element : SR_R4C1) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("- " + element);

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31mL'élément : " + element + " , est KO\u001B[0m");
            }
        }

        /*
        // Revenir en arrière pour retourner à la liste des sous-rubriques
        driver.navigate().back();
        Thread.sleep(4000);
        */


        // ****************************************   Mon réseau  *************************************************


        String C2 = " Mon réseau";

        String pathMenuTitle1 = "//button[contains(.,'" + C2 + "')]";
        driver.findElement(By.xpath(pathMenuTitle1)).click();
        Thread.sleep(2000);
        System.out.println("* " + C2);



/* N'existe pas en PPROD

        // Mes documents de référence

        String R4C2 = "Mes documents de référence";

        // Accéder à la rubrique Mes documents de référence
        driver.findElement(By.linkText(R4C2)).click();
        Thread.sleep(3000);


        //Accéder aux sous rubriques de la rubrique Mes documents de référence

        String[] SR_R4C2 = {
                "Mes processus",
                "Mes matériels",
                "Mes référentiels"
        };

        System.out.println("** " + R4C2 + " : ");
        for (String element : SR_R4C2) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("- " + element);

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31mL'élément : " + element + " , est KO\u001B[0m");
            }
        }

*/

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

        System.out.println("** " + R2C2 + " : ");
        for (String element : SR_R2C2) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("- " + element);

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31mL'élément : " + element + " , est KO\u001B[0m");
            }
        }



        // Mon déploiement

        String R1C2 = "Mon déploiement";

        // Accéder à la rubrique Mon déploiement

        driver.findElement(By.linkText(R1C2)).click();
        Thread.sleep(3000);


        //Accéder aux sous rubriques de la rubrique Mon déploiement

        String[] SR_R1C2 = {
                "Mes études",
                "Mes retours"
        };

        System.out.println("** " + R1C2 + " , sont :  ");
        for (String element : SR_R1C2) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("- " + element);

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31mL'élément : " + element + " , est KO\u001B[0m");
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
                "Mes Compte-Rendus d'Opérations",
                "Mes CRI",
                "Mes signalisations réseaux",
                "Mon exploitation & maintenance",
                "Récapitulatif Câblage Client final"
        };

        System.out.println("** " + R3C2 + " , sont :  ");
        for (String element : SR_R3C2) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("- " + element);

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31mL'élément : " + element + " , est KO\u001B[0m");
            }
        }

        // Revenir en arrière pour retourner à la liste des sous-rubriques
       // driver.navigate().back();
        //Thread.sleep(4000);




        // ****************************************   Mon Piloatge *************************************************


        String C3 = " Mon pilotage";

        String pathMenuTitle2 = "//button[contains(.,'" + C3 + "')]";
        driver.findElement(By.xpath(pathMenuTitle2)).click();
        Thread.sleep(2000);
        System.out.println("* " + C3);


        // Mes factures

        String R1C3 = "Mes factures";

        // Accéder à la rubrique Mes factures
        driver.findElement(By.linkText(R1C3)).click();
        Thread.sleep(3000);

        //Accéder aux sous rubriques de la rubrique Mes factures

        String[] SR_R1C3 = {
                "Mes demandes de subvention",
                "Mes redevances et contributions",
                "Mes factures usagers"
        };

        System.out.println("** " + R1C3 + " , sont :  ");
        for (String element : SR_R1C3) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("- " + element);

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31mL'élément : " + element + " , est KO\u001B[0m");
            }
        }



        // Mes réunions

        String R2C3 = "Mes réunions";

        // Accéder à la sous-rubrique Mes réunions

        driver.findElement(By.linkText(R2C3)).click();
        Thread.sleep(3000);

        //Accéder aux sous rubriques de la rubrique Mes réunions

        String[] SR_R2C3 = {
                "Mes comités hebdo",
                "Mes Comités Techniques",
                "Mes comités de suivi"
        };

        System.out.println("** " + R2C3 + " : ");
        for (String element : SR_R2C3) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("- " + element);

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31mL'élément : " + element + " , est KO\u001B[0m");
            }
        }


        // Mon reporting

        String R3C3 = "Mon reporting";

        // Accéder à la rubrique Mon reporting

        driver.findElement(By.linkText(R3C3)).click();
        Thread.sleep(3000);

        //Accéder aux sous rubriques de la rubrique Mon reporting

        String[] SR_R3C3 = {

                "Mes KPI ARCEP",
                "Mes intentions de déploiement",
                "Mes tableaux de bord",
                "Mon insertion professionnelle",
                "Mes rapports d'activité",
                "Mes procès verbaux",
                "Mes rapports CAC et comptes sociaux",
                "Mes IPE & CPN"
        };

        System.out.println("** " + R3C3 + " : ");
        for (String element : SR_R3C3) {
            boolean elementExiste = elementExiste(driver, By.linkText(element));

            if (elementExiste) {
                // Cliquer sur l'élément si il existe
                driver.findElement(By.linkText(element)).click();
                Thread.sleep(5000);

                System.out.println("- " + element);

                driver.navigate().back(); // Retour après avoir traité un élément
                Thread.sleep(2000);
            } else {
                // Ne rien faire si l'élément n'existe pas
                System.out.println("\u001B[31mL'élément : " + element + " , est KO\u001B[0m");
            }
        }


        // Revenir en arrière pour retourner à la liste des sous-rubriques
        driver.navigate().back();
        Thread.sleep(4000);


        // ****************************************** Déconnexion ******************************************

        driver.findElement(By.xpath(" //*[@id=\"aside\"]/div[2]/a")).click();


    }

}
