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
            Label label = new Label();
            label.textProperty().bind(item.fileNameProperty());
            container.getChildren().add(label);
            Button button = new Button("Supprimer");
            container.getChildren().add(button);
            setGraphic(container);
        }else{
            setGraphic(null);
        }
    }
}
