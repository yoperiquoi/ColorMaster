package fenetre.dessinateur;

import fenetre.commande.DessinerLigne;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Forme;
import metier.formes.Ligne;

/**
 * Définition d'un dessinateur permettant de définir la ligne à dessiner
 */
public class DessinateurLigne extends Dessinateur{
    /**
     * Ligne qui va être défini
     */
    private Ligne ligne = new Ligne();

    /**
     * Méthode utilisant la commande pour dessiner la ligne
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    @Override
    public void dessiner(GraphicsContext gc) {
        //Pour dessiner nous utilisons la commande initialisé avec la ligne déjà défini
        commande=new DessinerLigne(ligne);
        commande.execute(gc);
    }

    /**
     * Méthode permettant la définition de la ligne lorsque le bouton de la souris est pressé sur le canvas
     * @param event événement déclenché par le clic de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param couleur couleur sélectionnée dans le sélecteur de couleur du trait de la figure
     * @param couleurRemplissage couleur sélectionnée dans le sélecteur de de couleur du remplissage de la figure
     */
    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage) {
        //Pour ne pas se retrouver toujours avec la même référence du carré on fait attention à bien instancié une nouvelle
        //fois le carré avec rien de défini à l'intérieur
        ligne= new Ligne();
        //On défini ensuite la couleur du trait, du remplissage, sa largeur, et enfin on récupére les coordonnées
        //du point de départ de la figure
        gc.setStroke(couleur);
        ligne.setLargeurTrait((float)gc.getLineWidth());
        ligne.setCouleur(couleur);
        ligne.setX((float) event.getX());
        ligne.setY((float) event.getY());
    }

    /**
     *  Méthode permettant la définition de la ligne lorsque le bouton de la souris est relâché
     * @param event événement déclenché par le relâchement du clic de la souris
     */
    @Override
    public void definirFormeOnMouseReleased(MouseEvent event) {
        ligne.setX2((float)event.getX());
        ligne.setY2((float)event.getY());
    }

    /**
     * Méthode permettant de récupérer la ligne défini
     * @return forme défini dans le dessinateur
     */
    public Forme getForme() {
        return ligne;
    }
}
