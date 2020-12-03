package metier.formes;

import javafx.scene.canvas.GraphicsContext;

public class Carre extends Forme{
    private float cote;

    public Carre(float x,float y){
        setX(x);
        setY(y);
        setCote(cote);
    }

    public Carre() {

    }

    public float getCote() {
        return cote;
    }

    public void setCote(float cote) {
        this.cote = cote;
    }
}