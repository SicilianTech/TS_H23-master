package gui;

import exceptions.ExceptionEcheanceInsuffisante;
import exceptions.ExceptionPayableExisteDeja;
import exceptions.ExceptionPayableIntrouvable;
import inventaire.GestionnaireInventaire;
import payables.*;

import javax.print.attribute.DateTimeSyntax;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GUIGestionnaireInventaire extends JFrame
{
    private GestionnaireInventaire gestionnaireInventaire;
    private DefaultListModel modeleListePayables;
    private JList listePayables;
    private int idSuivant;

    public GUIGestionnaireInventaire(GestionnaireInventaire gestionnaireInventaire) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gestionnaireInventaire = gestionnaireInventaire;
        idSuivant = 100;
        creerEtAfficherGUI();
    }

    private void creerEtAfficherGUI() {
        add(creerTitrePanneau(), BorderLayout.NORTH);
        add(creerPanneauContenu(), BorderLayout.CENTER);

        setSize(1200, 300);
        setVisible(true);
    }

    private JPanel creerPanneauContenu() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(bordure());
        contentPane.setLayout(new BorderLayout());

        contentPane.add(creerActionsPayables(), BorderLayout.NORTH);
        contentPane.add(creerListePayables(), BorderLayout.CENTER);

        return contentPane;
    }

    private JPanel creerTitrePanneau() {
        JPanel titlePane = new JPanel();
        titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.Y_AXIS));
        titlePane.setBorder(bordure());

        JLabel title = new JLabel("Gestionnaire de l'inventaire", SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.PLAIN, 20));
        title.setBorder(titreBordure());
        titlePane.add(title);
        titlePane.add(new JSeparator());

        return titlePane;
    }

    private JScrollPane creerListePayables() {
        modeleListePayables = new DefaultListModel<>();
        for (Payable payable : gestionnaireInventaire.getTableauPayables())
            modeleListePayables.addElement(payable.toStringAffichage());

        listePayables = new JList(modeleListePayables);
        listePayables.setFont(new Font("Courier New", Font.PLAIN, 12));
        listePayables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(listePayables);

        return listScroller;
    }

    private JPanel creerActionsPayables() {
        JPanel payableButtons = new JPanel();
        payableButtons.setLayout(new BoxLayout(payableButtons, BoxLayout.X_AXIS));
        payableButtons.add(creerBoutonAjout());
        payableButtons.add(creerBoutonRetrait());
        payableButtons.add(creerBoutonEdition());
        payableButtons.add(creerBoutonEffacage());
        payableButtons.add(creerFacture());
        payableButtons.add(creerEmployeSalarie());
        payableButtons.add(creerEmployeHoraire());
        payableButtons.add(creerEmployeSalarieAvecCommission());
        payableButtons.add(creerEmployeHoraireAvecCommission());
        return payableButtons;
    }

    private JButton creerBoutonAjout() {
        JButton button = creerBouton("icons/icons8-add-new-24.png");
        button.addActionListener(event -> {
            //
            // TODO 11-- Ajoutez le code nécessaire pour augmenter d'un jour l'échéance de paiement
            //         ainsi que la gestion des erreurs possibles si nécessaire.
            //         L'information dans la liste des payables doit être automatiquement mise à jour.
            // Indices: Pour la mise à jour automatique, le plus simple est d'effacer et
            //            reconstruire modeleListePayables.

            int index = listePayables.getSelectedIndex();
            if(index == -1){
                JOptionPane.showMessageDialog(this, "Veuille selectionner un payable à modifier");
                return;
            }
            Payable payable = gestionnaireInventaire.getTableauPayables()[index];
            payable.setEcheanceJours(payable.getEcheanceJours() + 1);
            majModeleListe();
            listePayables.setModel(modeleListePayables);
        });

        return button;
    }

    private JButton creerBoutonRetrait() {
        JButton button = creerBouton("icons/icons8-minus-sign-24.png");
        button.addActionListener(event -> {
            //
            // TODO 12-- Ajoutez le code nécessaire pour réduire l'échéance paiement ainsi que la gestion des
            //  erreurs et afficher un dialogue d'erreur si jamais on essaye d'aller en dessous de zéro
            // Indices: Pour la mise à jour automatique, le plus simple est d'effacer et
            //            reconstruire modeleListePayables.
            //

            int index = listePayables.getSelectedIndex();
            if(index == -1){
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un payable à modifier");
                return;
            }
            Payable payable = gestionnaireInventaire.getTableauPayables()[index];
            int nouvelleEcheance = payable.getEcheanceJours() - 1;
            if (nouvelleEcheance < 0) {
                JOptionPane.showMessageDialog(this, "La nouvelle échéance ne peut pas être inférieure à zéro.");
                return;
            }
            payable.setEcheanceJours(nouvelleEcheance);
            majModeleListe();
            listePayables.setModel(modeleListePayables);
        });
        return button;
    }

    private JButton creerBoutonEdition() {
        JButton button = creerBouton("icons/icons8-edit-row-24.png");
        button.addActionListener(event -> {
            //
            // TODO 13-- Ajoutez le code pour ouvrir le dialogue d'édition d'un payable
            //         ainsi que la gestion des erreurs possibles si nécessaire.
            //          L'information dans la liste des payables doit être automatiquement mise à jour.
            // Indices: Pour la mise à jour automatique, le plus simple est d'effacer et
            //            reconstruire modeleListePayables.
            //
            int index = listePayables.getSelectedIndex();
            if(index == -1){
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un payable à modifier");
                return;
            }
            else{

                String ligne = listePayables.getSelectedValue().toString();
                String[] champs = ligne.split("\\[|\\]");
                JDialog boiteDialogue = new JDialog();
                boiteDialogue.setTitle(champs[3].trim());
                boiteDialogue.setSize(300, 400);
                JPanel panel = new JPanel(new GridLayout(0, 2));
                panel.add(new JLabel("ID:"));
                JTextField ID = new JTextField(Integer.toString(Integer.parseInt(champs[1].trim())));
                panel.add(ID);
                panel.add(new JLabel("Mémo:"));
                JTextField memo = new JTextField(champs[5].trim());
                panel.add(memo);
                panel.add(new JLabel("Catégorie:"));
                String categorieEmploye = champs[3].trim();
                JTextField categorie = new JTextField(categorieEmploye);
                panel.add(categorie);
                categorie.setEditable(false);
                panel.add(new JLabel("Échéance:"));
                JTextField echeance = new JTextField(champs[7].trim());
                panel.add(echeance);
                JTextField nom = new JTextField(champs[11].trim());
                JTextField nas = new JTextField();
                JTextField salaire = new JTextField();
                JTextField ht = new JTextField();
                JTextField TauxHoraire = new JTextField();
                JTextField ventes = new JTextField();
                JTextField commission = new JTextField();
                JTextField npiece = new JTextField();
                JTextField description = new JTextField();
                JTextField quantite = new JTextField();
                JTextField prixItem = new JTextField();

                if (categorieEmploye.equals("EmployeSalarie") || categorieEmploye.equals("EmployeSalarieAvecCommission") || categorieEmploye.equals("EmployeHoraire") || categorieEmploye.equals("EmployeHoraireAvecCommission")){
                    panel.add(new JLabel("Nom complet:"));
                    panel.add(nom);
                    nom.setEditable(false);
                    panel.add(new JLabel("NAS:"));
                    nas.setText(champs[13].trim());
                    panel.add(nas);
                    nas.setEditable(false);
                    panel.add(new JLabel("Salaire:"));
                    salaire.setText(champs[15].trim());
                    panel.add(salaire);
                    salaire.setEditable(true);

                if (categorieEmploye.equals("EmployeHoraire") || categorieEmploye.equals("EmployeHoraireAvecCommission")) {
                    panel.add(new JLabel("Heures travaillées:"));
                    ht.setText(champs[19].trim());
                    panel.add(ht);
                    panel.add(new JLabel("Taux Horaire:"));
                    TauxHoraire.setText(champs[17].trim());
                    panel.add(TauxHoraire);
                }

                    if (categorieEmploye.equals("EmployeHoraireAvecCommission")){
                        panel.add(new JLabel("Ventes:"));
                        ventes.setText(champs[21].trim());
                        panel.add(ventes);
                        panel.add(new JLabel("Commission:"));
                        commission.setText(champs[23].trim());
                        panel.add(commission);
                    }
                }
                if (categorieEmploye.equals("Facture")) {
                    panel.add(new JLabel("N. pièce:"));
                    npiece.setText(champs[11].trim());
                    panel.add(npiece);
                    npiece.setEditable(false);
                    panel.add(new JLabel("Description:"));
                    description.setText(champs[13].trim());
                    panel.add(description);
                    panel.add(new JLabel("Quantité:"));
                    quantite.setText(champs[15].trim());
                    panel.add(quantite);
                    panel.add(new JLabel("Prix par Item"));
                    prixItem.setText(champs[17].trim());
                    panel.add(prixItem);


                }
                if( categorieEmploye.equals("EmployeSalarieAvecCommission")){
                    panel.add(new JLabel("Ventes:"));
                    ventes.setText(champs[19].trim());
                    panel.add(ventes);
                    ventes.setEditable(true);
                    panel.add(new JLabel("Commission:"));
                    commission.setText(champs[17].trim());
                    panel.add(commission);
                    commission.setEditable(true);

                }
                JButton boutonSauvegarder = new JButton("Sauvegarder");
                boutonSauvegarder.addActionListener(e -> {
                    int id = Integer.parseInt(ID.getText());
                    String memoText = memo.getText();
                    int echeanceText = Integer.parseInt(echeance.getText());


                    Payable payable = gestionnaireInventaire.getPayable(id);
                       if(payable instanceof Employe) {
                           Employe employe = (Employe) payable;

                           employe.setEcheanceJours(echeanceText);
                           employe.setMemo(memoText);
                           double salaireTxt = Double.parseDouble(salaire.getText());
                           if (payable instanceof EmployeHoraire) {
                               EmployeHoraire employeHoraire = (EmployeHoraire) payable;
                               double thText = Double.parseDouble(TauxHoraire.getText().replace(",", "."));
                               double heureTravText = Double.parseDouble(ht.getText().replace(",","."));
                               employeHoraire.setTauxHoraire(thText);
                               employeHoraire.setHeuresTravaillees(heureTravText);
                               if (payable instanceof EmployeHoraireAvecCommission) {
                                   double commissionText = Double.parseDouble(commission.getText().replace(",","."));
                                   double ventesText = Double.parseDouble(ventes.getText().replace(",","."));
                                   EmployeHoraireAvecCommission employeHoraireCommission = (EmployeHoraireAvecCommission) payable;
                                   employeHoraireCommission.setTauxCommission(commissionText);
                                   employeHoraireCommission.setVentesBrutes(ventesText);
                               }
                           } else if (payable instanceof EmployeSalarie) {
                               EmployeSalarie employeSalarie = (EmployeSalarie) payable;
                               employeSalarie.setSalaireHebdomadaire(salaireTxt);
                               if (payable instanceof EmployeSalarieAvecCommission) {
                                   double commissionText = Double.parseDouble(commission.getText().replace(",","."));
                                   double ventesText = Double.parseDouble(ventes.getText().replace(",","."));
                                   EmployeSalarieAvecCommission employeSalarieAvecCommission = (EmployeSalarieAvecCommission) payable;
                                   employeSalarieAvecCommission.setTauxCommission(commissionText);
                                   employeSalarieAvecCommission.setVentesBrutes(ventesText);
                               }
                           }
                       }
                       if(payable instanceof Facture){
                           String descriptionTxt = description.getText();
                           int quantiteTxt = Integer.parseInt(quantite.getText());
                           double prixItemTxt = Double.parseDouble(prixItem.getText().replace(",","."));
                           Facture facture = (Facture) payable;
                           facture.setQuantite(quantiteTxt);
                           facture.setDescriptionPiece(descriptionTxt);
                           facture.setPrixParItem(prixItemTxt);
                           facture.setMemo(memoText);
                           facture.setEcheanceJours(echeanceText);
                       }

                    majModeleListe();
                    listePayables.setModel(modeleListePayables);
                    boiteDialogue.dispose();
                    afficherTableau();
                });

                JButton boutonAnnuler = new JButton("Annuler");
                boutonAnnuler.addActionListener(e -> {
                    boiteDialogue.dispose();
                });

                JButton boutoneffacer = new JButton(("Cancel"));
                panel.add(boutoneffacer);
                panel.add(boutonSauvegarder);
                boiteDialogue.add(panel);
                boiteDialogue.setVisible(true);


            }

    });
        return button;
    }

    private JButton creerBoutonEffacage() {
        JButton button = creerBouton("icons/icons8-erase-24.png");
        button.addActionListener(event -> {
            //
            // TODO 14-- Ajoutez le code nécessaire pour supprimer un payable ainsi que la gestion des
            //         erreurs pour afficher un dialogue d'erreur si jamais on essaye d'effacer un
            //         payable sans faire de sélection dans la liste.
            //         L'information dans la liste des payables doit être automatiquement mise à jour.
            // Indices: Pour la mise à jour automatique, le plus simple est d'effacer et
            //            reconstruire modeleListePayables.
            //
            int index = listePayables.getSelectedIndex();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un payable à supprimer");
                return;
            }
            Payable payable = gestionnaireInventaire.getTableauPayables()[index];
            int id = payable.getID();
            int option = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce payable?", "Confirmer la suppression", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    gestionnaireInventaire.retirerPayable(id);
                    modeleListePayables.remove(index);
                } catch (ExceptionPayableIntrouvable e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            }
        });

        return button;
    }

    private JButton creerFacture() {
        Facture nouvelle = new Facture(idSuivant, "PARTNUMBER", "PARTDESCRIPTION",
                0,0.0,"MÉMO");
        return creerBoutonPayable(nouvelle, "icons/FCT.png");
    }

    private JButton creerEmployeSalarie() {
        Employe nouveau = new EmployeSalarie(idSuivant++, "NOM","NAS",
                0.0, "MÉMO");
        return creerBoutonPayable(nouveau, "icons/EST.png");
    }

    private JButton creerEmployeHoraire() {
        Employe nouveau = new EmployeHoraire(idSuivant++, "NOM", "NAS",
                0.0, 0.0, "MÉMO");
        return creerBoutonPayable(nouveau, "icons/EHT.png");
    }

    private JButton creerEmployeSalarieAvecCommission() {
        Employe nouveau = new EmployeSalarieAvecCommission(idSuivant++, "NOM", "NAS",
                0.0,0.1,0.0,"MÉMO");
        return creerBoutonPayable(nouveau, "icons/EPT.png");
    }

    private JButton creerEmployeHoraireAvecCommission() {
        Employe nouveau = new EmployeHoraireAvecCommission(idSuivant++, "NOM", "NAS",
                0.0, 0.0, 0.1, 0.0, "MÉMO");
        return creerBoutonPayable(nouveau, "icons/ECT.png");
    }

    private JButton creerBoutonPayable(Payable nouveau, String icone) {
        JButton button = creerBouton(icone);
        button.addActionListener(event -> {
            try {
                gestionnaireInventaire.ajouterPayable(nouveau);
                modeleListePayables.insertElementAt(nouveau.toStringAffichage(),0);
            }
            catch (ExceptionPayableExisteDeja exception) {
                afficherDialogueErreur(exception.getMessage());
            }
            afficherTableau(); // Pas nécessaire pour les étudiants
        });
        return button;
    }

    private JButton creerBouton(String icone) {
        JButton bouton = new JButton(new ImageIcon(icone));
        bouton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5 , 5));
        return bouton;
    }

    private void afficherDialogueErreurSelection() {
        afficherDialogueErreur("SVP choisir un payable");
    }

    private void afficherDialogueErreur(String message) {
        GUIErreurDialogue dialog = new GUIErreurDialogue(this, message);
        dialog.setVisible(true);
    }

    private Border bordure() {
        return BorderFactory.createEmptyBorder(5, 10, 10, 10);
    }

    private Border titreBordure() {
        return BorderFactory.createEmptyBorder(5, 0, 10, 10);
    }

    private static int extraitID(String payableString) {
        if (payableString == null)
            return -1;
        payableString = payableString.replace("]", "");
        payableString = payableString.replace("[", "");
        payableString = payableString.trim().replaceAll("\\s+", " ");
        String[] parties = payableString.split(" ");
        return Integer.parseInt(parties[1]);
    }

    private void afficherTableau() {
        System.out.println("\n=> TEST Récupérer le tableau de payables après un événement");
        Payable[] payables = gestionnaireInventaire.getTableauPayables();
        for (Payable payable : payables)
            System.out.println(payable);
    }

    private void majModeleListe() {
        //listePayables.updateUI(); serait suffisant si on utilisait DefaultListModel<Payable>
        Payable[] payables = gestionnaireInventaire.getTableauPayables();
        modeleListePayables.clear();
        for (Payable payable : payables)
            modeleListePayables.addElement(payable.toStringAffichage());
    }
}
