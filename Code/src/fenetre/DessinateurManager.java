package fenetre;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import metier.formes.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.RenderedImage;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
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

    public void charger(GraphicsContext gc){

    }

    public void sauvegarder(GraphicsContext gc, Canvas canvas, Event event){
        FileChooser savefile = new FileChooser();
        savefile.setTitle("Save File");

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        File file = savefile.showSaveDialog(stage);
        if (file != null) {
            try {
                //Sauvegarde de l'image
                WritableImage writableImage = new WritableImage(1080, 790);
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);

                //Sauvegarde de l'historique redo et undo
                GsonBuilder builder= new GsonBuilder();
                Gson gson = builder.setPrettyPrinting().create();
                System.out.println(undoHistorique);
                String undo = gson.toJson(undoHistorique);
                String redo = gson.toJson(redoHistorique);
                String path1 = file.getAbsolutePath() + "Undo.json";
                String path2 = file.getAbsolutePath() + "Redo.json";
                try(FileWriter writer = new FileWriter(path1);
                    BufferedWriter bw = new BufferedWriter(writer)){
                        bw.write(undo);
                }catch (IOException ex){
                    System.out.println("Error!");
                }
                try(FileWriter writer = new FileWriter(path2);
                    BufferedWriter bw = new BufferedWriter(writer)){
                    bw.write(redo);
                }catch (IOException ex){
                    System.out.println("Error!");
                }
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }
    }

    public void charger(GraphicsContext gc, Event event){
        FileChooser openFile = new FileChooser();
        openFile.setTitle("Open File");
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        File file = openFile.showOpenDialog(stage);
        if (file != null) {
            try {
                //Récupére l'image
                InputStream io = new FileInputStream(file);
                Image img = new Image(io);
                gc.drawImage(img, 0, 0);

                //Récupére les historiques
                FormeDeserializer deserializer= new FormeDeserializer("type");
                deserializer.registerShapeType("Carre",Carre.class);
                deserializer.registerShapeType("Cercle",Cercle.class);
                deserializer.registerShapeType("Dessin",Dessin.class);
                deserializer.registerShapeType("Effacement",Effacement.class);
                deserializer.registerShapeType("Ellipse",Ellipse.class);
                deserializer.registerShapeType("Ligne",Ligne.class);
                deserializer.registerShapeType("Rectangle",Rectangle.class);
                deserializer.registerShapeType("Text",Text.class);

                Gson gson = new GsonBuilder().registerTypeAdapter(Forme.class,deserializer).create();

                String path = file.getAbsolutePath() + "Undo.json";

                List<Forme> undo;
                undo = gson.fromJson(new FileReader(path),new TypeToken<Stack<Forme>>(){}.getType());
                undoHistorique.clear();
                undoHistorique.addAll(undo);
                System.out.println(undoHistorique);

                path = file.getAbsolutePath() + "Redo.json";

                List<Forme> redo;
                redo = gson.fromJson(new FileReader(path),new TypeToken<Stack<Forme>>(){}.getType());
                redoHistorique.clear();
                redoHistorique.addAll(redo);
                System.out.println(redoHistorique);

            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }
    }

}
