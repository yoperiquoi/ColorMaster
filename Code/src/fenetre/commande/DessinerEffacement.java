package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Effacement;

import java.io.Serializable;

public class DessinerEffacement extends Dessiner implements ICommande {
    Effacement effacement;
    private final String type = "Effacement";

    public DessinerEffacement(Effacement effacement) {
        this.effacement=effacement;
    }

    @Override
    public void execute(GraphicsContext gc) {
        int i = 0 ;
        gc.setStroke(effacement.getCouleur());
        gc.setLineWidth(effacement.getLargueurTrait());
        gc.beginPath();
        for (float  x: effacement.pointsX) {
            float y=effacement.pointsY.get(i);
            gc.clearRect(x - effacement.getLargueurTrait() / 2, y - effacement.getLargueurTrait() / 2, effacement.getLargueurTrait(), effacement.getLargueurTrait());
            i=i+1;
        }
        gc.closePath();
    }
}
