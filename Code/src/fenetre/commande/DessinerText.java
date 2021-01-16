package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Text;

import java.io.Serializable;

/**
 * Défini la méthode de dessin pour un carré
 */
public class DessinerText  implements ICommande {
    Text text;

    //Type permettant la persistance en Json
    private final String type = "Text";

    /**
     * Constructeur de la commande prennant en paramètre un text
     * @param text figure qui va être dessiné
     */
    public DessinerText(Text text) {
        this.text=text;
    }

    /**
     * Méthode permettant le dessin du text sur un contexte graphique
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    @Override
    public void execute(GraphicsContext gc) {
        //On dessine le remplissage du texte
        gc.fillText(text.getContenu(), text.getX(), text.getY());
        //On dessine le contour du texte
        gc.strokeText(text.getContenu(), text.getX(), text.getY());
    }
}
