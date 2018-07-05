package modele;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author nabil
 */
public class Noeuds{
	private int[][] matrice;
	private int minorantCourant;
	private HashMap<Integer,Integer> solution;
	private ArrayList<Integer> sommetsH;
	private ArrayList<Integer> sommetsV;
	private boolean reduit;

	/**
	 * constructeur de la classe 
	 * @param matrice : matrice des distances 
	 * @param minorantCourant : coût de la solution partielle associée au noeud
	 * @param solution : solution partielle associée au noeud 
	 * @param sommetsH : liste des sommets restant sur les lignes de la matrice des distance
	 * @param sommetsV : liste des sommets restant sur les colonnes de la matrice des distance
	 * @param reduit : si oui ou non, la matrice de distances est réduite 
	 */
	public Noeuds(int[][] matrice, int minorantCourant, HashMap<Integer,Integer> solution, ArrayList<Integer> sommetsH, ArrayList<Integer> sommetsV, boolean reduit){
		this.matrice=matrice;
		this.minorantCourant=minorantCourant;
		this.solution=solution;
		this.sommetsH=sommetsH;
		this.sommetsV=sommetsV;
		this.reduit=reduit;
	}

	/**
	 * constructeur de la classe
	 * @param matrice : matrice des distances
	 * @param minorantCourant : coût de la solution partielle associée au noeud
	 */
	public Noeuds(int[][] matrice, int minorantCourant){
		this.matrice=matrice;
		this.minorantCourant=minorantCourant;
		this.solution=new HashMap<Integer,Integer>();
		this.sommetsH=new ArrayList<Integer>();
		this.sommetsV=new ArrayList<Integer>();
		this.reduit=false;
		for(int i=0;i<matrice.length;i++){
			sommetsH.add(i);
			sommetsV.add(i);
		}
	}

	/**
	* accesseur matrice
	* @return la matrice des distances
	*/
	public int[][] getMatrice(){
		return this.matrice;
	}

	/**
	* accesseur minorantCourant
	* @return le coût de la solution partielle actuelle
	*/
	public int getMinorant(){
		return this.minorantCourant;
	}

	/**
	* accesseur solution
	* @return la solution partielle associée
	*/
	public HashMap<Integer,Integer> getSolution(){
		return this.solution;
	}

	/**
	* accesseur sommetsH
	* @return les sommets restants sur les lignes
	*/
	public ArrayList<Integer> getSommetsH(){
		return this.sommetsH;
	}

	/**
	* accesseur sommetsV
	* @return les sommets restants sur les colonnes
	*/
	public ArrayList<Integer> getSommetsV(){
		return this.sommetsV;
	}

	/**
	* accesseur reduit
	* @return si oui ou non la matrice est réduite
	*/
	public boolean getReduit(){
		return this.reduit;
	}


}

