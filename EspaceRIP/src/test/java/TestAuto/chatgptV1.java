package TestAuto;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileNotFoundException;

import static java.lang.System.setProperty;

public class chatgptV1 {

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
        setProperty("webdriver.chrome.driver", "C:\\Users\\t.thabet\\Desktop\\chromedriverwin64\\chromedriver.exe");        // Initialiser le navigateur Chrome
        WebDriver driver = new ChromeDriver();

        // Ouvrir la page de connexion (remplacez l'URL par votre propre URL)
        String loginPageUrl = "https://qualif-espace-rip.orange-business.com/";
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
        String username = "ripripdemo";
        String password = "Demo2022#";
        Thread.sleep(10000);

        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys(username);
        driver.findElement(By.id("submit-button")).click();
        Thread.sleep(2000);
        WebElement passwordInput = driver.findElement(By.id("currentPassword"));
        passwordInput.sendKeys(password);


        // Cliquer sur le bouton de connexion (remplacez le sélecteur par celui de votre bouton de connexion)
        WebElement loginButton = driver.findElement(By.id("submit-button"));
        loginButton.click();
        Thread.sleep(7000);

        // Liste des rubriques
        String[] rubriques = {" Mes essentiels", " Mon réseau", " Mon pilotage"};

        for (String rubrique : rubriques) {
            // Entrer dans la rubrique
            String pathMenuTitle = "//button[contains(.,'" + rubrique + "')]";
            if (elementExiste(driver, By.xpath(pathMenuTitle))) {
                driver.findElement(By.xpath(pathMenuTitle)).click();
                Thread.sleep(2000);


                // Liste des sous-rubriques
                String[] sousRubriques = {"Mon contrat Public", "Ma sécurité chantiers", "Mes factures"};

                for (String sousRubrique : sousRubriques) {
                    // Entrer dans la sous-rubrique
                    boolean sousRubriqueExiste = elementExiste(driver, (By.linkText(sousRubrique)));
                    if (sousRubriqueExiste) {
                        driver.findElement(By.linkText(sousRubrique)).click();
                        Thread.sleep(5000);


                        // Liste des éléments
                        String[] elements = {"Mon contrat public signé", "Mes DC4", "Mes CSPS", "Mes demandes de subvention"};

                        for (String element : elements) {
                            boolean elementExiste = elementExiste(driver, By.linkText(element));

                            if (elementExiste) {
                                // Cliquer sur l'élément si il existe
                                driver.findElement(By.linkText(element)).click();
                                Thread.sleep(5000);
                                driver.navigate().back(); // Retour après avoir traité un élément
                                Thread.sleep(2000);
                            } else {
                                // Ne rien faire si l'élément n'existe pas
                                System.out.println("Element non trouvée : " + element);

                            }
                        }

                        // Revenir en arrière pour retourner à la liste des sous-rubriques
                        driver.navigate().back();
                       Thread.sleep(5000);

                } else{
                    // Revenir en arrière pour retourner à la liste des rubriques
                    driver.navigate().back();
                    Thread.sleep(5000);
                        System.out.println("sousRubrique non trouvée : " + sousRubrique);

                    }
            } }
            else {
                System.out.println("Rubrique non trouvée : " + rubrique);
            } }

            // Continuer avec le reste du test...
        }
/*
        public static boolean elementExiste (WebDriver driver, By by){
            try {
                driver.findElement(by);
                return true;
            } catch (NoSuchElementException e) {
                return false;
            }
        }
        public static boolean IntermedExiste (WebDriver driver, By by){
            try {
                driver.findElement(by);
                return true;
            } catch (NoSuchElementException e) {
                return false;
            }
        }*/
    }
