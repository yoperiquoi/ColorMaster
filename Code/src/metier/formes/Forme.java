package metier.formes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Classe définissant une forme en général
 */
public abstract class Forme implements Serializable {
    /**
     * Point X de départ
     */
    private float x;

    /**
     * Point Y de départ
     */
    private float y;

    /**
     * Couleur de trait de la figure
     */
    public Color couleur = Color.BLACK;

    /**
     * Couleur de remplissage de la figure
     */
    public Color couleurRemplissage= Color.TRANSPARENT;

    /**
     * Défini si la figure est remplie ou non
     */
    private Boolean rempli=false;

    /**
     * Largeur du trait
     */
    private float largeurTrait;

    /**
     * Méthode permettant de déplacer la figure à de nouvelles coordonnées
     * @param x nouvelle coordonnée x
     * @param y nouvelle coordonnée y
     */
    public void deplacer(float x, float y){
        setX(x);
        setY(y);
    }

    /**
     * Méthode permettant de récupérer X
     * @return la valeur de x
     */
    public float getX() {
        return x;
    }

    /**
     * Méthode permettant de définir X
     * @param x valeur de x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Méthode permettant de récupérer Y
     * @return la valeur de Y
     */
    public float getY() {
        return y;
    }

    /**
     * Méthode permettant de définir Y
     * @param y valeur de Y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Méthode permettant de savoir si la figure est rempli
     * @return si la figure est rempli ou non
     */
    public Boolean getRempli() {
        return rempli;
    }

    /**
     * Méthode permettant de définir si la figure est rempli
     * @param rempli si la figure est rempli ou non
     */
    public void setRempli(Boolean rempli) {
        this.rempli = rempli;
    }

    /**
     *  Méthode permettant de savoir la couleur de la figure
     * @return la couleur de la figure
     */
    public Color getCouleur() {
        return couleur;
    }

    /**
     * Méthode permettant de définir la couleur de la figure
     * @param couleur la couleur de la figure
     */
    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    /**
     *  Méthode permettant de savoir la couleur du remplissage de la figure
     * @return la couleur du remplissage de la figure
     */
    public Color getCouleurRemplissage() { return couleurRemplissage; }

    /**
     * Méthode permettant de définir la couleur du remplissage de la figure
     * @param couleurRemplissage la couleur du remplissage de la figure
     */
    public void setCouleurRemplissage(Color couleurRemplissage){this.couleurRemplissage=couleurRemplissage;}

    /**
     * Méthode permettant de récupérer la largeur du trait
     * @return largeur du trait
     */
    public float getLargeurTrait() {return largeurTrait;}

    /**
     * Méthode permettant de définir la largeur du trait
     * @param largeurTrait largeur du trait
     */
    public void setLargeurTrait(float largeurTrait) {this.largeurTrait = largeurTrait;}
}
