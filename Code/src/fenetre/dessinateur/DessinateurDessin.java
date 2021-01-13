package fenetre.dessinateur;

import fenetre.commande.DessinerDessin;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Dessin;

public class DessinateurDessin extends Dessinateur{
    Dessin dessin = new Dessin();

    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage){
        gc.setStroke(couleur);
        dessin.setCouleur(couleur);
        dessin.setX((float)event.getX());
        dessin.setY((float)event.getY());
        dessin.setLargueurTrait((float)gc.getLineWidth());
        gc.beginPath();
    }

    public void definirPendantFigure(MouseEvent e,GraphicsContext gc){
        gc.lineTo(e.getX(),e.getY());
        dessin.pointsY.add((float)e.getY());
        dessin.pointsX.add((float)e.getX());
        gc.stroke();
    }


    public void definirFormeOnMouseReleased(MouseEvent e,GraphicsContext gc){
        commande= new DessinerDessin(dessin);
        gc.lineTo(e.getX(),e.getY());
        dessin.pointsY.add((float)e.getY());
        dessin.pointsX.add((float)e.getX());
        gc.stroke();
        gc.closePath();
        DessinerDessin dessinerDessin = new DessinerDessin(dessin);
    }

    @Override
    public Dessin getForme(){
        return dessin;
    }
}
