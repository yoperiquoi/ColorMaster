package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Cercle;

import java.io.Serializable;

/**
 * Défini la méthode de dessin pour un cercle
 */
public class DessinerCercle  implements ICommande {
    /**
     * Cercle à dessiner
     */
    Cercle cercle;

    /**
     * Type permettant la persistance
     */
    private final static String type = "Cercle";

    /**
     * Construteur permettant de créer la commande avec un cercle prédéfini
     * @param cercle cercle qui va être dessiné
     */
    public DessinerCercle(Cercle cercle) {
        this.cercle=cercle;
    }

    /**
     * Méthode permettant le dessin du cercle sur un contexte graphique
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    @Override
    public void execute(GraphicsContext gc) {
        //On verifie si le cercle est rempli et si c'est le cas on dessine le remplissage du cercle
        if(cercle.getRempli()){
            gc.setFill(cercle.getCouleurRemplissage());
            gc.fillOval(cercle.getX(),cercle.getY(),cercle.getRayon(),cercle.getRayon());
        }
        //Et enfin on dessine le contour du cercle
        gc.strokeOval(cercle.getX(),cercle.getY(),cercle.getRayon(),cercle.getRayon());
    }
}
