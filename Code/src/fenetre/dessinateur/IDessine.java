package fenetre.dessinateur;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Forme;

/**
 * Interface définissant les méthode nécessaire pour la définition d'une forme
 */
interface IDessine {
    /**
     * Méthode utilisant la commande afin de dessiner la figure sur le canvas
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    void dessiner(GraphicsContext gc);

    /**
     * Méthode permettant la définition d'une forme lorsque le bouton de la souris est pressé sur le canvas
     * @param event événement déclenché par le clic de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param couleur couleur sélectionnée dans le sélecteur de couleur du trait de la figure
     * @param couleurRemplissage couleur sélectionnée dans le sélecteur de de couleur du remplissage de la figure
     */
    void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage);

    /**
     *  Méthode permettant la définition de la forme lorsque le bouton de la souris est relâché
     * @param event événement déclenché par le relâchement du clic de la souris
     */
    void definirFormeOnMouseReleased(MouseEvent event);

    /**
     * Méthode permettant de récupérer le carre défini
     * @return forme défini dans le dessinateur
     */
    Forme getForme();
}
