package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Ellipse;

import java.io.Serializable;

/**
 * Défini la méthode de dessin pour une ellipse
 */
public class DessinerEllipse  implements ICommande {
    /**
     * Ellipse a dessiner
     */
    Ellipse ellipse;

    /**
     * Type permettant la persistance
     */
    private final static String type = "Ellipse";

    /**
     * Constructeur de la commande prennant en paramètre une ellipse
     * @param ellipse figure qui va être dessiné
     */
    public DessinerEllipse(Ellipse ellipse) {
        this.ellipse=ellipse;
    }

    /**
     * Méthode permettant le dessin de l'ellipse sur un contexte graphique
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    @Override
    public void execute(GraphicsContext gc) {
        //On récupére le remplissage et on le dessine
        if(ellipse.getRempli()){
            gc.setFill(ellipse.getCouleurRemplissage());
            gc.fillOval(ellipse.getX(),ellipse.getY(),ellipse.getDiametre1(),ellipse.getDiametre2());
        }
        //On dessine ensuite le contour de l'ellipse
        gc.strokeOval(ellipse.getX(),ellipse.getY(),ellipse.getDiametre1(),ellipse.getDiametre2());
    }
}
