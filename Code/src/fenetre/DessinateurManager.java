package fenetre;

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
import metier.persistance.Chargement;
import metier.persistance.Recent;
import metier.persistance.Sauvegarde;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;

/**
 * Classe définissant le manager assurent la gestion de la partie dessin de l'application
 */
public class DessinateurManager {
    //Déclaration des différents dessinateur nécessaire pour dessiner les différentes formes
    /**
     * Dessinateur pour les forme "simple"
     */
    Dessinateur dessinateur;
    /**
     * Dessinateur pour le texte
     */
    DessinateurText dessinateurText = new DessinateurText();
    /**
     * Dessinateur pour le dessin
     */
    DessinateurDessin dessinateurDessin = new DessinateurDessin();
    /**
     * Dessinateur pour l'effeacement
     */
    DessinateurEffacement dessinateurEffacement = new DessinateurEffacement();

    //Déclaration des historique permettant le undo et redo
    /**
     * Pile responsable de l'enregistrement des dernières forme dessiné
     */
    Stack<ICommande> undoHistorique = new Stack<>();
    /**
     * Pile responsable de l'enregistrement des derniers retour en arrière
     */
    Stack<ICommande> redoHistorique = new Stack<>();

    //Déclaration de la propriété responsable du nommage du fichier
    /**
     * Propriété responsable du nommage du fichier
     */
    private final StringProperty fileName = new SimpleStringProperty("Nouveau fichier");
        public StringProperty fileNameProperty(){return fileName;}
        public String getFileName(){return fileName.get();}
        public void setFileName(String valeur){ fileName.set(valeur);}

    /**
     * Méthode permettant la définition de la forme lorsque le bouton de la souris est pressé sur le canvas
     * @param slider slider permettant de récupérer la taille du trait
     * @param e événement déclenché par le clic de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param couleur couleur sélectionnée dans le sélecteur de couleur du trait de la figure
     * @param couleurRemplissage couleur sélectionnée dans le sélecteur de de couleur du remplissage de la figure
     * @param outils tableau de ToggleButton permettant de récupérer que bouton est enfoncé et ainsi le dessinateur lui correspondant
     * @param dessinBtn bouton du dessin pour vérifier si il est enfoncé
     * @param effacerBtn bouton de l'effacement pour vérifier si il est enfoncé
     * @param textBtn bouton du texte pour vérifier si il est enfoncé
     * @param textArea textArea nécessaire pour récupérer le texte à dessiner
     */
    public void definirDebutFigure(Slider slider, MouseEvent e, GraphicsContext gc, Color couleur, Color couleurRemplissage, ToggleGroup outils , ToggleButton dessinBtn, ToggleButton effacerBtn, ToggleButton textBtn, TextArea textArea){
        dessinateur = (Dessinateur) outils.getSelectedToggle().getUserData();
        //La gestion des dessin, effacement et text sont différent car il ne constitue pas de forme "simple"
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

    /**
     * Méthode permettant la définition de la figure pendant que l'utilisateur déplace la souris sur le canvas
     * @param dessinBtn bouton correspondant au dessin
     * @param effacerBtn bouton correspondant à l'effacement
     * @param e événement déclenché par le déplacement de la souris avec le clic enfoncé
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    public void definirPendantFigure (ToggleButton dessinBtn,ToggleButton effacerBtn,MouseEvent e,GraphicsContext gc){
        if(dessinBtn.isSelected()){
            dessinateurDessin.definirPendantFigure(e,gc);
        }else if(effacerBtn.isSelected()) {
            dessinateurEffacement.definirPendantFigure(e,gc);
        }
    }

    /**
     * Méthode permettant la définition de la figure lorsque le clic de la souris est relâché
     * @param e événement déclenché par le clic de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param couleur couleur sélectionnée dans le sélecteur de couleur du trait de la figure
     * @param couleurRemplissage couleur sélectionnée dans le sélecteur de de couleur du remplissage de la figure
     * @param outils tableau de ToggleButton permettant de récupérer que bouton est enfoncé et ainsi le dessinateur lui correspondant
     * @param dessinBtn bouton du dessin pour vérifier si il est enfoncé
     * @param effacerBtn bouton de l'effacement pour vérifier si il est enfoncé
     * @param textBtn bouton du texte pour vérifier si il est enfoncé
     */
    public void definirFinFigure(MouseEvent e, GraphicsContext gc, Color couleur, Color couleurRemplissage, ToggleGroup outils , ToggleButton dessinBtn,ToggleButton effacerBtn,ToggleButton textBtn){
        if(dessinBtn.isSelected()){
            dessinateurDessin.definirFormeOnMouseReleased(e,gc);
            undoHistorique.push(dessinateurDessin.getCommande());
        }else if(effacerBtn.isSelected()){
            dessinateurEffacement.definirFormeOnMouseReleased(e,gc);
            undoHistorique.push(dessinateurEffacement.getCommande());
        }else if(textBtn.isSelected()){
            dessinateurText.dessiner(gc);
            undoHistorique.push(dessinateurText.getCommande());
        }else {
            dessinateur.definirFormeOnMouseReleased(e);
            dessinateur.dessiner(gc);
            undoHistorique.push(dessinateur.getCommande());
        }
    }

    /**
     * Méthode permettant de faire marche arrière et d'annuler la dernière action
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
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

    /**
     * Méthode permettant de refaire une action qui a été annulé
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
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


    /**
     * Méthode permettant la sauvegarde de l'image et des historiques lui correspondant
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param canvas canvas sur lequel on dessine
     * @param event événement déclenché par l'appui sur le bouton de sauvegarde
     */
    public void sauvegarder(GraphicsContext gc, Canvas canvas, Event event) {
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
                Stage scene = (Stage) ((Node) event.getSource()).getScene().getWindow();
                RecentManager recentManager =(RecentManager)scene.getUserData();
                recentManager.getLesFichiers().remove(recentManager.getLesFichiers().size()-1);
                recentManager.add(new Recent(file.getCanonicalPath(),file.getName(),true));
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

    /**
     * Méthode permettant de charger une image avec les historique lui correspondant
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param event événement déclenché par l'appui sur le bouton de chargement
     */
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
                Stage scene = (Stage) ((Node) event.getSource()).getScene().getWindow();
                RecentManager recentManager =(RecentManager)scene.getUserData();
                recentManager.getLesFichiers().remove(recentManager.getLesFichiers().size()-1);
                recentManager.add(new Recent(file.getCanonicalPath(),file.getName(),true));

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

    /**
     * Méthode permettant de charger un fichier lorsque l'utilisateur decide de le sélectionner depuis l'accueil
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param recentManager le manager des fichier récent ouvert
     */
    public void chargerFichier(GraphicsContext gc, RecentManager recentManager){
        File file = new File(recentManager.getLesFichiers().get(recentManager.getLesFichiers().size()-1).getFileName());
        try {
            //Récupére l'image
            InputStream io = new FileInputStream(file);
            Image img = new Image(io);
            gc.drawImage(img, 0, 0);
            this.setFileName(file.getName());
            recentManager.getLesFichiers().remove(recentManager.getLesFichiers().size()-1);
            recentManager.add(new Recent(file.getCanonicalPath(),file.getName(),true));

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


