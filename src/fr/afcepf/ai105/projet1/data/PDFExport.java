//https://howtodoinjava.com/apache-commons/read-generate-pdf-java-itext/
//https://github.com/itext/itextpdf/releases

package fr.afcepf.ai105.projet1.data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.afcepf.ai105.projet1.ihm.HbBottom;
import fr.afcepf.ai105.projet1.ihm.MainPanel;
import fr.afcepf.ai105.projet1.test.Main;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PDFExport{
	
	Document document = new Document();
	PdfWriter writer;
	
	public static List<Stagiaire> listeStagRecherchePDF;
	
	List<Stagiaire> listeStagiairesRecherche;

	public PDFExport(){

	}
	
	public void exportAll() { //Export to PDF directly from the File. Dependencies: lectureInfixePDF
		
		try{
			writer = PdfWriter.getInstance(document, new FileOutputStream("StagiairesTotal.pdf"));
			document.open();
				//Start
			StagiaireDAO sDao = new StagiaireDAO();
			RandomAccessFile raf = new RandomAccessFile(sDao.getNameBinFile(),"r");
			int nbTotalLignes = (int) (raf.length()/(StagiaireDAO.LONGUEUR_INFO_STAGIAIRE + 8*3)); //Octets
			
			// You might need to add if nbTotalLignes != 0, to control empty file. //Note to self (Tested), not necessary.
			document.add(new Paragraph("AFCEPF - Liste Total Stagiaires: \n\n"));
			lectureInfixePDF(raf,0);
			
			raf.close();
			//End
			document.close();
			writer.close();
		} catch (DocumentException e){
			e.printStackTrace();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void lectureInfixePDF (RandomAccessFile raf, long positionCourante) {  //This function is used on "exportAll"
		StagiaireDAO sDao = new StagiaireDAO();
		try {
			//1
			raf.seek(positionCourante + StagiaireDAO.SAG);
			long posSag = raf.readLong();
			if (posSag == positionCourante) {
				raf.seek(positionCourante);
//				String stagiaireRate = sDao.lireChar(StagiaireDAO.CARACTERES_TOTAL_STAGIAIRE, raf);
//				System.out.println(stagiaireRate);
			}
			if (posSag > 0) {
				lectureInfixePDF(raf, posSag);
			}
			raf.seek(positionCourante);
			String nom = StagiaireDAO.lireChar(StagiaireDAO.CNOM, raf);
			nom = nom.trim();
			String prenom = StagiaireDAO.lireChar(StagiaireDAO.CPRENOM, raf);
			prenom = prenom.trim();
			String departement = StagiaireDAO.lireChar(StagiaireDAO.CDEPARTEMENT, raf);
			departement = departement.trim();
			String promotion = StagiaireDAO.lireChar(StagiaireDAO.CPROMOTION, raf);
			promotion = promotion.trim();
			String annee = StagiaireDAO.lireChar(StagiaireDAO.CANNEE, raf);
			annee = annee.trim();
			
			PdfPTable table = new PdfPTable(5);  // 5 columns
			//float[] columnWidths = {1f, 1f, 1f, 1f, 1f};
			table.setWidthPercentage(100); //Width 100%
			
			PdfPCell cell1 = new PdfPCell(new Paragraph(nom));
			cell1.setBorderColor(BaseColor.BLACK);
			//cell1.setPaddingLeft(5);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell cell2 = new PdfPCell(new Paragraph(prenom));
			cell2.setBorderColor(BaseColor.BLACK);
			//cell2.setPaddingLeft(5);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell cell3 = new PdfPCell(new Paragraph(departement));
			cell3.setBorderColor(BaseColor.BLACK);
			//cell3.setPaddingLeft(5);
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			PdfPCell cell4 = new PdfPCell(new Paragraph(promotion));
			cell1.setBorderColor(BaseColor.BLACK);
			//cell1.setPaddingLeft(5);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			PdfPCell cell5 = new PdfPCell(new Paragraph(annee));
			cell1.setBorderColor(BaseColor.BLACK);
			//cell1.setPaddingLeft(5);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			document.add(table);

			//2
			raf.seek(positionCourante + StagiaireDAO.SAD);
			long posSad = raf.readLong();
			if (posSad > 0) {
				lectureInfixePDF(raf, posSad);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public void lectureInfixePDF (RandomAccessFile raf, long positionCourante) {  //Pas de table (toString directement)
//		StagiaireDAO sDao = new StagiaireDAO();
//		try {
//			//1
//			raf.seek(positionCourante + StagiaireDAO.SAG);
//			long posSag = raf.readLong();
//			if (posSag == positionCourante) {
//				raf.seek(positionCourante);
////				String stagiaireRate = sDao.lireChar(StagiaireDAO.CARACTERES_TOTAL_STAGIAIRE, raf);
////				System.out.println(stagiaireRate);
//			}
//			if (posSag > 0) {
//				lectureInfixePDF(raf, posSag);
//			}
//			raf.seek(positionCourante);
//			String nom = StagiaireDAO.lireChar(StagiaireDAO.CNOM, raf);
//			nom = nom.trim();
//			String prenom = StagiaireDAO.lireChar(StagiaireDAO.CPRENOM, raf);
//			prenom = prenom.trim();
//			String departement = StagiaireDAO.lireChar(StagiaireDAO.CDEPARTEMENT, raf);
//			departement = departement.trim();
//			String promotion = StagiaireDAO.lireChar(StagiaireDAO.CPROMOTION, raf);
//			promotion = promotion.trim();
//			String annee = StagiaireDAO.lireChar(StagiaireDAO.CANNEE, raf);
//			annee = annee.trim();
//			
//			document.add(new Paragraph(nom+" "+prenom+" "+departement+" "+promotion+" "+annee)); 
//
//			//2
//			raf.seek(positionCourante + StagiaireDAO.SAD);
//			long posSad = raf.readLong();
//			if (posSad > 0) {
//				lectureInfixePDF(raf, posSad);
//			}
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	

	
	
	public void selectionListePDF() { // NOT USED, BUT DONT DELETE IT. //Exporting PDF depending on the List<Stagiaire>
		StagiaireDAO sDao = new StagiaireDAO();
		List<Stagiaire> listeStagiaires = sDao.getAllStagiaires();
		try {
			RandomAccessFile raf = new RandomAccessFile(sDao.getNameBinFile(),"r");
			int nbTotalLignes = (int) (raf.length()/(StagiaireDAO.LONGUEUR_INFO_STAGIAIRE + 8*3));
			writer = PdfWriter.getInstance(document, new FileOutputStream("StagiairesTotal.pdf"));
			document.open();
			
			document.add(new Paragraph("AFCEPF - Liste Total Stagiaires: \n\n"));
			
			for(int i=0;i<nbTotalLignes;i++) {
				document.add(new Paragraph(listeStagiaires.get(i).toString())); 
	
			}
			
			raf.close();
			document.close();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	public void extractionTableViewToPDFDEPRECATED() { //Exporting PDF depending on the List<Stagiaire>
		listeStagiairesRecherche = new ArrayList<Stagiaire>();
		
		StagiaireDAO sDao = new StagiaireDAO();
		List<Stagiaire> listeStagiaires = sDao.getAllStagiaires(); //Prendre la liste en memoire

		MainPanel mp = (MainPanel) Main.getStage().getScene().getRoot(); //We need to generate a static Stage, or the usual option won't work.
		String nomText = mp.getBpLeft().getGpLeft().getTfNom().getText();

		for(int i=0;i<listeStagiaires.size();i++) {
			if( nomText.equals(listeStagiaires.get(i).getNom()) ) {
				System.out.print  ("\""+listeStagiaires.get(i).getNom()+"\"");
				System.out.print  ("\""+listeStagiaires.get(i).getPrenom()+"\"");
				System.out.print  ("\""+listeStagiaires.get(i).getDepartement()+"\"");
				System.out.print  ("\""+listeStagiaires.get(i).getPromotion()+"\"");
				System.out.println("\""+listeStagiaires.get(i).getAnnee()+"\"");

				Stagiaire stg = new Stagiaire(listeStagiaires.get(i).getNom(),listeStagiaires.get(i).getPrenom(),listeStagiaires.get(i).getDepartement(),
						listeStagiaires.get(i).getPromotion(),listeStagiaires.get(i).getAnnee()); 

				listeStagiairesRecherche.add(stg); 
			}
		}
		
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream("StagiairesSelection.pdf"));
			document.open();
			document.add(new Paragraph("AFCEPF - Liste Total Stagiaires: \n\n"));
			
			for(int i=0;i<listeStagiairesRecherche.size();i++) {
				document.add(new Paragraph(listeStagiairesRecherche.get(i).toString())); 
				//System.out.println(listeStagiaires.get(i).toString());
			}

			document.close();
			writer.close();
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void extractionTableViewToPDF() { //Exporting PDF using statics Lists assigned when the button is pressed.  //GOOD ONE
		int tailleRecherche = 0;
		
		if (listeStagRecherchePDF != null) { //if the list does not exist, it means the button "Recherche" has not been pressed.
			tailleRecherche = listeStagRecherchePDF.size();
		}
		try {
			
			writer = PdfWriter.getInstance(document, new FileOutputStream("StagiairesSelection.pdf"));
			document.open();

			//table.setSpacingBefore(10f); //Space before table
			//table.setSpacingAfter(10f); //Space after table
			
			document.add(new Paragraph("AFCEPF - Liste Recherche Stagiaires: \n\n"));
			
			for(int i=0;i<tailleRecherche;i++) {
				
				//String here
				
				
				PdfPTable table = new PdfPTable(5);  // 5 columns
				//float[] columnWidths = {1f, 1f, 1f, 1f, 1f};
				table.setWidthPercentage(100); //Width 100%
				//table.setWidths(columnWidths);
				
				String nom = listeStagRecherchePDF.get(i).getNom();
				String prenom = listeStagRecherchePDF.get(i).getPrenom();
				String departement = listeStagRecherchePDF.get(i).getDepartement();
				String promotion = listeStagRecherchePDF.get(i).getPromotion();
				String annee = listeStagRecherchePDF.get(i).getAnnee();
				
//				document.add(new Paragraph(listeStagRecherchePDF.get(i).toString())); 
		
				PdfPCell cell1 = new PdfPCell(new Paragraph(nom));
				cell1.setBorderColor(BaseColor.BLACK);
				//cell1.setPaddingLeft(5);
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

				PdfPCell cell2 = new PdfPCell(new Paragraph(prenom));
				cell2.setBorderColor(BaseColor.BLACK);
				//cell2.setPaddingLeft(5);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

				PdfPCell cell3 = new PdfPCell(new Paragraph(departement));
				cell3.setBorderColor(BaseColor.BLACK);
				//cell3.setPaddingLeft(5);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				PdfPCell cell4 = new PdfPCell(new Paragraph(promotion));
				cell1.setBorderColor(BaseColor.BLACK);
				//cell1.setPaddingLeft(5);
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				PdfPCell cell5 = new PdfPCell(new Paragraph(annee));
				cell1.setBorderColor(BaseColor.BLACK);
				//cell1.setPaddingLeft(5);
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);
				table.addCell(cell5);
				document.add(table);
				
			}
			
			document.close();
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
//	public void extractionTableViewToPDF() { //Pas de table (toString directement)
//		int tailleRecherche = 0;
//		
//		if (listeStagRecherchePDF != null) { //if the list does not exist, it means the button "Recherche" has not been pressed.
//			tailleRecherche = listeStagRecherchePDF.size();
//		}
//		try {
//			writer = PdfWriter.getInstance(document, new FileOutputStream("StagiairesSelection.pdf"));
//			document.open();
//			document.add(new Paragraph("AFCEPF - Liste Total Stagiaires: \n\n"));
//			
//			for(int i=0;i<tailleRecherche;i++) {
//				document.add(new Paragraph(listeStagRecherchePDF.get(i).toString())); 
//			}
//			
//			document.close();
//			writer.close();
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
	
	
	
	
}