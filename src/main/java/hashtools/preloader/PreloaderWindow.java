package hashtools.preloader;

import javafx.application.Preloader;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static hashtools.shared.Resource.ApplicationDimension.HEIGHT;
import static hashtools.shared.Resource.ApplicationDimension.WIDTH;
import static hashtools.shared.Resource.FXMLPath.PRELOADER_SCREEN;
import static hashtools.shared.Resource.ResourceBundle.LANGUAGE;

@Slf4j
public class PreloaderWindow extends Preloader {

    private Stage stage;

    private Scene createScene()
    throws IOException {
        URL fxmlPath = Optional
            .ofNullable(getClass().getResource(PRELOADER_SCREEN))
            .orElseThrow(() -> new FileNotFoundException(PRELOADER_SCREEN));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlPath);
        loader.setResources(ResourceBundle.getBundle(LANGUAGE));

        /*
         * Namespace is used to define constants that
         * can be accessed in the fxml file this loader
         * is working with.
         */
        ObservableMap<String, Object> namespace = loader.getNamespace();
        namespace.put("applicationWidth", WIDTH);
        namespace.put("applicationHeight", HEIGHT);

        Pane pane = loader.load();
        return new Scene(pane);
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification notification) {
        if (notification instanceof ClosePreloaderNotification) {
            stage.close();
        }
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        try {
            stage.setScene(createScene());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            log.error("Failed to create scene", e);
        }
    }

    public static final class ClosePreloaderNotification implements PreloaderNotification {}
}
