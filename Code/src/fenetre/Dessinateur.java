package fenetre;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Forme;

public class Dessinateur implements IDessine{
    private Forme forme;
    @Override
    public void dessiner(GraphicsContext gc) {
    }

    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage) {
    }

    @Override
    public void definirFormeOnMouseReleased(MouseEvent event) {
    }

    @Override
    public Forme getForme() {
        return forme;
    }

    public void setForme(Forme forme){ this.forme=forme; }
}
