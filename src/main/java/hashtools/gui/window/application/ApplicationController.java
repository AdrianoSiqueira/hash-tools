package hashtools.gui.window.application;

import aslib.security.SHAType;
import hashtools.core.exception.InvalidUrlException;
import hashtools.core.exception.NoInternetConnectionException;
import hashtools.core.language.LanguageManager;
import hashtools.core.model.Sample;
import hashtools.core.model.SampleList;
import hashtools.core.module.checker.CheckerModule;
import hashtools.core.module.generator.GeneratorModule;
import hashtools.core.service.WebService;
import hashtools.core.supply.Links;
import hashtools.gui.dialog.AlertBuilder;
import hashtools.gui.window.about.AboutWindow;
import hashtools.gui.window.manual.ManualWindow;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * <p>App screen controller class.</p>
 *
 * @author Adriano Siqueira
 * @version 1.0.25
 * @since 2.0.0
 */
public class ApplicationController implements Initializable {

    @FXML private HBox      paneRoot;
    @FXML private VBox      leftPane;
    @FXML private StackPane rightPane;

    @FXML private Button buttonChecker;
    @FXML private Button buttonGenerator;
    @FXML private Button buttonHowToUse;
    @FXML private Button buttonAbout;

    private HostServices getHostServices() {
        return (HostServices) stage.getProperties().get("host.services");
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

        paneGenerateAlgorithms.getChildren()
                              .stream()
                              .filter(CheckBox.class::isInstance)
                              .map(CheckBox.class::cast)
                              .forEach(cb -> cb.setSelected(false));
    }

    @FXML
    private void close() {
        stage.close();
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
        return paneGenerateAlgorithms.getChildren()
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
    private void openAboutWindow() {
        new AboutWindow();
    }

    @FXML
    private void openFileToCheck() {
        fieldCheck.setText("/home/adriano/Documents/settings_idea.zip");
    }

    @FXML
    private void openFileToGenerate() {
        fieldGenerate.setText("/home/adriano/Documents/settings_idea.zip");
    }

    @FXML
    private void openOfficialFile() {
        fieldOfficial.setText("/home/adriano/Documents/hashes.txt");
    }

    @FXML
    private void openOfflineManual() {
        new ManualWindow();
    }

    @FXML
    private void openOnlineManual() {
        try {
            WebService.openWebPage(getHostServices(), Links.APPLICATION_ONLINE_DOCUMENTATION.getUrl());
        } catch (NoInternetConnectionException e) {
            new AlertBuilder()
                    .alertType(Alert.AlertType.WARNING)
                    .title("HashTools")
                    .headerText(LanguageManager.get("Internet.Connection"))
                    .contentText(LanguageManager.get("There.is.no.internet.connection.") + " " + LanguageManager.get("Would.you.like.to.open.the.offline.manual.instead?"))
                    .buttons(ButtonType.YES, ButtonType.NO)
                    .build()
                    .showAndWait()
                    .ifPresent(response -> {
                        if (response.equals(ButtonType.YES)) {
                            openOfflineManual();
                        } else {
                            Logger.getGlobal()
                                  .warning(LanguageManager.get("Cannot.open.online.manual.due.to.offline.status."));
                        }
                    });
        } catch (InvalidUrlException e) {
            new AlertBuilder()
                    .alertType(Alert.AlertType.ERROR)
                    .title("HashTools")
                    .headerText(LanguageManager.get("Invalid.URL"))
                    .contentText(LanguageManager.get("The.given.URL.is.invalid."))
                    .build()
                    .show();
        }
    }

    @FXML
    private void openOutputFile() {
        fieldOutput.setText("/home/adriano/Documents/settings_idea.txt");
    }

    @FXML
    private void runCheckSequence() {
        String fieldCheckText    = fieldCheck.getText();
        String fieldOfficialText = fieldOfficial.getText();

        if (fieldCheckText.isBlank() || fieldOfficialText.isBlank()) return;

        new Thread(() -> {
            setLoadingState(true);

            SampleList list = new CheckerModule(
                    fieldCheckText,
                    fieldOfficialText
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
        String fieldGenerateText = fieldGenerate.getText();
        String fieldOutputText   = fieldOutput.getText();

        if (fieldGenerateText.isBlank() || fieldOutputText.isBlank()) return;

        new Thread(() -> {
            setLoadingState(true);

            List<?> algorithms = createGenerationAlgorithmList();

            new GeneratorModule(
                    fieldGenerateText,
                    fieldOutputText,
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
        if (!paneRootContent.getTabs().contains(tabResult)) return;

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

    private void setStage() {
        this.stage = (Stage) paneRoot.getScene().getWindow();
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

        Platform.runLater(this::setStage);
    }
}
