package payables;

public class EmployeSalarieAvecCommission extends EmployeSalarie implements Commission{

    private double TauxCommission;
    private double venteBrutes;

    public EmployeSalarieAvecCommission(int ID, String n, String NAS, double s, double tc, double vB, String m) {
        super(ID, n, NAS, s, m);
        this.TauxCommission = tc;
        this.venteBrutes = vB;
        categorie = Categorie.EmployeSalarieAvecCommission;
    }

    public double getTauxCommission() {
        return TauxCommission;
    }

    public void setTauxCommission(double tauxCommission) {
        TauxCommission = tauxCommission;
    }

    @Override
    public double getVentesBrutes() {
        return venteBrutes;
    }

    public void setVentesBrutes(double venteBrutes) {
        this.venteBrutes = venteBrutes;
    }

    @Override
    public double getMontantCommission(double venteBrutes){
        return venteBrutes * TauxCommission;
    }

    @Override
    public double getMontantPaiement(){
        return super.getSalaireHebdomadaire() + getMontantCommission(venteBrutes);
    }

    @Override
    public String toString() {
        return String.format("%s: %s%n%s: %,.2f",
                getCategorieString(), super.toString(), "salaire hebdomadaire", getSalaireHebdomadaire());
    }

    public String toStringAffichage() {
        String info = super.toStringAffichage();
        info += " Salaire [" + this.getSalaireHebdomadaire()  + "]";
        return info;
    }

    public String toStringSauvegarde() {
        String info = String.format("ID [%3d] Nom complet [%20s] NAS [%9s] Salaire [%6.2f] Mémo [%15s] Catégorie [%20s]",
                this.getID(), this.getNomComplet(), this.getNumeroAssuranceSociale(),
                this.getSalaireHebdomadaire(), getMontantCommission(venteBrutes),this.getMemo(), this.getCategorieString());
        return info;
    }
}