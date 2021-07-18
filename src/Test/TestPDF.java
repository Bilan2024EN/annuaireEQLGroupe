package Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.afcepf.ai105.projet1.data.Stagiaire;
import fr.afcepf.ai105.projet1.data.StagiaireDAO;

public class TestPDF {
	
	public static void main(String[] args) {
		Document document = new Document();
		PdfWriter writer;
		
		try{
			writer = PdfWriter.getInstance(document, new FileOutputStream("TEST.pdf"));
			document.open();

			//table.setSpacingBefore(10f); //Space before table
			//table.setSpacingAfter(10f); //Space after table
			
			document.add(new Paragraph("AFCEPF - Liste Total Stagiaires: \n\n"));
			
			for(int i=0;i<5;i++) {
				
				//String here
				
				
				PdfPTable table = new PdfPTable(5);  // 5 columns
				//float[] columnWidths = {1f, 1f, 1f, 1f, 1f};
				table.setWidthPercentage(100); //Width 100%
				//table.setWidths(columnWidths);
				
				PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
				cell1.setBorderColor(BaseColor.BLACK);
				//cell1.setPaddingLeft(5);
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

				PdfPCell cell2 = new PdfPCell(new Paragraph("Cell 2"));
				cell2.setBorderColor(BaseColor.BLACK);
				//cell2.setPaddingLeft(5);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

				PdfPCell cell3 = new PdfPCell(new Paragraph("Cell 3"));
				cell3.setBorderColor(BaseColor.BLACK);
				//cell3.setPaddingLeft(5);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				PdfPCell cell4 = new PdfPCell(new Paragraph("Cell 4"));
				cell1.setBorderColor(BaseColor.BLACK);
				//cell1.setPaddingLeft(5);
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				PdfPCell cell5 = new PdfPCell(new Paragraph("Cell 5"));
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
		} catch (DocumentException e){
			e.printStackTrace();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
