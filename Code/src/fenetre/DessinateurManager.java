package fenetre;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import fenetre.commande.DessinerDessin;
import fenetre.commande.DessinerEffacement;
import fenetre.commande.ICommande;
import fenetre.dessinateur.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
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
    DessinateurText dessinateurText = new DessinateurText();
    Stack<ICommande> undoHistorique = new Stack<>();
    Stack<ICommande> redoHistorique = new Stack<>();
    Dessin dessin = new Dessin();
    Effacement effacement = new Effacement();

    private StringProperty fileName = new SimpleStringProperty("Nouveau fichier");
        public StringProperty fileNameProperty(){return fileName;}
        public String getFileName(){return fileName.get();}
        public void setFileName(String valeur){ fileName.set(valeur);}

    public void definirDebutFigure(Slider slider, MouseEvent e, GraphicsContext gc, Color couleur, Color couleurRemplissage, ToggleGroup outils , ToggleButton dessinBtn, ToggleButton effacerBtn, ToggleButton textBtn, TextArea textArea){
        dessinateur = (Dessinateur) outils.getSelectedToggle().getUserData();
        if (dessinBtn.isSelected()) {
            gc.setStroke(couleur);
            dessin.setCouleur(couleur);
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
        }else if (textBtn.isSelected()){
            dessinateurText.definirFormeOnMousePressed(slider,e,gc,couleur,couleurRemplissage,textArea);
        } else{
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

    public void definirFinFigure(MouseEvent e, GraphicsContext gc, Color couleur, Color couleurRemplissage, ToggleGroup outils , ToggleButton dessinBtn,ToggleButton effacerBtn,ToggleButton textBtn){
        if(dessinBtn.isSelected()){
            gc.lineTo(e.getX(),e.getY());
            dessin.pointsY.add((float)e.getY());
            dessin.pointsX.add((float)e.getX());
            gc.stroke();
            gc.closePath();
            DessinerDessin dessinerDessin = new DessinerDessin(dessin);
            undoHistorique.push(dessinerDessin);
        }else if(effacerBtn.isSelected()){
            double largueurLigne = gc.getLineWidth();
            effacement.pointsY.add((float)e.getY());
            effacement.pointsX.add((float)e.getX());
            gc.clearRect(e.getX() - largueurLigne / 2, e.getY() - largueurLigne / 2, largueurLigne, largueurLigne);
            DessinerEffacement dessinerEffacement = new DessinerEffacement(effacement);
            undoHistorique.push(dessinerEffacement);
        }else if(textBtn.isSelected()){
            dessinateurText.dessiner(gc);
            undoHistorique.push(dessinateur.getCommande());
        }else {
            dessinateur.definirFormeOnMouseReleased(e);
            dessinateur.dessiner(gc);
            undoHistorique.push(dessinateur.getCommande());
        }
        System.out.println(undoHistorique);
    }

    public void undo(GraphicsContext gc){
        if(!undoHistorique.empty()){
            gc.setFill(Color.WHITE);
            gc.fillRect(0,0, 1080,720);
            ICommande derniereCommande = undoHistorique.pop();
            redoHistorique.push(derniereCommande);
            for (ICommande c: undoHistorique) {
                c.execute(gc);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Action impossible !");
            alert.setHeaderText("L'historique de undo est vide !");
            alert.setContentText("Il n'est plus possible de revenir en arrière.");
            alert.show();
        }

    }

    public void redo(GraphicsContext gc) {
            if (!redoHistorique.empty()) {
                ICommande derniereCommande = redoHistorique.pop();
                derniereCommande.execute(gc);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Action impossible !");
                alert.setHeaderText("L'historique de redo est vide !");
                alert.setContentText("Il n'est plus possible de refaire une action.");
                alert.show();
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
            /*
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
                this.setFileName(file.getName());

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

                    List<Forme> redo;
                    redo = gson.fromJson(new FileReader(path2),new TypeToken<Stack<Forme>>(){}.getType());
                    redoHistorique.clear();
                    redoHistorique.addAll(redo);
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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur ouverture document");
                    alert.setHeaderText("Il y a eu une erreur pendant le chargement du fichier !");
                    alert.setContentText(ex.message);
                    undoHistorique= new Stack<Forme>();
                    redoHistorique= new Stack<Forme>();
                    alert.show();
            }

             */
        }
    }


