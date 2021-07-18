package fr.afcepf.ai105.projet1.ihm;

import java.util.List;

import fr.afcepf.ai105.projet1.data.Stagiaire;
import fr.afcepf.ai105.projet1.data.StagiaireDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ApRight extends AnchorPane{

	StagiaireDAO sDao = new StagiaireDAO();
	ObservableList<Stagiaire> observableStagiaire = FXCollections.observableArrayList(sDao.getAllStagiaires());
	TableView<Stagiaire> tableView = new TableView<Stagiaire>(observableStagiaire);
	private static String infoStagiaire = "";


	public static String getInfoStagiaire() {
		return infoStagiaire;
	}

	public ApRight() {


		TableColumn<Stagiaire,String> colNom = new TableColumn<Stagiaire,String>("Nom"); 
		colNom.setCellValueFactory(new PropertyValueFactory<Stagiaire,String>("nom")); //must be the name of the attribute.
		TableColumn<Stagiaire,String> colPrenom = new TableColumn<Stagiaire,String>("Prenom"); 
		colPrenom.setCellValueFactory(new PropertyValueFactory<Stagiaire,String>("prenom")); //must be the name of the attribute.
		TableColumn<Stagiaire,String> colDepart = new TableColumn<Stagiaire,String>("Departement"); 
		colDepart.setCellValueFactory(new PropertyValueFactory<Stagiaire,String>("departement")); //must be the name of the attribute.
		TableColumn<Stagiaire,String> colPromo = new TableColumn<Stagiaire,String>("Promotion"); 
		colPromo.setCellValueFactory(new PropertyValueFactory<Stagiaire,String>("promotion")); //must be the name of the attribute.
		TableColumn<Stagiaire,String> colAnne = new TableColumn<Stagiaire,String>("Année"); 
		colAnne.setCellValueFactory(new PropertyValueFactory<Stagiaire,String>("annee")); //must be the name of the attribute.

		tableView.getColumns().addAll(colNom,colPrenom,colDepart,colPromo,colAnne);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		getChildren().add(tableView);
		setPrefSize(1620,200);
		
		

		AnchorPane.setTopAnchor(tableView, 5.);
		AnchorPane.setBottomAnchor(tableView, 5.);
		AnchorPane.setRightAnchor(tableView, 5.);
		AnchorPane.setLeftAnchor(tableView, 5.);


		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Stagiaire>() {

			@Override
			public void changed(ObservableValue<? extends Stagiaire> observable, Stagiaire oldValue, Stagiaire newValue) {
				MainPanel mp = (MainPanel) getScene().getRoot();

				mp.getBpLeft().getGpLeft().getTfNom().setText(newValue.getNom()); // Assing the value of the tableView into the Textfield.
				mp.getBpLeft().getGpLeft().getTfPrenom().setText(newValue.getPrenom());
				mp.getBpLeft().getGpLeft().getTfDepart().setText(newValue.getDepartement());
				mp.getBpLeft().getGpLeft().getTfPromo().setText(newValue.getPromotion());
				mp.getBpLeft().getGpLeft().getTfAnne().setText(newValue.getAnnee());
				
				infoStagiaire = StagiaireDAO.stagiaireToString(newValue);
			}

		});

	}
	
	public ApRight(List<Stagiaire> listeStagiairesRecherche) {
		
		ObservableList<Stagiaire> observableStagiaireRecherche;
		observableStagiaireRecherche = FXCollections.observableArrayList(listeStagiairesRecherche); // not from getAllStagiaires.
		tableView = new TableView<Stagiaire>(observableStagiaireRecherche);

		TableColumn<Stagiaire,String> colNom = new TableColumn<Stagiaire,String>("Nom"); 
		colNom.setCellValueFactory(new PropertyValueFactory<Stagiaire,String>("nom")); //must be the name of the attribute.
		TableColumn<Stagiaire,String> colPrenom = new TableColumn<Stagiaire,String>("Prenom"); 
		colPrenom.setCellValueFactory(new PropertyValueFactory<Stagiaire,String>("prenom")); //must be the name of the attribute.
		TableColumn<Stagiaire,String> colDepart = new TableColumn<Stagiaire,String>("Departement"); 
		colDepart.setCellValueFactory(new PropertyValueFactory<Stagiaire,String>("departement")); //must be the name of the attribute.
		TableColumn<Stagiaire,String> colPromo = new TableColumn<Stagiaire,String>("Promotion"); 
		colPromo.setCellValueFactory(new PropertyValueFactory<Stagiaire,String>("promotion")); //must be the name of the attribute.
		TableColumn<Stagiaire,String> colAnne = new TableColumn<Stagiaire,String>("Année"); 
		colAnne.setCellValueFactory(new PropertyValueFactory<Stagiaire,String>("annee")); //must be the name of the attribute.

		tableView.getColumns().addAll(colNom,colPrenom,colDepart,colPromo,colAnne);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		getChildren().add(tableView);
		setPrefSize(1620,200);
		
		

		AnchorPane.setTopAnchor(tableView, 5.);
		AnchorPane.setBottomAnchor(tableView, 5.);
		AnchorPane.setRightAnchor(tableView, 5.);
		AnchorPane.setLeftAnchor(tableView, 5.);

		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Stagiaire>() {

			@Override
			public void changed(ObservableValue<? extends Stagiaire> observable, Stagiaire oldValue, Stagiaire newValue) {
				MainPanel mp = (MainPanel) getScene().getRoot();

				mp.getBpLeft().getGpLeft().getTfNom().setText(newValue.getNom()); // Assing the value of the tableView into the Textfield.
				mp.getBpLeft().getGpLeft().getTfPrenom().setText(newValue.getPrenom());
				mp.getBpLeft().getGpLeft().getTfDepart().setText(newValue.getDepartement());
				mp.getBpLeft().getGpLeft().getTfPromo().setText(newValue.getPromotion());
				mp.getBpLeft().getGpLeft().getTfAnne().setText(newValue.getAnnee());

				infoStagiaire = StagiaireDAO.stagiaireToString(newValue);
			}

		});

	}

	
	public StagiaireDAO getsDao() {
		return sDao;
	}

	public void setsDao(StagiaireDAO sDao) {
		this.sDao = sDao;
	}

	public TableView<Stagiaire> getTableView() {
		return tableView;
	}

	public void setTableView(TableView<Stagiaire> tableView) {
		this.tableView = tableView;
	}



}
