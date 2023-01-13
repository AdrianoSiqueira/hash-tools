package hashtools.gui.window.application;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * <p>
 * Window that will hold the main application's screen.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.1.0
 * @since 2.0.0
 */
public class ApplicationWindow extends Application {

    @Override
    public void start(Stage stage) {
        stage.getProperties().put("host.services", getHostServices());
        new ApplicationController().start(stage);
    }
}
