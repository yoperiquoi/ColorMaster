package metier.formes;

import javafx.scene.paint.Color;

/**
 * Classe permettant de définir un texte
 */
public class Text extends Forme{
    /**
     * Contenu du text
     */
    private String contenu;

    /**
     * Constructeur vide permettant une définition plus tard
     */
    public Text() {}

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
