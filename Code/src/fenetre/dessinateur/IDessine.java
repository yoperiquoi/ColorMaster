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
}
