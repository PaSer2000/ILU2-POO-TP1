package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

    public Etal(String produit) {
        this.produit = produit;
    }
	
	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
	    if (!etalOccupe) {
	        throw new IllegalStateException("L'étal n'est pas occupé.");
	    }
		etalOccupe = false;
		StringBuilder chaine = new StringBuilder("Le vendeur " + vendeur.getNom() + " quitte son �tal, ");
		int produitVendu = quantiteDebutMarche - quantite;
		if (produitVendu > 0) {
			chaine.append("il a vendu " + produitVendu + " " + produit + " parmi les " + quantiteDebutMarche + " qu'il voulait vendre.\n");
		} else {
			chaine.append("il n'a malheureusement rien vendu.\n");
		}
		return chaine.toString();
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return vendeur.getNom() + " vend " + quantite + " " + produit + "\n";
		}
		return "L'étal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
	    if (acheteur == null) {
	        throw new NullPointerException("L'acheteur ne peut pas être null.");
	    }
	    if (quantiteAcheter < 1) {
	        throw new IllegalArgumentException("La quantité doit être positive.");
	    }
	    if (!etalOccupe) {
	        throw new IllegalStateException("L'étal doit être occupé pour acheter un produit.");
	    }
	    
		StringBuilder chaine = new StringBuilder();
		chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter + " " + produit + " � " + vendeur.getNom());
		if (quantite == 0) {
			chaine.append(", malheureusement il n'y en a plus !");
			quantiteAcheter = 0;
		}
		if (quantiteAcheter > quantite) {
			chaine.append(", comme il n'y en a plus que " + quantite + ", "
					+ acheteur.getNom() + " vide l'�tal de "
					+ vendeur.getNom() + ".\n");
			quantiteAcheter = quantite;
			quantite = 0;
		}
		if (quantite != 0) {
			quantite -= quantiteAcheter;
			chaine.append(". " + acheteur.getNom()
					+ ", est ravi de tout trouver sur l'étal de "
					+ vendeur.getNom() + "\n");
		}
		return chaine.toString();
	}

	public boolean contientProduit(String produit) {
		return this.produit.equals(produit);
	}
}
