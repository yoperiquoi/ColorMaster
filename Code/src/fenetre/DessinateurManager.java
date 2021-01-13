package fenetre;

import fenetre.commande.DessinerDessin;
import fenetre.commande.DessinerEffacement;
import fenetre.commande.ICommande;
import fenetre.dessinateur.Dessinateur;
import fenetre.dessinateur.DessinateurDessin;
import fenetre.dessinateur.DessinateurEffacement;
import fenetre.dessinateur.DessinateurText;
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
import metier.Chargement;
import metier.Sauvegarde;
import metier.formes.Dessin;
import metier.formes.Effacement;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;

public class DessinateurManager {
    Dessinateur dessinateur;
    DessinateurText dessinateurText = new DessinateurText();
    DessinateurDessin dessinateurDessin = new DessinateurDessin();
    DessinateurEffacement dessinateurEffacement = new DessinateurEffacement();
    Stack<ICommande> undoHistorique = new Stack<>();
    Stack<ICommande> redoHistorique = new Stack<>();

    private final StringProperty fileName = new SimpleStringProperty("Nouveau fichier");
        public StringProperty fileNameProperty(){return fileName;}
        public String getFileName(){return fileName.get();}
        public void setFileName(String valeur){ fileName.set(valeur);}

    public void definirDebutFigure(Slider slider, MouseEvent e, GraphicsContext gc, Color couleur, Color couleurRemplissage, ToggleGroup outils , ToggleButton dessinBtn, ToggleButton effacerBtn, ToggleButton textBtn, TextArea textArea){
        dessinateur = (Dessinateur) outils.getSelectedToggle().getUserData();
        if(dessinBtn.isSelected()){
            dessinateurDessin.definirFormeOnMousePressed(e,gc,couleur,couleurRemplissage);
        }else if (effacerBtn.isSelected()) {
            dessinateurEffacement.definirFormeOnMousePressed(e,gc,couleur,couleurRemplissage);
        }else if (textBtn.isSelected()){
            dessinateurText.definirFormeOnMousePressed(slider,e,gc,couleur,couleurRemplissage,textArea);
        }else{
            dessinateur.definirFormeOnMousePressed(e,gc,couleur,couleurRemplissage);
        }
    }

    public void definirPendantFigure (ToggleButton dessinBtn,ToggleButton effacerBtn,MouseEvent e,GraphicsContext gc){
        if(dessinBtn.isSelected()){
            dessinateurDessin.definirPendantFigure(e,gc);
        }else if(effacerBtn.isSelected()) {
            dessinateurEffacement.definirPendantFigure(e,gc);
        }
    }

    public void definirFinFigure(MouseEvent e, GraphicsContext gc, Color couleur, Color couleurRemplissage, ToggleGroup outils , ToggleButton dessinBtn,ToggleButton effacerBtn,ToggleButton textBtn){
        if(dessinBtn.isSelected()){
            dessinateurDessin.definirFormeOnMouseReleased(e,gc);
            undoHistorique.push(dessinateurDessin.getCommande());
        }else if(effacerBtn.isSelected()){
            dessinateurEffacement.definirFormeOnMouseReleased(e,gc);
            undoHistorique.push(dessinateurEffacement.getCommande());
        }else if(textBtn.isSelected()){
            dessinateurText.dessiner(gc);
            undoHistorique.push(dessinateur.getCommande());
        }else {
            dessinateur.definirFormeOnMouseReleased(e);
            dessinateur.dessiner(gc);
            undoHistorique.push(dessinateur.getCommande());
        }
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
                undoHistorique.push(derniereCommande);
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

                String path1 = file.getAbsolutePath() + "Undo.json";
                String path2 = file.getAbsolutePath() + "Redo.json";

                Sauvegarde.sauvegarder(path1,undoHistorique);
                Sauvegarde.sauvegarder(path2,redoHistorique);
                this.setFileName(file.getName());
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur ouverture document");
                alert.setHeaderText("Il y a eu une erreur pendant le sauvegarde du fichier !");
                alert.setContentText(ex.getMessage());
                undoHistorique = new Stack<ICommande>();
                redoHistorique = new Stack<ICommande>();
                alert.show();
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
                this.setFileName(file.getName());

                String path1 = file.getAbsolutePath() + "Undo.json";
                String path2 = file.getAbsolutePath() + "Redo.json";

                if (!Files.exists(Path.of(path1)) | !Files.exists(Path.of(path2))){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur ouverture document");
                    alert.setHeaderText("Les historique de undo et de redo n'ont pas pu être chargé");
                    alert.setContentText("Un fichier de sauvegarde d'historique de undo et de redo n'a pas été trouvé.");
                    undoHistorique= new Stack<ICommande>();
                    redoHistorique= new Stack<ICommande>();
                    alert.show();
                }
                undoHistorique = Chargement.charger(path1);
                redoHistorique = Chargement.charger(path2);
                setFileName(file.getName());
                } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur ouverture document");
                alert.setHeaderText("Il y a eu une erreur pendant le chargement du fichier !");
                alert.setContentText(ex.getMessage());
                undoHistorique = new Stack<ICommande>();
                redoHistorique = new Stack<ICommande>();
                alert.show();
            }
        }
    }
}


