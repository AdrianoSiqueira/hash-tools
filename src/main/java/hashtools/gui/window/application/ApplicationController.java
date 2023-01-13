package hashtools.gui.window.application;

import hashtools.core.language.LanguageManager;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * <p>
 * Application screen controller class.
 * </p>
 *
 * @author Adriano Siqueira
 */
public class ApplicationController implements Controller {

    private static final String FXML_PATH       = "Application.fxml";
    private static final String STYLESHEET_PATH = "Application.css";

    private final String buttonHighlightStyleClass = "button-highlight";

    @FXML private BorderPane paneRoot;
    @FXML private VBox       paneLeft;
    @FXML private HBox       paneProgress;
    @FXML private GridPane   paneCenter;
    @FXML private GridPane   paneAlgorithm;
    @FXML private TitledPane paneDetail;

    @FXML private MenuBar  menuBar;
    @FXML private Menu     menuFile;
    @FXML private Menu     menuHelp;
    @FXML private MenuItem itemClose;
    @FXML private MenuItem itemOnlineManual;
    @FXML private MenuItem itemAbout;

    @FXML private Button buttonCheck;
    @FXML private Button buttonGenerate;
    @FXML private Button buttonRun;
    @FXML private Button buttonOpenInputFile;
    @FXML private Button buttonOpenOfficialFile;

    @FXML private Label labelInput;
    @FXML private Label labelOfficial;
    @FXML private Label labelAlgorithm;

    @FXML private TextField fieldInput;
    @FXML private TextField fieldOfficial;
    @FXML private TextArea  areaDetail;

    @FXML private CheckBox checkInputFile;
    @FXML private CheckBox checkOfficialFile;
    @FXML private CheckBox checkMd5;
    @FXML private CheckBox checkSha1;
    @FXML private CheckBox checkSha224;
    @FXML private CheckBox checkSha256;
    @FXML private CheckBox checkSha384;
    @FXML private CheckBox checkSha512;

    @FXML private ProgressBar progressBar;

    private Stage  stage;
    private Logger logger;

    @Override
    public void start(Stage stage) {
        loadLoggingFeature();
        loadFxml();
        configureActions();

        this.stage = stage;
        stage.setScene(new Scene(paneRoot));
        stage.show();
    }

    private void close() {
        stage.close();
    }

    private void configureActions() {
        itemAbout.setOnAction(e -> openAboutDialog());
        itemClose.setOnAction(e -> close());
        itemOnlineManual.setOnAction(e -> openOnlineManual());
    }

    private void enableCheckMode() {
        buttonCheck.getStyleClass().add(buttonHighlightStyleClass);
        buttonGenerate.getStyleClass().remove(buttonHighlightStyleClass);
    }

    private void enableGenerateMode() {
        buttonCheck.getStyleClass().remove(buttonHighlightStyleClass);
        buttonGenerate.getStyleClass().add(buttonHighlightStyleClass);
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(FXML_PATH));
        loader.setController(this);
        loader.setResources(LanguageManager.getBundle());

        try {
            Parent root = loader.load();

            loadStylesheet().ifPresent(root.getStylesheets()::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLoggingFeature() {
        logger = LoggerFactory.getLogger(getClass());
    }

    private Optional<String> loadStylesheet() {
        return Optional.ofNullable(getClass().getResource(STYLESHEET_PATH))
                       .map(URL::toString);
    }

    private void openAboutDialog() {
        logger.info("In the future the about dialog will open.");
    }

    private void openOnlineManual() {
        HostServices hostServices = (HostServices) stage.getProperties().get("host.services");
        hostServices.showDocument("https://github.com/AdrianoSiqueira/hash-tools/wiki");
    }
}
