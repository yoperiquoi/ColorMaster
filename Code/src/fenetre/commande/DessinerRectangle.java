package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Rectangle;

import java.io.Serializable;

/**
 * Défini la méthode de dessin pour un carré
 */
public class DessinerRectangle  implements ICommande {

    /**
     * Rectangle à dessiner
     */
    Rectangle rectangle;

    /**
     * Type permettant la persistance
     */
    private final String type = "Carre";

    /**
     * Constructeur de la commande prennant en paramètre un rectangle
     * @param rectangle figure qui va être dessiné
     */
    public DessinerRectangle(Rectangle rectangle) {
        this.rectangle=rectangle;
    }

    /**
     * Méthode permettant le dessin du rectangle sur un contexte graphique
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    @Override
    public void execute(GraphicsContext gc) {
        //On récupére et dessine le remplissage
        if(rectangle.getRempli()){
            gc.setFill(rectangle.getCouleurRemplissage());
            gc.fillRect(rectangle.getX(),rectangle.getY(),rectangle.getLargeur(),rectangle.getLongueur());
        }
        //On dessine le contour du rectangle
        gc.strokeRect(rectangle.getX(),rectangle.getY(),rectangle.getLargeur(),rectangle.getLongueur());
    }
}
