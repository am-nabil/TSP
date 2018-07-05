package modele;

import java.util.ArrayList;

/**
 * @author nabil
 */
public class MPIPLI extends MPI {

	public MPIPLI (String nom){
		super(nom);
	}

	@Override
	protected int choisirUnSommet(int[][] matriceDeDistances, ArrayList<Integer> sommetsVisites, ArrayList<Integer> sommetsNonVisites){
		int sommetChoisi=0;
		int maxDist=Integer.MIN_VALUE;
		//pour tout sommet n'appartenant pas au cycle
		for(Integer k : sommetsNonVisites){
			//calculer sa distance par rapport aux sommets du cycle
			for(Integer i : sommetsVisites){
				//distance entre i et k
				int distanceik=matriceDeDistances[i][k];
				//si cette distance est maximale choisir k et mettre à jour le min
				if(distanceik>maxDist){
					maxDist=distanceik;
					sommetChoisi=k;
				}
			}
		}
		return sommetChoisi;
	}
}