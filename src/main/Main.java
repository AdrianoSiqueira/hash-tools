package main;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import language.LanguageManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    private static Stage        stage;
    private static HostServices hostServices;

    public static void main(String[] args) {
        launch(args);
    }

    public static HostServices getHostService() {
        return hostServices;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void resize(int delay, boolean center) {
        if (stage.isMaximized()) return;

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                stage.sizeToScene();
                if (center) stage.centerOnScreen();
            });
        }).start();
    }

    public void start(Stage stage)
    throws IOException {
        Main.stage        = stage;
        Main.hostServices = this.getHostServices();

        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource("/gui/AppUI.fxml"),
                LanguageManager.getBundle()
        );

        Parent root = loader.load();
        root.getStylesheets().add("/css/AppUI.css");

        stage.setTitle(LanguageManager.get("HashTools"));
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/icon/sha256.png"));
        stage.show();
    }
}