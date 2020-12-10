package fenetre;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import metier.formes.Forme;
import metier.formes.Text;

public class DessinateurText {
    private Text text = new Text();

    public void dessiner(GraphicsContext gc,TextArea textArea) {
        gc.fillText(textArea.getText(), text.getX(), text.getY());
        gc.strokeText(textArea.getText(), text.getX(), text.getY());
    }

    public void definirFormeOnMousePressed(Slider slider, MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage) {
        gc.setLineWidth(1);
        gc.setFont(Font.font(slider.getValue()));
        gc.setStroke(couleur);
        gc.setFill(couleurRemplissage);
        if(text.getCouleurRemplissage() != Color.TRANSPARENT){
            text.setRempli(true);
        }
        text.setX((float)event.getX());
        text.setY((float)event.getY());
    }


    public Forme getForme() {
        return text;
    }
}
