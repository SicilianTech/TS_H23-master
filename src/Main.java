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
    // TODO 15-- Codez la fonction lireInventaire ici
    public static void lireInventaire(String nomFichier, GestionnaireInventaire gestionnaireInventaire) throws FileNotFoundException {
        File fichier = new File("payable.in");
        Scanner lecteurFichier = new Scanner(fichier);
        while (lecteurFichier.hasNextLine()) {
            while (lecteurFichier.hasNextLine()) {
                String ligne = lecteurFichier.nextLine();
                String[] champs = ligne.split("] ");
                int id = Integer.parseInt(champs[0].substring(4)); // enlever les premiers caractères "ID ["
                String typePayable = champs[1].substring(0, champs[1].indexOf("[")).trim();
                String[] attributs = champs[1].substring(champs[1].indexOf("[") + 1, champs[1].length() - 1).split(", ");
                Payable payable = null;

                switch (typePayable) {
                    case "Facture":
                        int idFacture = Integer.parseInt(attributs[0].substring(7));
                        String numero = attributs[1].substring(16);
                        String description = attributs[2].substring(8);
                        int nombre = Integer.parseInt(attributs[3]);
                        double prix = Double.parseDouble(attributs[4].substring(7));
                        String memo = attributs[5].substring(6);
                        payable = new Facture(idFacture, numero, description, nombre, prix, memo);
                        break;
                    case "EmployeSalarie":
                        String nomEmploye = attributs[0].substring(8);
                        String NAS = attributs[1].substring(5);
                        double salaire = Double.parseDouble(attributs[2].substring(8));
                        String memoEmploye = attributs[3].substring(6);
                        payable = new EmployeSalarie(id, nomEmploye, NAS, salaire, memoEmploye);
                        break;
                    case "EmployeSalarieAvecCommission":
                        String nomEmployeComm = attributs[0].substring(8);
                        String NASComm = attributs[1].substring(5);
                        double salaireComm = Double.parseDouble(attributs[2].substring(8));
                        double tauxCommissionComm = Double.parseDouble(attributs[3].substring(16));
                        double ventesComm = Double.parseDouble(attributs[4].substring(9));
                        String memoEmployeComm = attributs[5].substring(6);
                        payable = new EmployeSalarieAvecCommission(id, nomEmployeComm, NASComm, salaireComm, tauxCommissionComm, ventesComm, memoEmployeComm);
                        break;
                    case "EmployeHoraire":
                        String nomEmployeHoraire = attributs[0].substring(8);
                        String NASHoraire = attributs[1].substring(5);
                        double tauxHoraireHoraire = Double.parseDouble(attributs[2].substring(14));
                        double heuresTravailleesHoraire = Double.parseDouble(attributs[3].substring(20));
                        String memoEmployeHoraire = attributs[4].substring(6);
                        payable = new EmployeHoraire(id, nomEmployeHoraire, NASHoraire, tauxHoraireHoraire, heuresTravailleesHoraire, memoEmployeHoraire);
                        break;
                    case "EmployeHoraireAvecCommission":
                        String nomEmployeCommHoraire = attributs[0].substring(8);
                        String NASHoraireComm = attributs[1].substring(5);
                        double tauxHoraireHoraireComm = Double.parseDouble(attributs[2].substring(14));
                        double heuresTravailleesHoraireComm = Double.parseDouble(attributs[3].substring(20));
                        double tauxCommissionHoraireComm = Double.parseDouble(attributs[4].substring(16));
                        double ventesHoraireComm = Double.parseDouble(attributs[5].substring(9));
                        String memoEmployeCommHoraire = attributs[6].substring(6);
                        payable = new EmployeHoraireAvecCommission(id, nomEmployeCommHoraire, NASHoraireComm, tauxHoraireHoraireComm, heuresTravailleesHoraireComm, tauxCommissionHoraireComm, ventesHoraireComm, memoEmployeCommHoraire);
                        break;
                }
            }
        }
    }
    // TODO 16-- Codez la fonction ecrireInventaire ici
    public static void ecrireInventaire(String nomFichier, Payable[] payables) {
        try {
            FileWriter fichier = new FileWriter(nomFichier); // utilisation de la variable nomFichier
            PrintWriter ecritureFichier = new PrintWriter("payables.out");

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
        final boolean LECTURE = false;
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
