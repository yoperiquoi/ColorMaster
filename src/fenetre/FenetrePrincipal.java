package fenetre;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.shape.Shape;

import java.util.Stack;

public class FenetrePrincipal {
    Stack<Shape> historiqueUndo= new Stack<Shape>();
    Stack<Shape> historiqueRedo= new Stack<Shape>();

    public ToggleButton dessinBtn;
    public ToggleButton effacerBtn;
    public ToggleButton ligneBtn;
    public ToggleButton rectangleBtn;
    public ToggleButton cercleBtn;
    public ToggleButton ellipseBtn;
    public ToggleButton textBtn;

    ToggleButton[] tableauOutils={dessinBtn,effacerBtn,ligneBtn,rectangleBtn,cercleBtn,ellipseBtn,textBtn};

    public ColorPicker selectionCouleur;
    public ColorPicker selectionRempl;

    public TextArea textArea;

    public Slider slider;

    public Label labelSlider;

    public Button undoBtn;
    public Button redoBtn;
    public Button saveBtn;
    public Button openBtn;

    Button[] tableauBtn = {undoBtn,redoBtn,saveBtn,openBtn};

    ToggleGroup Outils = new ToggleGroup();



}
