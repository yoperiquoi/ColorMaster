package fenetre;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Forme;
import metier.formes.Rectangle;

public class DessinateurRectangle extends Dessinateur{
    private Rectangle rectangle= new Rectangle();

    @Override
    public void dessiner(GraphicsContext gc) {
        if(rectangle.getRempli()){
            gc.setFill(rectangle.getCouleurRemplissage());
            gc.fillRect(rectangle.getX(),rectangle.getY(),rectangle.getLargeur(),rectangle.getLongueur());
        }
        gc.strokeRect(rectangle.getX(),rectangle.getY(),rectangle.getLargeur(),rectangle.getLongueur());
    }

    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage) {
        gc.setStroke(couleur);
        rectangle.setLargeurTrait((float)gc.getLineWidth());
        rectangle.setCouleur(couleur);
        rectangle.setCouleurRemplissage(couleurRemplissage);
        if (rectangle.getCouleurRemplissage() != Color.TRANSPARENT) {
            rectangle.setRempli(true);
        }
        rectangle.setX((float) event.getX());
        rectangle.setY((float) event.getY());
    }

    @Override
    public void definirFormeOnMouseReleased(MouseEvent event) {
        rectangle.setLargeur(Math.abs((float)event.getX()-rectangle.getX()));
        rectangle.setLongueur(Math.abs((float)event.getY()-rectangle.getY()));
        if (event.getX()<rectangle.getX()){
            rectangle.setX((float)event.getX());
        }
        if(event.getY()<rectangle.getY()){
            rectangle.setY((float)event.getY());
        }
    }

    @Override
    public Forme getForme() {
        return rectangle;
    }
}
