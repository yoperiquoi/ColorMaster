package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Dessin;

import java.io.Serializable;

/**
 * Défini la méthode de dessin pour un dessin
 */
public class DessinerDessin  implements ICommande {
    Dessin dessin;
    //Type permettant la persistance en Json
    private final String type = "Dessin";

    /**
     * Constructeur de la commande prennant en paramètre un dessin
     * @param dessin figure qui va être dessiné
     */
    public DessinerDessin(Dessin dessin){
        this.dessin= dessin;
    }

    /**
     * Méthode permettant le dessin du dessin sur un contexte graphique
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    @Override
    public void execute(GraphicsContext gc) {
        int i = 0 ;
        //On récupére la couleur du trait et sa largueur
        gc.setStroke(dessin.getCouleur());
        gc.setLineWidth(dessin.getLargueurTrait());
        gc.beginPath();
        //On parcours le tableau des x et y et on trace un point sur chaque coordonnées
        for (float  x: dessin.pointsX) {
            float y=dessin.pointsY.get(i);
            gc.lineTo(x,y);
            gc.stroke();
            i=i+1;
        }
        gc.closePath();
    }
}
