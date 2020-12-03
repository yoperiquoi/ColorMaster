package metier.formes;

import javafx.scene.canvas.GraphicsContext;

public class Ligne extends Forme{
    private float y2;
    private float x2;

    public Ligne(float x,float y,float x2,float y2){
        setX(x);
        setY(y);
        setX2(x2);
        setY2(y2);
    }

    public Ligne(){

    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }
}
