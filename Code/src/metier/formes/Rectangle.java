package metier.formes;

import javafx.scene.paint.Color;

/**
 * Classe définissant le rectangle
 */
public class Rectangle extends Forme{
    /**
     * Type de la forme pour la persistance
     */
    private final String type = "Rectangle";

    /**
     * Longueur du rectangle
     */
    private float longueur;

    /**
     * Largeur du rectangle
     */
    private float largeur;

    /**
     * Constructeur avec les propriétés déjà renseigné
     * @param x coordonnée x du point de départ
     * @param y coordonnée y du point de départ
     * @param longueur longueur du rectangle
     * @param largeur largeur du rectangle
     * @param rempli si le carré est rempli ou non
     * @param couleurRemp la couleur du remplissage
     * @param couleur la couleur du contour du carré
     */
    public Rectangle(float longueur, float largeur, float x, float y, boolean rempli, Color couleurRemp,Color couleur) {
        setLongueur(longueur);
        setLargeur(largeur);
        setX(x);
        setY(y);
        setRempli(rempli);
        setCouleur(couleur);
        setCouleurRemplissage(couleurRemp);
    }

    /**
     * Constructeur vide permettant une définition plus tard
     */
    public Rectangle() {

    }

    /**
     * Méthode permettant de récupérer la largeur du rectangle
     * @return largeur du rectangle
     */
    public float getLargeur() {
        return largeur;
    }

    /**
     * Méthode permettant de définir la largeur du rectangle
     * @param largeur largeur du rectangle
     */
    public void setLargeur(float largeur) {
        this.largeur = largeur;
    }

    /**
     * Méthode permettant de récupérer la longueur du rectangle
     * @return longueur du rectangle
     */
    public float getLongueur() {
        return longueur;
    }

    /**
     * Méthode permettant de définir la longueur du rectangle
     * @param longueur longueur du rectangle
     */
    public void setLongueur(float longueur) {
        this.longueur = longueur;
    }

}
