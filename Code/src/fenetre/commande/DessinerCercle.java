package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Cercle;

import java.io.Serializable;

public class DessinerCercle implements ICommande {
    Cercle cercle;
    private final String type = "Cercle";

    public DessinerCercle(Cercle cercle) {
        this.cercle=cercle;
    }

    @Override
    public void execute(GraphicsContext gc) {
        if(cercle.getRempli()){
            gc.setFill(cercle.getCouleurRemplissage());
            gc.fillOval(cercle.getX(),cercle.getY(),cercle.getRayon(),cercle.getRayon());
        }
        gc.strokeOval(cercle.getX(),cercle.getY(),cercle.getRayon(),cercle.getRayon());
    }
}
