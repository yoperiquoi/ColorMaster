package metier.formes;
import javafx.scene.canvas.GraphicsContext;


public class Rectangle extends Forme{
    private float longueur;
    private float largeur;

    public Rectangle(float longueur,float largeur,float x, float y) {
        setLongueur(longueur);
        setLargeur(largeur);
        setX(x);
        setY(y);
    }

    public Rectangle() {

    }

    public float getLargeur() {
        return largeur;
    }

    public void setLargeur(float largeur) {
        this.largeur = largeur;
    }

    public float getLongueur() {
        return longueur;
    }

    public void setLongueur(float longueur) {
        this.longueur = longueur;
    }

}
