package fenetre.dessinateur;

import fenetre.commande.DessinerEffacement;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Effacement;

public class DessinateurEffacement extends Dessinateur{
    Effacement effacement = new Effacement();

    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage){
        double largeur = gc.getLineWidth();
        effacement.setX((float)event.getX());
        effacement.setY((float)event.getY());
        effacement.setLargueurTrait((float)gc.getLineWidth());
        gc.clearRect(event.getX() - largeur / 2, event.getY() - largeur / 2, largeur, largeur);
    }

    public void definirPendantFigure(MouseEvent e,GraphicsContext gc){
        double largueurLigne = gc.getLineWidth();
        effacement.pointsY.add((float)e.getY());
        effacement.pointsX.add((float)e.getX());
        gc.setFill(Color.WHITE);
        gc.fillRect(e.getX() - largueurLigne / 2, e.getY() - largueurLigne / 2, largueurLigne, largueurLigne);
    }


    public void definirFormeOnMouseReleased(MouseEvent e,GraphicsContext gc){
        double largueurLigne = gc.getLineWidth();
        effacement.pointsY.add((float)e.getY());
        effacement.pointsX.add((float)e.getX());
        gc.setFill(Color.WHITE);
        gc.fillRect(e.getX() - largueurLigne / 2, e.getY() - largueurLigne / 2, largueurLigne, largueurLigne);
        commande= new DessinerEffacement(effacement);
    }

    @Override
    public Effacement getForme(){
        return effacement;
    }
}
