package payables;

public class EmployeHoraireAvecCommission extends EmployeHoraire implements Commission {
    private double tauxCommission;
    private double ventesBrutes;

    public EmployeHoraireAvecCommission(int id, String nom, String nas,
                                        double tauxHoraire, double heuresTravaillees,
                                        double tauxCommission, double ventesBrutes, String memo) {
        super(id, nom, nas, tauxHoraire, heuresTravaillees, memo);
        setTauxCommission(tauxCommission);
        categorie = Categorie.EmployeHoraireAvecCommission;
        setVentesBrutes(ventesBrutes);
    }

    public double getTauxCommission() {
        return tauxCommission;
    }
    //
    // TODO 03-- Ajoutez tout le code nécessaire pour coder la classe au complet coder la classe au completen vous basant sur le diagramme UML
    //         ainsi que la gestion des erreurs possibles si nécessaire
    //
    @Override
    public String toString() {
        return String.format("%s; %s: %,.2f",
                super.toString(),
                "taux de commission", getTauxCommission(),
                "ventes brutes", getVentesBrutes());
    }

    public String toStringAffichage() {
        String info = super.toStringAffichage();
        info += " Commission [" + this.getTauxCommission() + "] Ventes [" + this.getVentesBrutes() + "]";
        return info;
    }

    public String toStringSauvegarde() {
        String info = String.format("ID [%3d] Nom complet [%20s] NAS [%9s] Taux Horaire [%4.2f] Heures travaillées [%4.2f] Taux commission [%4.2f] Ventes [%10.2f] Mémo [%15s] Catégorie [%20s]",
                this.getID(), this.getNomComplet(), this.getNumeroAssuranceSociale(),
                this.getTauxHoraire(), this.getHeuresTravaillees(),
                this.getTauxCommission(), this.getVentesBrutes(),
                this.getMemo(), this.getCategorieString());
        return info;
    }
}
