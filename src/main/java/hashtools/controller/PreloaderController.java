package hashtools.controller;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static javafx.application.Preloader.StateChangeNotification.Type.BEFORE_START;

@Slf4j
public class PreloaderController extends Preloader implements Controller {

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

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(createScene(FXML_PATH));
        stage.centerOnScreen();
        stage.show();
    }


    public static final class CloseNotification implements PreloaderNotification {
    }
}
