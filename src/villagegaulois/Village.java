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
        marche = new Marche(nbEtalsMarche, nom);
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

	public String afficherVillageois() /*throws VillageSansChefException*/ {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les l�gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		/*
	    if (chef == null) {
	        throw new VillageSansChefException();
	    }
	    */
		return chaine.toString();
	}
	
	/*m�thode 1*/
    public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
    	StringBuilder chaine = new StringBuilder();
    	chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit +  ". \n");   	
        int indiceEtalLibre = marche.trouverEtalLibre();
        
        if (indiceEtalLibre == -1) {	
        	chaine.append(vendeur + "n'a pas trouvé d'étal libre! \n");
        } else {
            marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
            chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (indiceEtalLibre + 1) + ".\n");
        }
        return chaine.toString();
    }
    
	/*m�thode 2*/
    public String rechercherVendeursProduit(String produit) {
    	StringBuilder chaine = new StringBuilder();
        Etal[] etalsVendantProduit = marche.trouverEtals(produit);
        int nombreEtalsTrouvee = 0;
        
        for (Etal etal : etalsVendantProduit) {
            if (etal != null) {
                nombreEtalsTrouvee++;
            }
        }
        if (nombreEtalsTrouvee == 0) {
        	chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.\n");
        } else if (nombreEtalsTrouvee == 1) {
        	chaine.append("Seul le vendeur " + etalsVendantProduit[0].getVendeur().getNom() + " propose des " + produit + " au marché.\n");
        } else {
        	chaine.append("Les vendeurs qui proposent des " + produit + "sont :\n");
        	for (Etal etal : etalsVendantProduit) {
                if (etal != null) {
                    chaine.append("- " + etal.getVendeur().getNom() + "\n");
                }        	}
        }
        return chaine.toString();
    }
    
	/*m�thode 3*/
    public Etal rechercherEtal(Gaulois vendeur) {
        return marche.trouverVendeur(vendeur);
    }

	/*m�thode 4*/
    public String partirVendeur(Gaulois vendeur) {
        Etal etalLibere = rechercherEtal(vendeur);
        return etalLibere.libererEtal();
    }
    
	/*m�thode 5*/
    public String afficherMarche() {
        return marche.afficherMarche();
    }
	
	
	private static class Marche {
		
		private Etal[] etals;
		private int nbEtals;
	    private String nomVillage;
		private int taille = 50;

		private Marche(int nbEtals, String nomVillage) {
			this.etals = new Etal[taille];
			this.nbEtals = nbEtals;
			this.nomVillage = nomVillage; 
		    for (int i = 0; i < nbEtals; i++) {
		        this.etals[i] = new Etal("Produit par défaut");
		    }
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
	        StringBuilder chaine = new StringBuilder("Le marché du village " + "\"" +nomVillage +"\""+ " possède plusieurs étals :\n");
	        int nbEtalVide = 0;

	        for (int i = 0; i < nbEtals; i++) {
	            if (etals[i].isEtalOccupe()) {
	                chaine.append(etals[i].afficherEtal());
	            } else {
	                nbEtalVide++;
	            }
	        }

	        if (nbEtalVide > 0) {
	            chaine.append("Il reste ").append(nbEtalVide).append(" �tals non utilis�s dans le march�.\n");
	        }

	        return chaine.toString();
	    }
	}
}