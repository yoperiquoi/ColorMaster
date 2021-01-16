package metier.formes;

import javafx.scene.paint.Color;

/**
 * Classe définissant un cercle
 */
public class Cercle extends Forme{
    /**
     * Rayon du cercle
     */
    private float rayon;

    /**
     * Diamètre du cercle
     */
    private final float diametre=2*rayon;

    /**
     * Coordonnée X du centre du cercle
     */
    private float centreX;

    /**
     * Coordonnée Y du centre du cercle
     */
    private float centreY;

    /**
     * Type de la forme pour la persistance
     */
    private final String type = "Cercle";

    /**
     * Constructeur avec les propriétés déjà renseigné
     * @param x coordonnée x du point de départ
     * @param y coordonnée y du point de départ
     * @param rayon longueur du rayon
     * @param rempli si le carré est rempli ou non
     * @param couleurRemp la couleur du remplissage
     * @param couleur la couleur du contour du carré
     */
    public Cercle(float rayon, float x, float y, boolean rempli, Color couleurRemp, Color couleur){
        setRayon(rayon);
        setX(x);
        setY(y);
        setRempli(rempli);
        setCouleur(couleur);
        setCouleurRemplissage(couleurRemp);
    }

    /**
     * Constructeur vide permettant une définition plus tard
     */
    public Cercle() {

    }

    /**
     * Méthode permettant de récupérer la longueur du perimétre du cercle
     * @return la longueur du perimetre du cercle
     */
    public float getPerimetre() {
        return diametre;
    }

    /**
     * Méthode permettant de définir la longueur du rayon
     * @param rayon longueur du rayon
     */
    public void setRayon(float rayon) {
        this.rayon = rayon;
    }

    /**
     * Méthode permettant de retourner la longueur du rayon
     * @return la longueur du rayon
     */
    public float getRayon(){return rayon;}

    /**
     * Méthode permettant de retourner la coordonnée X du centre
     * @return coordonnée X du centre
     */
    public float getCentreX() {
        return centreX;
    }

    /**
     * Méthode permettant de retourner la coordonnée Y du centre
     * @return coordonnée Y du centre
     */
    public float getCentreY(){
        return centreY;
    }

    /**
     * Méthode permettant de définir le centre du cercle
     * @param centreX coordonnée X du centre
     * @param centreY coordonnée Y du centre
     */
    public void setCentre(float centreX,float centreY) {
        this.centreX = centreX;
        this.centreY=centreY;
    }


}
