package fr.afcepf.ai105.projet1.ihm;


import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.JOptionPane;

import fr.afcepf.ai105.projet1.data.AdminPassword;
import fr.afcepf.ai105.projet1.data.PDFExport;
import fr.afcepf.ai105.projet1.data.Stagiaire;
import fr.afcepf.ai105.projet1.data.StagiaireDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HbTop extends HBox{

	private Menu mFichier = new Menu("Fichier");
	private MenuItem mExport = new MenuItem("Exporter fichier to PDF");
	private MenuItem mExportRecherche = new MenuItem("Exporter selection to PDF");
	private MenuItem mImport = new MenuItem("Importer *.txt");

	private Menu mEdit = new Menu("Edit");	
	private MenuItem mChangerMdp = new MenuItem("Changer mot de passe Admin");

	private Menu mRetourner = new Menu("Retourner");
	private MenuItem mAcceuil = new MenuItem("Retourner à l'acceuil");

	private Menu mHelp = new Menu("?");
	private MenuItem mManuel = new MenuItem("Manuel en PDF");
	private MenuItem mAfcepf = new MenuItem("AFCEPF site web");
	//private MenuItem mNum = new MenuItem("Support");
	//private MenuItem mContact = new MenuItem("Contact");


	private MenuBar mb = new MenuBar();


	public HbTop() {

		if(AdminPassword.isUser) {
			mChangerMdp.setDisable(true);
			//mImport.setDisable(true);
		}


		mFichier.getItems().addAll(mExport, mExportRecherche, mImport);
		mEdit.getItems().addAll(mChangerMdp);
		mRetourner.getItems().addAll(mAcceuil);
		mHelp.getItems().addAll(mManuel, mAfcepf);


		mb.getMenus().addAll(mFichier,mEdit,mRetourner, mHelp);
		mb.setMaxWidth(300);
		getChildren().add(mb); //VBox

		setSpacing(50);
		setAlignment(Pos.CENTER_LEFT);
		setStyle("-fx-background-color: darkgrey");
		setPrefSize(400, 40);


		//Button Action.
		mImport.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				StagiaireDAO sDao = new StagiaireDAO();
				
				FileChooser fs = new FileChooser();
				File initDir = new File("C:");
				fs.setInitialDirectory(initDir);
				
				Stage primaryStage = (Stage) getScene().getWindow();
				
				File f = fs.showOpenDialog(primaryStage.getOwner());
				if(f != null) {
					System.out.println(f.getAbsolutePath()); //TO REMOVE.
					sDao.setNameFile(f.getAbsolutePath());
					sDao.convertTxtIntoBin(sDao.nameFile, sDao.nameBinFile);
					
					//Clear tableView after importing.
					MainPanel mp = (MainPanel) getScene().getRoot(); 
					TableView<Stagiaire> tableView = mp.getApRight().getTableView(); 
					mp.getApRight().getChildren().remove(tableView);
					ApRight ap = new ApRight();
					mp.setRight(ap);
					
					JOptionPane.showMessageDialog(null, "Fichier import� correctement!");
				}
				
			}
		});
		
		mExport.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int result = JOptionPane.showConfirmDialog((Component) null, "Voulez-vous exporter votre fichier entier a PDF ?",
						"Fonction Modifier", JOptionPane.OK_CANCEL_OPTION);
				System.out.println("result : " + result);
				if(result == 0) { 
					//Action
					PDFExport pdf = new PDFExport();
					pdf.exportAll();
				}

			}
		});


		mExportRecherche.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int result = JOptionPane.showConfirmDialog((Component) null, "Voulez-vous exporter votre recherche a PDF ?",
						"Fonction Modifier", JOptionPane.OK_CANCEL_OPTION);
				System.out.println("result : " + result);
				if(result == 0) { 
					PDFExport pdf = new PDFExport();
					pdf.extractionTableViewToPDF(); //change to 1
				}

			}
		});

		mChangerMdp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String newPassword;
				JOptionPane inpOption = new JOptionPane();
	 			
				newPassword = inpOption.showInputDialog("Veuillez saisir votre nouveau mot de passe: ");

				try {
					if(newPassword != null && !newPassword.equals("")) {
						AdminPassword adminPassword = new AdminPassword(newPassword);
						JOptionPane.showMessageDialog(null,"Mot de passe modifi? correctement.");
					}
					else {
						JOptionPane.showMessageDialog(null,"Erreur! Le mot de passe doit avoir au moins un caractere.");
					}
					
				

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			}
		});

		mAcceuil.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				AcceuilPanel acc = new AcceuilPanel();
				Scene sc = new Scene(acc,350,300);
				Stage stage = (Stage) getScene().getWindow();
				stage.setScene(sc);

			}
		});

		mManuel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String pathManual = "Manueldutilisation.pdf";
				File file = new File(pathManual);

				//first check if Desktop is supported by Platform or not
				if(!Desktop.isDesktopSupported()){
					System.out.println("Desktop is not supported");
					return;
				}

				Desktop desktop = Desktop.getDesktop();
				if(file.exists()) {
					try {
						desktop.open(file);
					} catch (IOException e) {
						System.out.println("Manual not found!");
						e.printStackTrace();
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Le manuel n'a pas été trouvé dans le dossier principal");

			}
		});

		mAfcepf.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) getScene().getWindow();
				WebView browser = new WebView();
				final Scene newScene = new Scene(browser, 350, 300);
				//final Scene newScene = new Scene(browser, 350, 300);
				//			stage.setScene(scene);

				//Nouvelle fenêtre
				Stage newWindow = new Stage();
				newWindow.setTitle("AFCEPF");
				newWindow.setScene(newScene);

				// Position de la fenêtre
				newWindow.setX(stage.getX() + 200);
				newWindow.setY(stage.getY() + 100);

				newWindow.show();

				stage.show();

				WebEngine wE = browser.getEngine();
				wE.load("http://www.afcepf.fr");

			}
		});

		
	} //Fin HbTop
}

