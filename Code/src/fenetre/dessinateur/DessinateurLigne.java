package fenetre.dessinateur;

import fenetre.commande.DessinerLigne;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Forme;
import metier.formes.Ligne;

public class DessinateurLigne extends Dessinateur{
    private final Ligne ligne = new Ligne();
    @Override
    public void dessiner(GraphicsContext gc) {
        commande=new DessinerLigne(ligne);
        gc.strokeLine(ligne.getX(),ligne.getY(),ligne.getX2(),ligne.getY2());
    }

    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage) {
        gc.setStroke(couleur);
        ligne.setLargeurTrait((float)gc.getLineWidth());
        ligne.setCouleur(couleur);
        ligne.setX((float) event.getX());
        ligne.setY((float) event.getY());
    }

    @Override
    public void definirFormeOnMouseReleased(MouseEvent event) {
        ligne.setX2((float)event.getX());
        ligne.setY2((float)event.getY());
    }

    @Override
    public Forme getForme() {
        return ligne;
    }
}
