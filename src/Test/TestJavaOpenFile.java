package Test;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class TestJavaOpenFile {

	public static void main(String[] args) throws IOException {
		//text file, should be opening in default text editor
		File file = new File("C:\\Users\\formation\\Desktop\\GONZALEZ_OLMOS_JAVIER\\WSInitAlgo\\Projet1_AnnuaireAfcepf\\Manueldutilisation.pdf");

		//first check if Desktop is supported by Platform or not
		if(!Desktop.isDesktopSupported()){
			System.out.println("Desktop is not supported");
			return;
		}

		Desktop desktop = Desktop.getDesktop();
		if(file.exists()) desktop.open(file);

		//let's try to open PDF file
		file = new File("/Users/pankaj/java.pdf");
		if(file.exists()) desktop.open(file);
	}

}