package fenetre;

import fenetre.dessinateur.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import metier.formes.*;

import java.util.Stack;

/**
 * Code-behind de la fenêtre principal permettant le dessin
 */
public class FenetrePrincipal {

    //Récupération des différents éléments de la vue
    @FXML
    public TextArea fileName;
    @FXML
    public GridPane grid;
    @FXML
    public Label details;
    @FXML
    private VBox vbox;
    @FXML
    private ToggleButton dessinBtn;
    @FXML
    private ToggleButton effacerBtn;
    @FXML
    private ToggleButton ligneBtn;
    @FXML
    private ToggleButton carreBtn;
    @FXML
    private ToggleButton rectangleBtn;
    @FXML
    private ToggleButton cercleBtn;
    @FXML
    private ToggleButton ellipseBtn;
    @FXML
    private ToggleButton textBtn;

    ToggleButton[] tableauOutils= new ToggleButton[8];

    ToggleGroup outils = new ToggleGroup();

    @FXML
    private final ColorPicker selectionCouleur = new ColorPicker(Color.BLACK);
    @FXML
    private final ColorPicker selectionRempl = new ColorPicker(Color.TRANSPARENT);

    @FXML
    private TextArea textArea;

    @FXML
    private Slider slider;

    @FXML
    private Label labelSlider;
    @FXML
    private Label couleurLigne;
    @FXML
    private Label couleurRempl;

    @FXML
    private Label nomFichier;

    @FXML
    private Button undoBtn;
    @FXML
    private Button redoBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button openBtn;

    Button[] tableauBtn=new Button[4];

    @FXML
    private Canvas canvas;


    /**
     * Méthode permettant la définition du contexte de la fenêtre avant l'ouverture de celle-ci
     */
    public void initialize(){

        DessinateurManager dessinateurManager= new DessinateurManager();

        tableauBtn[0] = undoBtn;
        tableauBtn[1] = redoBtn;
        tableauBtn[2] = saveBtn;
        tableauBtn[3] = openBtn;

        tableauOutils[0]= dessinBtn;
        tableauOutils[1]= effacerBtn;
        tableauOutils[2]= ligneBtn;
        tableauOutils[3]= carreBtn;
        tableauOutils[4]= rectangleBtn;
        tableauOutils[5]= cercleBtn;
        tableauOutils[6]= ellipseBtn;
        tableauOutils[7]= textBtn;

        for(ToggleButton outil: tableauOutils){
            outil.setMinWidth(120);
            outil.setToggleGroup(outils);
            outil.setCursor(Cursor.HAND);
        }
        textArea.setPrefRowCount(1);

        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        for (Button outil :tableauBtn){
            outil.setMinWidth(120);
            outil.setCursor(Cursor.HAND);
            outil.setTextFill(Color.BLACK);
            outil.setStyle("fx-background : #333");
        }

        saveBtn.setStyle("fx-background : #333");
        openBtn.setStyle("fx-background : #333");

        vbox.setPadding(new Insets(5));
        vbox.setStyle("fx-background : white");
        vbox.setSpacing(10);
        vbox.setPrefWidth(100);

        canvas.setWidth(1080);
        canvas.setHeight(720);

        GraphicsContext gc;
        gc=canvas.getGraphicsContext2D();
        gc.setLineWidth(1);
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0, canvas.getWidth(),canvas.getHeight());

        selectionCouleur.setValue(Color.BLACK);
        selectionRempl.setValue(Color.TRANSPARENT);

        DessinateurCercle dessinateurCercle=new DessinateurCercle();
        DessinateurCarre dessinateurCarre= new DessinateurCarre();
        DessinateurEllipse dessinateurEllipse= new DessinateurEllipse();
        DessinateurRectangle dessinateurRectangle = new DessinateurRectangle();
        DessinateurLigne dessinateurLigne = new DessinateurLigne();
        DessinateurText dessinateurText = new DessinateurText();

        ligneBtn.setUserData(dessinateurLigne);
        carreBtn.setUserData(dessinateurCarre);
        rectangleBtn.setUserData(dessinateurRectangle);
        cercleBtn.setUserData(dessinateurCercle);
        ellipseBtn.setUserData(dessinateurEllipse);
        textBtn.setUserData(dessinateurText);



        selectionCouleur.setOnAction(e->{
            gc.setStroke(selectionCouleur.getValue());
        });

        selectionRempl.setOnAction(e->{
            gc.setStroke(selectionRempl.getValue());
        });


        canvas.setOnMousePressed(e-> {
            if(!(outils.getSelectedToggle() == null)){
                dessinateurManager.definirDebutFigure(slider,e,gc,selectionCouleur.getValue(),selectionRempl.getValue(),outils,dessinBtn,effacerBtn,textBtn,textArea);
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Pas d'outil sélectionné");
                alert.setHeaderText("Pas d'outil sélectionné");
                alert.setContentText("Selectionnez un outil avant de dessiner");
                alert.show();
            }
        });


        canvas.setOnMouseDragged(e->{
            if(!(outils.getSelectedToggle() == null)){
            dessinateurManager.definirPendantFigure(dessinBtn,effacerBtn,e,gc);
            }
        });

        canvas.setOnMouseReleased(e->{
            if(!(outils.getSelectedToggle() == null)) {
                dessinateurManager.definirFinFigure(e, gc, selectionCouleur.getValue(), selectionRempl.getValue(), outils, dessinBtn, effacerBtn,textBtn);
            }
        });


        slider.valueProperty().addListener(e->{
            float largeur= (float)slider.getValue();
            if(textBtn.isSelected()){
                gc.setLineWidth(1);
                gc.setFont(Font.font(slider.getValue()));
            }
            labelSlider.setText(String.format("%.1f",largeur));
            gc.setLineWidth(largeur);
        });

        undoBtn.setOnAction(e->{
            dessinateurManager.undo(gc);
        });

        redoBtn.setOnAction(e->{
            dessinateurManager.redo(gc);
        });

        saveBtn.setOnAction(e->{
            dessinateurManager.sauvegarder(gc,canvas,e);
        });

        openBtn.setOnAction(e->{
            dessinateurManager.charger(gc,e);
        });

        Platform.runLater(()->{
            Stage thisStage = (Stage) grid.getScene().getWindow();
            RecentManager recentManager = (RecentManager) thisStage.getUserData();


            fileName.textProperty().bindBidirectional(nomFichier.textProperty());
            dessinateurManager.fileNameProperty().bindBidirectional(nomFichier.textProperty());

            thisStage.titleProperty().bind(
                    (dessinateurManager.fileNameProperty()).concat(" - ColorMaster")
            );
            fileName.textProperty().setValue(recentManager.getLesFichiers().get(recentManager.getLesFichiers().size()-1).getNom());
            if(recentManager.getLesFichiers().get(recentManager.getLesFichiers().size()-1).getEnregistre()){
                dessinateurManager.chargerFichier(gc,recentManager);
            }
            details.textProperty().bind(Bindings.format("Nom fichier : %s caractères",
                    Bindings.createStringBinding(
                            () -> Integer.toString(dessinateurManager.getFileName().length()), nomFichier.textProperty())));
        });
    }
}

