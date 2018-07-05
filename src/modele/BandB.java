package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author nabil
 */
public class BandB implements Algorithme {

	protected String nom;

	/**
	* constructeur de class
	* @param nom : nom de l'algorithme
	*/
	public BandB(String nom){
		this.nom = nom;
	}

	//retourne le nom de l'algo
	public String getNom(){
		return this.nom;
	}

	/**
	* retourne une copie de la matrice de coûts
	* @param m1 la matrice à copier
	* @return la copie
	*/
	protected int[][] copieMatrice(int[][] m1){
		int[][] m2 = new int[m1.length][m1.length];
		for (int i = 0; i < m1.length; i++)
			for(int j = 0; j < m1.length; j++)
  				m2[i][j] = m1[i][j];
  		return m2;
	}

	/**
	* retourne une copie de la solution
	* @param s1 solution à copier
	* @return la copie
	*/
	protected HashMap<Integer,Integer> copieSolution (HashMap<Integer,Integer> s1){
		HashMap<Integer,Integer> s2 = new HashMap<Integer,Integer>();
		s2.putAll(s1);
		return s2;
	}

	/**
	* reduire une matrice en soustrayant le min de chaque ligne de toutes les valeurs de cette ligne
	* puis faire de même pour les colonnes
	* @param matriceA : matrice de coûts
	* @return int : coût de la réduction
	*/
	protected int reduireMatrice(int[][] matriceA){
		int coutDeReduction = 0;
		for (int i=0; i<matriceA.length; i++){
			int minLigne = Integer.MAX_VALUE;
			for (int j=0; j<matriceA.length; j++){
				if(matriceA[i][j]<minLigne)
					minLigne=matriceA[i][j];
			}
			coutDeReduction+=minLigne;
			for (int j=0; j<matriceA.length; j++)
				matriceA[i][j]=matriceA[i][j]-minLigne;
		}
		for (int i=0; i<matriceA.length; i++){
			int minColonne = Integer.MAX_VALUE;
			for (int j=0; j<matriceA.length; j++){
				if(matriceA[j][i]<minColonne)
					minColonne=matriceA[j][i];
			}
			coutDeReduction+=minColonne;
			for (int j=0; j<matriceA.length; j++)
				matriceA[j][i]=matriceA[j][i]-minColonne;
		}
		return coutDeReduction;
	}

	/**
	* calcule de regret de la non inclusion d'une arête x-y dans la solution
	* @param matrice : matrice de cout
	* @param x : premier sommet de l'arête
	* @param y : second sommet de l'arête
	* @return le cout d'eviction
	*/
	protected int calculeEviction(int[][] matrice, int x, int y){
		int eviction = 0;
		int minligne = Integer.MAX_VALUE;
		int mincolonne = Integer.MAX_VALUE;
		for(int i=0;i<matrice.length;i++){
			if(i!=y && matrice[x][i] < minligne){
				minligne = matrice[x][i];
			}
		}
		for(int i=0;i<matrice.length;i++){
			if(i!=x && matrice[i][y] < mincolonne){
				mincolonne = matrice[i][y];
			}
		}
		return minligne+mincolonne;
	}

	/**
	* calcule l'arête de regret maximal dans la matrice
	* @param m : matrice de couts
	* @return : un tableau de taille 2 contenant les x et y de l'arête de regret max et le cout d'eviction
	*/
	protected int[] calculeMaxEviction(int[][] m){
		int x = 0;
		int y = 0;
		int maxEv=-1;
		for(int i=0;i<m.length;i++){
			for(int j=0;j<m[i].length;j++){
				if(m[i][j]==0){
					int v = calculeEviction(m, i, j);
					if(v>maxEv){
						x=i;
						y=j;
						maxEv=v;
					}
				}
			}
		}
		int[] r = {x,y,maxEv};
		return r;
	}

	/**
	* retourne le sommet a la fin de la chaine contenant x
	* @param solution : solution
	* @param x : le sommet x
	* @return le sommet a la fin de la chaine contenant x
	*/
	protected int getS(HashMap<Integer,Integer> solution, int x){
		int p;
		do{
			p = x;
			for(Entry<Integer, Integer> entry : solution.entrySet())
				if(entry.getValue()==x){
					x=entry.getKey();
					break;
				}
		}
		while( ! (p==x));
		return p;
	}

	/**
	* retourne le sommet au début de la chaine contenant x
	* @param solution : solution
	* @param x : le sommet x
	* @return le sommet au début de la chaine contenant x
	*/
	protected int getT(HashMap<Integer,Integer> solution, int x){
		int p;
		do{
			p = x;
			for(Entry<Integer, Integer> entry : solution.entrySet())
				if(entry.getKey()==x){
					x=entry.getValue();
					break;
				}
		}
		while( ! (p==x));
		return p;
	}

	/**
	* crée une nouvelle matrice de taille (n-1 x n-1) en supprimant la ligne et la colonne correspondant aux sommets de l'arête choisie
	* @param m1 : matrice de couts
	* @param x : l'indice de la ligne à supprimer
	* @param y : l'indice de la colonne à supprimer
	* @return la matrice réduite
	*/
	protected int[][] supprimerSommets(int[][] m1, int x, int y){
		int[][] m2 = new int[m1.length-1][m1.length-1];
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++)
				m2[i][j]=m1[i][j];
			for(int j=y+1;j<m1.length;j++)
				m2[i][j-1]=m1[i][j];
		}
		for(int i=x+1;i<m1.length;i++){
			for(int j=0;j<y;j++)
				m2[i-1][j]=m1[i][j];
			for(int j=y+1;j<m1.length;j++)
				m2[i-1][j-1]=m1[i][j];
		}
		return m2;
	}

	/**
	* calcule la taille d'une solution partielle 
	* (pour l'heuristique du choix du noeud à développer en cas d'égalité de minorant entre deux ou plusieurs noeuds)
	* @param solution : la solution
	* @return retourne la taille de la solution
	*/
	protected int tailleSolution(HashMap<Integer,Integer> solution){
		return solution.size();
	}

	/**
	* enfiler un noeud dans la bonne place en prévilègiant celui qui à le minorant le plus petit 
    * en cas d'égalité, privilègier celui qui a la solution la plus complète.
    * @param listeNoeuds : liste de noeuds
    * @param n : noeud à enfiler 
	*/
	protected void enfiler(ArrayList<Noeuds> listeNoeuds, Noeuds n){
		boolean flag = false;
		for(int i=0; i<listeNoeuds.size();i++){
			if(n.getMinorant() < listeNoeuds.get(i).getMinorant()){
				listeNoeuds.add(i,n);
				flag = true;
				break;
			}
			else if(n.getMinorant() == listeNoeuds.get(i).getMinorant()){
				if(tailleSolution(n.getSolution()) > tailleSolution(listeNoeuds.get(i).getSolution())){
					listeNoeuds.add(i,n);
					flag = true;
					break;
				}
			}
		}
		if(! flag){
			listeNoeuds.add(n);
		}
	}

	@Override
	public  boolean[][] solve(int [][] m){
		
		HashMap<Integer,Integer> solutionF=new HashMap<Integer,Integer>();
		//***************Initialisation et Noeud Racine**********
		int[][] mI = copieMatrice(m);
		int coutReduc = reduireMatrice(mI);
		Noeuds n1 = new Noeuds(mI, coutReduc);
		ArrayList<Noeuds> listeNoeuds = new ArrayList<Noeuds>();
		listeNoeuds.add(n1);
		//***************Initialisation et Noeud Racine**********
		int nbrNoeuds = 0;

		//***************ITERATION*******************************
		while(true){
			Noeuds noeudCourant = listeNoeuds.get(0);
						
			if(noeudCourant.getMatrice().length==2){
				solutionF = copieSolution(noeudCourant.getSolution());
				if (noeudCourant.getMatrice()[0][0] < noeudCourant.getMatrice()[1][0])
					solutionF.put(noeudCourant.getSommetsH().get(0),noeudCourant.getSommetsV().get(0));
				else
					solutionF.put(noeudCourant.getSommetsH().get(1),noeudCourant.getSommetsV().get(0));

				if(noeudCourant.getMatrice()[0][1] < noeudCourant.getMatrice()[1][1])
					solutionF.put(noeudCourant.getSommetsH().get(0),noeudCourant.getSommetsV().get(1));
				else
					solutionF.put(noeudCourant.getSommetsH().get(1),noeudCourant.getSommetsV().get(1));

				break;
			}
			else{
				if(! noeudCourant.getReduit())
					reduireMatrice(noeudCourant.getMatrice());
				
				//*************calcule de regret*********
				int[] maxEviction = calculeMaxEviction(noeudCourant.getMatrice());
				int x = maxEviction[0];
				int y = maxEviction[1];
				int maxEv = maxEviction[2];
				//*************calcule de regret*********
				
				//*************GenerationDesNoeuds*******
				//créer la nouvelle matrice en supprimant la ligne x et la colonne y
				int[][] mAvec = supprimerSommets(noeudCourant.getMatrice(), x,y);
				//liste des sommets restants en lignes
				ArrayList<Integer> sommetsHAvec = new ArrayList<Integer>(noeudCourant.getSommetsH());
				//supprimer x
				sommetsHAvec.remove(x);
				//liste des sommets restants en colonnes
				ArrayList<Integer> sommetsVAvec = new ArrayList<Integer>(noeudCourant.getSommetsV());
				//supprimer y
				sommetsVAvec.remove(y);
				//recupérer le sommet S à l'origine de la chaine contanant x //si la chaine ne contient que x : le retourner
				int indexS=sommetsVAvec.indexOf((Integer)getS(noeudCourant.getSolution(), noeudCourant.getSommetsH().get(x)));
				//recupérer le sommet T à la fin de la chaine contenant y //si la chaine ne contient que y : le retourner
				int indexT=sommetsHAvec.indexOf((Integer)getT(noeudCourant.getSolution(), noeudCourant.getSommetsV().get(y)));
				//mettre le chemin retour T-->S à l'infini
				mAvec[indexT][indexS]=Integer.MAX_VALUE;
				//créer la nouvelle solution contenant l'arete X-->Y
				HashMap<Integer,Integer> solutionAvec = copieSolution(noeudCourant.getSolution());
				solutionAvec.put(noeudCourant.getSommetsH().get(x),noeudCourant.getSommetsV().get(y));
				//créer le noeud de droite ( noeud dont la solution contient l'arete x-y choisie )
				Noeuds noeudAvec = new Noeuds(mAvec, noeudCourant.getMinorant()+reduireMatrice(mAvec), solutionAvec, sommetsHAvec, sommetsVAvec, true);
				//créer le noeud gauche ( noeud dont la solution ne contient pas l'arete x-y choisie )
				noeudCourant.getMatrice()[x][y] = Integer.MAX_VALUE;
				Noeuds noeudSans = new Noeuds(noeudCourant.getMatrice(), noeudCourant.getMinorant()+maxEv, noeudCourant.getSolution(), noeudCourant.getSommetsH(), noeudCourant.getSommetsV(), false);
				//***************GenerationDesNoeuds**********
				//supprimer le noeud courant de la file à priorité (déjà exploré donc).
				listeNoeuds.remove(0);
				//enfiler les fils de gauche et droite
				enfiler(listeNoeuds, noeudAvec);
				enfiler(listeNoeuds, noeudSans);
				nbrNoeuds+=2;
			}
		}
		//***************ITERATION*******************************
		//retourner la solution sous forme de matrice booleenne
		boolean[][] solution = new boolean[m.length][m.length];
		for(Entry<Integer, Integer> entry : solutionF.entrySet())
			solution[entry.getKey()][entry.getValue()]=true;
		System.out.println("Nombre de noeuds developpes : "+nbrNoeuds);
		System.out.println("taille file : "+listeNoeuds.size());
		return solution;
	}
}

