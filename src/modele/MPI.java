package modele;

import java.util.ArrayList;

/**
 * @author nabil
 */
public abstract class MPI implements Algorithme{

	protected String nom;

	public MPI(String nom){
		this.nom = nom;
	}

	//retourne le nom de l'algo
	public String getNom(){
		return this.nom;
	}

	protected abstract int choisirUnSommet(int[][] matriceDeDistances, ArrayList<Integer> sommetsVisites, ArrayList<Integer> sommetsNonVisites);

	@Override
	public  boolean[][] solve(int [][] matriceDeDistances){
		//nombre de sommets total
		int nbrSommets = matriceDeDistances.length;
		//on cree une liste de sommets non encore visites (tous au départ) 
		ArrayList<Integer> sommetsNonVisites = new ArrayList<Integer>();
		for(int i=0;i<matriceDeDistances.length;i++)
			sommetsNonVisites.add(i);
		//on cree une liste de sommets visites (vide au depart) 
		ArrayList<Integer> sommetsVisites = new ArrayList<Integer>();
		//on instancie une solution partielle (vide au depart -tout a false-)
		boolean[][] solution = new  boolean[matriceDeDistances.length][matriceDeDistances.length];
		//sommet de départ : choix arbitraire
		int sommet1 = 0;
		//on marque le sommet comme visité
		sommetsVisites.add(sommet1);
		//on l'enlève dans la liste des sommets non visités
		sommetsNonVisites.remove((Integer)sommet1);
		//tant qu'il reste des sommets non visites
		while(sommetsVisites.size()<nbrSommets){
			//choisir un premier sommet selon l'heuristique
			int sommetChoisi = choisirUnSommet(matriceDeDistances, sommetsVisites, sommetsNonVisites);
			//on cherche à minimiser (Mij+Mjk-Mik) où j est le sommet choisi et i-k : arête appartenant au cycle de la solution courante
			int coutMin = Integer.MAX_VALUE;
			//emplacement du nouveau sommet à inserer dans le cycle (initialisé à 0 mais forcément modifié par l'instruction suivante)
			int emplacement = 0;
			//le mettre à l'endroit le moins couteux
			//pour ça, on parcours notre cycle actuel et tester toutes les possibilites
			for(int i=0; i<sommetsVisites.size();i++){
				//calcule du cout en mettant sommetChoisi à l'endroit i : on calcule la formule (Mij+Mjk-Mik)
				int cout = matriceDeDistances[sommetsVisites.get(i)][sommetChoisi]+
						   matriceDeDistances[sommetChoisi][sommetsVisites.get((i+1)%sommetsVisites.size())]-
						   matriceDeDistances[sommetsVisites.get(i)][sommetsVisites.get((i+1)%sommetsVisites.size())];
				//si meilleure choix que le précédent => mettre à jour le coutMin et garder l'emplacement en mémoire
				if(cout<coutMin){
					coutMin=cout;
					emplacement=i;
				}
			}
			//x-y l'arête au milieu de laquelle on insère le sommet choisi
			int x=sommetsVisites.get(emplacement);
			int y=sommetsVisites.get((emplacement+1)%sommetsVisites.size());
			//Enlever l'arete i-k de la solution
			solution[x][y]=false;
			//inserer le sommet choisi j dans la liste des sommets visités
			sommetsVisites.add((emplacement+1)%sommetsVisites.size(),sommetChoisi);
			//enlever le sommet de la liste des sommet qui restent à visiter
			sommetsNonVisites.remove((Integer)sommetChoisi);
			//mettre à jour la solution en ajoutant i-j et j-k à la solution
			solution[x][sommetChoisi]=true;
			solution[sommetChoisi][y]=true;
		}
		return solution;
	}

}