package fenetre.dessinateur;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public interface IDessinePendant {
    void definirPendantFigure(MouseEvent e, GraphicsContext gc);
}
