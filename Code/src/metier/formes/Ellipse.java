package metier.formes;

import javafx.scene.canvas.GraphicsContext;

public class Ellipse extends Forme{
    private float diametre1;
    private float diametre2;

    public Ellipse(float diametre1, float diametre2, float x ,float y){
        setX(x);
        setY(y);
        setDiametre1(diametre1);
        setDiametre2(diametre2);
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
