package metier.formes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Forme {
    private float x;
    private float y;
    private Color couleur = Color.BLACK;
    private Boolean rempli;

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
}
