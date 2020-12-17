package fenetre;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Forme;

import java.util.Stack;

public class DessinateurManager {
    Dessinateur dessinateur;
    Stack<Forme> undoHistorique = new Stack<>();
    Stack<Forme> redoHistorique = new Stack<>();
    Slider slider;
    private DoubleProperty eppaisseurTrait= new SimpleDoubleProperty();
    public final double getEppaisseurTrait(){return eppaisseurTrait.get();}
    public final void setEppaisseurTrait(double value){eppaisseurTrait.set(value);}
    public DoubleProperty eppaisseurTraitProperty(){return eppaisseurTrait;}

    public void definirDebutFigure(MouseEvent e, GraphicsContext gc, Color couleur, Color couleurRemplissage, ToggleGroup outils , ToggleButton dessinBtn,ToggleButton effacerBtn){
        dessinateur =(Dessinateur) outils.getSelectedToggle().getUserData();
        if (dessinBtn.isSelected()) {
            gc.setStroke(couleur);
            gc.beginPath();
        } else if (effacerBtn.isSelected()) {
            double largeur = gc.getLineWidth();
            gc.clearRect(e.getX() - largeur / 2, e.getY() - largeur / 2, largeur, largeur);
        }else{
            dessinateur.definirFormeOnMousePressed(e,gc,couleur,couleurRemplissage);
        }
    }

    public void definirPendantFigure (ToggleButton dessinBtn,ToggleButton effacerBtn,MouseEvent e,GraphicsContext gc){
        if(dessinBtn.isSelected()){
            gc.lineTo(e.getX(),e.getY());
            gc.stroke();
        }else if(effacerBtn.isSelected()) {
            double largueurLigne = gc.getLineWidth();
            gc.clearRect(e.getX() - largueurLigne / 2, e.getY() - largueurLigne / 2, largueurLigne, largueurLigne);
        }
    }

    public void definirFinFigure(MouseEvent e, GraphicsContext gc, Color couleur, Color couleurRemplissage, ToggleGroup outils , ToggleButton dessinBtn,ToggleButton effacerBtn){
        if(dessinBtn.isSelected()){
            gc.lineTo(e.getX(),e.getY());
            gc.stroke();
            gc.closePath();
        }else if(effacerBtn.isSelected()){
            double largueurLigne = gc.getLineWidth();
            gc.clearRect(e.getX() - largueurLigne / 2, e.getY() - largueurLigne / 2, largueurLigne, largueurLigne);
        }else {
            dessinateur.definirFormeOnMouseReleased(e);
            dessinateur.dessiner(gc);
            undoHistorique.push(dessinateur.getForme());
        }
    }

    /*
    public DessinateurManager(){

        slider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                eppaisseurTrait.setValue(slider.getValue());
            }
        });
    }
    */
}
