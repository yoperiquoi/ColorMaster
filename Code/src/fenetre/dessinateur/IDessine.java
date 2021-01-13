package fenetre.dessinateur;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Forme;

interface IDessine {
    void dessiner(GraphicsContext gc);
    void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage);
    void definirFormeOnMouseReleased(MouseEvent event);
    Forme getForme();
}
