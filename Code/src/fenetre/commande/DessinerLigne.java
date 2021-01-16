package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Ligne;

import java.io.Serializable;

/**
 * Défini la méthode de dessin pour un carré
 */
public class DessinerLigne  implements ICommande {
    Ligne ligne;

    //Type permettant la persistance en Json
    private final String type = "Ligne";

    /**
     * Constructeur de la commande prennant en paramètre une ligne
     * @param ligne figure qui va être dessiné
     */
    public DessinerLigne(Ligne ligne) {
        this.ligne=ligne;
    }

    /**
     * Méthode permettant le dessin du ligne sur un contexte graphique
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    @Override
    public void execute(GraphicsContext gc) {
        //On dessine la ligne
        gc.strokeLine(ligne.getX(),ligne.getY(),ligne.getX2(),ligne.getY2());
    }
}
