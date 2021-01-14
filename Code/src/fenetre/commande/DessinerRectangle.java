package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Rectangle;

import java.io.Serializable;

public class DessinerRectangle extends Dessiner implements ICommande {
    Rectangle rectangle;
    private final String type = "Carre";

    public DessinerRectangle(Rectangle rectangle) {
        this.rectangle=rectangle;
    }

    @Override
    public void execute(GraphicsContext gc) {
        if(rectangle.getRempli()){
            gc.setFill(rectangle.getCouleurRemplissage());
            gc.fillRect(rectangle.getX(),rectangle.getY(),rectangle.getLargeur(),rectangle.getLongueur());
        }
        gc.strokeRect(rectangle.getX(),rectangle.getY(),rectangle.getLargeur(),rectangle.getLongueur());
    }
}
