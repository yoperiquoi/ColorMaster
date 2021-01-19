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

/**
 * Code-behind de la fenêtre d'acceuil
 */
public class FenetreAcceuil {
    //Récupération des différents éléments de la vue
    /**
     * Bouton ouvrir de la vue
     */
    @FXML
    public Button btnOuvrir;

    /**
     * Textfield reponsable d'afficher le nom du fichier
     */
    @FXML
    public TextField fileName;

    /**
     * Bouton ouvrir de la vue
     */
    @FXML
    public Button btnNouveauDoc;

    /**
     * ListView affichant les fichiers récents
     */
    @FXML
    public ListView<Recent> laListView;

    /**
     * Text affichant le fichier récent sélectionné
     */
    @FXML
    public Text fichierSelected;

    /**
     * Bouton ajouter de la vue
     */
    @FXML
    public Button btnAjouter;

    /**
     * Bouton supprimer
     */
    @FXML
    public Button btnSupprimer;


    /**
     * Méthode permettant la définition du contexte de la fenêtre avant l'ouverture de celle-ci
     */
    public void initialize(){
        //Instanciation du manager pour les fichiers récents
        RecentManager recentManager = new RecentManager();
        fileName.setText("Nouveau document");


        //On bind les items de la list view à ceux de la liste observable du manager
        laListView.itemsProperty().bind(recentManager.lesFichiersProperty());

        //On défini un binding pour afficher le fichier sélectionné dans un label
        laListView.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(oldValue!=null){
                fichierSelected.textProperty().unbind();
            }
            if(newValue != null){
                fichierSelected.textProperty().bind(newValue.fileNameProperty());
            }
        }));

        //On défini la cell Factory qu'elle devra utiliser pour afficher les fichiers récents
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
                      if (laListView.getSelectionModel().getSelectedItem()==null){
                          return;
                      }
                      root = FXMLLoader.load(getClass().getResource("/fxml/FenetrePrincipal.fxml"));
                      Stage stage = new Stage();
                      Recent recent= laListView.getSelectionModel().getSelectedItem();
                      recentManager.del(recent);
                      recentManager.add(recent);
                      stage.setUserData(recentManager);
                      stage.setResizable(false);
                      stage.setScene(new Scene(root, 1220, 800));
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
                    stage.setResizable(false);
                    stage.setScene(new Scene(root, 1200, 800));
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
