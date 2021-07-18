package fr.afcepf.ai105.projet1.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;



public class StagiaireDAO {

	public String nameBinFile = "stagiairesBin.bin";
	public String nameFile = "stagiaires.txt";

	public static int nbTotalLignes = 0; // Il faut obtenir ce information après generer le fichier binaire.

	public static final int CNOM = 30;
	public static final int CPRENOM = 20;
	public static final int CDEPARTEMENT = 5;
	public static final int CPROMOTION = 12;
	public static final int CANNEE = 4;

	public static int CARACTERES_TOTAL_STAGIAIRE = CNOM + CPRENOM + CDEPARTEMENT + CPROMOTION + CANNEE;

	public final static int LONGUEUR_INFO_STAGIAIRE = (CNOM + CPRENOM + CDEPARTEMENT + CPROMOTION + CANNEE) * 2; 

	public final static int SAG = LONGUEUR_INFO_STAGIAIRE;
	public final static int SAD = LONGUEUR_INFO_STAGIAIRE + 8;
	
	public int nbTotalStagiaires = 0;
	
	public static List<Stagiaire> listeCourante;

	
	public void convertTxtIntoBin(String textFile, String binFile) {
		File f = new File(textFile);
		nbTotalLignes = 0;
		long positionBin;

		try (FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				RandomAccessFile raf = new RandomAccessFile(binFile, "rw")) {

			String ligne;
			String infosStagiaire = "";
			int posLine = 1;

			while ((ligne = br.readLine()) != null){

				switch(posLine) {
				case 1:
					ligne = ajouterEspace(CNOM,ligne);
					infosStagiaire += ligne;
					break;

				case 2:
					ligne = ajouterEspace(CPRENOM,ligne);
					infosStagiaire += ligne;
					break;

				case 3:
					ligne = ajouterEspace(CDEPARTEMENT,ligne);
					infosStagiaire += ligne;
					break;

				case 4:
					ligne = ajouterEspace(CPROMOTION,ligne);
					infosStagiaire += ligne;
					break;

				case 5:
					ligne = ajouterEspace(CANNEE,ligne);
					infosStagiaire += ligne;
				}

				posLine++;

				if(posLine == 6) {
					positionBin = 0;
					addABR(raf, infosStagiaire, positionBin);
					infosStagiaire = "";
					nbTotalLignes++;
					nbTotalStagiaires++;
					posLine = 0;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error when using convertTxtIntoBin funcion. Please check (StagiaireDAO CLASS)");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public void addABR (RandomAccessFile raf, String infosStagiaire, long positionCourante) {

		try {
			if (raf.length() == 0) {
				ecrireElementDansArbre(raf, infosStagiaire);

			} else {
				raf.seek(positionCourante);
				String cleCourante = lireChaine(raf, CARACTERES_TOTAL_STAGIAIRE);

				if (infosStagiaire.compareTo(cleCourante) < 0) {
					raf.seek(positionCourante + SAG);
					long posSag = raf.readLong();
					if (posSag < 0) {
						raf.seek(positionCourante + SAG);
						long positionDuNouvelElement = raf.length();
						raf.writeLong(positionDuNouvelElement);
						raf.seek(positionDuNouvelElement);
						ecrireElementDansArbre(raf, infosStagiaire);

					} else {
						addABR(raf, infosStagiaire, posSag);
					}

				} else {

					if (infosStagiaire.compareTo(cleCourante) > 0) {
						raf.seek(positionCourante + SAD);
						long posSad = raf.readLong();
						if (posSad < 0) {
							raf.seek(positionCourante + SAD);	
							long positionDuNouvelElement = raf.length();
							raf.writeLong(positionDuNouvelElement);
							raf.seek(positionDuNouvelElement);
							ecrireElementDansArbre(raf, infosStagiaire);
						} else {
							addABR(raf, infosStagiaire, posSad);
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

/*
		public void addABR (RandomAccessFile raf, String infosStagiaire, long positionCourante) {
	
			try {
				if (raf.length() == 0) {
					ecrireElementDansArbre(raf, infosStagiaire, positionCourante);
	
				} else {
					raf.seek(positionCourante);
					String cleCourante = lireChaine(raf, CARACTERES_TOTAL_STAGIAIRE);
	
					if (infosStagiaire.compareTo(cleCourante) < 0) {
						raf.seek(positionCourante + SAG);
						long posSag = raf.readLong();
						if (posSag == 0) {
							raf.seek(positionCourante + SAG);
							long positionDuNouvelElement = raf.length();
							raf.writeLong(positionDuNouvelElement);
							raf.seek(positionDuNouvelElement);
							ecrireElementDansArbre(raf, infosStagiaire, positionCourante);
							//	System.out.println("position : " + positionDuNouvelElement + "\r\npostion parent : " + positionCourante + "\r\n");
	
						} else {
							addABR(raf, infosStagiaire, posSag);
						}
	
					} else {
	
						if (infosStagiaire.compareTo(cleCourante) > 0) {
							raf.seek(positionCourante + SAD);
							long posSad = raf.readLong();
							if (posSad == 0) {
								raf.seek(positionCourante + SAD);	
								long positionDuNouvelElement = raf.length();
								raf.writeLong(positionDuNouvelElement);
								raf.seek(positionDuNouvelElement);
								ecrireElementDansArbre(raf, infosStagiaire, positionCourante);
								//	System.out.println("position : " + positionDuNouvelElement + "\r\npostion parent : " + positionCourante + "\r\n");
							} else {
								addABR(raf, infosStagiaire, posSad);
							}
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
*/


	public List<Stagiaire> getAllStagiaires(){ //When the software opens, this function reads the BIN file directly and imports Students into the tableView accordingly.
		List<Stagiaire> listeStagiaires = new ArrayList<Stagiaire>();
		try {
			RandomAccessFile raf = new RandomAccessFile(nameBinFile, "rw");
			if (raf.length() > 0) {
				listeStagiaires = lectureInfixe(raf, listeStagiaires, 0);
			}

			raf.close();
		} catch ( IOException e) {
			e.printStackTrace();
		}

		return listeStagiaires;
	}


	public List<Stagiaire> lectureInfixe (RandomAccessFile raf, List<Stagiaire> listeStagiaires, long positionCourante) {

		try {
			raf.seek(positionCourante + SAG);
			long posSag = raf.readLong();
			if (posSag == positionCourante) {
				raf.seek(positionCourante);
				String stagiaireRate = lireChar(CARACTERES_TOTAL_STAGIAIRE, raf);
				System.out.println(stagiaireRate);
			}
			if (posSag > 0) {
				lectureInfixe(raf, listeStagiaires, posSag);
			}
			raf.seek(positionCourante);
			String nom = lireChar(CNOM, raf);
			nom = nom.trim();
			String prenom = lireChar(CPRENOM, raf);
			prenom = prenom.trim();
			String departement = lireChar(CDEPARTEMENT, raf);
			departement = departement.trim();
			String promotion = lireChar(CPROMOTION, raf);
			promotion = promotion.trim();
			String annee = lireChar(CANNEE, raf);
			annee = annee.trim();

			Stagiaire stg = new Stagiaire(nom,prenom,departement,promotion,annee);
			listeStagiaires.add(stg); 

			raf.seek(positionCourante + SAD);
			long posSad = raf.readLong();
			if (posSad > 0) {
				lectureInfixe(raf, listeStagiaires, posSad);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeStagiaires;
	}



	public void deleteABR (RandomAccessFile raf, String infosStagiaire, long positionCourante, long positionParent) {

		try {
			raf.seek(positionCourante);

			String cleCourante = lireChaine(raf, CARACTERES_TOTAL_STAGIAIRE);

			if (infosStagiaire.compareTo(cleCourante) < 0) {
				raf.seek(positionCourante + SAG);
				long posSag = raf.readLong();
				if (posSag < 0) {
					System.out.println("élément non trouvé, suppression impossible");
				} else {
					deleteABR(raf, infosStagiaire, posSag, positionCourante);
				}

			} else {
				if (infosStagiaire.compareTo(cleCourante) > 0) {

					raf.seek(positionCourante + SAD);
					long posSad = raf.readLong();
					if (posSad < 0) {
						System.out.println("élément non trouvé, suppression impossible");
					} else {
						deleteABR(raf, infosStagiaire, posSad, positionCourante);
					}

				} else {																				// L'élément est trouvé
					long posSag = raf.readLong();														// 4 cas possibles à traiter :				
					long posSad = raf.readLong();														// pas de sous-arbre, un SAG, un SAD, 2 sous arbres

					if (posSad < 0) {																	// Cas 1 et 2
						raf.seek(positionParent);
						cleCourante = lireChaine(raf, CARACTERES_TOTAL_STAGIAIRE);
						if (infosStagiaire.compareTo(cleCourante) < 0) {
							raf.seek(positionParent + SAG);
							raf.writeLong(posSag);
						} else {
							if (infosStagiaire.compareTo(cleCourante) > 0) {
								raf.seek(positionParent + SAD);
								raf.writeLong(posSag);
							}
						}

					} else {
						if (posSag < 0) {																// Cas 3

							raf.seek(positionParent);
							cleCourante = lireChaine(raf, CARACTERES_TOTAL_STAGIAIRE);
							if (infosStagiaire.compareTo(cleCourante) < 0) {
								raf.seek(positionParent + SAG);
								raf.writeLong(posSad);
							} else {
								if (infosStagiaire.compareTo(cleCourante) > 0) {
									raf.seek(positionParent + SAD);
									raf.writeLong(posSad);
								}
							}

						} else {																		// Cas 4 : l'élément à supprimer possède un SAG et un SAD,
																										// on recherche donc l'élément qui va le remplacer
							long posSuccesseur = rechercheSuccesseur(raf, posSag, positionCourante);
																										// ATTENTION !!!!!
							raf.seek(positionParent);
							cleCourante = lireChaine(raf, CARACTERES_TOTAL_STAGIAIRE);					// SI LE SUCCESSEUR EST UN DES SOUS-ARBRES ON VA DANS LE MUR !!!
																										// --> on aura alors posSad ou posSag == successeur !!!!
							if (infosStagiaire.compareTo(cleCourante) < 0) {							// --> stack overflow lors de la relecture du fichier binaire
								raf.seek(positionParent + SAG);
								raf.writeLong(posSuccesseur);

								raf.seek(posSuccesseur + SAG);
								if (posSuccesseur == posSag) {
									raf.writeLong(-1);
								} else {
									raf.writeLong(posSag);
								}

								raf.seek(posSuccesseur + SAD);
								raf.writeLong(posSad);

							} else {

								if (infosStagiaire.compareTo(cleCourante) > 0) {
									raf.seek(positionParent + SAD);
									raf.writeLong(posSuccesseur);

									raf.seek(posSuccesseur + SAG);
									if (posSuccesseur == posSag) {
										raf.writeLong(-1);
									} else {
										raf.writeLong(posSag);
									}

									raf.seek(posSuccesseur + SAD);
									raf.writeLong(posSad);

								} else {

									raf.seek(posSuccesseur + SAG);
									if (posSuccesseur == posSag) {
										raf.writeLong(-1);
									} else {
										raf.writeLong(posSag);
									}

									raf.seek(posSuccesseur + SAD);
									raf.writeLong(posSad);
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public long rechercheSuccesseur(RandomAccessFile raf, long positionCourante, long positionPrecedente) {
		long positionSuivante;
		try {
			raf.seek(positionCourante + SAD);
			positionSuivante = raf.readLong();

			if (positionSuivante < 0) {
				raf.seek(positionCourante + SAG);
				long filsPotentiel = raf.readLong();
				raf.seek(positionPrecedente + SAD);
				raf.writeLong(filsPotentiel);

			} else {
				positionCourante = rechercheSuccesseur(raf, positionSuivante, positionCourante);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return positionCourante;
	}




	public void ecrireElementDansArbre (RandomAccessFile raf, String infosElement) {		
		try {
			raf.writeChars(infosElement);
			raf.writeLong(-1);
			raf.writeLong(-1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static String ajouterEspace(int num, String ligne) { //Dependant on convertTxtIntoBin() function.
		if (ligne.length() > num) {
			ligne = ligne.substring(0, num);
			System.out.println("Trop de caractères; cette rubrique a été limitée à " + num + "caractères. L'entrée a donc été tronquée");
		}
		int nbEspaces = num - ligne.length();		
		for(int i=0;i<nbEspaces;i++)
			ligne += ' ';

		return ligne;
	}



	public static String lireChar(int num, RandomAccessFile raf) { //Dependant on getAllStagiaires() function
		String phrase = "";
		try {
			for(int j=0;j<num;j++)
				phrase += raf.readChar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return phrase;
	}



	public static String lireChaine(RandomAccessFile raf,int taille) throws IOException {
		String chaine = "";
		for(int i=0;i<taille;i++)
			chaine += raf.readChar();

		return chaine;
	}



	public static String stagiaireToString (Stagiaire stagiaire) {
		String nom = stagiaire.getNom();
		nom = ajouterEspace(CNOM, nom);
		String prenom = stagiaire.getPrenom();
		prenom = ajouterEspace(CPRENOM, prenom);
		String departement = stagiaire.getDepartement();
		departement = ajouterEspace(CDEPARTEMENT, departement);
		String promotion = stagiaire.getPromotion();
		promotion = ajouterEspace(CPROMOTION, promotion);
		String annee = stagiaire.getAnnee();
		annee = ajouterEspace(CANNEE, annee);

		String infosStagiaire = nom + prenom + departement + promotion + annee;

		return infosStagiaire;

	}



	public static Stagiaire stringToStagiaire (String infosStagiaire) {

		String nom = infosStagiaire.substring(0, CNOM);
		nom = nom.trim();
		String prenom = infosStagiaire.substring(CNOM, (CNOM + CPRENOM));
		prenom = prenom.trim();
		String departement = infosStagiaire.substring((CNOM + CPRENOM), (CNOM + CPRENOM + CDEPARTEMENT));
		departement = departement.trim();
		String promotion = infosStagiaire.substring((CNOM + CPRENOM + CDEPARTEMENT), (CNOM + CPRENOM + CDEPARTEMENT + CPROMOTION));
		departement = departement.trim();
		String annee = infosStagiaire.substring((CNOM + CPRENOM + CDEPARTEMENT + CPROMOTION), (CNOM + CPRENOM + CDEPARTEMENT + CPROMOTION + CANNEE));
		annee = annee.trim();

		Stagiaire stagiaire = new Stagiaire(nom, prenom, departement, promotion, annee);

		return stagiaire;
	}





	public String getNameBinFile() {
		return nameBinFile;
	}

	public void setNameBinFile(String nameBinFile) {
		this.nameBinFile = nameBinFile;
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	public static int getNbTotalLignes() {
		return nbTotalLignes;
	}

	public static void setNbTotalLignes(int nbTotalLignes) {
		StagiaireDAO.nbTotalLignes = nbTotalLignes;
	}

	public static int getCARACTERES_TOTAL_STAGIAIRE() {
		return CARACTERES_TOTAL_STAGIAIRE;
	}

	public static void setCARACTERES_TOTAL_STAGIAIRE(int cARACTERES_TOTAL_STAGIAIRE) {
		CARACTERES_TOTAL_STAGIAIRE = cARACTERES_TOTAL_STAGIAIRE;
	}

	public int getNbTotalStagiaires() {
		return nbTotalStagiaires;
	}

	public void setNbTotalStagiaires(int nbTotalStagiaires) {
		this.nbTotalStagiaires = nbTotalStagiaires;
	}

	public static int getCnom() {
		return CNOM;
	}

	public static int getCprenom() {
		return CPRENOM;
	}

	public static int getCdepartement() {
		return CDEPARTEMENT;
	}

	public static int getCpromotion() {
		return CPROMOTION;
	}

	public static int getCannee() {
		return CANNEE;
	}

	public static int getLongueurInfoStagiaire() {
		return LONGUEUR_INFO_STAGIAIRE;
	}

	public static int getSag() {
		return SAG;
	}

	public static int getSad() {
		return SAD;
	}


}
