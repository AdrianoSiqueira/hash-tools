package hashtools.dialog;

import hashtools.domain.Environment;
import hashtools.domain.exception.FileNotFoundException;
import hashtools.domain.exception.FxmlLoadException;
import hashtools.domain.exception.NullParameterException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DialogPane;

import java.io.IOException;

public interface DialogController {

    default DialogPane createDialogPane(String fxmlPath, DialogController controller) {
        evaluate(fxmlPath);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        loader.setResources(Environment.Software.LANGUAGE);
        loader.setController(controller);

        try {
            return loader.load();
        } catch (IOException e) {
            throw new FxmlLoadException(e);
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
