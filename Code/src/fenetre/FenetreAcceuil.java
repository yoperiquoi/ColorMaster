package fenetre;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import metier.persistance.Recent;

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

    @FXML
    public Text fichierSelected;
    @FXML
    public Button btnAjouter;
    @FXML
    public Button btnSupprimer;


    public void initialize(){
        RecentManager recentManager = new RecentManager();
        fileName.setText("Nouveau document");


        laListView.itemsProperty().bind(recentManager.lesFichiersProperty());
        laListView.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(oldValue!=null){
                fichierSelected.textProperty().unbind();
            }
            if(newValue != null){
                fichierSelected.textProperty().bind(newValue.fileNameProperty());
            }
        }));

        laListView.setCellFactory(__ -> new CelluleRecent());


        btnAjouter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser openFile = new FileChooser();
                openFile.setTitle("Open File");
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                File file = openFile.showOpenDialog(stage);
                if(file != null){
                    try {
                        recentManager.add(new Recent(file.getCanonicalPath(),file.getName(),true));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnOuvrir.setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent e) {
                  Parent root;
                  try {
                      root = FXMLLoader.load(getClass().getResource("/fxml/FenetrePrincipal.fxml"));
                      Stage stage = new Stage();
                      Recent recent= laListView.getSelectionModel().getSelectedItem();
                      recentManager.del(recent);
                      recentManager.add(recent);
                      stage.setUserData(recentManager);
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
                    File file = new File(System.getProperty("user.dir").concat("/").concat(fileName.getText()));
                    recentManager.add(new Recent(file.getCanonicalPath(),file.getName(),false));
                    stage.setUserData(recentManager);
                    stage.setScene(new Scene(root, 1200, 1000));
                    stage.show();
                    ((Node)(e.getSource())).getScene().getWindow().hide();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnSupprimer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(laListView.getSelectionModel().getSelectedItem() != null){
                    recentManager.del(laListView.getSelectionModel().getSelectedItem());
                }
            }
        });

    }
}
