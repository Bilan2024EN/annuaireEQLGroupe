package fr.afcepf.ai105.projet1.ihm;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class GpLeft extends GridPane{

	private static int fontSize = 14;
	private static String fontStyle = "Calibri";

	private Label lblNom = new Label("Nom:");
	private Label lblPrenom = new Label("Prenom:");
	private Label lblDepart = new Label("Depart:");
	private Label lblPromo = new Label("Promo:");
	private Label lblAnne = new Label("Anne:");
	private Label lblVoid = new Label("");
	private Label lblVoid2 = new Label("");
	private Label lblVoid3 = new Label("");
	//private Label lblComplete = new Label ("");

	private Button btnEffacer = new Button("Effacer Texte");

	private TextField tfNom = new TextField("");
	private TextField tfPrenom = new TextField("");
	private TextField tfDepart = new TextField("");
	private TextField tfPromo = new TextField("");
	private TextField tfAnne = new TextField("");


	public GpLeft() {
		tfNom.setPrefWidth(180);
		lblNom.setFont(Font.font (fontStyle, FontWeight.BOLD, fontSize));
		lblPrenom.setFont(Font.font (fontStyle, FontWeight.BOLD, fontSize));
		lblDepart.setFont(Font.font (fontStyle, FontWeight.BOLD, fontSize));
		lblPromo.setFont(Font.font (fontStyle, FontWeight.BOLD, fontSize));
		lblAnne.setFont(Font.font (fontStyle, FontWeight.BOLD, fontSize));
//		lblComplete.setStyle("-fx-font-size: 12");
//		lblComplete.setFont(Font.font ("Arial", FontPosture.ITALIC, fontSize));
	

		
		btnEffacer.setAlignment(Pos.CENTER);
		btnEffacer.setPrefWidth(180);
		btnEffacer.setPrefHeight(180);
		btnEffacer.setStyle("-fx-font-size: 14pt");
		addRow(0, lblNom,tfNom);
		addRow(1, lblPrenom,tfPrenom);
		addRow(2, lblDepart,tfDepart);
		addRow(3, lblPromo,tfPromo);
		addRow(4, lblAnne,tfAnne);
		addRow(5,lblVoid);
		addRow(6,lblVoid2,btnEffacer);
		//addRow(7,lblVoid3,lblComplete);


		setPadding(new Insets(15));
		setHgap(20);
		setVgap(15);
		setStyle("-fx-background-color: grey");
		setAlignment(Pos.TOP_CENTER);
		setPrefSize(300,200);

		btnEffacer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				tfNom.setText("");
				tfPrenom.setText("");
				tfDepart.setText("");
				tfPromo.setText("");
				tfAnne.setText("");
			}
		});

	}
	

//	public Label getLblComplete() {
//		return lblComplete;
//	}
//
//	public void setLblComplete(Label lblComplete) {
//		this.lblComplete = lblComplete;
//	}

	public Label getLblNom() {
		return lblNom;
	}

	public void setLblNom(Label lblNom) {
		this.lblNom = lblNom;
	}

	public Label getLblPrenom() {
		return lblPrenom;
	}

	public void setLblPrenom(Label lblPrenom) {
		this.lblPrenom = lblPrenom;
	}

	public Label getLblDepart() {
		return lblDepart;
	}

	public void setLblDepart(Label lblDepart) {
		this.lblDepart = lblDepart;
	}

	public Label getLblPromo() {
		return lblPromo;
	}

	public void setLblPromo(Label lblPromo) {
		this.lblPromo = lblPromo;
	}

	public Label getLblAnne() {
		return lblAnne;
	}

	public void setLblAnne(Label lblAnne) {
		this.lblAnne = lblAnne;
	}

	public TextField getTfNom() {
		return tfNom;
	}

	public void setTfNom(TextField tfNom) {
		this.tfNom = tfNom;
	}

	public TextField getTfPrenom() {
		return tfPrenom;
	}

	public void setTfPrenom(TextField tfPrenom) {
		this.tfPrenom = tfPrenom;
	}

	public TextField getTfDepart() {
		return tfDepart;
	}

	public void setTfDepart(TextField tfDepart) {
		this.tfDepart = tfDepart;
	}

	public TextField getTfPromo() {
		return tfPromo;
	}

	public void setTfPromo(TextField tfPromo) {
		this.tfPromo = tfPromo;
	}

	public TextField getTfAnne() {
		return tfAnne;
	}

	public void setTfAnne(TextField tfAnne) {
		this.tfAnne = tfAnne;
	}


}
