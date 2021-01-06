package fenetre;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
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
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;

public class DessinateurManager {
    Dessinateur dessinateur;
    Stack<Forme> undoHistorique = new Stack<>();
    Stack<Forme> redoHistorique = new Stack<>();
    Slider slider;
    Dessin dessin = new Dessin();
    Effacement effacement = new Effacement();

    private StringProperty fileName = new SimpleStringProperty();
        public StringProperty fileNameProperty(){return fileName;}
        public String getFileName(){return fileName.get();}
        public void setFileName(String valeur){ fileName.set(valeur);}


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
            gc.setFill(Color.WHITE);
            gc.fillRect(0,0, 1080,720);
            Forme formeSupprimee= undoHistorique.lastElement();
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

            undoHistorique.pop();

            for(int i=0; i<undoHistorique.size();i++){
                Forme forme = undoHistorique.elementAt(i);
                System.out.println(undoHistorique.size());
                if(forme.getClass() == Ligne.class){
                    Ligne ligneTemp = (Ligne)forme;
                    DessinateurLigne dessinateurLigne = new DessinateurLigne();
                    dessinateurLigne.setForme(ligneTemp);
                    dessinateurLigne.dessiner(gc);
                }
                if(forme.getClass() == Cercle.class){
                    Cercle cercleTemp = (Cercle) forme;
                    DessinateurCercle dessinateurCercle = new DessinateurCercle();
                    dessinateurCercle.setForme(cercleTemp);
                    dessinateurCercle.dessiner(gc);
                }
                if(forme.getClass() == Carre.class){
                    Carre carreTemp = (Carre)forme;
                    DessinateurCarre dessinateurCarre = new DessinateurCarre();
                    dessinateurCarre.setForme(carreTemp);
                    dessinateurCarre.dessiner(gc);
                }
                if(forme.getClass() == Rectangle.class){
                    Rectangle rectangleTemp = (Rectangle)forme;
                    DessinateurRectangle dessinateurRectangle = new DessinateurRectangle();
                    dessinateurRectangle.setForme(rectangleTemp);
                    dessinateurRectangle.dessiner(gc);
                }
                if(forme.getClass() == Text.class){
                    Text textTemp = (Text)forme;
                    DessinateurText dessinateurText = new DessinateurText();
                    dessinateurText.setForme(textTemp);
                    dessinateurText.dessiner(gc);

                }
                if(forme.getClass() == Ellipse.class){
                    Ellipse ellipseTemp = (Ellipse)forme;
                    DessinateurEllipse dessinateurEllipse = new DessinateurEllipse();
                    dessinateurEllipse.setForme(ellipseTemp);
                    dessinateurEllipse.dessiner(gc);
                }
            }
        }else{
            System.err.println("Aucune retour possible !");
        }

    }

    public void redo(GraphicsContext gc) {
            if (!redoHistorique.empty()) {
                Forme forme = redoHistorique.pop();

                if (forme.getClass() == Ligne.class) {
                    Ligne ligneTemp = (Ligne) forme;
                    DessinateurLigne dessinateurLigne = new DessinateurLigne();
                    dessinateurLigne.setForme(ligneTemp);
                    dessinateurLigne.dessiner(gc);
                    undoHistorique.push(ligneTemp);
                }
                if (forme.getClass() == Cercle.class) {
                    Cercle cercleTemp = (Cercle) forme;
                    DessinateurCercle dessinateurCercle = new DessinateurCercle();
                    dessinateurCercle.setForme(cercleTemp);
                    dessinateurCercle.dessiner(gc);
                    undoHistorique.push(cercleTemp);
                }
                if (forme.getClass() == Carre.class) {
                    Carre carreTemp = (Carre) forme;
                    DessinateurCarre dessinateurCarre = new DessinateurCarre();
                    dessinateurCarre.setForme(carreTemp);
                    dessinateurCarre.dessiner(gc);
                    undoHistorique.push(carreTemp);
                }
                if (forme.getClass() == Rectangle.class) {
                    Rectangle rectangleTemp = (Rectangle) forme;
                    DessinateurRectangle dessinateurRectangle = new DessinateurRectangle();
                    dessinateurRectangle.setForme(rectangleTemp);
                    dessinateurRectangle.dessiner(gc);
                    undoHistorique.push(rectangleTemp);
                }
                if (forme.getClass() == Text.class) {
                    Text textTemp = (Text) forme;
                    DessinateurText dessinateurText = new DessinateurText();
                    dessinateurText.setForme(textTemp);
                    dessinateurText.dessiner(gc);
                    undoHistorique.push(textTemp);
                }
                if (forme.getClass() == Ellipse.class) {
                    Ellipse ellipseTemp = (Ellipse) forme;
                    DessinateurEllipse dessinateurEllipse = new DessinateurEllipse();
                    dessinateurEllipse.setForme(ellipseTemp);
                    dessinateurEllipse.dessiner(gc);
                    undoHistorique.push(ellipseTemp);
                }
            }else{
                System.err.println("Aucune action à refaire !");
            }
    }



    public void sauvegarder(GraphicsContext gc, Canvas canvas, Event event){
        FileChooser savefile = new FileChooser();
        savefile.setInitialFileName(fileName.getValue());
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

                String path1 = file.getAbsolutePath() + "Undo.json";
                String path2 = file.getAbsolutePath() + "Redo.json";

                if(Files.exists(Paths.get(path1)) && Files.exists(Paths.get(path2))) {
                    List<Forme> undo;
                    undo = gson.fromJson(new FileReader(path1), new TypeToken<Stack<Forme>>() {
                    }.getType());
                    undoHistorique.clear();
                    undoHistorique.addAll(undo);
                    System.out.println(undoHistorique);

                    List<Forme> redo;
                    redo = gson.fromJson(new FileReader(path2),new TypeToken<Stack<Forme>>(){}.getType());
                    redoHistorique.clear();
                    redoHistorique.addAll(redo);
                    System.out.println(redoHistorique);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur ouverture document");
                    alert.setHeaderText("Les historique de undo et de redo n'ont pas pu être chargé");
                    alert.setContentText("Un fichier de sauvegarde d'historique de undo et de redo n'a pas été trouvé.");
                    undoHistorique= new Stack<Forme>();
                    redoHistorique= new Stack<Forme>();
                    alert.show();
                }
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }
    }

}
