package fenetre.commande;

import javafx.scene.canvas.GraphicsContext;
import metier.formes.Carre;

import java.io.Serializable;

public class DessinerCarre implements ICommande {
    Carre carre;
    private String type = "Carre";

    public DessinerCarre(Carre carre){
        this.carre=carre;
    }


    @Override
    public void execute(GraphicsContext gc) {
        if (carre.getRempli()){
            gc.setFill(carre.getCouleurRemplissage());
            gc.fillRect(carre.getX(),carre.getY(),carre.getCote(),carre.getCote());
        }
        gc.strokeRect(carre.getX(),carre.getY(),carre.getCote(),carre.getCote());
    }
}