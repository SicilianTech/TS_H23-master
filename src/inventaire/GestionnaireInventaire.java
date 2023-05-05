package inventaire;

import exceptions.*;
import payables.*;

public class GestionnaireInventaire {
    //
    // TODO 07-- Ajoutez tout le code nécessaire en vous basant sur le diagramme UML
    //         ainsi que la gestion des erreurs possibles si nécessaire
    //
    public Payable getPayable(int ID) throws ExceptionPayableIntrouvable {
        Payable payable = baseDonnees.trouverParID(ID);
        if (payable != null)
            return payable;
        else
            throw new ExceptionPayableIntrouvable(ID);
    }

    public Payable[] getTableauPayables() {
        return baseDonnees.getTableauPayables();
    }
}
