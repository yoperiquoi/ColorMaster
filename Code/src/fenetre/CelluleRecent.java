package fenetre;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import metier.persistance.Recent;

public class CelluleRecent extends javafx.scene.control.ListCell<Recent> {

    @Override
    protected void updateItem(Recent item, boolean empty){
        super.updateItem(item,empty);
        if(!empty){
            HBox container = new HBox();
            Label label1 = new Label();
            label1.textProperty().bind(item.fileNameProperty());
            Label label2 = new Label();
            label2.textProperty().setValue("Nom fichier : ".concat(item.getNom()).concat(" | Chemin : "));
            container.getChildren().add(label2);
            container.getChildren().add(label1);
            setGraphic(container);
        }else{
            setGraphic(null);
        }
    }
}
