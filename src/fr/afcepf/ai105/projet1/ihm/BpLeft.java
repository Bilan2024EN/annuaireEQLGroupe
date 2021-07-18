package fr.afcepf.ai105.projet1.ihm;

import fr.afcepf.ai105.projet1.data.Stagiaire;
import fr.afcepf.ai105.projet1.data.StagiaireDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class BpLeft extends BorderPane {

	GpLeft gpLeft = new GpLeft();
	Button btnMettreAJour = new Button("ACTUALISER");
	VBox vbImage = new VBox();

	public BpLeft() {

		btnMettreAJour.setMaxWidth(190);
		btnMettreAJour.setMaxHeight(100);
		btnMettreAJour.setStyle("-fx-font-size: 14pt; -fx-background-insets: 5; -fx-border-color:darkgrey");

		ImageView selectedImage = new ImageView();
		Image img = new Image("file:afcepf.png");
		selectedImage.setImage(img);
		vbImage.getChildren().add(selectedImage);
		vbImage.setAlignment(Pos.CENTER);
		vbImage.setPrefSize(50, 100);


		setStyle("-fx-background-color: grey");

		setPrefSize(300,50);
		setTop(gpLeft);
		setCenter(btnMettreAJour);
		setBottom(vbImage);
		

		btnMettreAJour.setOnAction(new EventHandler<ActionEvent>(){
			//IMPORTANT NOTE: If "Mettre a Jour" does not properly update the table, make sure the ObservableList is established as a class attribute inside "ApRight.java".
			@Override
			public void handle(ActionEvent event) {
				MainPanel mp = (MainPanel) getScene().getRoot(); 
				TableView<Stagiaire> tableView = mp.getApRight().getTableView(); 
				mp.getApRight().getChildren().remove(tableView);
				ApRight ap = new ApRight();
				mp.setRight(ap);
			}
		});
	}


	public GpLeft getGpLeft() {
		return gpLeft;
	}

	public void setGpLeft(GpLeft gpLeft) {
		this.gpLeft = gpLeft;
	}


}
