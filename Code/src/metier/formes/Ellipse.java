package metier.formes;

import javafx.scene.paint.Color;

/**
 * Classe définissant l'ellipse
 */
public class Ellipse extends Forme{
    /**
     * Type de la forme pour la persistance
     */
    private final String type = "Ellipse";

    /**
     * Diamètre 1 de l'ellipse
     */
    private float diametre1;

    /**
     * Diamètre 2 de l'ellipse
     */
    private float diametre2;

    /**
     * Constructeur avec les propriétés déjà renseigné
     * @param diametre1 longueur du diamètre 1
     * @param diametre2 longueur du diamètre 2
     * @param x coordonnée x du point de départ
     * @param y coordonnée y du point de départ
     * @param rempli si le carré est rempli ou non
     * @param couleurRemp la couleur du remplissage
     * @param couleur la couleur du contour du carré
     */
    public Ellipse(float diametre1, float diametre2, float x , float y, boolean rempli, Color couleurRemp, Color couleur){
        setX(x);
        setY(y);
        setDiametre1(diametre1);
        setDiametre2(diametre2);
        setRempli(rempli);
        setCouleur(couleur);
        setCouleurRemplissage(couleurRemp);
    }

    /**
     * Constructeur vide permettant une définition plus tard
     */
    public Ellipse() {

    }

    /**
     * Méthode permettant de récupérer la longueur du diamètre 1
     * @return la longueur du diamètre 1
     */
    public float getDiametre1() {
        return diametre1;
    }

    /**
     * Méthode permettant de définir la longueur du diamètre 1
     * @param diametre1 longueur du diamètre 1
     */
    public void setDiametre1(float diametre1) {
        this.diametre1 = diametre1;
    }

    /**
     * Méthode permettant de récupérer la longueur du diamètre 2
     * @return la longueur du diamètre 2
     */
    public float getDiametre2() {
        return diametre2;
    }

    /**
     * Méthode permettant de définir la longueur du diamètre 2
     * @param diametre2 longueur du diamètre 2
     */
    public void setDiametre2(float diametre2) {
        this.diametre2 = diametre2;
    }
}
