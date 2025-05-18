package cedric.calculatrice;

//on importe les librairies utiles au développement de l'interface graphique
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculatrice implements Runnable {

    //Variable pour l'affichage
    JLabel affichage = new JLabel("0");
    double retenue = 0;
    int operation;
    boolean nouveauNombre = false;
    public void run() {
        //Création de l'interface graphique
        JFrame frame = new JFrame("Calulatrice");
        //Paramétrage de base de la fermeture de la fenêtre
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Création du pavé numérique
        Panel paveNumerique = new Panel();
        // D'abord on crée les boutons
        JButton bouton1 = new JButton("1");
        JButton bouton2 = new JButton("2");
        JButton bouton3 = new JButton("3");
        JButton bouton4 = new JButton("4");
        JButton bouton5 = new JButton("5");
        JButton bouton6 = new JButton("6");
        JButton bouton7 = new JButton("7");
        JButton bouton8 = new JButton("8");
        JButton bouton9 = new JButton("9");
        JButton bouton0 = new JButton("0");
        JButton boutonVirgule = new JButton(",");
        JButton boutonPlus = new JButton("+");
        JButton boutonMoins = new JButton("-");
        JButton boutonFois = new JButton("x");
        JButton boutonDivise = new JButton("/");
        JButton boutonReset = new JButton("C");
        JButton boutonEgal = new JButton("=");
        // Puis on les affecte dans le bon ordre au pavé
        paveNumerique.setLayout(new GridLayout(5,3));
        paveNumerique.add(bouton7);
        paveNumerique.add(bouton8);
        paveNumerique.add(bouton9);
        paveNumerique.add(bouton4);
        paveNumerique.add(bouton5);
        paveNumerique.add(bouton6);
        paveNumerique.add(bouton1);
        paveNumerique.add(bouton2);
        paveNumerique.add(bouton3);
        paveNumerique.add(boutonPlus);
        paveNumerique.add(bouton0);
        paveNumerique.add(boutonMoins);
        paveNumerique.add(boutonFois);
        paveNumerique.add(boutonVirgule);
        paveNumerique.add(boutonDivise);
        //Création du panneau global de l'application
        Panel panel = new Panel();
        //Mise en place du pattern utilisé pour agencer la calculatrice
        panel.setLayout(new BorderLayout());
        //On met le cadrant en haut
        panel.add(affichage, BorderLayout.NORTH);
        //On met le pavé numérique au centre
        panel.add(paveNumerique, BorderLayout.CENTER);
        //On met le boutton d'égalité à droite
        panel.add(boutonEgal , BorderLayout.EAST);
        //On met le boutton reset à gauche
        panel.add(boutonReset , BorderLayout.WEST);
        //On implémente la calculatrice dans la fenêtre
        frame.getContentPane().add(panel);
        //On pack l'ensemble dans la fenêtre
        frame.pack();
        //On rend la fenêtre visible
        frame.setVisible(true);

        bouton1.addActionListener(new ChiffreListener());
        bouton2.addActionListener(new ChiffreListener());
        bouton3.addActionListener(new ChiffreListener());
        bouton4.addActionListener(new ChiffreListener());
        bouton5.addActionListener(new ChiffreListener());
        bouton6.addActionListener(new ChiffreListener());
        bouton7.addActionListener(new ChiffreListener());
        bouton8.addActionListener(new ChiffreListener());
        bouton9.addActionListener(new ChiffreListener());
        bouton0.addActionListener(new ChiffreListener());
        boutonPlus.addActionListener(new OperationListener());
        boutonMoins.addActionListener(new OperationListener());
        boutonFois.addActionListener(new OperationListener());
        boutonDivise.addActionListener(new OperationListener());
        boutonReset.addActionListener(new ResetListener());
        boutonVirgule.addActionListener(new VirguleListener());
        boutonEgal.addActionListener(new EgalListener());
    }

    /*
     *   S'occupe de gérer les actions lorsque l'on appuie sur un chiffre
     */
    class ChiffreListener implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e){
            // On prend la valeur du chiffre sur lequel nous venons d'appuyer
            String chiffre = ((JButton)e.getSource()).getText();

            // Si le zéro initial est affiché le remplacer par le chiffre entré
            if(affichage.getText().equals("0")){
                affichage.setText(chiffre);
            } else if(nouveauNombre){ //affichage.getText() == "0"
                // Si nous sommes en train d'écrire un nouveau nombre remplacer la retenue par ce nombre et remettre la variable sur false
                affichage.setText(chiffre);
                nouveauNombre = false;
            } else{
                // Sinon se contenter de rajouter le chiffre à la suite du nombre en rédaction
                affichage.setText(affichage.getText()+chiffre);
            }
        }
    }

    /*
     *   Gère les évènements lié aux touches des opérateurs
     */
    class OperationListener implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e){
            Object source = ((JButton)e.getSource()).getText();

            //faire l'opération, rentrer le nouveau nombre en mémoire, l'afficher et préciser que le nombre afficher n'est pas le prochain nombre à rentrer
            if(!nouveauNombre) {
                retenue = operations(retenue, Double.parseDouble(affichage.getText()), operation);
                affichage.setText(Double.toString(retenue));
                nouveauNombre = true; // permet de bloquer la retenue mais de modifier l'opérateur
            }
            // Met en mémoire le nouvel opérateur
            if(source == "+") {
                operation = 0;
            } else if(source == "-") {
                operation = 1;
            } else if (source == "x") {
                operation = 2;
            } else if (source == "/") {
                operation = 3;
            } 
        }
    }

    /*
     *   Renvoie le résultat de l'opération ou de la suite d'opération écrite
     */
    class EgalListener implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e){
            if(!nouveauNombre) {
                retenue = operations(retenue, Double.parseDouble(affichage.getText()), operation);
                affichage.setText(Double.toString(retenue));
                nouveauNombre = true;
            }
        }
    }

    /*
     *   Remet tout à zéro
     */
    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e){
            retenue = 0;
            affichage.setText("0");
            nouveauNombre = false;
        }
    }
/*
*   Se charge d'ajouter un point s'il n'est pas déjà présent sur le nombre en cours de rédaction
*/
    class VirguleListener implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e){
            //si le nombre en cours ne contient pas déjà une virgule
            if(!affichage.getText().contains(".")) {
                affichage.setText(affichage.getText()+".");
            }
        }
    }
/*
* Applique la bonne opération en fonction du nombre en retenue, du nombre affiché et de l'opérateur sélectionné
 */
    public double operations(double a, double b, int operateur) {
        switch (operateur) {
            //addition
            case 0:
                return a + b;
            //soustraction
            case 1:
                return a - b;
            //multiplication
            case 2:
                return a * b;
            //division
            case 3:
                return a / b;

            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater( new Calculatrice());
    }
}
