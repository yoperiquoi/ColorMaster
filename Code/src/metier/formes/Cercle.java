package metier.formes;

import javafx.scene.canvas.GraphicsContext;

public class Cercle extends Forme{
    private float rayon;
    private float perimetre=2*rayon;

    public Cercle(float rayon,float x, float y){
        setRayon(rayon);
        setX(x);
        setY(y);
    }

    public float getPerimetre() {
        return perimetre;
    }

    public void setRayon(float rayon) {
        this.rayon = rayon;
    }
}
