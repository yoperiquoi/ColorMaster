package metier.formes;

import java.util.LinkedList;
import java.util.List;

public class Dessin extends Forme{
    float largueurTrait;
    private String type = "Dessin";
    public List<Float> pointsX = new LinkedList<Float>();
    public List<Float> pointsY = new LinkedList<Float>();

    public float getLargueurTrait(){return largueurTrait;}

    public void setLargueurTrait(float largueur){largueurTrait=largueur;}

    @Override
    public String toString() {
        return "Dessin{" +
                "largueurTrait=" + largueurTrait +
                ", pointsX=" + pointsX +
                ", pointsY=" + pointsY +
                '}';
    }
}
