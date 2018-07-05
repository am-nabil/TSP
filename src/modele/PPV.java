package modele;

import java.util.ArrayList;

/**
 * @author nabil
 */
public class PPV implements Algorithme {

	protected String nom;

	public PPV(String nom){
		this.nom = nom;
	}

	//retourne le nom de l'algo
	public String getNom(){
		return this.nom;
	}

	//un min dans un tableau
	private int min (int[] t){
		int min = 0;
      	for (int i = 1; i < t.length; i++) {
        	if (t[i] < t[min]) min = i;
      	}
      	return min;
	}

	@Override
	public  boolean[][] solve(int [][] m){
		//copie de la matrice
		int[][] matriceDeDistances = new int[m.length][m.length];
		for(int i=0;i<matriceDeDistances.length;i++)
			for(int j=0;j<matriceDeDistances.length;j++)
				matriceDeDistances[i][j]=m[i][j];

		//nombre de sommets total
		int nbrSommets = matriceDeDistances.length;
		//on cree une liste de sommets visites (vide au depart) 
		ArrayList<Integer> sommetsVisites = new ArrayList<Integer>();
		//on instancie une solution partielle (vide au depart -tout a false-)
		boolean[][] solution = new  boolean[matriceDeDistances.length][matriceDeDistances.length];
		//sommet de départ
		int sommetDeDepart = 0;
		//on part d'un sommet initial (ici le sommet 0) qu'on ajoute à la liste des sommets visites;
		sommetsVisites.add(sommetDeDepart);
		//interdire le retour à ce sommet en mettent tous les chemins à +infini
		for(int i=0; i<matriceDeDistances.length;i++)
			matriceDeDistances[i][sommetDeDepart] = Integer.MAX_VALUE;
		//tant qu'il reste des sommets non visites
		while(sommetsVisites.size()<nbrSommets){
			//on part du dernier sommet visité
			int sommetCourant = sommetsVisites.get(sommetsVisites.size()-1);
			//on calcule le plus proche voisin dans la matrice
			int ppv = min(matriceDeDistances[sommetCourant]);
			//on rajoute l'arc à la solution
			solution[sommetCourant][ppv]=true;
			//on ajoute le sommet ppv à la liste des sommets déjà visités
			sommetsVisites.add(ppv);
			//interdire le retour à ce sommet en mettent tous les chemins à +infini
			for(int i=0; i<matriceDeDistances.length;i++)
				matriceDeDistances[i][ppv] = Integer.MAX_VALUE;
		}
		//une fois tous les sommets visités : revenir au sommet de départ
		solution[sommetsVisites.get(sommetsVisites.size()-1)][sommetDeDepart]=true;
		return solution;
	}

}