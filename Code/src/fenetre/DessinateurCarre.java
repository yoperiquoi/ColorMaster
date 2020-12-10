package fenetre;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Carre;
import metier.formes.Forme;

public class DessinateurCarre extends Dessinateur{
    private Carre carre = new Carre();

    @Override
    public void dessiner(GraphicsContext gc) {
        if (carre.getRempli()){
            gc.setFill(carre.getCouleurRemplissage());
            gc.fillRect(carre.getX(),carre.getY(),carre.getCote(),carre.getCote());
        }
        gc.strokeRect(carre.getX(),carre.getY(),carre.getCote(),carre.getCote());
    }

    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage) {
        gc.setStroke(couleur);
        carre.setLargeurTrait((float)gc.getLineWidth());
        carre.setCouleur(couleur);
        carre.setCouleurRemplissage(couleurRemplissage);
        if (carre.getCouleurRemplissage() != Color.TRANSPARENT) {
            carre.setRempli(true);
        }
        carre.setX((float) event.getX());
        carre.setY((float) event.getY());
    }

    @Override
    public void definirFormeOnMouseReleased(MouseEvent event) {
        carre.setCote((float)Math.abs(event.getX()-carre.getX()));
        if (event.getX()<carre.getX()){
            carre.setX((float)event.getX());
        }
        if(event.getY()<carre.getY()){
            carre.setY((float)event.getY());
        }
    }

    @Override
    public Forme getForme() {
        return carre;
    }
}
