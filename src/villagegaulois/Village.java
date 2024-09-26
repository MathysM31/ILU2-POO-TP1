package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private static Marche marche;

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
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
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	private static class Marche{
		private Etal [] etals;

		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int indiceEtal = 0;indiceEtal<nbEtals;indiceEtal++) {
				Etal etal = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit); 
		}
		
		private int trouverEtalLibre() {
			for (int indiceEtal=0;indiceEtal<etals.length;indiceEtal++) {
				if (etals[indiceEtal].isEtalOccupe()) {
					return indiceEtal;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int compteur = 0;
			for (int indiceEtal=0;indiceEtal<etals.length;indiceEtal++) {
				if (etals[indiceEtal].isEtalOccupe()) {
					compteur++;
				}
			}
			Etal[] etalOccupe = new Etal [compteur];
			for (int indiceEtal=0;indiceEtal<compteur;indiceEtal++) {
					if (etals[indiceEtal].contientProduit(produit)) {
						etalOccupe[indiceEtal] = etals[indiceEtal];
				}
			}
			return etalOccupe;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int indiceEtal = 0;indiceEtal<etals.length;indiceEtal++) {
				if (etals[indiceEtal].getVendeur().equals(gaulois)) {
					return etals[indiceEtal];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			String affichageMarche = "";
			int nbEtalVide = 0;
			for (int indiceEtal = 0;indiceEtal<etals.length;indiceEtal++) {
				if (etals[indiceEtal].isEtalOccupe()) {
					affichageMarche += etals[indiceEtal].afficherEtal();
					return etals[indiceEtal].afficherEtal();
				}
			}
			return affichageMarche + "Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n";
		}
		
		
	}
}