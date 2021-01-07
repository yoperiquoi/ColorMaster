package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Cercle;

public class DessinerCercle implements ICommande{
    Cercle cercle;

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
