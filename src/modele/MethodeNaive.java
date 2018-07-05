package modele;

import java.util.ArrayList;

/**
 * @author nabil
 */
public class MethodeNaive implements Algorithme{

	protected String nom;

	public MethodeNaive(String nom){
		this.nom = nom;
	}

	//retourne le nom de l'algo
	public String getNom(){
		return this.nom;
	}

	public int cout(int[][] matrice, ArrayList<Integer> sol){
		if(sol.isEmpty())
			return Integer.MAX_VALUE;
		int cout = 0;
		for(int i=0; i<sol.size();i++){
			cout+=matrice[sol.get(i)][sol.get((i+1)%sol.size())];
		}
		return cout;
	}

	private static ArrayList<Integer> meilleuresolution = new ArrayList<Integer>();
	public ArrayList<Integer> explorer(int[][] matrice, ArrayList<Integer> solutionCourante, ArrayList<Integer> sommetsRestants){
		if(sommetsRestants.isEmpty()){
			if(cout(matrice,solutionCourante)<cout(matrice,meilleuresolution)){
				meilleuresolution=solutionCourante;
			}

		}
		else{
			for(Integer i:sommetsRestants){
				ArrayList<Integer> solutionCouranteI = new ArrayList<Integer>(solutionCourante);
				solutionCouranteI.add(i);
				ArrayList<Integer> sommetsRestantsI = new ArrayList<Integer>(sommetsRestants);
				sommetsRestantsI.remove((Integer)i);
				explorer(matrice, solutionCouranteI, sommetsRestantsI);
			}
		}
		return meilleuresolution;
	}

	@Override
	public  boolean[][] solve(int [][] m){
		//preparation des données
		meilleuresolution = new ArrayList<Integer>();
		ArrayList<Integer> sommetsRestants= new ArrayList<Integer>();
		for(int i = 1; i<m.length;i++)
			sommetsRestants.add((Integer)i);
		ArrayList<Integer> solutionCourante= new ArrayList<Integer>();
		solutionCourante.add(0);
		//recherche arborescente
		ArrayList<Integer> ms = explorer(m, solutionCourante, sommetsRestants);
		//retourner la solution
		boolean[][] solution = new boolean[m.length][m.length];
		for(int i=0; i<ms.size();i++){
			solution[ms.get(i)][ms.get((i+1)%ms.size())]=true;
		}
		return solution;
	}

}