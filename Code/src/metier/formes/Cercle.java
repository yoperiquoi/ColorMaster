package metier.formes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cercle extends Forme{
    private float rayon;
    private float diametre=2*rayon;
    private float centreX;
    private float centreY;


    public Cercle(float rayon, float x, float y, boolean rempli, Color couleurRemp, Color couleur){
        setRayon(rayon);
        setX(x);
        setY(y);
        setRempli(rempli);
        setCouleur(couleur);
        setCouleurRemplissage(couleurRemp);
    }

    public Cercle() {

    }

    public float getPerimetre() {
        return diametre;
    }

    public void setRayon(float rayon) {
        this.rayon = rayon;
    }

    public float getRayon(){return rayon;}

    public float getCentreX() {
        return centreX;
    }
    public float getCentreY(){
        return centreY;
    }

    public void setCentre(float centreX,float centreY) {
        this.centreX = centreX;
        this.centreY=centreY;
    }


}
