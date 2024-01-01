package hashtools.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static javafx.application.Preloader.StateChangeNotification.Type.BEFORE_START;

@Slf4j
public class Preloader extends javafx.application.Preloader {

    private static final String FXML_PATH = "/hashtools/fxml/preloader.fxml";

    private Stage stage;

    private void closeAfterDelay() {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                Platform.runLater(stage::close);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification notification) {
        if (notification.getType() == BEFORE_START) {
            /*
             * This block will be executed before the application's
             * 'start' method is called, so we added a delay before
             * closing preload to give the main window time to load.
             */
            closeAfterDelay();
        }
    }

    private Pane loadFxml(URL url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);

        try {
            return loader.load();
        } catch (IOException e) {
            log.error("Could not load fxml: '" + url + "'", e);
            return null;
        }
    }

    @Override
    public void start(Stage stage) {
        /*
         * Attempts to create a scene loading the fxml
         * file. If it fails the scene will be blank.
         */
        Optional.of(getClass())
                .map(clazz -> clazz.getResource(FXML_PATH))
                .map(this::loadFxml)
                .map(Scene::new)
                .ifPresent(stage::setScene);

        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.show();
    }
}
