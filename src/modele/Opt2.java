package modele;

import java.util.ArrayList;

/**
 * @author nabil
 */
public class Opt2 implements Algorithme {

	protected String nom;

	public Opt2(String nom){
		this.nom = nom;
	}

	//retourne le nom de l'algo
	public String getNom(){
		return this.nom;
	}

	/**
	* calcule le cout d'une solution
	*/
	private int calculeCout(int[][] matrice, boolean[][] solution){
		int coutTotal = 0;
		for(int i=0;i<solution.length;i++)
			for(int j=0;j<solution.length;j++)
				if (solution[i][j])
					coutTotal+=matrice[i][j];
		return coutTotal;
	}

	/**
	* retourne un cycle en liste
	*/
	private ArrayList<Integer> getCycle(boolean[][] solution, int s){
		int succ = s;
		ArrayList<Integer> cycle = new ArrayList<Integer>();
		cycle.add(succ);
		do{
			for(int i=0;i<solution.length;i++)
				if(solution[succ][i]){
					succ=i;
					cycle.add(succ);
					break;
				}
		}
		while(succ != s);
		return cycle;
	}

	@Override
	public  boolean[][] solve(int [][] m){
		int taille = m.length;
		
		Algorithme ppv = new PPV("PPV");
		boolean[][] solution = ppv.solve(m);
		int coutMinObtenu = calculeCout(m,solution);
		System.out.println("Minorant retourne par PPV : "+coutMinObtenu);

		boolean amelioration=true;
		while(amelioration){
			for(int k=0;k<taille;k++){
				amelioration = false;
				//on applique 2OPT sur le sommet k
				ArrayList<Integer> cycle = getCycle(solution, k);
				//tous les sommets sauf le sommet lui meme et ses deux voisins
				int v=0;
				for(int p=2;p<cycle.size()-3;p++){
					if(m[cycle.get(v)][cycle.get(v+1)] + m[cycle.get(p)][cycle.get(p+1)] > m[cycle.get(v)][cycle.get(p)] + m[cycle.get(v+1)][cycle.get(p+1)]){
					   	solution[cycle.get(v)][cycle.get(v+1)] = false;
					   	solution[cycle.get(p)][cycle.get(p+1)] = false;
					   	solution[cycle.get(v)][cycle.get(p)] = true;
					   	solution[cycle.get(v+1)][cycle.get(p+1)] = true;
					   	for(int i=v+1; i<p;i++){
					   		solution[cycle.get(i)][cycle.get(i+1)]=false;
					   		solution[cycle.get(i+1)][cycle.get(i)]=true;
					   	}
					   	coutMinObtenu=calculeCout(m,solution);
					   	cycle = getCycle(solution, v);
					   	amelioration=true;
					}
				}
			}
		}
		return solution;
	}

}