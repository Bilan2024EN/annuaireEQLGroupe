package fr.afcepf.ai105.projet1.data;

public class Stagiaire {

	private String nom;
	private String prenom;
	private String departement;
	private String promotion;
	private String annee;
	
	public Stagiaire() {
		
	}
	
	
	public Stagiaire(String nom, String prenom, String depart, String promo, String anne) {
		this.nom = nom;
		this.prenom = prenom;
		this.departement = depart;
		this.promotion = promo;
		this.annee = anne;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getDepartement() {
		return departement;
	}
	public void setDepartement(String depart) {
		this.departement = depart;
	}
	public String getPromotion() {
		return promotion;
	}
	public void setPromotion(String promo) {
		this.promotion = promo;
	}
	public String getAnnee() {
		return annee;
	}
	public void setAnnee(String anne) {
		this.annee = anne;
	}



	@Override
	public String toString() {
		return nom + " " + prenom + " " + departement + " " + promotion + " "
				+ annee;
	}
	
	
	
}
