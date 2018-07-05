package modele;

import java.util.ArrayList;

/**
 * @author nabil
 */
public class Opt3 implements Algorithme {

	protected String nom;

	public Opt3(String nom){
		this.nom = nom;
	}

	//retourne le nom de l'algo
	public String getNom(){
		return this.nom;
	}

	/**
	* calcule de cout de la solution
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
	* retourne la solution sous forme de liste
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

	/**
	 * inverser le chemin entre dep et arr : dep-->...-->arr => arr-->....-->dep
	 */
	private void inverseChemin(boolean[][] solution, ArrayList<Integer> cycle, int dep, int arr){
		for(int i=cycle.indexOf((Integer)dep); i<cycle.indexOf((Integer)arr);i++){
			solution[cycle.get(i)][cycle.get(i+1)]=false;
			solution[cycle.get(i+1)][cycle.get(i)]=true;
		}
	}

	@Override
	public  boolean[][] solve(int [][] m){
		int taille = m.length;
		
		Algorithme opt2 = new Opt2("2-Opt");
		boolean[][] solution = opt2.solve(m);
		int coutMinObtenu = calculeCout(m,solution);
		System.out.println("Cout de la solution 2OPT : "+coutMinObtenu);
		boolean amelioration=true;
		while(amelioration){
			ArrayList<Integer> cycle = getCycle(solution, 0);
			amelioration=false;
			for ( int a = 0 ; a < cycle.size() - 6 ; a++ ){
			    for ( int b = a + 2 ; b < cycle.size() - 4 ; b++ ){ 
			        for ( int c = b + 2 ; c < cycle.size() - 2 ; c++){
			        		
			        	if(m[cycle.get(a)][cycle.get(b)]+m[cycle.get(a+1)][cycle.get(c)]+m[cycle.get(b+1)][cycle.get(c+1)] < 
			        	   m[cycle.get(a)][cycle.get(a+1)]+m[cycle.get(b)][cycle.get(b+1)]+m[cycle.get(c)][cycle.get(c+1)]) {
			        	   	//retrait des arêtes
			        	   	solution[cycle.get(a)][cycle.get(a+1)]=false;
			        	   	solution[cycle.get(b)][cycle.get(b+1)]=false;
			        		solution[cycle.get(c)][cycle.get(c+1)]=false;
			        		//ajout des nouvelles arêtes
			        		solution[cycle.get(a)][cycle.get(b)]=true;
			        		solution[cycle.get(a+1)][cycle.get(c)]=true;
			        		solution[cycle.get(b+1)][cycle.get(c+1)]=true;	        		
			        		//inverser les chemins
			        		inverseChemin(solution, cycle, cycle.get(a+1), cycle.get(b));
			        		inverseChemin(solution, cycle, cycle.get(b+1), cycle.get(c));
			        		amelioration=true;
			        	}
			        	else if(m[cycle.get(a)][cycle.get(b+1)]+m[cycle.get(a+1)][cycle.get(c)]+m[cycle.get(b)][cycle.get(c+1)] < 
			        	   m[cycle.get(a)][cycle.get(a+1)]+m[cycle.get(b)][cycle.get(b+1)]+m[cycle.get(c)][cycle.get(c+1)]) {
			        		//retrait des arêtes
			        	   	solution[cycle.get(a)][cycle.get(a+1)]=false;
			        	   	solution[cycle.get(b)][cycle.get(b+1)]=false;
			        		solution[cycle.get(c)][cycle.get(c+1)]=false;
			        		//ajout des nouvelles arêtes
			        		solution[cycle.get(a)][cycle.get(b+1)]=true;
			        		solution[cycle.get(c)][cycle.get(a+1)]=true;
			        		solution[cycle.get(b)][cycle.get(c+1)]=true;	        		
			        		//pas d'inverse
			        		amelioration=true;
			        	}
			        	else if(m[cycle.get(a)][cycle.get(b+1)]+m[cycle.get(a+1)][cycle.get(c+1)]+m[cycle.get(b)][cycle.get(c)] < 
			        	   m[cycle.get(a)][cycle.get(a+1)]+m[cycle.get(b)][cycle.get(b+1)]+m[cycle.get(c)][cycle.get(c+1)]) {
			        		//retrait des arêtes et ajout des nouvelles
			        	   	solution[cycle.get(a)][cycle.get(a+1)]=false;
			        	   	solution[cycle.get(b)][cycle.get(b+1)]=false;
			        		solution[cycle.get(c)][cycle.get(c+1)]=false;	        		
			        		//ajout des nouvelles arêtes
			        		solution[cycle.get(a)][cycle.get(b+1)]=true;
			        		solution[cycle.get(a+1)][cycle.get(c+1)]=true;
			        		solution[cycle.get(c)][cycle.get(b)]=true;	        		
			        		//inverser les chemins
			        		inverseChemin(solution, cycle, cycle.get(a+1), cycle.get(b));
			        		amelioration=true;
			        	}
			        	else if(m[cycle.get(a)][cycle.get(c)]+m[cycle.get(a+1)][cycle.get(b+1)]+m[cycle.get(b)][cycle.get(c+1)] < 
			        	   m[cycle.get(a)][cycle.get(a+1)]+m[cycle.get(b)][cycle.get(b+1)]+m[cycle.get(c)][cycle.get(c+1)]) {
			        		//retrait des arêtes et ajout des nouvelles
			        	   	solution[cycle.get(a)][cycle.get(a+1)]=false;
			        	   	solution[cycle.get(b)][cycle.get(b+1)]=false;
			        		solution[cycle.get(c)][cycle.get(c+1)]=false;	        		
			        		//ajout des nouvelles arêtes
			        		solution[cycle.get(a)][cycle.get(c)]=true;
			        		solution[cycle.get(b+1)][cycle.get(a+1)]=true;
			        		solution[cycle.get(b)][cycle.get(c+1)]=true;	        		
			        		//inverser les chemins
			        		inverseChemin(solution, cycle, cycle.get(b+1), cycle.get(c));
			        		amelioration=true;
			        	}
			        	if(amelioration) break;
			        }
			        if(amelioration) break;
			    }
			    if(amelioration) break;
			}        
		}
		return solution;
	}

}
