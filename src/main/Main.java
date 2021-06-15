package main;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import language.LanguageManager;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Main
        extends Application {

    private static Stage        stage;
    private static HostServices hostServices;


    public static void main(String[] args) {
        if (args.length == 0) {
            // Run GUI mode
            launch();
        } else if (args[0].equalsIgnoreCase("--help")) {
            // Show CLI help content
            throw new UnsupportedOperationException(LanguageManager.get("Resource.not.implemented.yet."));
        } else if (args[0].equalsIgnoreCase("--check")) {
            // Run check sequence.
            throw new UnsupportedOperationException(LanguageManager.get("Resource.not.implemented.yet."));
        } else if (args[0].equalsIgnoreCase("--generate")) {
            // Run generation sequence.
            throw new UnsupportedOperationException(LanguageManager.get("Resource.not.implemented.yet."));
        }
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


    @Override
    public void start(Stage stage)
    throws IOException {
        Class<Main> thisClass = Main.class;

        Main.stage        = stage;
        Main.hostServices = this.getHostServices();

        FXMLLoader loader = new FXMLLoader(
                thisClass.getResource("/gui/AppUI.fxml"),
                LanguageManager.getBundle()
        );

        Parent root = loader.load();
        root.getStylesheets()
            .add(Objects.requireNonNull(thisClass.getResource("/css/AppUI.css"))
                        .toString());

        stage.setTitle(LanguageManager.get("HashTools"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}