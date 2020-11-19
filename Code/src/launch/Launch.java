package launch;

import fenetre.FenetrePrincipal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launch extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FenetrePrincipal.fxml"));
        stage.setScene(new Scene(root, 1200,1000));
        stage.setTitle("Color Master");
        stage.show();
    }

}
