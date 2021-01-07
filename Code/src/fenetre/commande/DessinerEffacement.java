package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Effacement;

public class DessinerEffacement implements ICommande {
    Effacement effacement;

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
