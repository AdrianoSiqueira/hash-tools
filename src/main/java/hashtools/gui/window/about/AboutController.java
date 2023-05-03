package hashtools.gui.window.about;

import hashtools.core.service.LanguageService;
import hashtools.gui.window.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * <p>
 * Controller class for the 'About' window.
 * </p>
 */
@SuppressWarnings("unused")
public class AboutController extends AbstractController {

    @FXML
    private GridPane paneRoot;

    @FXML
    private Label labelAuthor;
    @FXML
    private Label labelHeaderAppUrl;
    @FXML
    private Label labelHeaderAuthor;
    @FXML
    private Label labelHeaderAuthorUrl;
    @FXML
    private Label labelHeaderManualUrl;
    @FXML
    private Label labelHeaderVersion;
    @FXML
    private Label labelVersion;

    @FXML
    private Hyperlink hyperlinkAuthorUrl;
    @FXML
    private Hyperlink hyperlinkManualUrl;
    @FXML
    private Hyperlink hyperlinkAppUrl;

    @FXML
    private Separator separator;

    public AboutController() {
        super.fxmlPath        = "About.fxml";
        super.stylesheetPath  = "About.css";
        super.languageService = new LanguageService();
    }

    private void configureActions() {
        hyperlinkAuthorUrl.setOnAction(e -> openUrl(hyperlinkAuthorUrl));
        hyperlinkManualUrl.setOnAction(e -> openUrl(hyperlinkManualUrl));
        hyperlinkAppUrl.setOnAction(e -> openUrl(hyperlinkAppUrl));
    }

    private void configureStage(Stage stage) {
        stage.setScene(new Scene(paneRoot));
        stage.show();
    }

    @Override
    public void launch(Stage stage) {
        loadLogger(this);
        loadFxml(this);
        loadFavIcon(stage, "/hashtools/gui/image/application-icon.png");
        loadWebService(stage);

        configureActions();
        configureStage(stage);
    }

    private void openUrl(Hyperlink hyperlink) {
        webService.openWebPage(hyperlink.getText());
    }
}
