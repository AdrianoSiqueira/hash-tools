package hashtools.window;

import hashtools.shared.Resource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class ApplicationWindow extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Resource.FXMLPath.MAIN_SCREEN));
            loader.setResources(ResourceBundle.getBundle(Resource.ResourceBundle.LANGUAGE));
            Pane pane = loader.load();

            stage.setScene(new Scene(pane));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load the fxml file.", e);
        }

        stage.setTitle("HashTools");
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }
}
