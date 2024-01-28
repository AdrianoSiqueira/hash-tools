package hashtools.controller;

import javafx.application.Preloader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PreloaderController extends Preloader implements Controller {

    private static final String FXML_PATH = "/hashtools/fxml/preloader.fxml";

    private Stage stage;

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        if (info instanceof CloseNotification) {
            stage.close();
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
