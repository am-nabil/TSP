package modele;

/**
 * @author nabil
 */
public interface Algorithme{
	
	/**
	 * Retourne le nom de l'algorithme
	 * @return nom de l'algorithme
	 */
	public String getNom();	

	/**
	 * Méthode qui permet de résoudre le problème 
	 * @param matriceDeDistances : matrice de coût du TSP
	 * @return La solution retournée par l'algorithme
	 */
	public  boolean[][] solve(int[][] matriceDeDistances);
}