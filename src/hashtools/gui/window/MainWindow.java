package hashtools.gui.window;

import aslib.fx.dialog.StackTraceDialogBuilder;
import hashtools.core.service.WebService;
import hashtools.gui.screen.app.App;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * <p>
 * Application main window. It contains the Stage and HostServices used by the
 * other modules.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.1.1
 * @since 2.0.0
 */
public class MainWindow extends Application {

    /**
     * <p>
     * Creates a instance of the {@link MainWindow} class.
     * </p>
     *
     * @since 1.0.0
     */
    public MainWindow() {
        this.start(new Stage());
    }


    /**
     * <p>
     * This will allow the controller to close the stage and to open a website.
     * </p>
     *
     * @param stage  Will be passed to controller.
     * @param loader Will be used to get the controller.
     *
     * @since 1.0.0
     */
    private void configureController(Stage stage, FXMLLoader loader) {
        App controller = loader.getController();
        controller.setStage(stage);
    }

    /**
     * <p>
     * Configures the stage setting its attributes and listeners.
     * </p>
     *
     * @param stage Stage that will be configured.
     * @param root  Root node used to create the scene.
     *
     * @since 1.0.0
     */
    private void configureStage(Stage stage, Parent root) {
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        stage.setTitle("Hash Tools");
        stage.setScene(new Scene(root));
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * <p>
     * Configures the WebService. It is used along the application to open web
     * pages.
     * </p>
     */
    private void configureWebService() {
        WebService.setHostServices(this.getHostServices());
    }


    @Override
    public void start(Stage stage) {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/hashtools/gui/screen/app/App.fxml"),
                ResourceBundle.getBundle("hashtools.core.language.Language")
        );

        try {
            // Must be called before the configureController method
            Parent root = loader.load();

            configureController(stage, loader);
            configureWebService();
            configureStage(stage, root);
        } catch (IOException e) {
            new StackTraceDialogBuilder()
                    .setAlertType(Alert.AlertType.ERROR)
                    .setTitle(this.getClass().getSimpleName())
                    .setHeaderText("FXMLLoader failed to load")
                    .setThrowable(e)
                    .build()
                    .show();

            e.printStackTrace();
        }
    }
}
