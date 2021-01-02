package fenetre;

import com.sun.javafx.geom.Path2D;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.*;

import java.util.Stack;

public class DessinateurManager {
    Dessinateur dessinateur;
    Stack<Forme> undoHistorique = new Stack<>();
    Stack<Forme> redoHistorique = new Stack<>();
    Slider slider;
    Dessin dessin = new Dessin();
    Effacement effacement = new Effacement();
    private DoubleProperty eppaisseurTrait= new SimpleDoubleProperty();
    public final double getEppaisseurTrait(){return eppaisseurTrait.get();}
    public final void setEppaisseurTrait(double value){eppaisseurTrait.set(value);}
    public DoubleProperty eppaisseurTraitProperty(){return eppaisseurTrait;}

    public void definirDebutFigure(MouseEvent e, GraphicsContext gc, Color couleur, Color couleurRemplissage, ToggleGroup outils , ToggleButton dessinBtn,ToggleButton effacerBtn){
        dessinateur =(Dessinateur) outils.getSelectedToggle().getUserData();
        if (dessinBtn.isSelected()) {
            gc.setStroke(couleur);
            dessin.setX((float)e.getX());
            dessin.setY((float)e.getY());
            dessin.setLargueurTrait((float)gc.getLineWidth());
            gc.beginPath();
        } else if (effacerBtn.isSelected()) {
            double largeur = gc.getLineWidth();
            effacement.setX((float)e.getX());
            effacement.setY((float)e.getY());
            effacement.setLargueurTrait((float)gc.getLineWidth());
            gc.clearRect(e.getX() - largeur / 2, e.getY() - largeur / 2, largeur, largeur);
        }else{
            dessinateur.definirFormeOnMousePressed(e,gc,couleur,couleurRemplissage);
        }
    }

    public void definirPendantFigure (ToggleButton dessinBtn,ToggleButton effacerBtn,MouseEvent e,GraphicsContext gc){
        if(dessinBtn.isSelected()){
            gc.lineTo(e.getX(),e.getY());
            dessin.pointsY.add((float)e.getY());
            dessin.pointsX.add((float)e.getX());
            gc.stroke();
        }else if(effacerBtn.isSelected()) {
            double largueurLigne = gc.getLineWidth();
            effacement.pointsY.add((float)e.getY());
            effacement.pointsX.add((float)e.getX());
            gc.clearRect(e.getX() - largueurLigne / 2, e.getY() - largueurLigne / 2, largueurLigne, largueurLigne);
        }
    }

    public void definirFinFigure(MouseEvent e, GraphicsContext gc, Color couleur, Color couleurRemplissage, ToggleGroup outils , ToggleButton dessinBtn,ToggleButton effacerBtn){
        if(dessinBtn.isSelected()){
            gc.lineTo(e.getX(),e.getY());
            dessin.pointsY.add((float)e.getY());
            dessin.pointsX.add((float)e.getX());
            gc.stroke();
            gc.closePath();
            undoHistorique.push(dessin);
        }else if(effacerBtn.isSelected()){
            double largueurLigne = gc.getLineWidth();
            effacement.pointsY.add((float)e.getY());
            effacement.pointsX.add((float)e.getX());
            gc.clearRect(e.getX() - largueurLigne / 2, e.getY() - largueurLigne / 2, largueurLigne, largueurLigne);
            undoHistorique.push(effacement);
        }else {
            dessinateur.definirFormeOnMouseReleased(e);
            dessinateur.dessiner(gc);
            undoHistorique.push(dessinateur.getForme());
        }
        System.out.println(undoHistorique);
    }

    public void undo(GraphicsContext gc){
        if(!undoHistorique.empty()){
            gc.clearRect(0,0,1080,720);
            Forme formeSupprimee= undoHistorique.pop();
            if(formeSupprimee.getClass() == Ligne.class){
                Ligne ligneTemp = (Ligne)formeSupprimee;
                redoHistorique.push(ligneTemp);
            }
            if(formeSupprimee.getClass() == Cercle.class){
                Cercle cercleTemp = (Cercle) formeSupprimee;
                redoHistorique.push(cercleTemp);
            }
            if(formeSupprimee.getClass() == Carre.class){
                Carre carreTemp = (Carre)formeSupprimee;
                redoHistorique.push(carreTemp);
            }
            if(formeSupprimee.getClass() == Rectangle.class){
                Rectangle rectangleTemp = (Rectangle)formeSupprimee;
                redoHistorique.push(rectangleTemp);
            }
            if(formeSupprimee.getClass() == Text.class){
                Text textTemp = (Text)formeSupprimee;
                redoHistorique.push(textTemp);
            }
            if(formeSupprimee.getClass() == Ellipse.class){
                Ellipse ellipseTemp = (Ellipse)formeSupprimee;
                redoHistorique.push(ellipseTemp);
            }

            for(int i=0; i<undoHistorique.size();i++){
                Forme forme = undoHistorique.elementAt(i);
                System.out.println(undoHistorique.size());
                if(forme.getClass() == Ligne.class){
                    Ligne ligneTemp = (Ligne)forme;
                    gc.setLineWidth(ligneTemp.getLargeurTrait());
                    gc.setStroke(ligneTemp.getCouleur());
                    gc.setFill(ligneTemp.getCouleurRemplissage());
                    gc.strokeLine(ligneTemp.getX(),ligneTemp.getY(),ligneTemp.getX2(), ligneTemp.getY2());
                }
                if(forme.getClass() == Cercle.class){
                    Cercle cercleTemp = (Cercle) forme;
                    gc.setLineWidth(cercleTemp.getLargeurTrait());
                    gc.setStroke(cercleTemp.getCouleur());
                    gc.setFill(cercleTemp.getCouleurRemplissage());
                    gc.strokeOval(cercleTemp.getX(), cercleTemp.getY(), cercleTemp.getRayon(),cercleTemp.getRayon());
                }
                if(forme.getClass() == Carre.class){
                    Carre carreTemp = (Carre)forme;
                    gc.setLineWidth(carreTemp.getLargeurTrait());
                    gc.setStroke(carreTemp.getCouleur());
                    gc.setFill(carreTemp.getCouleurRemplissage());
                    gc.strokeRect(carreTemp.getX(),carreTemp.getY(),carreTemp.getCote(),carreTemp.getCote());
                }
                if(forme.getClass() == Rectangle.class){
                    Rectangle rectangleTemp = (Rectangle)forme;
                    gc.setLineWidth(rectangleTemp.getLargeurTrait());
                    gc.setStroke(rectangleTemp.getCouleur());
                    gc.setFill(rectangleTemp.getCouleurRemplissage());
                    gc.strokeRect(rectangleTemp.getX(),rectangleTemp.getY(),rectangleTemp.getLargeur(),rectangleTemp.getLongueur());
                }
                if(forme.getClass() == Text.class){
                    Text textTemp = (Text)forme;

                }
                if(forme.getClass() == Ellipse.class){
                    Ellipse ellipseTemp = (Ellipse)forme;
                }
            }
        }else{
            System.err.println("Aucune retour possible !");
        }

    }

    public void redo(GraphicsContext gc) {
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
