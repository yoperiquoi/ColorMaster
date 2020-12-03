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

        Ligne ligne= new Ligne();
        Rectangle rectangle= new Rectangle();
        Cercle cercle= new Cercle();
        Ellipse ellipse= new Ellipse();
        Carre carre = new Carre();

        selectionCouleur.setOnAction(e->{
            gc.setStroke(selectionCouleur.getValue());
        });

        selectionRempl.setOnAction(e->{
            gc.setStroke(selectionRempl.getValue());
        });

        canvas.setOnMousePressed(e-> {
            if (dessinBtn.isSelected()) {
                gc.setStroke(selectionCouleur.getValue());
                gc.beginPath();
            } else if (effacerBtn.isSelected()) {
                double largeur = gc.getLineWidth();
                gc.clearRect(e.getX() - largeur / 2, e.getY() - largeur / 2, largeur, largeur);
            } else if (ligneBtn.isSelected()) {
                gc.setStroke(selectionCouleur.getValue());
                ligne.setCouleur(selectionCouleur.getValue());
                ligne.setX((float) e.getX());
                ligne.setY((float) e.getY());
            } else if (rectangleBtn.isSelected()) {
                gc.setStroke(selectionCouleur.getValue());
                rectangle.setCouleur(selectionCouleur.getValue());
                rectangle.setCouleurRemplissage(selectionRempl.getValue());
                if (selectionRempl.getValue() != Color.TRANSPARENT) {
                    rectangle.setRempli(true);
                }
                rectangle.setX((float) e.getX());
                rectangle.setY((float) e.getY());
            } else if (cercleBtn.isSelected()) {
                gc.setStroke(selectionCouleur.getValue());
                cercle.setCouleur(selectionCouleur.getValue());
                cercle.setCouleurRemplissage(selectionRempl.getValue());
                if (selectionRempl.getValue() != Color.TRANSPARENT) {
                    cercle.setRempli(true);
                }
                cercle.setX((float) e.getX());
                cercle.setY((float) e.getY());
            } else if (ellipseBtn.isSelected()) {
                gc.setStroke(selectionCouleur.getValue());
                ellipse.setCouleur(selectionCouleur.getValue());
                ellipse.setCouleurRemplissage(selectionRempl.getValue());
                if (selectionRempl.getValue() != Color.TRANSPARENT) {
                    ellipse.setRempli(true);
                }
                ellipse.setX((float) e.getX());
                ellipse.setY((float) e.getY());
            }else if(carreBtn.isSelected()){
                gc.setStroke(selectionCouleur.getValue());
                carre.setCouleur(selectionCouleur.getValue());
                carre.setCouleurRemplissage(selectionRempl.getValue());
                if (selectionRempl.getValue() != Color.TRANSPARENT) {
                    carre.setRempli(true);
                }
                carre.setX((float) e.getX());
                carre.setY((float) e.getY());
            } else if (textBtn.isSelected()) {
                gc.setLineWidth(1);
                gc.setFont(Font.font(slider.getValue()));
                gc.setStroke(selectionCouleur.getValue());
                gc.setFill(selectionRempl.getValue());
                gc.fillText(textArea.getText(), e.getX(), e.getY());
                gc.strokeText(textArea.getText(), e.getX(), e.getY());
            }
        });


        canvas.setOnMouseDragged(e->{
            if(dessinBtn.isSelected()){
                gc.lineTo(e.getX(),e.getY());
                gc.stroke();
            }else if(effacerBtn.isSelected()) {
                double largueurLigne = gc.getLineWidth();
                gc.clearRect(e.getX() - largueurLigne / 2, e.getY() - largueurLigne / 2, largueurLigne, largueurLigne);
            }
        });

        canvas.setOnMouseReleased(e->{
            if(dessinBtn.isSelected()){
                gc.lineTo(e.getX(),e.getY());
                gc.stroke();
                gc.closePath();
            }else if(effacerBtn.isSelected()){
                double largueurLigne = gc.getLineWidth();
                gc.clearRect(e.getX() - largueurLigne / 2, e.getY() - largueurLigne / 2, largueurLigne, largueurLigne);
            }else if(ligneBtn.isSelected()){
                ligne.setX2((float)e.getX());
                ligne.setY2((float)e.getY());
                gc.strokeLine(ligne.getX(),ligne.getY(),ligne.getX2(),ligne.getY2());
                historiqueUndo.push(ligne);
            }else if(rectangleBtn.isSelected()){
                rectangle.setLargeur(Math.abs((float)e.getX()-rectangle.getX()));
                rectangle.setLongueur(Math.abs((float)e.getY()-rectangle.getY()));
                if (e.getX()<rectangle.getX()){
                    rectangle.setX((float)e.getX());
                }
                if(e.getY()<rectangle.getY()){
                    rectangle.setY((float)e.getY());
                }
                gc.strokeRect(rectangle.getX(),rectangle.getY(),rectangle.getLargeur(),rectangle.getLongueur());
                historiqueUndo.push(rectangle);
            }else if(cercleBtn.isSelected()){
                cercle.setCentre((float)((e.getX()-cercle.getX())/2),(float)(e.getY()-cercle.getY()/2));
                cercle.setRayon((float)(Math.abs(e.getX()-cercle.getCentreX())+Math.abs(e.getY()-cercle.getCentreY())));
                if(cercle.getRempli()){
                    gc.setFill(selectionRempl.getValue());
                    gc.fillOval(cercle.getCentreX(),cercle.getCentreY(),cercle.getRayon(),cercle.getRayon());
                }
                gc.strokeOval(cercle.getCentreX(),cercle.getCentreY(),cercle.getRayon(),cercle.getRayon());
                historiqueUndo.push(cercle);
            }
        });





    }
}

