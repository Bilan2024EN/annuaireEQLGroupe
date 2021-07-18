package fr.afcepf.ai105.projet1.ihm;

import java.awt.Component;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import fr.afcepf.ai105.projet1.data.AdminPassword;
import fr.afcepf.ai105.projet1.data.PDFExport;
import fr.afcepf.ai105.projet1.data.Stagiaire;
import fr.afcepf.ai105.projet1.data.StagiaireDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

public class HbBottom extends HBox {
	
	private int widthButtons = 140;
	private int heightButtons = 40;
	
	private Button btnAjouter = new Button("Ajouter");
	private Button btnModifier = new Button("Modifier");
	private Button btnSupprimer = new Button("Supprimer");
	private Button btnRechercher = new Button("Rechercher");
	private Button btnInitialiser = new Button("Initialiser");
	
	List<Stagiaire> listeStagiairesRecherche = new ArrayList<Stagiaire>();

	public HbBottom() {
	
		btnAjouter.setPrefWidth(widthButtons);
		btnAjouter.setPrefHeight(heightButtons);
		btnModifier.setPrefWidth(widthButtons);
		btnModifier.setPrefHeight(heightButtons);
		btnSupprimer.setPrefWidth(widthButtons);
		btnSupprimer.setPrefHeight(heightButtons);
		btnRechercher.setPrefWidth(widthButtons);
		btnRechercher.setPrefHeight(heightButtons);
		btnInitialiser.setPrefWidth(widthButtons);
		btnInitialiser.setPrefHeight(heightButtons);
		
		//Validation
		btnModifier.setDisable(true);
		btnSupprimer.setDisable(true);
		
		if(!AdminPassword.isUser) {// If the admin enters the problem, let him click on the buttons.
			btnModifier.setDisable(false);
			btnSupprimer.setDisable(false);
		}
		

		setSpacing(20);
		setAlignment(Pos.CENTER);
			
		
		setStyle("-fx-background-color: darkgrey; -fx-font-size: 14pt");
		setPrefSize(400, 75);
		
		getChildren().addAll(btnAjouter, btnModifier, btnSupprimer, btnRechercher);
		
		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainPanel mp = (MainPanel) getScene().getRoot();
			
			
				String nom = mp.getBpLeft().getGpLeft().getTfNom().getText();
				String prenom = mp.getBpLeft().getGpLeft().getTfPrenom().getText();
				String departement = mp.getBpLeft().getGpLeft().getTfDepart().getText();
				String promotion = mp.getBpLeft().getGpLeft().getTfPromo().getText();
				String annee = mp.getBpLeft().getGpLeft().getTfAnne().getText();
				
				String tailleString = nom + prenom + departement + promotion + annee;
				
				if(!tailleString.equals("")){
				
				int result = JOptionPane.showConfirmDialog((Component) null, "Voulez-vous ajouter ce stagiere ?",
						"Fonction Ajouter", JOptionPane.OK_CANCEL_OPTION);
				System.out.println("result : " + result); //À eliminer
				
				
				if(result == 0) {
					StagiaireDAO sDao = new StagiaireDAO();
					RandomAccessFile raf;
					try {
						raf = new RandomAccessFile(sDao.getNameBinFile(),"rw");
						
						nom = sDao.ajouterEspace(sDao.getCnom(), nom);
						prenom = sDao.ajouterEspace(sDao.getCprenom(), prenom);
						departement = sDao.ajouterEspace(sDao.getCdepartement(), departement);
						promotion = sDao.ajouterEspace(sDao.getCpromotion(), promotion);
						annee = sDao.ajouterEspace(sDao.getCannee(), annee);

						String infosStagiaire = nom + prenom + departement + promotion + annee;

						sDao.addABR(raf, infosStagiaire, 0);
						
						TableView<Stagiaire> tableView = mp.getApRight().getTableView(); //It updates the list after pressing "Supr"
						mp.getApRight().getChildren().remove(tableView);
						ApRight ap = new ApRight();
						mp.setRight(ap);

						raf.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				}else {
					JOptionPane.showMessageDialog(null, "Le formulaire est vide!");
				}
			}
		});

		
		btnRechercher.setOnAction(new EventHandler<ActionEvent>() { //DO NOT DELETE

			@Override
			public void handle(ActionEvent event) {
				
				listeStagiairesRecherche = new ArrayList<Stagiaire>();
				boolean stagiereFound = false;
				
				StagiaireDAO sDao = new StagiaireDAO();
				List<Stagiaire> listeStagiaires = sDao.getAllStagiaires(); //Prendre la liste en memoire

				MainPanel mp = (MainPanel) getScene().getRoot();
				
				String nomText = mp.getBpLeft().getGpLeft().getTfNom().getText();
				String prenomText = mp.getBpLeft().getGpLeft().getTfPrenom().getText();
				String departmentText = mp.getBpLeft().getGpLeft().getTfDepart().getText();
				String promotionText = mp.getBpLeft().getGpLeft().getTfPromo().getText();
				String anneText = mp.getBpLeft().getGpLeft().getTfAnne().getText();
				
				Stagiaire stagiereSearched = new Stagiaire(nomText,prenomText,departmentText,promotionText,anneText);

				//String[] stagiereSearched = {nomText, prenomText,departmentText,promotionText,anneText};
				
				for(int i=0;i<listeStagiaires.size();i++) {
					stagiereFound = isStagiereFound(stagiereSearched,listeStagiaires,i);
					
					if(stagiereFound) {
						listeStagiairesRecherche.add(listeStagiaires.get(i)); 
						System.out.print  ("\""+listeStagiaires.get(i).getNom()+"\"");
						System.out.print  ("\""+listeStagiaires.get(i).getPrenom()+"\"");
						System.out.print  ("\""+listeStagiaires.get(i).getDepartement()+"\"");
						System.out.print  ("\""+listeStagiaires.get(i).getPromotion()+"\"");
						System.out.println("\""+listeStagiaires.get(i).getAnnee()+"\"");
					}
				}
				PDFExport.listeStagRecherchePDF = listeStagiairesRecherche;
				ApRight	ap = new ApRight(listeStagiairesRecherche);
				mp.setRight(ap);
				
			}
		});
		
		//SUPPRIMER
		btnSupprimer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int result = JOptionPane.showConfirmDialog((Component) null, "Voulez-vous supprimer ce stagiaire ?",
						"Fonction Supprimer", JOptionPane.OK_CANCEL_OPTION);
				System.out.println("result : " + result);
				if(result == 0) { // Ça veut dire que tu as pressé "YES"
					RandomAccessFile raf;
					try {
						MainPanel mp = (MainPanel) getScene().getRoot();

						raf = new RandomAccessFile(mp.getApRight().getsDao().getNameBinFile(),"rw");

						String nom = mp.getBpLeft().getGpLeft().getTfNom().getText();
						nom = StagiaireDAO.ajouterEspace(StagiaireDAO.getCnom(), nom);
						String prenom = mp.getBpLeft().getGpLeft().getTfPrenom().getText();
						prenom = StagiaireDAO.ajouterEspace(StagiaireDAO.getCprenom(), prenom);
						String departement = mp.getBpLeft().getGpLeft().getTfDepart().getText();
						departement = StagiaireDAO.ajouterEspace(StagiaireDAO.getCdepartement(), departement);
						String promotion = mp.getBpLeft().getGpLeft().getTfPromo().getText();
						promotion = StagiaireDAO.ajouterEspace(StagiaireDAO.getCpromotion(), promotion);
						String annee = mp.getBpLeft().getGpLeft().getTfAnne().getText();
						annee = StagiaireDAO.ajouterEspace(StagiaireDAO.getCannee(), annee);

						String infosStagiaire = nom + prenom + departement + promotion + annee;

						Stagiaire stagiaire = mp.getApRight().getTableView().getSelectionModel().getSelectedItem();
						System.out.println(infosStagiaire);

						mp.getApRight().getsDao().deleteABR(raf, infosStagiaire, 0, 0);
						System.out.println(infosStagiaire);
						
						TableView<Stagiaire> tableView = mp.getApRight().getTableView(); //It updates the list after pressing "Supr"
						mp.getApRight().getChildren().remove(tableView);
						ApRight ap = new ApRight();
						mp.setRight(ap);

						raf.close();


					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//Sinon, tu canceles.

			}
		});
		
		btnModifier.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				int result = JOptionPane.showConfirmDialog((Component) null, "Voulez-vous modifier ce stagiaire ?",
						"Fonction Modifier", JOptionPane.OK_CANCEL_OPTION);
				System.out.println("result : " + result);
				if(result == 0) { // Ça veut dire que tu as pressé "YES"
					RandomAccessFile raf;
					try {

						StagiaireDAO sDao = new StagiaireDAO();
						MainPanel mp = (MainPanel) getScene().getRoot();
						
						raf = new RandomAccessFile(mp.getApRight().getsDao().getNameBinFile(),"rw");

						String nom = mp.getBpLeft().getGpLeft().getTfNom().getText();
						nom = sDao.ajouterEspace(sDao.getCnom(), nom);
						String prenom = mp.getBpLeft().getGpLeft().getTfPrenom().getText();
						prenom = sDao.ajouterEspace(sDao.getCprenom(), prenom);
						String departement = mp.getBpLeft().getGpLeft().getTfDepart().getText();
						departement = sDao.ajouterEspace(sDao.getCdepartement(), departement);
						String promotion = mp.getBpLeft().getGpLeft().getTfPromo().getText();
						promotion = sDao.ajouterEspace(sDao.getCpromotion(), promotion);
						String annee = mp.getBpLeft().getGpLeft().getTfAnne().getText();
						annee = sDao.ajouterEspace(sDao.getCannee(), annee);

						String newInfosStagiaire = nom + prenom + departement + promotion + annee;

						String oldInfosStagiaire = ApRight.getInfoStagiaire();
						System.out.println(oldInfosStagiaire);

						System.out.println(newInfosStagiaire);
						
						mp.getApRight().getsDao().deleteABR(raf, oldInfosStagiaire, 0, 0);
						mp.getApRight().getsDao().addABR(raf, newInfosStagiaire, 0);
						
						TableView<Stagiaire> tableView = mp.getApRight().getTableView(); //It updates the list after pressing "Supr"
						mp.getApRight().getChildren().remove(tableView);
						ApRight ap = new ApRight();
						mp.setRight(ap);
						
						raf.close();
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				//Sinon, tu canceles.
			}
		});
		
		
		

	}
	
	public boolean isStagiereFound(Stagiaire stagiereSearched, List<Stagiaire> stagMemory ,int index) {
		
		if( !stagiereSearched.getNom().equals("")  && !stagMemory.get(index).getNom().startsWith(stagiereSearched.getNom()) )  return false;
		if( !stagiereSearched.getPrenom().equals("") && !stagMemory.get(index).getPrenom().startsWith(stagiereSearched.getPrenom()) )  return false;
		if( !stagiereSearched.getDepartement().equals("") && !stagMemory.get(index).getDepartement().startsWith(stagiereSearched.getDepartement()) )  return false;	
		if( !stagiereSearched.getPromotion().equals("") && !stagMemory.get(index).getPromotion().startsWith(stagiereSearched.getPromotion()) )  return false;
		if( !stagiereSearched.getAnnee().equals("") && !stagMemory.get(index).getAnnee().startsWith(stagiereSearched.getAnnee()) )  return false;

		return true;
	}
	

	public Button getBtnAjouter() {
		return btnAjouter;
	}

	public void setBtnAjouter(Button btnAjouter) {
		this.btnAjouter = btnAjouter;
	}

	public Button getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(Button btnModifier) {
		this.btnModifier = btnModifier;
	}

	public Button getBtnSupprimer() {
		return btnSupprimer;
	}

	public void setBtnSupprimer(Button btnSupprimer) {
		this.btnSupprimer = btnSupprimer;
	}

	public Button getbtnRechercher() {
		return btnRechercher;
	}

	public void setbtnRechercher(Button btnRechercher) {
		this.btnRechercher = btnRechercher;
	}

	public Button getBtnInitialiser() {
		return btnInitialiser;
	}

	public void setBtnInitialiser(Button btnInitialiser) {
		this.btnInitialiser = btnInitialiser;
	}
	
	
	

}
