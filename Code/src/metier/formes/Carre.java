package metier.formes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Carre extends Forme{
    private float cote;
    private final String type = "Carre";

    public Carre(float x, float y, float cote, boolean rempli, Color couleurRemp, Color couleur){
        setX(x);
        setY(y);
        setCote(cote);
        setRempli(rempli);
        setCouleur(couleur);
        setCouleurRemplissage(couleurRemp);
    }

    public Carre() {

    }

    public float getCote() {
        return cote;
    }

    public void setCote(float cote) {
        this.cote = cote;
    }

    @Override
    public String toString() {
        return "Carre{" +
                "cote=" + cote +
                ", type='" + type + '\'' +
                '}';
    }
}
