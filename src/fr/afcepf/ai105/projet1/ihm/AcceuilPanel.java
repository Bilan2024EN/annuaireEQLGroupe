package fr.afcepf.ai105.projet1.ihm;

import java.io.IOException;

import javax.swing.JOptionPane;

import fr.afcepf.ai105.projet1.data.AdminPassword;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

public class AcceuilPanel extends BorderPane{

	HBox hbTop = new HBox(); //TITLE
	
	VBox vbLeft = new VBox();
		//Button btnImportTxt = new Button("Import *.txt");
		Button btnUtilisateur = new Button("Utilisateur");
		
	GridPane gpRight = new GridPane();
		Button btnAdmin = new Button("Administrateur");
		PasswordField tfPassword = new PasswordField();

	public AcceuilPanel() {
		try { //initializates object to read file and store password in memory.
			AdminPassword adminPass = new AdminPassword(); //It reads the password from the file.
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//IMAGE
		VBox vbImage = new VBox();
		ImageView selectedImage = new ImageView();
		Image img = new Image("file:annuaire.jpg");
		selectedImage.setImage(img);
		vbImage.getChildren().add(selectedImage);
		vbImage.setAlignment(Pos.CENTER);
		vbImage.setPrefSize(50, 100);
	
		//btnImportTxt.setFont(Font.font ("Arial",FontPosture.ITALIC,12));
		btnUtilisateur.setFont(Font.font ("Arial", 20));
		btnAdmin.setFont(Font.font ("Arial", 20));
		
		tfPassword.setPromptText("Mot de passe admin");
		tfPassword.setPrefWidth(80);

		hbTop.setAlignment(Pos.CENTER);
		hbTop.getChildren().add(vbImage);
	
		
		vbLeft.getChildren().addAll(btnUtilisateur); //btnImportTxt
		vbLeft.setStyle("-fx-background-color: darkgrey; -fx-border-color: black");
		vbLeft.setPrefSize(250, 100);
		vbLeft.setAlignment(Pos.CENTER);
		vbLeft.setSpacing(20);
		
		gpRight.addColumn(0,btnAdmin,tfPassword);
		gpRight.setVgap(20);
		gpRight.setStyle("-fx-background-color: darkgrey; -fx-border-color: black");
		gpRight.setAlignment(Pos.CENTER);

		gpRight.setPrefSize(120, 40);
		
		setTop(hbTop);
		setLeft(vbLeft);
		setCenter(gpRight);
	
		
		//ACTIONS
		
		btnUtilisateur.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				AdminPassword.isUser = true;
				MainPanel mp = new MainPanel();
				Scene scene = new Scene(mp);
				Stage stage = (Stage) getScene().getWindow();
				stage.setScene(scene);
			}
		});
		
		btnAdmin.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if(tfPassword.getText().equals(AdminPassword.motDePasseFichier)) {
					AdminPassword.isUser = false;
					MainPanel mp = new MainPanel();
					Scene scene = new Scene(mp);
					Stage stage = (Stage) getScene().getWindow();
					stage.setScene(scene);
				}
				else {
					JOptionPane.showMessageDialog(null, "Votre mot de passe est incorrect.");
				}

			}

		});
		
		tfPassword.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(tfPassword.getText().equals(AdminPassword.motDePasseFichier)) {
					AdminPassword.isUser = false;
					MainPanel mp = new MainPanel();
					Scene scene = new Scene(mp);
					Stage stage = (Stage) getScene().getWindow();
					stage.setScene(scene);
				}
				else {
					JOptionPane.showMessageDialog(null, "Votre mot de passe est incorrect.");
				}
				tfPassword.clear();
				
			}
		});

	}
}
