package fenetre.dessinateur;

import fenetre.commande.DessinerEllipse;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Ellipse;
import metier.formes.Forme;

public class DessinateurEllipse extends Dessinateur{
    Ellipse ellipse= new Ellipse();
    @Override
    public void dessiner(GraphicsContext gc) {
        commande= new DessinerEllipse(ellipse);
        if(ellipse.getRempli()){
            gc.setFill(ellipse.getCouleurRemplissage());
            gc.fillOval(ellipse.getX(),ellipse.getY(),ellipse.getDiametre1(),ellipse.getDiametre2());
        }
        gc.strokeOval(ellipse.getX(),ellipse.getY(),ellipse.getDiametre1(),ellipse.getDiametre2());
    }

    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage) {
        gc.setStroke(couleur);
        ellipse.setLargeurTrait((float)gc.getLineWidth());
        ellipse.setCouleur(couleur);
        ellipse.setCouleurRemplissage(couleurRemplissage);
        if (ellipse.getCouleurRemplissage() != Color.TRANSPARENT) {
            ellipse.setRempli(true);
        }
        ellipse.setX((float) event.getX());
        ellipse.setY((float) event.getY());
    }

    @Override
    public void definirFormeOnMouseReleased(MouseEvent event) {
        ellipse.setDiametre1((float)Math.abs(event.getX()-ellipse.getX()));
        ellipse.setDiametre2((float)Math.abs(event.getY()-ellipse.getY()));

        if(ellipse.getX()>event.getX()){
            ellipse.setX((float)event.getX());
        }
        if(ellipse.getY()> event.getY()){
            ellipse.setY((float)event.getY());
        }
    }

    @Override
    public Forme getForme() {
        return ellipse;
    }
}
