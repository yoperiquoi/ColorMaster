package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Text;

import java.io.Serializable;

public class DessinerText extends Dessiner implements ICommande {
    Text text;
    private final String type = "Text";

    public DessinerText(Text text) {
        this.text=text;
    }

    @Override
    public void execute(GraphicsContext gc) {
        gc.fillText(text.getContenu(), text.getX(), text.getY());
        gc.strokeText(text.getContenu(), text.getX(), text.getY());
    }
}
