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

/**
 * Définition d'un dessinateur permettant de définir le texte à dessiner
 * Classe différente des autre car ici on défini un texte
 */
public class DessinateurText extends Dessinateur{
    /**
     * Texte qui va être defini
     */
    private Text text = new Text();

    /**
     * Méthode utilisant la commande pour dessiner le texte
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    public void dessiner(GraphicsContext gc) {
        commande= new DessinerText(text);
        commande.execute(gc);
    }

    /**
     * Méthode permettant la définition du texte lorsque le bouton de la souris est pressé sur le canvas
     * @param slider slider permettant de récupérer la grosseur de la police
     * @param event événement déclenché par le clic de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param couleur couleur sélectionnée dans le sélecteur de couleur du trait de la figure
     * @param couleurRemplissage couleur sélectionnée dans le sélecteur de de couleur du remplissage de la figure
     * @param textArea textArea permettant de récupérer le text qui devra être dessiné
     */
    public void definirFormeOnMousePressed(Slider slider, MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage,TextArea textArea) {
        text=new Text();
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


    /**
     * Méthode permettant de récupérer le texte défini
     * @return forme défini dans le dessinateur
     */
    public Forme getForme() {
        return text;
    }
}
