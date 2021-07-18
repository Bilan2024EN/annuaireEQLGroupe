package Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class TestRecursiveREADBin {
	
	
	static final int CNOM = 30;
	static final int CPRENOM = 20;
	static final int CDEPART = 5;
	static final int CPROMO = 12;
	static final int CANNE = 4;
	
	public static int studentLength = (CNOM+CPRENOM+CDEPART+CPROMO+CANNE)*2;
	public static int studentCharLength = CNOM + CPRENOM + CDEPART + CPROMO + CANNE;
	
	//Lionel
	

	static final int PARENT = studentLength;
	static final int SAG = studentLength + 8;
	static final int SAD = studentLength + 16;
	public long positionBin;
	public int nbTotalStagiaires = 0;
	

	public static void main(String[] args) throws IOException {
		
//		int ac = 0;
//		for(int i=0;i<50;i++) {
//			//System.out.println(i+1+": "+ac);
//			ac+=164;
//		}
//		
//		System.out.println(ac);
			
			
		
		RandomAccessFile raf = new RandomAccessFile("stagiairesBin.bin","r");
		String out = "";
		
		for(int i=0;i<raf.length()/(studentLength + 8*3);i++) { //Num lignes.
			out = "";
			
				for(int j=0;j<studentCharLength;j++) //String de la ligne.
					 out += raf.readChar();
				
				long p = raf.readLong(); // 8
				long fg = raf.readLong();
				long fd = raf.readLong();
				
				System.out.println(out+" "+p+" "+fg+" "+fd);
		}
		
		raf.close();
		
		// Read Sorted:
		
		
	}

}
