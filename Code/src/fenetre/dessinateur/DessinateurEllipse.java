package fenetre.dessinateur;

import fenetre.commande.DessinerEllipse;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Ellipse;
import metier.formes.Forme;

/**
 * Définition d'un dessinateur permettant de définir le carré à dessiner
 */
public class DessinateurEllipse extends Dessinateur{
    /**
     * Ellipse qui va être défini
     */
    Ellipse ellipse= new Ellipse();
    /**
     * Méthode utilisant la commande pour dessiner l'ellipse
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    @Override
    public void dessiner(GraphicsContext gc) {
        //Pour dessiner nous utilisons la commande initialisé avec l'ellipse déjà défini
        commande= new DessinerEllipse(ellipse);
        commande.execute(gc);
    }

    /**
     * Méthode permettant la définition de l'ellipse lorsque le bouton de la souris est pressé sur le canvas
     * @param event événement déclenché par le clic de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param couleur couleur sélectionnée dans le sélecteur de couleur du trait de la figure
     * @param couleurRemplissage couleur sélectionnée dans le sélecteur de de couleur du remplissage de la figure
     */
    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage) {
        //Pour ne pas se retrouver toujours avec la même référence du ellipse on fait attention à bien instancié une nouvelle
        //fois le ellipse avec rien de défini à l'intérieur
        ellipse= new Ellipse();
        //On défini ensuite la couleur du trait, du remplissage, sa largeur, et enfin on récupére les coordonnées
        //du point de départ de la figure
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

    /**
     *  Méthode permettant la définition de l'ellipse lorsque le bouton de la souris est relâché
     * @param event événement déclenché par le relâchement du clic de la souris
     */
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

    /**
     * Méthode permettant de récupérer l'ellipse défini
     * @return forme défini dans le dessinateur
     */
    public Forme getForme() {
        return ellipse;
    }
}
