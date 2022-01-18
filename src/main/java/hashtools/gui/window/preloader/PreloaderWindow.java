package hashtools.gui.window.preloader;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Window that will hold the preloader screen.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.1.0
 * @since 2.0.0
 */
public class PreloaderWindow extends Preloader {

    private Stage stage;


    private void closeAfterDelay() {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ignored) {}

            Platform.runLater(stage::close);
        }).start();
    }

    @SuppressWarnings("ConstantConditions")
    private Scene createScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Preloader.fxml"));

        return new Scene(root);
    }

    @SuppressWarnings("ConstantConditions")
    private Image loadAppIcon() {
        return new Image(getClass().getResourceAsStream("/hashtools/gui/image/application-icon.png"));
    }


    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        if (info.getType() == StateChangeNotification.Type.BEFORE_START) closeAfterDelay();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(createScene());
        stage.getIcons().add(loadAppIcon());
        stage.show();
    }
}