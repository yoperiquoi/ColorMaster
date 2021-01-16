package metier.formes;

import javafx.scene.paint.Color;

/**
 * Classe permettant de définir un texte
 */
public class Text extends Forme{
    /**
     * Type de la forme pour la persistance
     */
    private final String type = "Text";

    /**
     * Contenu du text
     */
    private String contenu;

    /**
     * Constructeur avec les propriétés déjà renseigné
     * @param x coordonnée x du point de départ
     * @param y coordonnée y du point de départ
     * @param contenu texte
     * @param rempli si le carré est rempli ou non
     * @param couleurRemp la couleur du remplissage
     * @param couleur la couleur du contour du carré
     */
    public Text(float x, float y, String contenu, boolean rempli, Color couleurRemp, Color couleur){
        setX(x);
        setY(y);
        setContenu(contenu);
        setRempli(rempli);
        setCouleur(couleur);
        setCouleurRemplissage(couleurRemp);
    }

    /**
     * Constructeur vide permettant une définition plus tard
     */
    public Text() {

    }

    /**
     * Méthode permettant de récupérer le texte
     * @return le texte
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * Méthode permettant de définir le texte
     * @param contenu texte
     */
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}
