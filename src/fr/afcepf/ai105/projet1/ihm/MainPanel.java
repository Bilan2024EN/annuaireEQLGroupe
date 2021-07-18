package fr.afcepf.ai105.projet1.ihm;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import fr.afcepf.ai105.projet1.test.Main;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class MainPanel extends BorderPane{
	
	BpLeft bpLeft = new BpLeft();
	ApRight apRight = new ApRight();
	HbTop hbTop = new HbTop();
	HbBottom hbBottom = new HbBottom();

	
	public MainPanel() {
		
		setPrefWidth(1920);
		setPrefHeight(980);
		//setPrefHeight(500);
		
		setTop(hbTop);
		setBottom(hbBottom);
		setRight(apRight);
		setLeft(bpLeft);
		setTop(hbTop);
		
		Stage primaryStage = Main.getStage(); //Calls the primary Scene. Can't use the usual call function.
		primaryStage.setMaximized(true);
		primaryStage.setResizable(true);
		
	}
	


	public BpLeft getBpLeft() {
		return bpLeft;
	}

	public void setBpLeft(BpLeft bpLeft) {
		this.bpLeft = bpLeft;
	}

	public ApRight getApRight() {
		return apRight;
	}

	public void setApRight(ApRight apRight) {
		this.apRight = apRight;
	}

	public HbTop getHbTop() {
		return hbTop;
	}

	public void setHbTop(HbTop hbTop) {
		this.hbTop = hbTop;
	}

	public HbBottom getHbBottom() {
		return hbBottom;
	}

	public void setHbBottom(HbBottom hbBottom) {
		this.hbBottom = hbBottom;
	}
	
	
}
