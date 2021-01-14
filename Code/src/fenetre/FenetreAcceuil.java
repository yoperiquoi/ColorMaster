package fenetre;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import metier.Recent;

import java.io.File;
import java.io.IOException;

public class FenetreAcceuil {
    @FXML
    public Button btnOuvrir;

    @FXML
    public TextField fileName;

    @FXML
    public Button btnNouveauDoc;

    @FXML
    public ListView<Recent> laListView;

    public Text fichierSelected;


    public void initialize(){
        RecentManager recentManager = new RecentManager();
        fileName.setText("Nouveau document");

        laListView.itemsProperty().bind(recentManager.lesFichiersProperty());

        laListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Recent>() {
            @Override
            public void changed(ObservableValue<? extends Recent> observableValue, Recent file, Recent t1) {
                    fichierSelected.textProperty().bind(t1.fileNameProperty());
                }
        });


        btnOuvrir.setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent e) {
                  Parent root;
                  try {
                      root = FXMLLoader.load(getClass().getResource("/fxml/FenetrePrincipal.fxml"));
                      Stage stage = new Stage();
                      stage.setScene(new Scene(root, 1200, 1000));
                      stage.show();
                      ((Node)(e.getSource())).getScene().getWindow().hide();
                  }
                  catch (IOException ex) {
                      ex.printStackTrace();
                  }
              }
        });

        btnNouveauDoc.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("/fxml/FenetrePrincipal.fxml"));
                    Stage stage = new Stage();
                    stage.setUserData(fileName.getText());
                    stage.setScene(new Scene(root, 1200, 1000));
                    stage.show();
                    ((Node)(e.getSource())).getScene().getWindow().hide();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


    }
}
