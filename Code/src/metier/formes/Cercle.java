package metier.formes;

import javafx.scene.canvas.GraphicsContext;

public class Cercle extends Forme{
    private float rayon;
    private float diametre=2*rayon;
    private float centreX;
    private float centreY;


    public Cercle(float rayon,float x, float y){
        setRayon(rayon);
        setX(x);
        setY(y);
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
