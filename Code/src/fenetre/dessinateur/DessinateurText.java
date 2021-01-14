package fenetre.dessinateur;

import fenetre.commande.DessinerText;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import metier.formes.Forme;
import metier.formes.Text;

public class DessinateurText extends Dessinateur{
    private final Text text = new Text();

    public void dessiner(GraphicsContext gc) {
        commande= new DessinerText(text);
        commande.execute(gc);
    }

    public void definirFormeOnMousePressed(Slider slider, MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage,TextArea textArea) {
        gc.setLineWidth(1);
        gc.setFont(Font.font(slider.getValue()));
        gc.setStroke(couleur);
        gc.setFill(couleurRemplissage);
        if(text.getCouleurRemplissage() != Color.TRANSPARENT){
            text.setRempli(true);
        }
        text.setX((float)event.getX());
        text.setY((float)event.getY());
        text.setContenu(textArea.getText());
    }


    public Forme getForme() {
        return text;
    }
}
