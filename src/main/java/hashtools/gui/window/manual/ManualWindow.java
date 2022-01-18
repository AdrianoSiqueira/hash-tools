package hashtools.gui.window.manual;

import hashtools.core.language.LanguageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * <p>
 * Window that will hold the manual screen.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class ManualWindow extends Application {

    /**
     * <p>
     * Creates the scene for this window.
     * </p>
     *
     * @return A scene with the content from the fxml file.
     *
     * @throws IOException If FXMLLoader fails to load.
     * @since 1.0.0
     */
    private Scene createScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("Manual.fxml"),
                LanguageManager.getBundle()
        );

        return new Scene(loader.load());
    }


    @Override
    public void start(Stage stage) {
        try {
            stage.setScene(createScene());
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle(LanguageManager.get("Manual"));
        stage.show();
    }
}
