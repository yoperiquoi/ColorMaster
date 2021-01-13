package metier.formes;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Rectangle extends Forme{
    private final String type = "Rectangle";
    private float longueur;
    private float largeur;

    public Rectangle(float longueur, float largeur, float x, float y, boolean rempli, Color couleurRemp,Color couleur) {
        setLongueur(longueur);
        setLargeur(largeur);
        setX(x);
        setY(y);
        setRempli(rempli);
        setCouleur(couleur);
        setCouleurRemplissage(couleurRemp);
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
