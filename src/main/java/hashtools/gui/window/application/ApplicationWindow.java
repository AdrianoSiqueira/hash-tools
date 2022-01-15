package hashtools.gui.window.application;

import hashtools.core.language.LanguageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * <p>
 * Window that will hold the main application's screen.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.2
 * @since 2.0.0
 */
public class ApplicationWindow extends Application {

    /**
     * <p>
     * Creates the scene for this window.
     * </p>
     *
     * @return A scene with the content from the fxml file.
     *
     * @since 1.0.0
     */
    private Scene createScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("Application.fxml"),
                LanguageManager.getBundle()
        );

        return new Scene(loader.load());
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.getProperties().put("host.services", getHostServices());
        stage.setTitle("HashTools");
        stage.setScene(createScene());
        stage.show();
    }
}
