package fenetre.dessinateur;

import fenetre.commande.DessinerCercle;
import fenetre.commande.ICommande;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Cercle;

public class DessinateurCercle extends Dessinateur {
    private Cercle cercle= new Cercle();


    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage){
        gc.setStroke(couleur);
        cercle.setCouleurRemplissage(couleurRemplissage);
        cercle.setCouleur(couleur);
        cercle.setX((float)event.getX());
        cercle.setLargeurTrait((float)gc.getLineWidth());
        if(couleurRemplissage != Color.TRANSPARENT){
            cercle.setRempli(true);
        }
    }

    @Override
    public void definirFormeOnMouseReleased(MouseEvent event){
        cercle.setRayon((float)(Math.abs(event.getX()-cercle.getX())+Math.abs(event.getY()-cercle.getY()))/2);
        if(cercle.getX()> event.getX()){
            cercle.setX((float)event.getX());
        }
        if(cercle.getY() > event.getY()){
            cercle.setY((float)event.getY());
        }
    }

    @Override
    public void dessiner(GraphicsContext gc){
        commande= new DessinerCercle(cercle);
        if(cercle.getRempli()){
            gc.setFill(cercle.getCouleurRemplissage());
            gc.fillOval(cercle.getX(),cercle.getY(),cercle.getRayon(),cercle.getRayon());
        }
        gc.strokeOval(cercle.getX(),cercle.getY(),cercle.getRayon(),cercle.getRayon());
    }

    @Override
    public Cercle getForme(){
        return cercle;
    }
}
