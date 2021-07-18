package fr.afcepf.ai105.projet1.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import fr.afcepf.ai105.projet1.ihm.MainPanel;
import fr.afcepf.ai105.projet1.test.Main;
import javafx.stage.Stage;

public class AdminPassword {
	public static String motDePasseFichier;
	public static boolean isUser = true;

	public AdminPassword() throws IOException {
		String passFile = "data.txt";
		File f = new File(passFile);
		
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
	
		motDePasseFichier = br.readLine();
		
		System.out.println(motDePasseFichier); //DELETE IN THE END.
		
		fr.close();
		br.close();
		
	}
	
	public AdminPassword(String s) throws IOException {
		String passFile = "data.txt";
		File f = new File(passFile);
		FileWriter fr = new FileWriter(f,false);
		
		motDePasseFichier = s;
		fr.write(motDePasseFichier);
		
		System.out.println(s); //DELETE IN THE END.
		
		fr.close();

		
	}
	
//	public static String encryptPassword (String mdp) {
//		String mdpEncripted = "";
//		
//		
//		
//		return mdpEncripted;
//	}
}
