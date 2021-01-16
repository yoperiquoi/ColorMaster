package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Effacement;

import java.io.Serializable;

/**
 * Défini la méthode de dessin pour un effacement
 */
public class DessinerEffacement  implements ICommande {
    Effacement effacement;
    //Type permettant la persistance en Json
    private final String type = "Effacement";

    /**
     * Constructeur de la commande prennant en paramètre un effacement
     * @param effacement figure qui va être dessiné
     */
    public DessinerEffacement(Effacement effacement) {
        this.effacement=effacement;
    }

    /**
     * Méthode permettant le dessin de l'effacement sur un contexte graphique
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    @Override
    public void execute(GraphicsContext gc) {
        //Même méthode que pour le dessin mais on efface au lieu de dessiner une ligne
        int i = 0 ;
        gc.setStroke(effacement.getCouleur());
        gc.setLineWidth(effacement.getLargueurTrait());
        gc.beginPath();
        for (float  x: effacement.pointsX) {
            float y=effacement.pointsY.get(i);
            gc.clearRect(x - effacement.getLargueurTrait() / 2, y - effacement.getLargueurTrait() / 2, effacement.getLargueurTrait(), effacement.getLargueurTrait());
            i=i+1;
        }
        gc.closePath();
    }
}
