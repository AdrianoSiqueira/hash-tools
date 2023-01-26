package hashtools.gui.window.application;

import hashtools.gui.window.AbstractController;
import hashtools.gui.window.about.AboutController;
import javafx.fxml.FXML;
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

/**
 * <p>
 * Application screen controller class.
 * </p>
 *
 * @author Adriano Siqueira
 */
@SuppressWarnings("unused")
public class ApplicationController extends AbstractController {

    private final String buttonHighlightStyleClass = "button-highlight";

    @FXML
    private BorderPane paneRoot;
    @FXML
    private VBox       paneLeft;
    @FXML
    private HBox       paneProgress;
    @FXML
    private GridPane   paneCenter;
    @FXML
    private GridPane   paneAlgorithm;
    @FXML
    private TitledPane paneDetail;

    @FXML
    private MenuBar  menuBar;
    @FXML
    private Menu     menuFile;
    @FXML
    private Menu     menuHelp;
    @FXML
    private MenuItem itemClose;
    @FXML
    private MenuItem itemOnlineManual;
    @FXML
    private MenuItem itemAbout;

    @FXML
    private Button buttonCheck;
    @FXML
    private Button buttonGenerate;
    @FXML
    private Button buttonRun;
    @FXML
    private Button buttonOpenInputFile;
    @FXML
    private Button buttonOpenOfficialFile;
    @FXML
    private Button buttonOpenOutputFile;

    @FXML
    private Label labelInput;
    @FXML
    private Label labelOfficial;
    @FXML
    private Label labelOutput;
    @FXML
    private Label labelAlgorithm;

    @FXML
    private TextField fieldInput;
    @FXML
    private TextField fieldOfficial;
    @FXML
    private TextField fieldOutput;
    @FXML
    private TextArea  areaDetail;

    @FXML
    private CheckBox checkInputFile;
    @FXML
    private CheckBox checkOfficialFile;
    @FXML
    private CheckBox checkMd5;
    @FXML
    private CheckBox checkSha1;
    @FXML
    private CheckBox checkSha224;
    @FXML
    private CheckBox checkSha256;
    @FXML
    private CheckBox checkSha384;
    @FXML
    private CheckBox checkSha512;

    @FXML
    private ProgressBar progressBar;

    private Stage stage;

    public ApplicationController() {
        super.fxmlPath       = "Application.fxml";
        super.stylesheetPath = "Application.css";
    }

    private void close() {
        stage.close();
    }

    private void configureActions() {
        itemAbout.setOnAction(e -> openAboutDialog());
        itemClose.setOnAction(e -> close());
        itemOnlineManual.setOnAction(e -> openOnlineManual());
        buttonCheck.setOnAction(e -> enableCheckMode());
        buttonGenerate.setOnAction(e -> enableGenerateMode());
        buttonRun.setOnAction(e -> run());
    }

    private void configureStage(Stage stage) {
        this.stage = stage;
        stage.setScene(new Scene(paneRoot));
        stage.show();
    }

    private void enableCheckMode() {
        buttonCheck.getStyleClass().add(buttonHighlightStyleClass);
        buttonGenerate.getStyleClass().remove(buttonHighlightStyleClass);
        paneAlgorithm.setDisable(true);
    }

    private void enableGenerateMode() {
        buttonCheck.getStyleClass().remove(buttonHighlightStyleClass);
        buttonGenerate.getStyleClass().add(buttonHighlightStyleClass);
        paneAlgorithm.setDisable(false);
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

    private void openAboutDialog() {
        Stage stage = new Stage();
        stage.getProperties().put("host.services", webService.getHostServices());

        new AboutController().launch(stage);
    }

    private void openOnlineManual() {
        webService.openWebPage("https://github.com/AdrianoSiqueira/hash-tools/wiki");
    }

    private void run() {}
}
