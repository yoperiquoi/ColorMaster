package metier.formes;

/**
 * Classe identique au dessin sauf pour son type
 */
public class Effacement extends Dessin{
    /**
     * Type de la forme pour la persistance
     */
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
