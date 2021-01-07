package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Text;

public class DessinerText implements ICommande{
    Text text;

    public DessinerText(Text text) {
        this.text=text;
    }

    @Override
    public void execute(GraphicsContext gc) {
        gc.fillText(text.getContenu(), text.getX(), text.getY());
        gc.strokeText(text.getContenu(), text.getX(), text.getY());
    }
}
