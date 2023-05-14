import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.util.Scanner;
import exceptions.*;
import payables.*;
import inventaire.GestionnaireInventaire;
import gui.GUIGestionnaireInventaire;

public class Main {
    public static void lireInventaire(String nomFichier, GestionnaireInventaire gestionnaireInventaire) throws FileNotFoundException {
        File fichier = new File(nomFichier);
        Scanner lecteurFichier = new Scanner(fichier);
        while (lecteurFichier.hasNextLine()) {
            while (lecteurFichier.hasNextLine()) {
                String ligne = lecteurFichier.nextLine();

                String[] champs = ligne.split("\\[|\\]");
                String categorie = champs[champs.length - 1].trim();
                int id = Integer.parseInt(champs[1].trim());
                String memo = champs[champs.length - 3].trim();
                if(categorie.startsWith("Employe")) {
                    String nomComplet = champs[3].trim();
                    String nas = champs[5].trim();
                    if(categorie.equals("EmployeSalarie")) {
                        double salaire = Double.parseDouble(champs[7].replace(",", ".").trim());

                        try {
                            gestionnaireInventaire.ajouterPayable(new EmployeSalarie(id, nomComplet, nas, salaire, memo));
                        } catch (ExceptionPayableExisteDeja e) {
                            System.out.println("Erreur: " + e.getMessage());
                        }
                    }
                        if(categorie.equals("EmployeSalarieAvecCommission")){
                            double salaire = Double.parseDouble(champs[7].replace(",", ".").trim());
                            double tauxCommission = Double.parseDouble(champs[9].replace(",", ".").trim());
                            double ventesBrutes = Double.parseDouble(champs[11].replace(",", ".").trim());
                            try {
                                gestionnaireInventaire.ajouterPayable(new EmployeSalarieAvecCommission(id, nomComplet, nas, salaire, tauxCommission,ventesBrutes, memo));
                            } catch (ExceptionPayableExisteDeja e) {

                                System.out.println("Erreur: " + e.getMessage());
                            }

                        }
                    if(categorie.equals("EmployeHoraire")){
                        double tauxHoraire = Double.parseDouble(champs[7].replace(",", ".").trim());
                        double heureTravaillees = Double.parseDouble(champs[9].replace(",", ".").trim());
                        try {
                            gestionnaireInventaire.ajouterPayable(new EmployeHoraire(id, nomComplet, nas, tauxHoraire, heureTravaillees, memo));
                        } catch (ExceptionPayableExisteDeja e) {

                            System.out.println("Erreur: " + e.getMessage());
                        }
                    }
                        if (categorie.equals("EmployeHoraireAvecCommission")){
                            double tauxHoraire = Double.parseDouble(champs[7].replace(",", ".").trim());
                            double heureTravaillees = Double.parseDouble(champs[9].replace(",", ".").trim());
                            double tauxCommission = Double.parseDouble(champs[11].replace(",", ".").trim());
                            double ventesBrutes = Double.parseDouble(champs[13].replace(",", ".").trim());
                            try {
                                gestionnaireInventaire.ajouterPayable(new EmployeHoraireAvecCommission(id, nomComplet, nas, tauxHoraire, heureTravaillees,tauxCommission,ventesBrutes, memo));
                            } catch (ExceptionPayableExisteDeja e) {

                                System.out.println("Erreur: " + e.getMessage());
                            }

                        }
                }
                if(categorie.equals("Facture")){
                    String numero = champs[3].trim();
                    String description = champs[5].trim();
                    int nombre = Integer.parseInt(champs[7].trim());
                    double prix = Double.parseDouble(champs[9].replace(",", ".").trim());
                    try {
                        gestionnaireInventaire.ajouterPayable(new Facture(id,numero,description,nombre,prix, memo));
                    } catch (ExceptionPayableExisteDeja e) {

                        System.out.println("Erreur: " + e.getMessage());
                    }
                }





            }
        }
    }

    public static void ecrireInventaire(String monFichier, Payable[] payables) {
        try {
            FileWriter fichier = new FileWriter(monFichier);
            PrintWriter ecritureFichier = new PrintWriter(fichier);

            for  (Payable payable : payables) {
                if (payable instanceof EmployeSalarie) {
                    EmployeSalarie employe = (EmployeSalarie) payable;
                    ecritureFichier.printf("ID [%3d] Nom complet [%20s] NAS [%9s] Salaire [%8.2f] Mémo [%20s] Catégorie [%20s]\n",
                            employe.getID(), employe.getNomComplet(), employe.getNumeroAssuranceSociale(),
                            employe.getMontantPaiement(), employe.getMemo(), employe.getCategorieString());
                } else if (payable instanceof Facture) {
                    Facture facture = (Facture) payable;
                    ecritureFichier.printf("ID [%3d] Numéro [%20s] Description [%20s] Nombre [%3d] Prix [%8.2f] Mémo [%20s] Catégorie [%20s]\n",
                            facture.getID(), facture.getNumeroPiece(), facture.getDescriptionPiece(), facture.getQuantite(),
                            facture.getPrixParItem(), facture.getMemo(), facture.getCategorieString());
                } else if (payable instanceof EmployeHoraire) {
                    EmployeHoraire employe = (EmployeHoraire) payable;
                    ecritureFichier.printf("ID [%3d] Nom complet [%20s] NAS [%9s] Taux Horaire [%8.2f] Heures travaillées [%5.2f] Mémo [%20s] Catégorie [%20s]\n",
                            employe.getID(), employe.getNomComplet(), employe.getNumeroAssuranceSociale(),
                            employe.getTauxHoraire(), employe.getHeuresTravaillees(), employe.getMemo(),
                            employe.getCategorieString());
                } else if (payable instanceof EmployeHoraireAvecCommission) {
                    EmployeHoraireAvecCommission employe = (EmployeHoraireAvecCommission) payable;
                    ecritureFichier.printf("ID [%3d] Nom complet [%20s] NAS [%9s] Taux Horaire [%8.2f] Heures travaillées [%5.2f] Taux commission [%5.2f] Ventes [%10.2f] Mémo [%20s] Catégorie [%20s]\n",
                            employe.getID(), employe.getNomComplet(), employe.getNumeroAssuranceSociale(),
                            employe.getTauxHoraire(), employe.getHeuresTravaillees(), employe.getTauxCommission(),
                            employe.getVentesBrutes(), employe.getMemo(), employe.getCategorieString());
                }
            }
            ecritureFichier.close(); // fermeture du fichier après la fin de la boucle
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }


    public static void main(String[] args) throws Exception {

        /* ---DÉMARRAGE DES TESTS  --------------------------------------------------------------------------*/
        //
        // -- Dé-commentez les tests au fur et à mesure de votre implémentation
        //         Éventuellement, tous les tests devraient fonctionner et vous devriez
        //         obtenir le même affichage que celui dans src/tests-sortie-attendue.txt
        //
        GestionnaireInventaire gestionnaireInventaire = new GestionnaireInventaire();

        System.out.println("\n=> TEST Création ou lecture de nouveaux payables");
        // Le bout de code qui suit vous permet de tester si votre fonction lireInventaire fonctionne correctement
        // Le contenu de src/payables.in est le même que celui généré par le code du else
        final boolean LECTURE = true;
        if (LECTURE)
           lireInventaire("payables.in", gestionnaireInventaire);
        else {
            gestionnaireInventaire.ajouterPayable(new EmployeSalarie(10, "Marie Renaud", "246864246", 5000, "Bonne employée"));
            gestionnaireInventaire.ajouterPayable(new EmployeHoraire(11, "Kevin Bouchard", "123321123", 25.50, 35, "Assidu"));
            gestionnaireInventaire.ajouterPayable(new EmployeSalarieAvecCommission(12, "Aline Brullemans", "123327832", 4000, 0.1, 15000, "Peu motivée"));
            gestionnaireInventaire.ajouterPayable(new EmployeHoraireAvecCommission(13, "Alan Walsh", "973813265", 15, 32.5,0.15, 40000, "Du potentiel"));
            gestionnaireInventaire.ajouterPayable(new Facture(14, "34X53", "Tournevis", 34, 23, "Gros vendeur"));
        }
        System.out.println("\n=> TEST Trouver un payable et afficher l'information sur ce payable");
        Payable payable = gestionnaireInventaire.getPayable(10);
        System.out.println(payable);

        System.out.println("\n=> TEST Création d'un payable avec un ID existant");
       try {
           gestionnaireInventaire.ajouterPayable(new EmployeHoraire(10,"Mario Bouchard","129271123",55000,40,"Déjà vu?"));
       } catch (ExceptionPayableExisteDeja e) {
           System.out.println(e.getMessage());
       }

        System.out.println("\n=> TEST Enlever un payable");
        gestionnaireInventaire.retirerPayable(10);

        System.out.println("\n=> TEST Enlever un payable non existant (catch exception)");
        try {
            gestionnaireInventaire.retirerPayable(10);
        } catch (ExceptionPayableIntrouvable e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n=> TEST Remettre un payable");
        gestionnaireInventaire.ajouterPayable(new EmployeSalarie(10,"Marie Renaud","246864246",5000, "Bonne employée" ));

        System.out.println("\n=> TEST Allonger deux fois le délai de paiement d'un payable et afficher les nouvelles informations");
        gestionnaireInventaire.augmenterEcheancePayable(11, 25);
        gestionnaireInventaire.augmenterEcheancePayable(11, 25);
        payable = gestionnaireInventaire.getPayable(11);
        System.out.println(payable + "\nÉchéance en jours: " + payable.getEcheanceJours());

        System.out.println("\n=> TEST Raccourcir le délai de paiement d'un payable et afficher les nouvelles informations");
        gestionnaireInventaire.diminuerEcheancePayable(11, 25);
        payable = gestionnaireInventaire.getPayable(11);
        System.out.println(payable + "\nÉchéance en jours: " + payable.getEcheanceJours());

        System.out.println("\n=> Raccourcir le délai de paiement d'un payable non existant (catch exception)");
        try {
            gestionnaireInventaire.diminuerEcheancePayable(9, 1);
        } catch (ExceptionPayableIntrouvable e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n=> TEST Trop raccourcir le délai de paiement d'un payable (catch exception)");
        try {
            gestionnaireInventaire.diminuerEcheancePayable(11, 100);
        } catch (ExceptionEcheanceInsuffisante e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n=> TEST Récupérer le tableau de payables");
        Payable[] payables = gestionnaireInventaire.getTableauPayables();
        for (Payable p : payables) {
            System.out.println(p.toStringAffichage());
            System.out.printf("%s %n%s: %,.2f%n%n", p, "paiement dû", p.getMontantPaiement());
        }
        System.out.println("\n=> TEST Écrire le tableau de payables dans un fichier pour relecture");
        ecrireInventaire("payables.out", payables);

        System.out.println("\n=> TEST Ce qu'il faut payer à tous les payables, " +
               "incluant un bonus de 10% sur le salaire hebdomadaire des employés salariées à commission");
        for (Payable p : payables) {
            System.out.print("ID " + p.getID());
            if (p instanceof EmployeSalarieAvecCommission ) {
                EmployeSalarieAvecCommission employe = (EmployeSalarieAvecCommission) p;
                System.out.print(". Salaire de base = " + employe.getSalaireHebdomadaire());
                employe.setSalaireHebdomadaire(1.10 * employe.getSalaireHebdomadaire());
                System.out.print(", nouveau salaire de base avec une augmentation de 10%: " + employe.getSalaireHebdomadaire());
            }
            System.out.println(". À payer: " + p.getMontantPaiement());
        }


        /* --- DÉMARRAGE de l'interface graphique (GUI)-------------------------------------------------------------*/
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        GUIGestionnaireInventaire GUIGestionnaireInventaire = new GUIGestionnaireInventaire(gestionnaireInventaire);
    }
}
