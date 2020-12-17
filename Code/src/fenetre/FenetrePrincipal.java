package fenetre;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import metier.formes.*;

import java.util.Stack;

public class FenetrePrincipal {

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
    private ColorPicker selectionCouleur = new ColorPicker(Color.BLACK);
    @FXML
    private ColorPicker selectionRempl = new ColorPicker(Color.TRANSPARENT);

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
    private Dessinateur dessinateurSelectionne;

    public void initialize(){


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
        vbox.setStyle("fx-background : #666");
        vbox.setSpacing(10);
        vbox.setPrefWidth(100);

        canvas.setWidth(1080);
        canvas.setHeight(720);

        GraphicsContext gc;
        gc=canvas.getGraphicsContext2D();
        gc.setLineWidth(1);

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

        DessinateurManager dessinateurManager= new DessinateurManager();

        selectionCouleur.setOnAction(e->{
            gc.setStroke(selectionCouleur.getValue());
        });

        selectionRempl.setOnAction(e->{
            gc.setStroke(selectionRempl.getValue());
        });


        canvas.setOnMousePressed(e-> {
            dessinateurManager.definirDebutFigure(e,gc,selectionCouleur.getValue(),selectionRempl.getValue(),outils,dessinBtn,effacerBtn);
        });


        canvas.setOnMouseDragged(e->{
            dessinateurManager.definirPendantFigure(dessinBtn,effacerBtn,e,gc);
        });

        canvas.setOnMouseReleased(e->{
            dessinateurManager.definirFinFigure(e,gc,selectionCouleur.getValue(),selectionRempl.getValue(),outils,dessinBtn,effacerBtn);
        });

        //Ne marche pas correctement :
        //Cercle, Text et carre moyen


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
            gc.clearRect(0,0,1080,720);
            if(!historiqueUndo.empty()){
                Forme formeSupprimee= historiqueUndo.lastElement();
                if(formeSupprimee.getClass() == Ligne.class){
                    Ligne ligneTemp = (Ligne)formeSupprimee;
                    historiqueRedo.push(ligneTemp);
                }
                if(formeSupprimee.getClass() == Cercle.class){
                    Cercle cercleTemp = (Cercle) formeSupprimee;
                    historiqueRedo.push(cercleTemp);
                }
                if(formeSupprimee.getClass() == Carre.class){
                    Carre carreTemp = (Carre)formeSupprimee;
                    historiqueRedo.push(carreTemp);
                }
                if(formeSupprimee.getClass() == Rectangle.class){
                    Rectangle rectangleTemp = (Rectangle)formeSupprimee;
                    historiqueRedo.push(rectangleTemp);
                }
                if(formeSupprimee.getClass() == Text.class){
                    Text textTemp = (Text)formeSupprimee;
                    historiqueRedo.push(textTemp);
                }
                if(formeSupprimee.getClass() == Ellipse.class){
                    Ellipse ellipseTemp = (Ellipse)formeSupprimee;
                    historiqueRedo.push(ellipseTemp);
                }

                for(int i=0; i<historiqueUndo.size();i++){
                    Forme forme = historiqueUndo.elementAt(i);
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
        });




    }
}

