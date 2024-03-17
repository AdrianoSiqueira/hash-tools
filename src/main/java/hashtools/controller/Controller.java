package hashtools.controller;

import hashtools.domain.Environment;
import hashtools.domain.exception.FileNotFoundException;
import hashtools.domain.exception.NullParameterException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;

public interface Controller {

    default Scene createScene(String fxmlPath) {
        evaluate(fxmlPath);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        loader.setResources(Environment.Software.LANGUAGE);

        try {
            Pane pane = loader.load();
            return new Scene(pane);
        } catch (IOException e) {
            throw new RuntimeException("Could not load fxml", e);
        }
    }

    default void evaluate(String path) {
        if (path == null) {
            throw new NullParameterException("'fxmlPath'");
        }

        if (getClass().getResource(path) == null) {
            throw new FileNotFoundException("'%s'".formatted(path));
        }
    }
}
