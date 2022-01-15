package hashtools.gui.window.about;

import hashtools.core.language.LanguageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * <p>
 * Window that will hold the About's screen.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class AboutWindow extends Application {

    /**
     * <p>
     * Creates an instance of {@link AboutWindow} class. The {@link #start(Stage)}
     * method will be automatically called.
     * </p>
     *
     * @since 1.0.0
     */
    public AboutWindow() {
        Stage stage = new Stage();
        stage.getProperties().put("host.services", getHostServices());

        this.start(stage);
    }


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
                getClass().getResource("About.fxml"),
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

        stage.setTitle(LanguageManager.get("About"));
        stage.show();
    }
}
