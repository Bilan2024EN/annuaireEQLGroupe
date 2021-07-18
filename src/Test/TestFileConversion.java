package Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class TestFileConversion {
	
	static final int Cnom = 30;
	static final int Cprenom = 20;
	static final int Cdepart = 5;
	static final int Cpromo = 12;


	//Total 128
	
	static int tailleLigne = (Cnom+Cprenom+Cdepart+Cpromo)*2 + 4; //4 equals to Anné.
	

	public static void main(String[] args) throws IOException {
		String nameFileTxt = "E:\\Programas\\AFCEPF Proyecto\\stagiaires.txt";
		String nameFileBin = "E:\\Programas\\AFCEPF Proyecto\\stagiairesBin.bin";
		
		
		File fileTxt = new File(nameFileTxt);
		FileReader fr = new FileReader(fileTxt);
		BufferedReader br = new BufferedReader(fr);
		RandomAccessFile raf = new RandomAccessFile(nameFileBin, "rw");
	
		
		String ligne;
		int posLine = 1;
			int anne;
			int numLignes = 0;

	
		while ((ligne = br.readLine()) != null){
			
			if(ligne.equals("*"))
				numLignes++;
			
			switch(posLine) {
			case 1:
				ligne = ajouterEspace(Cnom,ligne);
				raf.writeChars(ligne);
				break;
			case 2:
				ligne = ajouterEspace(Cprenom,ligne);
				raf.writeChars(ligne);
				break;
			case 3:
				ligne = ajouterEspace(Cdepart,ligne);
				raf.writeChars(ligne);
				break;
			case 4:
				ligne = ajouterEspace(Cpromo,ligne);
				raf.writeChars(ligne);
				break;
			case 5:
				anne = Integer.parseInt(ligne);
				raf.writeInt(anne);
			}

			posLine++;
			
			if(posLine == 6)
				posLine = 0;
		}
		
		trierFichier(numLignes,raf);
		
		montrerFichierRaf(raf, numLignes);
		raf.close();
		
		System.out.println("File closed!");
		

	}
	
	public static String ajouterEspace(int num, String ligne) {
		int nbEspaces = num - ligne.length();
		for(int i=0;i<nbEspaces;i++)
			ligne += ' ';
		

		return ligne;
	}
	
	public static void trierFichier(int numLignes, RandomAccessFile raf) throws IOException {
		int imini;
		String chaine1 = "",chaine2 = "";
		
		for (int i=0;i<numLignes-1;i++) {
			imini = i;
			raf.seek(imini*tailleLigne);
			chaine1 = lireChaine(raf,Cnom);
			//System.out.println(chaine1);
			
			for(int j=(i+1);j<numLignes;j++) {
				raf.seek(j*tailleLigne);
				chaine2 = lireChaine(raf,Cnom);
				//System.out.println(chaine2);
				
				if(chaine1.compareTo(chaine2) > 0) {
					chaine1 = chaine2;
					imini = j;
				}
			}
			swapUser(imini,i,raf);
		}
	}
	
	public static String lireChaine(RandomAccessFile raf,int taille) throws IOException {
		String chaine = "";
		for(int i=0;i<taille;i++)
			chaine += raf.readChar();
		
		return chaine;
	}
	
	public static void swapUser(int numLigne1, int numLigne2, RandomAccessFile raf) throws IOException {
		String chaine1A = "", chaine2A = "";
		int surface2, surface1;

		//Lecture
		raf.seek(numLigne1*tailleLigne);
		chaine2A = lireChaine(raf, Cnom+Cprenom+Cdepart+Cpromo);
		surface2 = raf.readInt();

		raf.seek(numLigne2*tailleLigne);
		chaine1A = lireChaine(raf, Cnom+Cprenom+Cdepart+Cpromo);
		surface1 = raf.readInt();


		//Ecriture
		raf.seek(numLigne1*tailleLigne);
		raf.writeChars(chaine1A); 
		raf.writeInt(surface1); 

		raf.seek(numLigne2*tailleLigne);
		raf.writeChars(chaine2A); 
		raf.writeInt(surface2); 
	}
	
	public static void montrerFichierRaf(RandomAccessFile raf, int numLignes) { //Read current file.

		try {
			raf.seek(0);
			for(int i=0;i<numLignes;i++) {
				String phrase = "";
				phrase += lireChar(Cnom, raf);
				phrase += lireChar(Cprenom, raf);
				phrase += lireChar(Cdepart, raf);
				phrase += lireChar(Cpromo, raf);
				phrase += raf.readInt();
				
				System.out.println(phrase);

			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static String lireChar(int num, RandomAccessFile raf) {
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

}
