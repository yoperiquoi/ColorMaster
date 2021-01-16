package metier.formes;

import javafx.scene.paint.Color;

/**
 * Classe définissant une ligne
 */
public class Ligne extends Forme{
    /**
     * Type de la forme pour la persistance
     */
    private final String type = "Ligne";

    /**
     * Coordonnées Y d'arrivé de la ligne
     */
    private float y2;

    /**
     * Coordonnées X d'arrivé de la ligne
     */
    private float x2;

    /**
     * Constructeur avec les propriétés déjà renseigné
     * @param x coordonnée x du point de départ
     * @param y coordonnée y du point de départ
     * @param x2 coordonnée x du point de d'arriver
     * @param y2 coordonnée y du point de d'arriver
     * @param couleur la couleur du trait
     */
    public Ligne(float x, float y, float x2, float y2, Color couleur){
        setX(x);
        setY(y);
        setX2(x2);
        setY2(y2);
        setCouleur(couleur);
    }

    /**
     * Constructeur vide permettant une définition plus tard
     */
    public Ligne(){

    }

    /**
     * Méthode permettant de récupérer le point y d'arrivé de la ligne
     * @return point y
     */
    public float getY2() {
        return y2;
    }

    /**
     * Méthode permettant de définir le point y d'arrivé de la ligne
     * @param y2 point y
     */
    public void setY2(float y2) {
        this.y2 = y2;
    }

    /**
     * Méthode permettant de récupérer le point x d'arrivé de la ligne
     * @return point x
     */
    public float getX2() {
        return x2;
    }

    /**
     * Méthode permettant de définir le point y d'arrivé de la ligne
     * @param x2 point x
     */
    public void setX2(float x2) {
        this.x2 = x2;
    }

    @Override
    public String toString() {
        return "Ligne{" +
                "type='" + type + '\'' +
                ", y2=" + y2 +
                ", x2=" + x2 +
                '}';
    }
}
