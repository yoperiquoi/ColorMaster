package metier.formes;

import javafx.scene.paint.Color;

/**
 * Classe définissant le rectangle
 */
public class Rectangle extends Forme{
    /**
     * Longueur du rectangle
     */
    private float longueur;

    /**
     * Largeur du rectangle
     */
    private float largeur;

    /**
     * Constructeur vide permettant une définition plus tard
     */
    public Rectangle() {}

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
