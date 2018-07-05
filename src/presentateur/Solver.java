package presentateur;

import modele.*;
import parser.ParseFichier;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;

public class Solver{
    protected String fichier;
    protected String algorithme;
    public Solver(String fichier, String algorithme){
        this.fichier=fichier;
        this.algorithme=algorithme;
    }

    public String solve() throws FileNotFoundException, IOException,NumberFormatException, IndexOutOfBoundsException {
                ParseFichier pf = new ParseFichier();
                int[][] matrice = pf.parser(this.fichier);

                Algorithme ppv = new PPV("Plus Proche Voisin");
                Algorithme mpippi = new MPIPPI("Insertion Sommet Libre Le Plus Proche");
                Algorithme mpipli = new MPIPLI("Insertion Sommet Libre Le Plus Loin"); 
                Algorithme regretMaximal = new RegretMaximal("Regret Maximal"); 
                Algorithme bandb = new BandB("BandB"); 
                Algorithme naive = new MethodeNaive("Methode Exhaustive"); 
                Algorithme opt2 = new Opt2("2-Opt"); 
                Algorithme opt3 = new Opt3("3-Opt");

                Client cl = new Client(ppv);

                String alg = this.algorithme;
                switch (alg){
                    case "PPV": cl.setAlgorithme(ppv); break;
                    case "MPIPP":  cl.setAlgorithme(mpippi); break;
                    case "MPIPL":  cl.setAlgorithme(mpipli); break;
                    case "Regret Max":  cl.setAlgorithme(regretMaximal); break;
                    case "2-OPT":  cl.setAlgorithme(opt2); break;
                    case "3-OPT":  cl.setAlgorithme(opt3); break;
                    case "LITTLE":  cl.setAlgorithme(bandb); break;
                    case "Exhaustive":  cl.setAlgorithme(naive); break;
                }

                String print="";
                
                long start = System.currentTimeMillis();
                try{
                    boolean[][] solution = cl.solve(matrice);
                    print = ("Solution            : "); 
                    
                    ArrayList<Integer> cycle = new ArrayList<Integer>();
                    int succ = 0;
                    int s = succ;
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

                    for(Integer i : cycle)
                        print+=("["+i+"]-");
                    print+="\n";       

                    print+=("cout de la solution : ");
                    int coutTotal = 0;
                    for(int i=0;i<solution.length;i++)
                        for(int j=0;j<solution.length;j++)
                            if (solution[i][j])
                                coutTotal+=matrice[i][j];
                    print+=coutTotal;
                    print+="\n";
                }catch(OutOfMemoryError erreur){
                    print+=("OutOfMemoryError\n");
                }catch(Exception exception){
                    print+=("Arrêt d'exécution\n");
                }
                //date de fin
                long end = System.currentTimeMillis();
                //affichage de performance
                long timeSpent = end-start;
                long minutes = (timeSpent/60000);
                long secondes = (timeSpent - (minutes*60000)) /1000;
                long millisecondes = (timeSpent - (minutes*60000) - (secondes*1000));
                print+=("Temps d'execution   : "+minutes+" Minutes et "+secondes+" Secondes et "+millisecondes+" Millisecondes.");
                print+=("\n");

                return print;

    }
}