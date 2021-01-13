package metier.formes;

public class Effacement extends Dessin{
    private final String type = "Effacement";
    @Override
    public String toString() {
        return "Effacement{" +
                "largueurTrait=" + largueurTrait +
                ", pointsX=" + pointsX +
                ", pointsY=" + pointsY +
                '}';
    }
}
