package hashtools.application;

import hashtools.preloader.PreloaderWindow;
import hashtools.shared.Resource;
import javafx.application.Application;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

import static hashtools.shared.Resource.ApplicationDimension.HEIGHT;
import static hashtools.shared.Resource.ApplicationDimension.WIDTH;
import static hashtools.shared.Resource.ImagePath.FAV_ICON;

@Slf4j
public class ApplicationWindow extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Resource.FXMLPath.APPLICATION_SCREEN));
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
            throw new RuntimeException("Failed to load the fxml file.", e);
        }

        Optional
            .ofNullable(getClass().getResourceAsStream(FAV_ICON))
            .map(Image::new)
            .ifPresentOrElse(
                stage.getIcons()::add,
                () -> log.error("Failed to load fav icon")
            );

        stage.setTitle("HashTools");
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();

        notifyPreloader(new PreloaderWindow.ClosePreloaderNotification());
    }
}
