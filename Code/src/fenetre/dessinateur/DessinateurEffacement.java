package fenetre.dessinateur;

import fenetre.commande.DessinerEffacement;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Effacement;

/**
 * Définition d'un dessinateur permettant de définir l'effacement à dessiner
 */
public class DessinateurEffacement extends Dessinateur{

    //Cette classe fonctionne comme pour le dessin juste à la place de tracer,
    //on remplace par du blanc

    /**
     * Effacement qui va être défini
     */
    Effacement effacement = new Effacement();

    /**
     * Méthode permettant la définition de l'effacement lorsque le bouton de la souris est pressé sur le canvas
     * @param event événement déclenché par le clic de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param couleur couleur sélectionnée dans le sélecteur de couleur du trait de la figure
     * @param couleurRemplissage couleur sélectionnée dans le sélecteur de de couleur du remplissage de la figure
     */
    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage){
        effacement= new Effacement();
        double largeur = gc.getLineWidth();
        effacement.setX((float)event.getX());
        effacement.setY((float)event.getY());
        effacement.setLargueurTrait((float)gc.getLineWidth());
        gc.clearRect(event.getX() - largeur / 2, event.getY() - largeur / 2, largeur, largeur);
    }

    /**
     * Méthode permettant la définition de la figure pendant que l'utilisateur déplace la souris sur le canvas
     * @param e événement déclenché par le déplacement de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    public void definirPendantFigure(MouseEvent e,GraphicsContext gc){
        double largueurLigne = gc.getLineWidth();
        effacement.pointsY.add((float)e.getY());
        effacement.pointsX.add((float)e.getX());
        gc.setFill(Color.WHITE);
        gc.fillRect(e.getX() - largueurLigne / 2, e.getY() - largueurLigne / 2, largueurLigne, largueurLigne);
    }


    /**
     *  Méthode permettant la définition de l'effacement lorsque le bouton de la souris est relâché
     * @param e événement déclenché par le relâchement du clic de la souris
     */
    public void definirFormeOnMouseReleased(MouseEvent e){
        effacement.pointsY.add((float)e.getY());
        effacement.pointsX.add((float)e.getX());
    }

    public void dessiner(GraphicsContext gc){
        double largueurLigne = gc.getLineWidth();
        gc.setFill(Color.WHITE);
        gc.fillRect(effacement.pointsX.get(effacement.pointsX.size()-1)- largueurLigne / 2, effacement.pointsY.get(effacement.pointsY.size()-1) - largueurLigne / 2, largueurLigne, largueurLigne);
        commande= new DessinerEffacement(effacement);
    }

    /**
     * Méthode permettant de récupérer le dessin défini
     * @return forme défini dans le dessinateur
     */
    public Effacement getForme(){
        return effacement;
    }
}
