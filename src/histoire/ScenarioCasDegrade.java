package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {
	
    public static void main(String[] args) {
    	
    	// Scénario 0: Libérer étal n’ayant pas été occupé
    	
//        Etal etal = new Etal(null);
//        try {
//            etal.libererEtal();
//        } catch (IllegalStateException e) {
//            System.out.println("Exception attrapée : " + e.getMessage());
//        }
//        System.out.println("Fin du test");
        
        // Scénario 1: Acheteur null
    	
        System.out.println("Scénario 1: Acheteur null");
        try {
            Etal etal = new Etal("Potion magique");
            etal.acheterProduit(5, null);
        } catch (NullPointerException e) {
            System.err.println("Exception attrapée: " + e.getMessage());
        }

        // Scénario 2: Quantité négative
        
        System.out.println("\nScénario 2: Quantité négative");
        try {
            Etal etal = new Etal("Potion magique");
            etal.acheterProduit(-2, new Gaulois("Astérix", 10));
        } catch (IllegalArgumentException e) {
            System.err.println("Exception attrapée: " + e.getMessage());
        }

        // Scénario 3: Étal non occupé
        
        System.out.println("\nScénario 3: Étal non occupé");
        try {
            Etal etal = new Etal("Potion magique");
            etal.acheterProduit(3, new Gaulois("Obélix", 10));
        } catch (IllegalStateException e) {
            System.err.println("Exception attrapée: " + e.getMessage());
        }
    }
}