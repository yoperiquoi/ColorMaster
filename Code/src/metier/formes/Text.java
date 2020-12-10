package metier.formes;

import javafx.scene.paint.Color;

public class Text extends Forme{
    private String contenu;

    public Text(float x, float y, String contenu, boolean rempli, Color couleurRemp, Color couleur){
        setX(x);
        setX(y);
        setContenu(contenu);
        setRempli(rempli);
        setCouleur(couleur);
        setCouleurRemplissage(couleurRemp);
    }

    public Text() {

    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}
