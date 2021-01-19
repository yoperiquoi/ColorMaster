package fenetre.dessinateur;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * Interface définissant les forme qui sont défini pendant le déplacement de la souris
 */
public interface IDessinePendant {
    /**
     * Méthode permettant de définir la forme pendant le mouvement de la souris
     * @param e event du mouvement de la souris
     * @param gc contexte graphique du canvas sur lequel on dessine
     */
    void definirPendantFigure(MouseEvent e, GraphicsContext gc);
}
