package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Ellipse;

import java.io.Serializable;

public class DessinerEllipse implements ICommande {
    Ellipse ellipse;
    private final String type = "Ellipse";

    public DessinerEllipse(Ellipse ellipse) {
        this.ellipse=ellipse;
    }

    @Override
    public void execute(GraphicsContext gc) {
        if(ellipse.getRempli()){
            gc.setFill(ellipse.getCouleurRemplissage());
            gc.fillOval(ellipse.getX(),ellipse.getY(),ellipse.getDiametre1(),ellipse.getDiametre2());
        }
        gc.strokeOval(ellipse.getX(),ellipse.getY(),ellipse.getDiametre1(),ellipse.getDiametre2());
    }
}
