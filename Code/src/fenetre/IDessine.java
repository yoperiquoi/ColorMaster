package fenetre;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Forme;

interface IDessine {
    public abstract void dessiner(GraphicsContext gc);
    public abstract void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage);
    public abstract void definirFormeOnMouseReleased(MouseEvent event);
    public abstract Forme getForme();
}
