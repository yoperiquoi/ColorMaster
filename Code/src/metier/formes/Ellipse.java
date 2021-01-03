package metier.formes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ellipse extends Forme{
    private String type = "Ellipse";
    private float diametre1;
    private float diametre2;

    public Ellipse(float diametre1, float diametre2, float x , float y, boolean rempli, Color couleurRemp, Color couleur){
        setX(x);
        setY(y);
        setDiametre1(diametre1);
        setDiametre2(diametre2);
        setRempli(rempli);
        setCouleur(couleur);
        setCouleurRemplissage(couleurRemp);
    }

    public Ellipse() {

    }

    public float getDiametre1() {
        return diametre1;
    }

    public void setDiametre1(float diametre1) {
        this.diametre1 = diametre1;
    }

    public float getDiametre2() {
        return diametre2;
    }

    public void setDiametre2(float diametre2) {
        this.diametre2 = diametre2;
    }
}
