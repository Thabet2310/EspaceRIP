package SAONE;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static java.lang.System.setProperty;

public class testSolution {

    public static boolean elementExiste(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.isDisplayed(); // Vérifiez si l'élément est visible
        } catch (NoSuchElementException | ElementNotInteractableException e) {
            return false;
        }
    }

    @Test
    public static void main(String[] args) throws InterruptedException {
        setProperty("webdriver.chrome.driver", "C:\\Users\\t.thabet\\chromedriver\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Ouvrir la page de connexion (remplacez l'URL par votre propre URL)
        String loginPageUrl = "https://pprod-espace-rip.orange-business.com ";
        driver.get(loginPageUrl);
        Thread.sleep(2000);

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

        // Définir les données directement dans le code
        List<Rubrique> rubriques = Arrays.asList(
                new Rubrique("Mes Essentiels", Arrays.asList(
                        new SousRubrique("Mon contrat Public", Arrays.asList(
                                "Mon contrat public signé",
                                "Mon acte de transfert",
                                "Ma lettre de stabilité actionnaire",
                                "Mon autorisation ARCEP",
                                "Mes documents statutaires",
                                "Mes attestations d'assurance",
                                "Mes avenants",
                                "Mes autres documents structurants",
                                "Mes courriers"
                        )),
                        new SousRubrique("Mes contrats tiers", Arrays.asList(
                                "Mes contrats sous-traitance Orange SA",
                                "Mes contrats sous-traitance Orange Concessions",
                                "Mes contrats fournisseur Orange Wholesale France",
                                "Mes contrats autres fournisseurs"
                        ))
                        // Ajoutez d'autres sous-rubriques au besoin
                ))
                // Ajoutez d'autres rubriques au besoin
        );

        // Itérer sur les rubriques, sous-rubriques et éléments
        for (Rubrique rubrique : rubriques) {
            navigateToRubrique(driver, rubrique.getNom());
            for (SousRubrique sousRubrique : rubrique.getSousRubriques()) {
                navigateThroughSousRubriques(driver, sousRubrique.getNom());
                for (String element : sousRubrique.getElements()) {
                    navigateThroughElements(driver, element);
                }
            }
        }

        // Fermer le navigateur
        driver.quit();
    }

    private static void navigateToRubrique(WebDriver driver, String rubrique) throws InterruptedException {
        String pathMenuTitle = "//button[contains(.,'" + rubrique + "')]";
        if (elementExiste(driver, By.xpath(pathMenuTitle))) {
            driver.findElement(By.xpath(pathMenuTitle)).click();
            Thread.sleep(2000);
            System.out.println("* " + rubrique);
        } else {
            System.out.println("Rubrique non trouvée : " + rubrique);
        }
    }

    private static void navigateThroughSousRubriques(WebDriver driver, String sousRubrique) throws InterruptedException {
        String pathSousRubrique = "//a[contains(.,'" + sousRubrique + "')]";
        boolean sousRubriqueExiste = elementExiste(driver, By.xpath(pathSousRubrique));
        if (sousRubriqueExiste) {
            driver.findElement(By.xpath(pathSousRubrique)).click();
            Thread.sleep(5000);
            System.out.println("** " + sousRubrique + " , sont :  ");
        } else {
            System.out.println("Sous-rubrique non trouvée : " + sousRubrique);
        }
    }

    private static void navigateThroughElements(WebDriver driver, String element) throws InterruptedException {
        String pathElement = "//a[contains(.,'" + element.trim() + "')]";
        boolean elementExiste = elementExiste(driver, By.xpath(pathElement));
        if (elementExiste) {
            System.out.println("- " + element.trim());
            // Vous pouvez ajouter des actions spécifiques pour chaque élément si nécessaire
        } else {
            System.out.println("Element non trouvé : " + element.trim());
        }
        // Revenir en arrière après le test de chaque élément
        driver.navigate().back();
        Thread.sleep(2000);
    }

    // Définir des classes pour structurer les données
    static class Rubrique {
        private final String nom;
        private final List<SousRubrique> sousRubriques;

        public Rubrique(String nom, List<SousRubrique> sousRubriques) {
            this.nom = nom;
            this.sousRubriques = sousRubriques;
        }

        public String getNom() {
            return nom;
        }

        public List<SousRubrique> getSousRubriques() {
            return sousRubriques;
        }
    }

    static class SousRubrique {
        private final String nom;
        private final List<String> elements;

        public SousRubrique(String nom, List<String> elements) {
            this.nom = nom;
            this.elements = elements;
        }

        public String getNom() {
            return nom;
        }

        public List<String> getElements() {
            return elements;
        }
    }
}
