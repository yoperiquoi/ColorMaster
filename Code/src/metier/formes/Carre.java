package metier.formes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe définissant un carré
 */
public class Carre extends Forme{
    /**
     * Longueur des coté du carré
     */
    private float cote;


    /**
     * Constructeur vide permettant une définition plus tard
     */
    public Carre() {

    }

    /**
     * Méthode permettant de récupérer la longueur du coté du carré
     * @return la longueur du coté du carré
     */
    public float getCote() {
        return cote;
    }

    /**
     * Méthode permettant de définir la longueur du coté du carré
     * @param cote longueur du coté
     */
    public void setCote(float cote) {
        this.cote = cote;
    }


}
