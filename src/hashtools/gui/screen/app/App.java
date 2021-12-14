package hashtools.gui.screen.app;

import aslib.fx.dialog.MessageDialogBuilder;
import aslib.net.ConnectionChecker;
import aslib.net.ConnectionStatus;
import aslib.security.SHAType;
import hashtools.core.language.LanguageManager;
import hashtools.core.model.Sample;
import hashtools.core.model.SampleList;
import hashtools.core.module.checker.CheckerModule;
import hashtools.core.module.generator.GeneratorModule;
import hashtools.core.supply.Links;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * <p>App screen controller class.</p>
 *
 * @author Adriano Siqueira
 * @version 1.0.6
 * @since 2.0.0
 */
public class App implements Initializable {

    @FXML private BorderPane paneRoot;
    @FXML private TabPane    paneRootContent;
    @FXML private GridPane   paneCheckContent;
    @FXML private GridPane   paneGenerateContent;
    @FXML private GridPane   paneGenerateAlgorithms;
    @FXML private VBox       paneResultContent;

    @FXML private MenuBar menuBar;

    @FXML private Menu menuFile;
    @FXML private Menu menuSettings;
    @FXML private Menu menuHelp;
    @FXML private Menu menuManual;

    @FXML private MenuItem itemClose;
    @FXML private MenuItem itemOnline;
    @FXML private MenuItem itemOffline;
    @FXML private MenuItem itemAbout;

    @FXML private Tab tabCheck;
    @FXML private Tab tabGenerate;
    @FXML private Tab tabResult;

    @FXML private Label labelCheckInput;
    @FXML private Label labelOfficial;
    @FXML private Label labelGenerateInput;
    @FXML private Label labelOutput;

    @FXML private TextField fieldCheck;
    @FXML private TextField fieldOfficial;
    @FXML private TextField fieldGenerate;
    @FXML private TextField fieldOutput;

    @FXML private CheckBox checkMD5;
    @FXML private CheckBox checkSHA1;
    @FXML private CheckBox checkSHA224;
    @FXML private CheckBox checkSHA256;
    @FXML private CheckBox checkSHA384;
    @FXML private CheckBox checkSHA512;
    private       Group    groupAlgorithmCheckBox;

    @FXML private Button buttonCheck;
    @FXML private Button buttonOpenInputFileCheck;
    @FXML private Button buttonOpenOfficial;
    @FXML private Button buttonGenerate;
    @FXML private Button buttonOpenInputFileGenerate;
    @FXML private Button buttonOpenOutputFile;

    @FXML private TableView<Sample>           tableResult;
    @FXML private TableColumn<Sample, String> columnAlgorithm;
    @FXML private TableColumn<Sample, String> columnOfficialHash;
    @FXML private TableColumn<Sample, String> columnCalculatedHash;
    @FXML private TableColumn<Sample, String> columnResult;

    @FXML private ProgressBar progressBar;

    // Provided from parent caller
    private HostServices hostServices;
    private Stage        stage;


    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    private void calculateTabPaneHeaderWidth() {
        double width = paneRootContent.getWidth()
                       /
                       paneRootContent.getTabs()
                                      .size()
                       - 20;

        paneRootContent.setTabMinWidth(width);
        paneRootContent.setTabMaxWidth(width);
    }

    private void clearCheckTab() {
        fieldCheck.clear();
        fieldOfficial.clear();
    }

    private void clearGenerateTab() {
        fieldGenerate.clear();
        fieldOutput.clear();

        groupAlgorithmCheckBox.getChildren()
                              .stream()
                              .map(CheckBox.class::cast)
                              .forEach(cb -> cb.setSelected(false));
    }

    @FXML
    private void close() {
        stage.close();
    }

    private void configureAlgorithmCheckBoxGroup() {
        groupAlgorithmCheckBox.getChildren()
                              .addAll(checkMD5, checkSHA1, checkSHA224,
                                      checkSHA256, checkSHA384, checkSHA512);
    }

    private void configureAlgorithmsCheckBoxes() {
        checkMD5.setUserData(SHAType.MD5);
        checkSHA1.setUserData(SHAType.SHA1);
        checkSHA224.setUserData(SHAType.SHA224);
        checkSHA256.setUserData(SHAType.SHA256);
        checkSHA384.setUserData(SHAType.SHA384);
        checkSHA512.setUserData(SHAType.SHA512);
    }

    private void configureTabPaneTabWidth() {
        paneRootContent.widthProperty()
                       .addListener((observable, oldValue, newValue) -> calculateTabPaneHeaderWidth());

        paneRootContent.getTabs()
                       .addListener((ListChangeListener<Tab>) c -> calculateTabPaneHeaderWidth());
    }

    private void configureTableColumns() {
        columnAlgorithm.setSortType(TableColumn.SortType.ASCENDING);
        columnAlgorithm.minWidthProperty()
                       .bind(tableResult.widthProperty().multiply(0.146));
        columnAlgorithm.setCellValueFactory(param -> new SimpleStringProperty(
                param.getValue()
                     .getAlgorithm()
                     .getName()
        ));

        columnOfficialHash.setSortable(false);
        columnOfficialHash.minWidthProperty()
                          .bind(tableResult.widthProperty().multiply(0.35));
        columnOfficialHash.setCellValueFactory(param -> new SimpleStringProperty(
                param.getValue()
                     .getOfficialHash()
        ));

        columnCalculatedHash.setSortable(false);
        columnCalculatedHash.minWidthProperty()
                            .bind(tableResult.widthProperty().multiply(0.35));
        columnCalculatedHash.setCellValueFactory(param -> new SimpleStringProperty(
                param.getValue()
                     .getCalculatedHash()
        ));

        columnResult.minWidthProperty()
                    .bind(tableResult.widthProperty().multiply(0.146));
        columnResult.setCellValueFactory(param -> new SimpleStringProperty(
                param.getValue()
                     .getResult()
                     .getText()
        ));


        tableResult.getSortOrder()
                   .add(columnAlgorithm);
    }

    private List<SHAType> createGenerationAlgorithmList() {
        return groupAlgorithmCheckBox.getChildren()
                                     .stream()
                                     .filter(CheckBox.class::isInstance)
                                     .map(CheckBox.class::cast)
                                     .filter(CheckBox::isSelected)
                                     .map(Node::getUserData)
                                     .filter(SHAType.class::isInstance)
                                     .map(SHAType.class::cast)
                                     .collect(Collectors.toList());
    }

    @FXML
    private void openCheckFile() {
        fieldCheck.setText("/home/adriano/Documents/settings_idea.zip");
    }

    @FXML
    private void openGenerateFile() {
        fieldGenerate.setText("/home/adriano/Documents/settings_idea.zip");
    }

    @FXML
    private void openOfficialFile() {
        fieldOfficial.setText("/home/adriano/Documents/hashes.txt");
    }

    @FXML
    private void openOutputFile() {
        fieldOutput.setText("/home/adriano/Documents/settings_idea.txt");
    }

    @FXML
    private void runCheckSequence() {
        // TODO Check if fields are empty before continuing
        new Thread(() -> {
            setLoadingState(true);

            SampleList list = new CheckerModule(
                    fieldCheck.getText(),
                    fieldOfficial.getText()
            ).call();

            // GUI changes must be made in a JavaFX thread.
            Platform.runLater(() -> {
                updateResultTab(list);
                setLoadingState(false);
                setResultTabVisible(true);
                selectResultTab();
                clearCheckTab();
            });
        }).start();
    }

    @FXML
    private void runGenerateSequence() {
        // TODO Check if fields are empty before continuing
        new Thread(() -> {
            setLoadingState(true);

            List<?> algorithms = createGenerationAlgorithmList();

            new GeneratorModule(
                    fieldCheck.getText(),
                    fieldOfficial.getText(),
                    algorithms
            ).call();

            // GUI changes must be made in a JavaFX thread.
            Platform.runLater(() -> {
                setLoadingState(false);
                clearGenerateTab();
            });
        }).start();
    }

    private void selectResultTab() {
        // TODO Check if tabResult is present before selecting it
        paneRootContent.getSelectionModel()
                       .select(tabResult);
    }

    private void setLoadingState(boolean loadingState) {
        paneRootContent.setCursor(loadingState ? Cursor.WAIT : Cursor.DEFAULT);

        /*
         * Disabled components cannot have custom cursor. Because that, it is
         * necessary to disable its children instead.
         */
        paneRootContent.getChildrenUnmodifiable()
                       .forEach(node -> node.setDisable(loadingState));
    }

    private void setResultTabVisible(boolean visible) {
        ObservableList<Tab> tabs = paneRootContent.getTabs();

        if (!visible) {
            tabs.remove(tabResult);
        } else if (!tabs.contains(tabResult)) {
            tabs.add(tabResult);
        }
    }

    @FXML
    private void showAboutInfo() {
        Logger.getGlobal()
              .info("Showing info about the application.");
    }

    @FXML
    private void showOfflineManual() {
        Logger.getGlobal()
              .info("Showing offline manual.");
    }

    @FXML
    private void showOnlineManual() {
        if (ConnectionChecker.check().equals(ConnectionStatus.ONLINE)) {
            Optional.ofNullable(hostServices)
                    .ifPresent(services -> services.showDocument(Links.ONLINE_DOCUMENTATION.getUrl()));
        } else {
            new MessageDialogBuilder()
                    .setAlertType(Alert.AlertType.WARNING)
                    .setHeaderText(LanguageManager.get("Internet.Connection"))
                    .setContentText(LanguageManager.get("There.is.no.internet.connection..Would.you.like.to.open.offline.manual.instead?"))
                    .setButtons(ButtonType.YES, ButtonType.NO)
                    .build()
                    .showAndWait()
                    .ifPresent(response -> {
                        if (response.equals(ButtonType.YES)) {
                            showOfflineManual();
                        } else {
                            Logger.getGlobal()
                                  .warning("Cannot open online manual due to offline status.");
                        }
                    });
        }
    }

    private void updateResultTab(SampleList list) {
        tableResult.getItems()
                   .setAll(list.getSamples());
        tableResult.sort();

        progressBar.setProgress(list.getReliabilityPercentage() / 100);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTabPaneTabWidth();
        setResultTabVisible(false);
        configureTableColumns();
        configureAlgorithmsCheckBoxes();
        configureAlgorithmCheckBoxGroup();

//        openCheckFile();
//        openOfficialFile();
//        runCheckSequence();
    }
}