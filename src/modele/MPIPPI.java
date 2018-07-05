package modele;

import java.util.ArrayList;

/**
 * @author nabil
 */
public class MPIPPI extends MPI{

	public MPIPPI (String nom){
		super(nom);
	}

	@Override
	protected int choisirUnSommet(int[][] matriceDeDistances, ArrayList<Integer> sommetsVisites, ArrayList<Integer> sommetsNonVisites){
		int sommetChoisi=0;
		int minDist=Integer.MAX_VALUE;
		//pour tout sommet n'appartenant pas au cycle
		for(Integer k : sommetsNonVisites){
			//calculer sa distance par rapport aux sommets du cycle
			for(Integer i : sommetsVisites){
				//distance entre i et k
				int distanceik=matriceDeDistances[i][k];
				//si cette distance est minimale choisir k et mettre à jour le min
				if(distanceik<minDist){
					minDist=distanceik;
					sommetChoisi=k;
				}
			}
		}
		return sommetChoisi;
	}

}