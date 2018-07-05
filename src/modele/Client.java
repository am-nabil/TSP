package modele;

/**
 * @author nabil
 */
public class Client{

	protected Algorithme algorithme;
	public Client(Algorithme algorithme){
		this.algorithme = algorithme;
	}
	
	public Algorithme getAlgorithme(){
		return this.algorithme;
	}
	
	public void setAlgorithme(Algorithme algorithme){
		this.algorithme = algorithme;
	}

	public boolean[][] solve (int [][] matriceDeDistances) {
		return algorithme.solve(matriceDeDistances);
	}

}