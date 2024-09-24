package hashtools.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationWindow extends Application {

    private static final String FXML_PATH = "/hashtools/fxml/main-screen.fxml";

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(FXML_PATH));
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
