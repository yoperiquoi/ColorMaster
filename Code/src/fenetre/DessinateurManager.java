package fenetre;

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
     * Dessinateur pour l'effacement
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

    /**
     * Méthode permettant de récupérer le string property du nom du fichier
      * @return string property du nom du fichier
     */
    public StringProperty fileNameProperty(){return fileName;}

    /**
     * Méthode permettant de récupérer le nom du fichier
     * @return nom du fichier
     */
     public String getFileName(){return fileName.get();}

    /**
     * Méthode permettant de définir le nom du fichier
     * @param valeur nom du fichier
     */
    public void setFileName(String valeur){ fileName.set(valeur);}

    /**
     * Méthode permettant la définition de la forme lorsque le bouton de la souris est pressé sur le canvas
     * @param slider slider permettant de récupérer la taille du trait
     * @param e événement déclenché par le clic de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param couleur couleur sélectionnée dans le sélecteur de couleur du trait de la figure
     * @param couleurRemplissage couleur sélectionnée dans le sélecteur de de couleur du remplissage de la figure
     * @param outils tableau de ToggleButton permettant de récupérer que bouton est enfoncé et ainsi le dessinateur lui correspondant
     * @param textBtn bouton du texte pour vérifier si il est enfoncé
     * @param textArea textArea nécessaire pour récupérer le texte à dessiner
     */
    public void definirDebutFigure(Slider slider, MouseEvent e, GraphicsContext gc, Color couleur, Color couleurRemplissage, ToggleGroup outils , ToggleButton textBtn, TextArea textArea){
        dessinateur = (Dessinateur) outils.getSelectedToggle().getUserData();
        //La gestion des dessin, effacement et text sont différent car il ne constitue pas de forme "simple"
        if (textBtn.isSelected()){
            dessinateurText.definirFormeOnMousePressed(slider,e,gc,couleur,couleurRemplissage,textArea);
        }else{
            dessinateur.definirFormeOnMousePressed(e,gc,couleur,couleurRemplissage);
        }
    }

    /**
     * Méthode permettant la définition de la figure pendant que l'utilisateur déplace la souris sur le canvas
     * @param e événement déclenché par le déplacement de la souris avec le clic enfoncé
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    public void definirPendantFigure (MouseEvent e,GraphicsContext gc){
        //Seul le dessin et l'effacement sont appelé lors du déplacement de la souris car ce sont des forme particulière
        //du fait qu'elle ne suive pas de formule mathématique
        if(dessinateur instanceof IDessinePendant){
            ((IDessinePendant) dessinateur).definirPendantFigure(e,gc);
        }
    }

    /**
     * Méthode permettant la définition de la figure lorsque le clic de la souris est relâché
     * @param e événement déclenché par le clic de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param textBtn bouton du texte pour vérifier si il est enfoncé
     */
    public void definirFinFigure(MouseEvent e, GraphicsContext gc, ToggleButton textBtn){
        if(textBtn.isSelected()){
            dessinateurText.dessiner(gc);
            undoHistorique.push(dessinateurText.getCommande());
        }else {
            dessinateur.definirFormeOnMouseReleased(e);
            dessinateur.dessiner(gc);
            //Les commande des dessinateur qui sont alors rempli avec la forme défini sont stocker dans l'historique
            //de redo pour que l'on puisse retracer les figure
            undoHistorique.push(dessinateur.getCommande());
        }
    }

    /**
     * Méthode permettant de faire marche arrière et d'annuler la dernière action
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    public void undo(GraphicsContext gc){
        //Pour faire un retour en arrière on supprimer juste le dernier élement de l'historique de undo
        //puis on retrace tout les figures restante dans l'historique
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
        //Pour faire annuler la dernière action on la récupére simplement puis on l'exécute
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
     * @param canvas canvas sur lequel on dessine
     * @param event événement déclenché par l'appui sur le bouton de sauvegarde
     */
    public void sauvegarder( Canvas canvas, Event event) {
        //On utilise un FileChooser pour que l'utilisateur puisse choisir ou enregistrer avec le nom voulu
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

                //Enregistrement des historiques
                Sauvegarde.sauvegarder(file.getAbsolutePath() + "Undo.json",undoHistorique);
                Sauvegarde.sauvegarder(file.getAbsolutePath() + "Redo.json",redoHistorique);

                //Récupération du manager des récent pour ajouter le nouveau fichier enregistré
                Stage scene = (Stage) ((Node) event.getSource()).getScene().getWindow();
                RecentManager recentManager =(RecentManager)scene.getUserData();
                recentManager.getLesFichiers().remove(recentManager.getLesFichiers().size()-1);
                recentManager.add(new Recent(file.getCanonicalPath(),file.getName(),true));

                //On défini enfin le nom du fichier comme celui défini par l'utilisateur
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
        //On utilise un FileChooser pour que l'utilisateur puisse choisir quel fichier charger
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

                //Défini le nom de l'image au nom du fichier
                this.setFileName(file.getName());

                //Ajoute le nouveau fichier chargé au récents
                Stage scene = (Stage) ((Node) event.getSource()).getScene().getWindow();
                RecentManager recentManager =(RecentManager)scene.getUserData();
                recentManager.getLesFichiers().remove(recentManager.getLesFichiers().size()-1);
                recentManager.add(new Recent(file.getCanonicalPath(),file.getName(),true));

                //On récupére les historiques
                chargerHistoriques(file);
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
     * Méthode permettant de charger les historiques de undo et de redo
     * @param file fichier correspond à l'image à charger
     */
    private void chargerHistoriques(File file) {
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

            chargerHistoriques(file);

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


