package SAONE;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

import static java.lang.System.setProperty;

public class Chatgptaide {

    static boolean elementExists(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.isDisplayed(); // Vérifiez si l'élément est visible
        } catch (NoSuchElementException | ElementNotInteractableException e) {
            return false;
        }
    }

    static void clickAndWait(WebDriver driver, By by) throws InterruptedException {
        WebElement element = driver.findElement(by);
        element.click();

        // Utiliser Thread.sleep pour attendre 5 secondes après le clic
        Thread.sleep(5000);

        // Attente explicite pour s'assurer que l'élément suivant est visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body"))); // Changez le sélecteur en fonction de votre cas

        // Revenir en arrière pour retourner à la liste des sous-rubriques
        driver.navigate().back();
    }

    @Test
    public static void main(String[] args) throws InterruptedException {
        setProperty("webdriver.chrome.driver", "C:\\Users\\t.thabet\\chromedriver\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        String loginPageUrl = "https://pprod-espace-rip.orange-business.com";
        driver.get(loginPageUrl);

        boolean pageIntermediairePresente = elementExists(driver, By.id("details-button"));

        if (pageIntermediairePresente) {
            clickAndWait(driver, By.id("details-button"));
            clickAndWait(driver, By.id("proceed-link"));
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


        String[] rubriques = {" Mes essentiels", "Mon réseau", "Mon pilotage"};

        for (String rubrique : rubriques) {
            navigateToRubrique(driver, rubrique);
        }

        driver.quit();
    }

    private static void navigateToRubrique(WebDriver driver, String rubrique) throws InterruptedException {
        String pathMenuTitle = "//button[contains(.,'" + rubrique + "')]";
        if (elementExists(driver, By.xpath(pathMenuTitle))) {
            clickAndWait(driver, By.xpath(pathMenuTitle));
            System.out.println("* " + rubrique);
            navigateThroughSousRubriques(driver);
        } else {
            //System.out.println("Rubrique non trouvée : " + rubrique);
        }
    }

    private static void navigateThroughSousRubriques(WebDriver driver) throws InterruptedException {
        String[] sousRubriques = {"Mon contrat Public", "Mes contrats tiers", "Ma communication", "Mes offres & contrats usagers", "Mes documents de référence", "Ma sécurité chantiers", "Mon déploiement", "Ma vie de réseau", "Mes factures", "Mes réunions", "Mon reporting"};

        for (String sousRubrique : sousRubriques) {
            boolean sousRubriqueExists = elementExists(driver, By.linkText(sousRubrique));
            if (sousRubriqueExists) {
                clickAndWait(driver, By.linkText(sousRubrique));
                System.out.println("** " + sousRubrique + " , sont :  ");
                navigateThroughElements(driver);
            } else {
                // Ne rien faire si la sous rubrique n'existe pas
            }
        }
    }

    private static void navigateThroughElements(WebDriver driver) throws InterruptedException {
        String[] elements = {"Mon contrat public signé", "Mon acte de transfert", "Ma lettre de stabilité actionnaire", "Mon autorisation ARCEP", "Mes documents statutaires", "Mes attestations d'assurance" , "Mes avenants", "Mes autres documents structurants", "Mes courriers", "Mes contrats sous-traitance Orange SA", "Mes contrats sous-traitance Orange Concessions", "Mes contrats fournisseur Orange Wholesale France", "Mes contrats autres fournisseurs", "Mes illustrations", "Mes logos", "Mes maquettes d'outils de vente", "Mes textes et présentations", "Ma charte", "Mes réunions publiques", "Ma communication éligibilité", "Mes offres de référence", "Mes contrats usagers génériques", "Mes contrats usagers signés", "Mes processus", "Mes matériels", "Mes référentiels", "Mes CSPS", "Mes PGC", "Mes PPR", "Mes études", "Mes retours", "Mes DOE", "Mes Compte-Rendus d'Opérations", "Mes CRI", "Mes signalisations réseaux", "Mon exploitation & maintenance", "Récapitulatif Câblage Client final", "Mes demandes de subvention", "Mes redevances et contributions", "Mes factures usagers", "Mes comités hebdo", "Mes Comités Techniques", "Mes comités de suivi", "Mes KPI ARCEP", "Mes intentions de déploiement", "Mes tableaux de bord", "Mon insertion professionnelle", "Mes rapports d'activité", "Mes procès verbaux", "Mes rapports CAC et comptes sociaux" ,"Mes IPE & CPN"};

        for (String element : elements) {
            boolean elementExists = elementExists(driver, By.linkText(element));

            if (elementExists) {
                clickAndWait(driver, By.linkText(element));
                System.out.println("- " + element);
            } else {
                // Ne rien faire si l'élément n'existe pas
            }
        }
    }
}
