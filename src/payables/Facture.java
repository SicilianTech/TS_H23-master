package payables;

public class Facture extends Payable {
	private final String numeroPiece;
	private String descriptionPiece;
	private int quantite;
	private double prixParItem;

	public Facture(int id, String numero, String description, int nombre, double prix, String memo) {
		super(id, memo);
		categorie = Categorie.Facture;
		numeroPiece = numero;
		descriptionPiece = description;
		setQuantite(nombre);
		setPrixParItem(prix);
	}

	public String getNumeroPiece() {
		return numeroPiece;
	}

	public String getDescriptionPiece() {
		return descriptionPiece;
	}

	public void setDescriptionPiece(String description) {
		descriptionPiece = description;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int nombre) {
		if (nombre >= 0)
			quantite = nombre;
		else
			throw new IllegalArgumentException("La quantité doit être >= 0");
	}

	public double getPrixParItem() {
		return prixParItem;
	}
	//
	// TODO 05-- Ajoutez les méthodes nécessaires en vous basant sur le diagramme UML
	//  		que la gestion des erreurs possibles si nécessaire.
	//
	public double getMontantPaiement() {
		return quantite * prixParItem;
	}

	public void setPrixParItem(double prixParItem) {
		this.prixParItem = prixParItem;
	}
	public String toString() {
		return String.format("Facture #%s - %s : %d x %.2f = %.2f", numeroPiece, descriptionPiece, quantite, prixParItem, getMontantPaiement());
	}

	public String toStringAffichage() {
		return String.format("Facture #%s\nDescription : %s\nQuantité : %d\nPrix par item : %.2f\nMontant total : %.2f",
				numeroPiece, descriptionPiece, quantite, prixParItem, getMontantPaiement());
	}

	public String toStringSauvegarde() {
		String info = String.format("ID [%3d] Numéro [%15s] Description [%25s] Nombre [%3d] Prix [%10.2f] Mémo [%15s] Catégorie [%20s]",
				this.getID(), this.numeroPiece, this.getDescriptionPiece(), this.getQuantite(), this.getPrixParItem(), this.getMemo(), this.getCategorieString());
		return info;
	}
}
