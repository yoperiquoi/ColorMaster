package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;

/**
 * Interface definissant la méthode nécessaire à la mise en place d'une commande et ainsi de
 * l'utilisation du polymorphisme pour utiliser les différentes méthode de dessin
 */
public interface ICommande {

    /**
     * Méthode que devront implémenter les commandes qui permettront de dessiner les forme
     * @param gc contexte graphique du canvas sur lequel on dessine dans l'application
     */
    void execute(GraphicsContext gc);
}
