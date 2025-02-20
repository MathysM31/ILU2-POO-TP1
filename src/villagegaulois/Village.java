package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalMax) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtalMax);
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
		private Etal[] etals;
		private int nbEtal;

		public Marche(int nbEtal) {
			this.nbEtal = nbEtal;
			this.etals = new Etal[nbEtal];
			for (int i = 0; i < nbEtal; i++) {
				this.etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			int indiceEtal = -1;
			for (int i = 0; i<nbEtal && indiceEtal == -1 ; i++) {
				if (!etals[i].isEtalOccupe()) {
					indiceEtal = i;
				}
			}
			return indiceEtal;
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtalProduit = 0;
			for (int i = 0; i<nbEtal && etals[i].isEtalOccupe(); i++) {
				if(etals[i].contientProduit(produit)) {
					nbEtalProduit++;
				}			
			}
			Etal[] etalsProduit = new Etal[nbEtalProduit];
			int indiceTabProd = 0;
			for (int i = 0; i<nbEtal && etals[i].isEtalOccupe(); i++) {
				if(etals[i].contientProduit(produit)) {
					etalsProduit[indiceTabProd] = etals[i];
					indiceTabProd++;
				}
			}
			return etalsProduit;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			Etal gauloisEtal = null;
			for (int i = 0; i<nbEtal && gauloisEtal == null; i++) {
				if(etals[i].getVendeur()==gaulois) {
					gauloisEtal = etals[i];
				}
			}
			return gauloisEtal;
		}

		public String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide = 0;
			for (int i = 0; i < nbEtal; i++) {
				if(etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				}else{
					nbEtalVide++;
				}
			}
			chaine.append("Il reste ");
			chaine.append(nbEtalVide);
			chaine.append(" etals non utilisés dans le marché");
			return chaine.toString();
		}
	}
	
	public String installerVendeur(Gaulois gaulois, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(gaulois.getNom());
		chaine.append(" cherche un endroit pour vendre ");
		chaine.append(nbProduit);
		chaine.append(" ");
		chaine.append(produit);
		chaine.append(".\n");
		int indEtalLibre = marche.trouverEtalLibre();
		chaine.append("Le vendeur ");
		chaine.append(gaulois.getNom());
		chaine.append(" vend des ");
		chaine.append(produit);
		chaine.append(" à l'étal n°");
		chaine.append(indEtalLibre+1);
		chaine.append(".\n");
		marche.utiliserEtal(indEtalLibre, gaulois, produit, nbProduit);
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Les vendeurs qui proposent des fleurs sont :\n");
		Etal[] etalProduit = marche.trouverEtals(produit);
		for(int i=0; i< etalProduit.length; i++){
			chaine.append("- ");
			chaine.append(etalProduit[i].getVendeur().getNom());
			chaine.append("\n");
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois gaulois) {
		Etal gEtal = marche.trouverVendeur(gaulois);
		return gEtal;
	}
	
	public String partirVendeur(Gaulois gaulois) {
		return marche.trouverVendeur(gaulois).libererEtal();
	}

	public String afficherMarche() {
		return "Le marche du village '" + getNom() + "' possède plusieurs étals :\n" + marche.afficherMarche();
	}
}