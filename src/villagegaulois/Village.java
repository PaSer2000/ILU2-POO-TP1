package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	
    private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMarche) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
        marche = new Marche(nbEtalsMarche);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}
	
	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	/*méthode 1*/
    public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
        int indiceEtalLibre = marche.trouverEtalLibre();
        if (indiceEtalLibre != -1) {
            marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
            return vendeur.getNom() + " s'installe comme vendeur sur le marché pour vendre " + nbProduit + " " + produit + ".\n";
        } else {
            return "Il n'y a plus d'étal disponible pour installer " + vendeur.getNom() + ".\n";
        }
    }
    
	/*méthode 2*/
    public String rechercherVendeursProduit(String produit) {
        Etal[] etalsVendantProduit = marche.trouverEtals(produit);
        StringBuilder result = new StringBuilder("Les vendeurs proposant " + produit + " sont :\n");
        for (Etal etal : etalsVendantProduit) {
            result.append("- ").append(etal.getVendeur().getNom()).append("\n");
        }
        return result.toString();
    }
    
	/*méthode 3*/
    public Etal rechercherEtal(Gaulois vendeur) {
        return marche.trouverVendeur(vendeur);
    }

	/*méthode 4*/
    public String partirVendeur(Gaulois vendeur) {
        Etal etal = marche.trouverVendeur(vendeur);
        if (etal != null) {
            etal.libererEtal();
            return vendeur.getNom() + " quitte le marché.\n";
        } else {
            return vendeur.getNom() + " n'est pas présent sur le marché.\n";
        }
    }
    
	/*méthode 5*/
    public String afficherMarche() {
        return marche.afficherMarche();
    }
	
	
	private static class Marche {
		
		private Etal[] etals;
		private int nbEtals;
		private int taille = 50;

		private Marche(int nbEtals) {
			this.etals = new Etal[taille];
			this.nbEtals = nbEtals;
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i =0; i<nbEtals; i++) {
				if ( !(etals[i].isEtalOccupe()) ) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			Etal[] etalsVendantProduit = new Etal[taille];
			
			for (int i =0; i<nbEtals; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsVendantProduit[i] = etals[i];
				}
			}
			return etalsVendantProduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i =0; i<nbEtals; i++) {
				if (etals[i].isEtalOccupe() && etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
	    public String afficherMarche() {
	        StringBuilder result = new StringBuilder();
	        int nbEtalVide = 0;

	        for (int i = 0; i < nbEtals; i++) {
	            if (etals[i].isEtalOccupe()) {
	                result.append(etals[i].afficherEtal());
	            } else {
	                nbEtalVide++;
	            }
	        }

	        if (nbEtalVide > 0) {
	            result.append("Il reste ").append(nbEtalVide).append(" étals non utilisés dans le marché.\n");
	        }

	        return result.toString();
	    }
	}
}