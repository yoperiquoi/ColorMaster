package launch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe assurent le lancement de l'application
 */
public class Launch extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FenetreAcceuil.fxml"));
        Scene scene = new Scene(root, 1200,1000);
        scene.getStylesheets().add(getClass().getResource("/css/FenetreAcceuil.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Color Master");
        stage.show();

    }

}
