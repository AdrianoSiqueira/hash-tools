package controller;

import aslib.fx.util.MessageDialog;
import aslib.net.ConnectionStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import language.LanguageManager;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AboutUI implements Initializable {

    private final Logger logger = Logger.getGlobal();

    @FXML
    public void openLink(ActionEvent event) {
        if (event.getSource() instanceof Hyperlink) {
            Hyperlink link = (Hyperlink) event.getSource();
            link.setCursor(Cursor.WAIT);

            if (ConnectionStatus.checkConnection().equals(ConnectionStatus.ONLINE)) {
                Main.getHostService()
                    .showDocument(link.getText());

                new Thread(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        link.setCursor(Cursor.HAND);
                    }
                }).start();
            } else {
                logger.log(Level.WARNING,
                           LanguageManager.get("No.internet.connection."));

                new MessageDialog.Builder()
                        .alertType(Alert.AlertType.WARNING)
                        .title(LanguageManager.get("About.HashTools"))
                        .header(LanguageManager.get("Internet.connection"))
                        .content(LanguageManager.get("No.internet.connection."))
                        .create()
                        .showAndWait();

                link.setCursor(Cursor.HAND);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}