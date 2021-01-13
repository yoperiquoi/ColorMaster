package fenetre;

import fenetre.dessinateur.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import metier.formes.*;

import java.util.Stack;

public class FenetrePrincipal {

    public TextArea fileName;

    Stack<Forme> historiqueUndo= new Stack<Forme>();
    Stack<Forme> historiqueRedo= new Stack<Forme>();

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

    public void initialize(){
        //Stage thisStage = (Stage) canvas.getScene().getWindow();

        DessinateurManager dessinateurManager= new DessinateurManager();

        fileName.textProperty().bindBidirectional(nomFichier.textProperty());
        dessinateurManager.fileNameProperty().bindBidirectional(nomFichier.textProperty());

        /*
        thisStage.titleProperty().bind(
                (dessinateurManager.fileNameProperty()).concat("- ColorMaster")
        );
        */

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
            outil.setMinWidth(90);
            outil.setToggleGroup(outils);
            outil.setCursor(Cursor.HAND);
        }
        textArea.setPrefRowCount(1);

        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        for (Button outil :tableauBtn){
            outil.setMinWidth(90);
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

    }
}

