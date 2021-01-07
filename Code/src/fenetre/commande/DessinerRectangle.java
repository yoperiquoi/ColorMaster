package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Rectangle;

public class DessinerRectangle implements ICommande{
    Rectangle rectangle;

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
