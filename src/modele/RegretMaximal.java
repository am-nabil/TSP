package modele;

import java.util.ArrayList;

/**
 * @author nabil
 */
public class RegretMaximal implements Algorithme {

	protected String nom;

	public RegretMaximal(String nom){
		this.nom = nom;
	}

	//retourne le nom de l'algo
	public String getNom(){
		return this.nom;
	}

	//calcule le regret d'une arête donnée dans une matrice de distances
	private int calculeRegret(int[][] m,int x, int y){
		int minColonne=Integer.MAX_VALUE;
		int minLigne=Integer.MAX_VALUE;
      	for (int i = 0; i < m.length; i++) {
        	if (y!=i && m[x][i] < minLigne)
        		minLigne = m[x][i];
      	}
      	for (int i = 0; i < m.length; i++) {
        	if (x!=i && m[i][y] < minColonne) 
        		minColonne = m[i][y];
      	}
      	int r = (minLigne+minColonne);
      	return r;
	}

	//retourne le premier element d'une chaine contenant x
	private int getS(boolean[][] m, int x){
		int p;
		do{
			p = x;
			for(int i=0;i<m.length;i++)
				if(m[i][x]==true){
					x=i;
					break;
				}
		}
		while( ! (p==x));
		return p;
	}

	//retourne le dernier element d'une chaine contenant y
	private int getT(boolean[][] m, int x){
		int s;
		do{
			s = x;
			for(int i=0;i<m.length;i++)
				if(m[x][i]==true){
					x=i;
					break;
				}
		}
		while(! (s==x));
		return s;
	}

	//si le sommet courant à un seul choix de destination : le retourner, sinon retourner -1
	private int aUniqueChoix(int[][] matriceDeDistances, int i){
		int index =0;
		int ch = 0;
		for(int j=0;j<matriceDeDistances.length;j++){
			if(matriceDeDistances[i][j] != Integer.MAX_VALUE){
				index++;
				ch = j;
			}
			if(index>1)
				return -1;
		}
		return ch;
	}

	private void mettreAjourMatrice(int[][] matriceDeDistances, boolean[][] solution, int x, int y){
		//ajouter l'arête à la solution
		solution[x][y]=true;
		//mettre à l'infini les distances au depart de x et les distances
		for (int k=0;k<matriceDeDistances.length;k++){
			matriceDeDistances[x][k] = Integer.MAX_VALUE;
			matriceDeDistances[k][y] = Integer.MAX_VALUE;
		}
		int s = getS(solution, x);
		int t = getT(solution, y);
		boolean xAPredecesseur = (s!=x);
		boolean yASuceesseur = (t!=y);
		if(!xAPredecesseur && !yASuceesseur)
			matriceDeDistances[y][x]=Integer.MAX_VALUE;
		else if(xAPredecesseur && !yASuceesseur)
			matriceDeDistances[y][s]=Integer.MAX_VALUE;
		else if(!xAPredecesseur && yASuceesseur)
			matriceDeDistances[t][x]=Integer.MAX_VALUE;
		else if(xAPredecesseur && yASuceesseur)
			matriceDeDistances[t][s]=Integer.MAX_VALUE;
	}

	@Override
	public  boolean[][] solve(int [][] m){
		//copie de la matrice
		int[][] matriceDeDistances = new int[m.length][m.length];
		for(int i=0;i<matriceDeDistances.length;i++)
			for(int j=0;j<matriceDeDistances.length;j++)
				matriceDeDistances[i][j]=m[i][j];
		//liste des sommets visites
		ArrayList<Integer> sommetsVisites = new ArrayList<Integer>();
		//matrice représentant la solution
		boolean[][] solution = new  boolean[matriceDeDistances.length][matriceDeDistances.length]; 
		//represente le regret maximal d'une arête donnée
		int regretMaximal = -1;
		//les sommets x,y de la première arête qu'on va choisir
		int x=0; int y=0;
		//parcourir toutes les arêtes restantes
		for(int i=0;i<matriceDeDistances.length;i++){
			for(int j=0;j<matriceDeDistances.length;j++){
				if(i!=j && !(matriceDeDistances[i][j]==Integer.MAX_VALUE)){
					//calculer le regret de l'arete i-j
					int regret = calculeRegret(matriceDeDistances,i,j);
					//si regret plus grand, mettre à jour
					if (regret>regretMaximal){
						regretMaximal=regret;
						x=i;
						y=j;
					}
				}
			}
		}
		//on met à jour la matrice
		mettreAjourMatrice(matriceDeDistances,solution,x,y);
		//on ajoute x et y aux sommets déjà visités
		sommetsVisites.add(x);
		sommetsVisites.add(y);
		//tant qu'il retse des sommets non visités
		while(sommetsVisites.size() < matriceDeDistances.length){
			//on part du dernier sommet visité
			int i = sommetsVisites.get(sommetsVisites.size()-1);
			//sommet à choisir
			int sommetChoisi = 0;
			//on remet le regret max à l'infini
			regretMaximal=-1;
			//si un seul choix, le récupérer sinon -1
			int uniqueChoix = aUniqueChoix(matriceDeDistances, i);
			//si on a plusieurs choix on choisi en fonction du regret
			if(uniqueChoix == -1){
				//on calcule le regret maximal sur la ligne du dernier sommet visité
				for(int j=0;j<matriceDeDistances.length;j++){
					if(i!=j && !(matriceDeDistances[i][j]==Integer.MAX_VALUE)){
						//calculer le regret de l'arete i-j
						int regret = calculeRegret(matriceDeDistances,i,j);
						//si regret plus grand, mettre à jour
						if (regret>regretMaximal){
							regretMaximal=regret;
							sommetChoisi=j;
						}
					}
				}
			}
			//sinon on prend l'unique choix
			else{
				sommetChoisi=uniqueChoix;
			}
			mettreAjourMatrice(matriceDeDistances, solution, i, sommetChoisi);
			sommetsVisites.add(sommetChoisi);
		}
		//ajouter la dernière arête de retour à la case départ
		solution [sommetsVisites.get(sommetsVisites.size()-1)][sommetsVisites.get(0)]= true;
		//retourner la solution
		return solution;
	}
	
}
