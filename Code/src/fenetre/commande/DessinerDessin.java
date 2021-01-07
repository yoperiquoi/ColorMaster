package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Dessin;

public class DessinerDessin implements ICommande{
    Dessin dessin;

    public DessinerDessin(Dessin dessin){
        this.dessin= dessin;
    }

    @Override
    public void execute(GraphicsContext gc) {
        int i = 0 ;
        gc.setStroke(dessin.getCouleur());
        gc.setLineWidth(dessin.getLargueurTrait());
        gc.beginPath();
        for (float  x: dessin.pointsX) {
            float y=dessin.pointsY.get(i);
            gc.lineTo(x,y);
            gc.stroke();
            i=i+1;
        }
        gc.closePath();
    }
}
