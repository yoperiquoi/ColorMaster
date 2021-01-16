package fenetre.dessinateur;

import fenetre.commande.DessinerRectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Forme;
import metier.formes.Rectangle;

/**
 * Définition d'un dessinateur permettant de définir le rectangle à dessiner
 */
public class DessinateurRectangle extends Dessinateur{
    /**
     * Rectangle qui va être défini
     */
    private Rectangle rectangle= new Rectangle();

    /**
     * Méthode utilisant la commande pour dessiner le rectangle
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    @Override
    public void dessiner(GraphicsContext gc) {
        commande=new DessinerRectangle(rectangle);
        commande.execute(gc);
    }

    /**
     * Méthode permettant la définition du rectangle lorsque le bouton de la souris est pressé sur le canvas
     * @param event événement déclenché par le clic de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param couleur couleur sélectionnée dans le sélecteur de couleur du trait de la figure
     * @param couleurRemplissage couleur sélectionnée dans le sélecteur de de couleur du remplissage de la figure
     */
    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage) {
        rectangle= new Rectangle();
        gc.setStroke(couleur);
        rectangle.setLargeurTrait((float)gc.getLineWidth());
        rectangle.setCouleur(couleur);
        rectangle.setCouleurRemplissage(couleurRemplissage);
        if (rectangle.getCouleurRemplissage() != Color.TRANSPARENT) {
            rectangle.setRempli(true);
        }
        rectangle.setX((float) event.getX());
        rectangle.setY((float) event.getY());
    }

    /**
     *  Méthode permettant la définition du rectangle lorsque le bouton de la souris est relâché
     * @param event événement déclenché par le relâchement du clic de la souris
     */
    @Override
    public void definirFormeOnMouseReleased(MouseEvent event) {
        rectangle.setLargeur(Math.abs((float)event.getX()-rectangle.getX()));
        rectangle.setLongueur(Math.abs((float)event.getY()-rectangle.getY()));
        if (event.getX()<rectangle.getX()){
            rectangle.setX((float)event.getX());
        }
        if(event.getY()<rectangle.getY()){
            rectangle.setY((float)event.getY());
        }
    }

    /**
     * Méthode permettant de récupérer le rectangle défini
     * @return forme défini dans le dessinateur
     */
    @Override
    public Forme getForme() {
        return rectangle;
    }
}
