package hashtools.applicationmodule.application;

import hashtools.coremodule.Resource;
import hashtools.applicationmodule.preloader.PreloaderWindow;
import javafx.application.Application;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static hashtools.coremodule.Resource.ApplicationDimension.HEIGHT;
import static hashtools.coremodule.Resource.ApplicationDimension.WIDTH;
import static hashtools.coremodule.Resource.FXMLPath.APPLICATION_SCREEN;
import static hashtools.coremodule.Resource.ImagePath.FAV_ICON;

@Slf4j
public class ApplicationWindow extends Application {

    private Scene createScene()
    throws IOException {
        URL fxmlPath = Optional
            .ofNullable(getClass().getResource(APPLICATION_SCREEN))
            .orElseThrow(() -> new FileNotFoundException(APPLICATION_SCREEN));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlPath);
        loader.setResources(ResourceBundle.getBundle(Resource.Language.APPLICATION));

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

    private Image loadFavIcon()
    throws FileNotFoundException {
        return Optional
            .ofNullable(getClass().getResourceAsStream(FAV_ICON))
            .map(Image::new)
            .orElseThrow(() -> new FileNotFoundException(FAV_ICON));
    }

    @Override
    public void start(Stage stage) {
        try {
            stage.getIcons().add(loadFavIcon());
        } catch (FileNotFoundException e) {
            log.error("Failed to load fav icon", e);
        }

        try {
            stage.setScene(createScene());
            stage.setTitle("HashTools");
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            log.error("Failed to create scene", e);
        }

        notifyPreloader(new PreloaderWindow.ClosePreloaderNotification());
    }
}
