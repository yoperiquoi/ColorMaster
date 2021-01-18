package metier.formes;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe définissant un dessin
 */
public class Dessin extends Forme{
    /**
     * Largeur du trait
     */
    float largueurTrait;

    /**
     * Liste des coordonnées des points en X
     */
    public List<Float> pointsX = new LinkedList<Float>();

    /**
     * Liste des coordonnées des points en Y
     */
    public List<Float> pointsY = new LinkedList<Float>();

    /**
     * Méthode permettant de récupérer la largeur du trait
     * @return la largeur du trait
     */
    public float getLargueurTrait(){return largueurTrait;}

    /**
     * Méthode permettant de définir la largeur du trait
     * @param largueur largeur du trait
     */
    public void setLargueurTrait(float largueur){largueurTrait=largueur;}
}
