package parser;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.util.ArrayList;

/**
 * @author nabil
 */
public class ParseFichier{
	public int [][] parserTSP (String fichier) throws FileNotFoundException, IOException,NumberFormatException, IndexOutOfBoundsException {
			File f = new File(fichier);
			FileReader fr = new FileReader (f);
			BufferedReader in = new BufferedReader(fr);
			ArrayList<double[]> listeLignes = new ArrayList<double[]>();
			String line;
			while ((line = in.readLine()) != null) {
				if(line.equals("eof") || line.equals("EOF"))
					break;
				if(!(line.trim().charAt(0)+"").matches("\\d"))
					continue;
				String[] coordonnees = line.trim().split("\\s+");
				double[] c = new double[2];
				c[0]=Double.parseDouble(coordonnees[1]);
				c[1]=Double.parseDouble(coordonnees[2]);
				listeLignes.add(c);
			}
			in.close();


			int[][] matriceDistances = new int[listeLignes.size()][listeLignes.size()];

			for(int i=0; i<listeLignes.size(); i++){
				for(int j=i; j<listeLignes.size(); j++){
					if(i==j)
						matriceDistances[i][j]=Integer.MAX_VALUE;
					else{
						matriceDistances[i][j]= (int)Math.round(
													Math.sqrt (
														Math.pow(( listeLignes.get(i)[0] - listeLignes.get(j)[0] ),2) + 
														Math.pow(( listeLignes.get(i)[1] - listeLignes.get(j)[1] ),2) 
													)
												);
						matriceDistances[j][i]=matriceDistances[i][j]; 
					}
				}
			}
			
			return matriceDistances;
	}


	public int[][] parser(String fichier)  throws FileNotFoundException, IOException,NumberFormatException, IndexOutOfBoundsException{
		File f = new File(fichier);
			FileReader fr = new FileReader (f);
			BufferedReader in = new BufferedReader(fr);
			ArrayList<int[]> listeLignes = new ArrayList<int[]>();
			String line;
			while ((line = in.readLine())!=null) {
				if(line.equals("eof") || line.equals("EOF"))
					break;
				if(line.equals("") || !(line.trim().charAt(0)+"").matches("\\d"))
					continue;
				String[] distancesLigne = line.trim().split("\\s+");
				if(distancesLigne.length>0){
					int[] distancesLigneInt = new int[distancesLigne.length];
					for(int i=0;i<distancesLigne.length;i++){
						distancesLigneInt[i]=Integer.parseInt(distancesLigne[i]) != 0 ? Integer.parseInt(distancesLigne[i]) : Integer.MAX_VALUE;
					}
					listeLignes.add(distancesLigneInt);
				}
			}
			in.close();
			int[][] matriceDistances = new int[listeLignes.size()][listeLignes.size()];
			for(int i=0;i<listeLignes.size();i++){
				for(int j=0;j<i+1;j++){
					matriceDistances[i][j]=listeLignes.get(i)[j];
					matriceDistances[j][i]=listeLignes.get(i)[j];
				}
			}
			return matriceDistances;
	}
	
	
	
		public int[][] parserAs(String fichier) throws FileNotFoundException, IOException,NumberFormatException, IndexOutOfBoundsException{
		File f = new File(fichier);
			FileReader fr = new FileReader (f);
			BufferedReader in = new BufferedReader(fr);
			ArrayList<int[]> listeLignes = new ArrayList<int[]>();
			String line;
			while ((line = in.readLine())!=null) {
				if(line.equals("eof") || line.equals("EOF"))
					break;
				if(line.equals("") || (!(line.trim().charAt(0)+"").matches("\\d") && !(line.trim().charAt(0)+"").matches("-")) )
					continue;
				String[] distancesLigne = line.trim().split("\\s+");
				if(distancesLigne.length>0){
					int[] distancesLigneInt = new int[distancesLigne.length];
					for(int i=0;i<distancesLigne.length;i++){
						if ((distancesLigne[i]).equals("-"))
							distancesLigneInt[i] = Integer.MAX_VALUE;
						else  
							distancesLigneInt[i] = Integer.parseInt(distancesLigne[i]);
					}
					listeLignes.add(distancesLigneInt);
				}
			}
			in.close();
			int[][] matriceDistances = new int[listeLignes.size()][listeLignes.size()];
			for(int i=0;i<listeLignes.size();i++){
				for(int j=0;j<listeLignes.size();j++){
					matriceDistances[i][j]=listeLignes.get(i)[j];
				}
			}
			return matriceDistances;
	}
}
