package fr.afcepf.ai105.projet1.test;

import fr.afcepf.ai105.projet1.ihm.AcceuilPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Stage stage; //C'est nécessaire seulement pour maximizer le MainPanel. 
	
	public static Stage getStage() {
		return stage;
	}
	

	public static void main(String[] args) {
		launch(args);

	}
 
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		stage = primaryStage;
		AcceuilPanel ac = new AcceuilPanel();
		Scene scene = new Scene(ac,350,300);
		primaryStage.setScene(scene);
	
		primaryStage.show();
		primaryStage.setTitle("Annuaire AFCEPF");
		primaryStage.setResizable(false);
		primaryStage.setMaximized(false);
		
	}
	
	

}
