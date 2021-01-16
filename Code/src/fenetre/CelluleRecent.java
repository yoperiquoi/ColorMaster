package fenetre;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import metier.persistance.Recent;

/**
 * Classe définissant une cellule pour la représentation d'un fichier ouvert ou édité récemment
 */
public class CelluleRecent extends javafx.scene.control.ListCell<Recent> {

    /**
     * Méthode définissant la structure de la cellule pour l'affichage d'un fichier récent
     * @param item item a afficher (ici des Recent)
     * @param empty si la case est vide ou non
     */
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
