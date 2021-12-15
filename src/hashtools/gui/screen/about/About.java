package hashtools.gui.screen.about;

import aslib.fx.dialog.MessageDialogBuilder;
import hashtools.core.exception.NoInternetConnectionException;
import hashtools.core.language.LanguageManager;
import hashtools.core.service.WebService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * <p>
 * About screen controller class.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.1
 * @since 2.0.0
 */
public class About implements Initializable {

    @FXML private VBox     paneRoot;
    @FXML private GridPane paneContent;

    @FXML private Separator separator;

    @FXML private Label labelTitle;
    @FXML private Label labelVersion;
    @FXML private Label labelVersionNumber;
    @FXML private Label labelApplicationUrl;
    @FXML private Label labelAuthor;
    @FXML private Label labelAuthorName;
    @FXML private Label labelGithubUrl;
    @FXML private Label labelLinkedInUrl;

    @FXML private Hyperlink linkApplicationUrl;
    @FXML private Hyperlink linkGithubUrl;
    @FXML private Hyperlink linkLinkedInUrl;


    /**
     * <p>
     * Sets the action event in all {@link Hyperlink} fields.
     * </p>
     *
     * @since 1.0.0
     */
    private void configureHyperlinks() {
        linkApplicationUrl.setOnAction(new HyperlinkActionHandler());
        linkGithubUrl.setOnAction(new HyperlinkActionHandler());
        linkLinkedInUrl.setOnAction(new HyperlinkActionHandler());
    }

    /**
     * <p>
     * It will open the web page using the {@link WebService}.
     * </p>
     *
     * @param hyperlink Used to get the url.
     *
     * @since 1.0.0
     */
    private void openHyperlink(Hyperlink hyperlink) {
        try {
            new WebService().openWebPage(hyperlink);
        } catch (NoInternetConnectionException e) {
            new MessageDialogBuilder()
                    .setAlertType(Alert.AlertType.ERROR)
                    .setTitle("HashTools")
                    .setHeaderText(LanguageManager.get("Internet.Connection"))
                    .setContentText(LanguageManager.get("There.is.no.internet.connection."))
                    .setButtons(ButtonType.OK)
                    .build()
                    .show();

            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureHyperlinks();
    }


    /**
     * <p>
     * It will call {@link #openHyperlink} method providing the source node of
     * the event as the argument.
     * </p>
     *
     * @since 1.0.0
     */
    private class HyperlinkActionHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() instanceof Hyperlink hyperlink) {
                openHyperlink(hyperlink);
            }
        }
    }
}
