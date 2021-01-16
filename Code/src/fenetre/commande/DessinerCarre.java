package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Carre;

/**
 * Défini la méthode de dessin pour un carré
 */
public class DessinerCarre  implements ICommande {
    Carre carre;

    //Type permettant la persistance en Json
    private final String type = "Carre";

    /**
     * Constructeur de la commande prenant en paramètre un carre
     * @param carre figure qui va être dessiné
     */
    public DessinerCarre(Carre carre){
        this.carre=carre;
    }

    /**
     * Méthode permettant le dessin du carre sur un contexte graphique
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    @Override
    public void execute(GraphicsContext gc) {
        //On verifie si le carré est rempli et si c'est le cas on dessine le remplissage du carré
        if (carre.getRempli()){
            gc.setFill(carre.getCouleurRemplissage());
            gc.fillRect(carre.getX(),carre.getY(),carre.getCote(),carre.getCote());
        }
        //Et enfin on dessine le contour du carré
        gc.strokeRect(carre.getX(),carre.getY(),carre.getCote(),carre.getCote());
    }
}
