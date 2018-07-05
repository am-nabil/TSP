package vue;

import presentateur.*;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.util.ArrayList;

//Classe JetonJava
public class Vue extends JFrame {
    public static JTextField jt;
    private final JLabel jl;
    private final JLabel jl2;
    public static JTextArea jta;
    private final MonBoutonAfficher jba;
    //private final MonBoutonReinit jbi;
    private final JScrollPane sp;
    //private final MonBoutonTelecharger jbt;
    private final MonBoutonParcourrir jbp;
	private final MonBoutonResoudre jbr;
    private final ChoixAlgorithme cha;
//constructeur de classe
    public Vue(){

        setLocation(new Point(0,0));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(720,480));
        setResizable(false);
        setVisible(true);
        setTitle("PVC Solver");
		setBackground(Color.yellow);
        jl = new JLabel ("Fichier         : ");
        jl2 = new JLabel("Algorithme : ");
        jt = new JTextField(40);
        jba = new MonBoutonAfficher();
        //jbi = new MonBoutonReinit();
        //jbt = new MonBoutonTelecharger();
        jbp = new MonBoutonParcourrir();
		jbr = new MonBoutonResoudre();
		jta = new JTextArea(550,480);
        jta.setBorder(BorderFactory.createTitledBorder("Source"));
        String[] listeAlgos = {
                "PPV (Plus proche voisin)",
                "MPIPP (Méthode par insertion Plus proche)",
                "MPIPL (Méthode par insertion Plus loin)",
                "Regret Maximal",
                "2-OPT (2 permutations)",
                "3-OPT (3 permutations)",
                "LITTLE (Branch and bound)",
                "Exhaustive (Méthode Naive)"
        };
        cha = new ChoixAlgorithme(listeAlgos);
        sp = new JScrollPane(jta);
        sp.setPreferredSize(new Dimension(550,480));
        Box b1 = Box.createHorizontalBox();
        b1.add(jl);b1.add(jt);b1.add(jbp);//b1.add(jbi);
        Box b3 = Box.createHorizontalBox();
        b3.add(jl2);b3.add(cha);b3.add(jbr);
        Box b4 = Box.createHorizontalBox();
        b4.add(jta);b4.add(sp);
        Box b5 = Box.createVerticalBox();
        b5.add(b1);b5.add(b3);b5.add(b4);
        this.getContentPane().add(b5);
        this.setVisible(true);
        //add(jp2);
        //add(jp3);
        //add(jp4);
        pack();
    }
    
    //Sous classe bouton Afficher
    public class MonBoutonAfficher extends JButton implements ActionListener{
        public MonBoutonAfficher(){
                super("Afficher");
                addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(jt.getText().equals(""))
            {
                JOptionPane job = new JOptionPane();
                job.showMessageDialog(null, "Aucun Fichier Séléctionné !", "Attention", JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                try {
                    lire(jt.getText());
                } catch (IOException ex) {
                    JOptionPane job = new JOptionPane();
                    job.showMessageDialog(null, "Impossible de lire!", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    //Sous classe bouton parcourrir
    public class MonBoutonParcourrir extends JButton implements ActionListener{
        public MonBoutonParcourrir(){
                super("Parcourrir");
                addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame f = new JFrame();
            FileDialog fd = new FileDialog (f, "Selectionner un fichier TSP", FileDialog.LOAD);
            fd.setFile ("*.*");
            fd.setVisible(true);
            String fileDir="";
            String directory = (fd.getDirectory());   
            String fileName = (fd.getFile());
            if ((directory == null) || (fileName == null)) return;
            fileDir = directory+fileName;
            jt.setText(fileDir);
            //f.setVisible(true);
            f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }
    
    //sous classe bouton reinitialiser
    public class MonBoutonReinit extends JButton implements ActionListener{
        public MonBoutonReinit(){
                super("Reinitialiser");
                addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            jta.setText("");
            jt.setText("");
        }
    }    
    

    //sous classe bouton reinitialiser
    public class ChoixAlgorithme extends JComboBox implements ActionListener{
        public ChoixAlgorithme(String[] elements){
                super(elements);
                addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
    }


    //sous classe bouton Telecharger
    public class MonBoutonTelecharger extends JButton implements ActionListener{
        public MonBoutonTelecharger(){
                super("Enregistrer Sous");
                addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new  File("."+File.separator)); 
                int reponse = chooser.showDialog(chooser,"Enregistrer sous");
                if  (reponse == JFileChooser.APPROVE_OPTION){
                    String  fichier= chooser.getSelectedFile().toString(); 
                
                if(! jta.getText().equals("")){
                    File nomFichier = new File(fichier+".html");
                    ecrire(nomFichier,jta.getText());
                }
                else{
                    JOptionPane job = new JOptionPane();
                    job.showMessageDialog(null, "Fichier vide !", "Attention", JOptionPane.WARNING_MESSAGE);
                }
                }
           }catch(HeadlessException he){
                he.printStackTrace();
           }
        }
    }
     
    //Sous classe bouton générer du HTML 
    public class MonBoutonResoudre extends JButton implements ActionListener {
        public MonBoutonResoudre(){
                super("Resoudre");
                addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(jt.getText().equals(""))
            {
                JOptionPane job = new JOptionPane();
                job.showMessageDialog(null, "Aucun Fichier Séléctionné !", "Attention", JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                String fichier = jt.getText();
                Solver solver = new Solver(jt.getText(), cha.getSelectedItem().toString());
                try{
                	String print = solver.solve();
                	jta.setText(print);
				}
				catch (FileNotFoundException exception)
				{
					JOptionPane job = new JOptionPane();
                    job.showMessageDialog(null, "Le fichier "+fichier+" n'a pas été trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
				  	//return;
				}
				catch (IOException exception)
				{
				   	JOptionPane job = new JOptionPane();
                    job.showMessageDialog(null, "Erreur de lecture du fichier "+fichier, "Erreur" , JOptionPane.ERROR_MESSAGE);
				}
				catch (NumberFormatException exception)
				{
				   	JOptionPane job = new JOptionPane();
                    job.showMessageDialog(null, "Contenu mal-formaté du fichier "+fichier, "Erreur", JOptionPane.ERROR_MESSAGE);				  	
				}
				catch (IndexOutOfBoundsException exception)
				{
					JOptionPane job = new JOptionPane();
                    job.showMessageDialog(null, "Tailles differentes - sérialisation impossible du fichier "+fichier, "Erreur", JOptionPane.ERROR_MESSAGE);				  	
				}

            }
        }
    }
    
        
    //Methode permettant de lire dans un fichier et l'afficher sur notre JTextArea
    public void lire(String entree) throws IOException{
        try{
            BufferedReader buff = new BufferedReader(new FileReader(entree));
 
            try {
                String line;
                jta.setText("");
                while ((line = buff.readLine()) != null) {
                    jta.append(line);
                    jta.append("\n");
                }
            } 
            finally {
                buff.close();
            }
        }
        catch (IOException ioe) {
                    JOptionPane job = new JOptionPane();
                    job.showMessageDialog(null, "Impossible de lire!", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Methode qui ecrit dans un fichier
    public void ecrire(File f, String texte){
        try {
            PrintWriter fichier = new PrintWriter(new FileWriter(f), false);
            fichier.println(texte);
            fichier.close();
        }
        catch(Exception e) {
                    JOptionPane job = new JOptionPane();
                    job.showMessageDialog(null, "Impossible d'ecrire!", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    
    }


    
}
