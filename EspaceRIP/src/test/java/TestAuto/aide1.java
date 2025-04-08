
/*
for (String sousRubrique : sousRubriques) {
        // Entrer dans la sous-rubrique
        boolean sousRubriqueExiste = elementExiste(driver, By.linkText(sousRubrique));
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
        driver.navigate().back();
        } else {
        // Ne rien faire si l'élément n'existe pas
        }
        }

        // Revenir en arrière pour retourner à la liste des sous-rubriques
        driver.navigate().back();
        Thread.sleep(5000);
        } else {
        // Revenir en arrière pour retourner à la liste des rubriques
        driver.navigate().back();
        Thread.sleep(5000);
        }
        }

*/

/*
for (String rubrique : rubriques) {
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
        } else {
        // Revenir en arrière pour retourner à la liste des rubriques
        driver.navigate().back();
        Thread.sleep(5000);
        System.out.println("sousRubrique non trouvée : " + sousRubrique);
        }
        }
        } else {
        System.out.println("Rubrique non trouvée : " + rubrique);
        }
        }
*/