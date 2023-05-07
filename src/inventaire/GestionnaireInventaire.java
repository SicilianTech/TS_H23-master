package inventaire;

import exceptions.*;
import payables.*;

public class GestionnaireInventaire {
    //
    // TODO 07-- Ajoutez tout le code nécessaire en vous basant sur le diagramme UML
    //         ainsi que la gestion des erreurs possibles si nécessaire
    //
    private BaseDonnees baseDonnees;
    public GestionnaireInventaire(){
        baseDonnees = new BaseDonnees();
    }
    public void ajouterPayable(Payable p) throws ExceptionPayableExisteDeja {
        for (Payable payable : baseDonnees.getTableauPayables()){
            if (p.getID() == payable.getID()) {
                throw new ExceptionPayableExisteDeja(p.getID());
            }
        }
        baseDonnees.inserer(p);
    }
    public void retirerPayable(int ID) throws ExceptionPayableIntrouvable{
        Payable payable = baseDonnees.trouverParID(ID);
        if (payable == null){
            throw new ExceptionPayableIntrouvable(ID);
        }
        baseDonnees.enlever(ID);

    }

    public void augmenterEcheancePayable(int ID, int e) throws ExceptionEcheanceInsuffisante, ExceptionPayableIntrouvable{
        Payable payable = baseDonnees.trouverParID(ID);
        if (payable == null){
            throw new ExceptionPayableIntrouvable(ID);
        }
            else{
                payable.augmenterEcheance(e);
            }
        }
    public void diminuerEcheancePayable(int ID, int e) throws ExceptionEcheanceInsuffisante, ExceptionPayableIntrouvable{
        Payable payable = baseDonnees.trouverParID(ID);
        if (payable == null){
            throw new ExceptionPayableIntrouvable(ID);
        }
        else{
            if (payable.getEcheanceJours() - e < 0){
                throw new ExceptionEcheanceInsuffisante(payable.getEcheanceJours() - e);
            }
            else{
                payable.diminuerEcheance(e);
            }
        }
    }
    public Payable getPayable(int ID) {
        Payable payable = baseDonnees.trouverParID(ID);
            return payable;
    }

    public Payable[] getTableauPayables() {
        return baseDonnees.getTableauPayables();
    }



}
