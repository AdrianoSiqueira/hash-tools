package hashtools.controller;

import hashtools.utility.FileManager;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Slf4j
public class ApplicationController extends Application implements Initializable, Controller {

    private static final String TITLE     = "HashTools";
    private static final String FXML_PATH = "/hashtools/fxml/application.fxml";

    @FXML
    private GridPane  paneRoot;
    @FXML
    private HBox      paneRunMode;
    @FXML
    private GridPane  paneAlgorithm;
    @FXML
    private StackPane paneRun;

    @FXML
    private ToggleGroup  groupRunMode;
    @FXML
    private ToggleButton buttonCheck;
    @FXML
    private ToggleButton buttonCompare;
    @FXML
    private ToggleButton buttonGenerate;

    @FXML
    private Button buttonRun;

    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label labelAlgorithm;

    @FXML
    private TextField field1;
    @FXML
    private TextField field2;
    @FXML
    private TextArea  areaStatus;

    @FXML
    private CheckBox checkUseFile1;
    @FXML
    private CheckBox checkUseFile2;
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

    private void enableCheckerMode() {
        buttonCheck.setSelected(true);

        checkUseFile1.setSelected(false);
        checkUseFile1.setDisable(false);

        checkUseFile2.setSelected(false);
        checkUseFile2.setDisable(false);

        // Algorithm selection is automatic
        paneAlgorithm.setDisable(true);

        field1.clear();
        field1.setDisable(false);

        field2.clear();
        field2.setDisable(false);

        areaStatus.clear();

        setFileOpeningHandler();
    }

    private void enableComparatorMode() {
        buttonCompare.setSelected(true);

        checkUseFile1.setSelected(true);
        checkUseFile1.setDisable(true);

        checkUseFile2.setSelected(true);
        checkUseFile2.setDisable(true);

        // Algorithm selection is automatic
        paneAlgorithm.setDisable(true);

        field1.clear();
        field1.setDisable(false);

        field2.clear();
        field2.setDisable(false);

        areaStatus.clear();

        setFileOpeningHandler();
    }

    private void enableDragAndDrop(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
    }

    private void enableGeneratorMode() {
        buttonGenerate.setSelected(true);

        checkUseFile1.setSelected(false);
        checkUseFile1.setDisable(false);

        checkUseFile2.setSelected(false);
        checkUseFile2.setDisable(true);

        // Algorithm selection is manual
        paneAlgorithm.setDisable(false);

        field1.clear();
        field1.setDisable(false);

        field2.clear();
        field2.setDisable(true);

        areaStatus.clear();

        setFileOpeningHandler();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        enableCheckerMode();

        buttonCheck.setOnAction(event -> enableCheckerMode());
        buttonGenerate.setOnAction(event -> enableGeneratorMode());
        buttonCompare.setOnAction(event -> enableComparatorMode());

        groupRunMode.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                oldValue.setSelected(true);
            }
        });

        checkUseFile1.setOnAction(event -> setFileOpeningHandler());
        checkUseFile2.setOnAction(event -> setFileOpeningHandler());

        field1.setOnContextMenuRequested(Event::consume);
        field1.editableProperty().bind(checkUseFile1.selectedProperty().not());
        field1.setOnDragOver(this::enableDragAndDrop);
        field1.setOnDragDropped(event -> processDragAndDrop(
            event.getDragboard(),
            field1,
            checkUseFile1
        ));

        field2.setOnContextMenuRequested(Event::consume);
        field2.editableProperty().bind(checkUseFile2.selectedProperty().not());
        field2.setOnDragOver(this::enableDragAndDrop);
        field2.setOnDragDropped(event -> processDragAndDrop(
            event.getDragboard(),
            field2,
            checkUseFile2
        ));

        areaStatus.setOnContextMenuRequested(Event::consume);
    }

    private Optional<String> openFile(MouseButton button, String title, FileChooser.ExtensionFilter... extensionFilters) {
        if (button != MouseButton.SECONDARY) {
            return Optional.empty();
        }

        return new FileManager()
            .openFile(title, extensionFilters)
            .map(Path::toAbsolutePath)
            .map(Path::toString);
    }

    private void openFileToCheck(MouseEvent event) {
        openFile(
            event.getButton(),
            "Select a file to check",
            new FileChooser.ExtensionFilter("All", "*")
        ).ifPresent(field1::setText);
    }

    private void openFileToGenerate(MouseEvent event) {
        openFile(
            event.getButton(),
            "Select a file to generate",
            new FileChooser.ExtensionFilter("All", "*")
        ).ifPresent(field1::setText);
    }

    private void openFirstFileToCompare(MouseEvent event) {
        openFile(
            event.getButton(),
            "Select the first file to compare",
            new FileChooser.ExtensionFilter("All", "*")
        ).ifPresent(field1::setText);
    }

    private void openHashFile(MouseEvent event) {
        openFile(
            event.getButton(),
            "Select a hash file",
            new FileChooser.ExtensionFilter("Hash", "*.md5", "*.sha1", "*.sha224", "*.sha256", "*.sha384", "*.sha512", "*.txt"),
            new FileChooser.ExtensionFilter("All", "*")
        ).ifPresent(field2::setText);
    }

    private void openSecondFileToCompare(MouseEvent event) {
        openFile(
            event.getButton(),
            "Select the second file to compare",
            new FileChooser.ExtensionFilter("All", "*")
        ).ifPresent(field2::setText);
    }

    private void processDragAndDrop(Dragboard dragboard, TextField field, CheckBox checkBox) {
        /*
         * The file content is only processed when the checkbox
         * IS SELECTED. On the other way, the string is only
         * processed when checkbox IS NOT SELECTED.
         *
         * The implementation uses an Optional to prevent the
         * replacement of old content.
         */

        if (dragboard.hasFiles()) {
            if (checkBox.isSelected()) {
                Optional
                    .ofNullable(dragboard.getFiles())
                    .filter(list -> !list.isEmpty())
                    .map(List::getFirst)
                    .filter(File::isFile)
                    .map(File::getAbsolutePath)
                    .ifPresent(field::setText);
            }
        } else if (!checkBox.isSelected()) {
            Optional
                .ofNullable(dragboard.getString())
                .ifPresent(field::setText);
        }
    }

    private void setFileOpeningHandler() {
        if (buttonCheck.isSelected()) {
            field1.setOnMouseClicked(checkUseFile1.isSelected() ? this::openFileToCheck : null);
            field2.setOnMouseClicked(checkUseFile2.isSelected() ? this::openHashFile : null);
        } else if (buttonCompare.isSelected()) {
            field1.setOnMouseClicked(this::openFirstFileToCompare);
            field2.setOnMouseClicked(this::openSecondFileToCompare);
        } else if (buttonGenerate.isSelected()) {
            field1.setOnMouseClicked(checkUseFile1.isSelected() ? this::openFileToGenerate : null);
            field2.setOnMouseClicked(null);
        }
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(TITLE);
        stage.setScene(createScene(FXML_PATH));
        stage.centerOnScreen();
        stage.show();

        notifyPreloader(new PreloaderController.CloseNotification());
    }
}
