package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Ligne;

import java.io.Serializable;

public class DessinerLigne implements ICommande {
    Ligne ligne;
    private String type = "Ligne";

    public DessinerLigne(Ligne ligne) {
        this.ligne=ligne;
    }

    @Override
    public void execute(GraphicsContext gc) {
        gc.strokeLine(ligne.getX(),ligne.getY(),ligne.getX2(),ligne.getY2());
    }
}
