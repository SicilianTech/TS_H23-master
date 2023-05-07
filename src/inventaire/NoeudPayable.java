package inventaire;

import payables.Payable;

public class NoeudPayable {
    public Payable payable;
    private NoeudPayable suivant;

    public NoeudPayable(Payable p, NoeudPayable suivant) {
        this.payable = p;
        this.suivant = suivant;
    }

    public NoeudPayable getNoeudSuivant() {
        return suivant;
    }

    public void setNoeudSuivant(NoeudPayable s) {
        suivant = s;
    }
}