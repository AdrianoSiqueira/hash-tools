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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
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

    private void addUserDataToLeftPaneButtons() {
        buttonChecker.setUserData(getClass().getResource("/hashtools/gui/screen/checker/Checker.fxml"));
        buttonGenerator.setUserData(getClass().getResource("/hashtools/gui/screen/generator/Generator.fxml"));
        buttonHowToUse.setUserData(getClass().getResource("/hashtools/gui/screen/checker/Checker.fxml"));
        buttonAbout.setUserData(getClass().getResource("/hashtools/gui/screen/generator/Generator.fxml"));
    }

    private Node loadFromFxml(URL url) throws IOException {
        return FXMLLoader.load(url, LanguageManager.getBundle());
    }

    private void openModuleScreen(ActionEvent event) {
        if (!(event.getSource() instanceof Button button)) return;

        try {
            Node node = loadFromFxml((URL) button.getUserData());
            rightPane.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String styleClass = "module-button-highlight";
        removeStyleClassFromLeftPane(styleClass);
        button.getStyleClass().add(styleClass);
    }

    private void removeStyleClassFromLeftPane(String styleClass) {
        leftPane.getChildren()
                .forEach(n -> n.getStyleClass().remove(styleClass));
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
