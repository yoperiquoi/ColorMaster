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
     * Type de la forme pour la persistance
     */
    private final String type = "Carre";

    /**
     * Constructeur avec les propriétés déjà renseigné
     * @param x coordonnée x du point de départ
     * @param y coordonnée y du point de départ
     * @param cote longueur d'un coté
     * @param rempli si le carré est rempli ou non
     * @param couleurRemp la couleur du remplissage
     * @param couleur la couleur du contour du carré
     */
    public Carre(float x, float y, float cote, boolean rempli, Color couleurRemp, Color couleur){
        setX(x);
        setY(y);
        setCote(cote);
        setRempli(rempli);
        setCouleur(couleur);
        setCouleurRemplissage(couleurRemp);
    }

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

    @Override
    public String toString() {
        return "Carre{" +
                "cote=" + cote +
                ", type='" + type + '\'' +
                '}';
    }
}
