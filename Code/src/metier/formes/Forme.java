package metier.formes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.lang.reflect.Type;

public abstract class Forme implements Serializable {
    private float x;
    private float y;
    private Color couleur = Color.BLACK;
    private Color couleurRemplissage= Color.TRANSPARENT;
    private Boolean rempli=false;
    private float largeurTrait;

    public void deplacer(float x, float y){
        setX(x);
        setY(y);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Boolean getRempli() {
        return rempli;
    }

    public void setRempli(Boolean rempli) {
        this.rempli = rempli;
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public Color getCouleurRemplissage() { return couleurRemplissage; }

    public void setCouleurRemplissage(Color couleurRemplissage){this.couleurRemplissage=couleurRemplissage;}

    public float getLargeurTrait() {return largeurTrait;}

    public void setLargeurTrait(float largeurTrait) {this.largeurTrait = largeurTrait;}
}
