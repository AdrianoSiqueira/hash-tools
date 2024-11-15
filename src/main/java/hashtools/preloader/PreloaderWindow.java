package hashtools.preloader;

import hashtools.shared.Resource;
import javafx.application.Preloader;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ResourceBundle;

import static hashtools.shared.Resource.ApplicationDimension.HEIGHT;
import static hashtools.shared.Resource.ApplicationDimension.WIDTH;

public class PreloaderWindow extends Preloader {

    private Stage stage;

    @Override
    public void handleApplicationNotification(PreloaderNotification notification) {
        if (notification instanceof ClosePreloaderNotification) {
            stage.close();
        }
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Resource.FXMLPath.PRELOADER_SCREEN));
            loader.setResources(ResourceBundle.getBundle(Resource.ResourceBundle.LANGUAGE));

            /*
             * Namespace is used to define constants that
             * can be accessed in the fxml file this loader
             * is working with.
             */
            ObservableMap<String, Object> namespace = loader.getNamespace();
            namespace.put("applicationWidth", WIDTH);
            namespace.put("applicationHeight", HEIGHT);

            Pane pane = loader.load();
            stage.setScene(new Scene(pane));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load fxml file", e);
        }

        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }

    public static final class ClosePreloaderNotification implements PreloaderNotification {}
}
