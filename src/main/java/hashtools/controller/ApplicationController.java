package hashtools.controller;

import hashtools.domain.Algorithm;
import hashtools.domain.CheckerRequest;
import hashtools.domain.CheckerResponse;
import hashtools.domain.ExtensionFilter;
import hashtools.domain.GeneratorRequest;
import hashtools.domain.GeneratorResponse;
import hashtools.domain.exception.PropertyException;
import hashtools.domain.identification.Identifiable;
import hashtools.domain.identification.IdentifiableFactory;
import hashtools.domain.messagedigest.DigestUpdater;
import hashtools.domain.messagedigest.DigestUpdaterFactory;
import hashtools.domain.officialdata.OfficialDataGetter;
import hashtools.domain.officialdata.OfficialDataGetterFactory;
import hashtools.formatter.CheckerResponseFormatter;
import hashtools.formatter.GeneratorResponseFormatter;
import hashtools.service.CheckerService;
import hashtools.service.GeneratorService;
import hashtools.utility.AlgorithmFinder;
import hashtools.utility.FileManager;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ContextMenuEvent;
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

    private static final String TITLE = "HashTools";
    private static final String FXML_PATH = "/hashtools/fxml/application.fxml";
    private static final String SERVICE_RUNNABLE = "service_runnable";

    @FXML
    private GridPane paneRoot;
    @FXML
    private HBox paneRunMode;
    @FXML
    private GridPane paneAlgorithm;
    @FXML
    private StackPane paneRun;

    @FXML
    private ToggleGroup groupRunMode;
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
    private TextArea areaStatus;

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

    @FXML
    private ContextMenu paneAlgorithmContextMenu;
    @FXML
    private MenuItem itemSelectAll;
    @FXML
    private MenuItem itemSelectNone;
    @FXML
    private MenuItem itemInvertSelection;

    private ResourceBundle language;

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
        field2.disableProperty().unbind();
        field2.setDisable(false);

        areaStatus.clear();

        setFileOpeningHandler();
        setLabelsText();
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
        field2.disableProperty().unbind();
        field2.setDisable(false);

        areaStatus.clear();

        setFileOpeningHandler();
        setLabelsText();
    }

    private void enableDragAndDrop(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
    }

    private void enableGeneratorMode() {
        buttonGenerate.setSelected(true);

        checkUseFile1.setSelected(false);
        checkUseFile1.setDisable(false);

        checkUseFile2.setSelected(false);
        checkUseFile2.setDisable(false);

        // Algorithm selection is manual
        paneAlgorithm.setDisable(false);

        field1.clear();
        field1.setDisable(false);

        field2.clear();
        field2.disableProperty().bind(checkUseFile2.selectedProperty().not());

        areaStatus.clear();

        setFileOpeningHandler();
        setLabelsText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.language = resources;
        enableCheckerMode();

        buttonCheck.setOnAction(event -> enableCheckerMode());
        buttonCheck.getProperties().put(SERVICE_RUNNABLE, new CheckerRunnable());

        buttonGenerate.setOnAction(event -> enableGeneratorMode());
        buttonGenerate.getProperties().put(SERVICE_RUNNABLE, new GeneratorRunnable());

        buttonCompare.setOnAction(event -> enableComparatorMode());
        buttonCompare.getProperties().put(SERVICE_RUNNABLE, new ComparatorRunnable());

        buttonRun.setOnAction(event -> runService());

        groupRunMode.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                oldValue.setSelected(true);
            }
        });

        checkUseFile1.setOnAction(event -> {
            setFileOpeningHandler();
            setLabelsText();
        });

        checkUseFile2.setOnAction(event -> {
            setFileOpeningHandler();
            setLabelsText();
        });

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

        itemSelectAll.setOnAction(event -> selectAllAlgorithms());
        itemSelectNone.setOnAction(event -> selectNoAlgorithms());
        itemInvertSelection.setOnAction(event -> invertAlgorithmsSelection());

        paneAlgorithm.setOnMouseClicked(this::showPaneAlgorithmContextMenu);

        paneAlgorithm
            .getChildren()
            .stream()
            .filter(CheckBox.class::isInstance)
            .map(CheckBox.class::cast)
            .forEach(checkBox -> checkBox.setOnContextMenuRequested(this::showPaneAlgorithmContextMenu));
    }

    private void invertAlgorithmsSelection() {
        paneAlgorithm
            .getChildren()
            .stream()
            .filter(CheckBox.class::isInstance)
            .map(CheckBox.class::cast)
            .forEach(checkBox -> checkBox.setSelected(!checkBox.isSelected()));
    }

    private Optional<String> openFile(MouseButton button, String title, FileChooser.ExtensionFilter... extensionFilters) {
        return Optional
            .of(button)
            .filter(b -> b == MouseButton.SECONDARY)
            .map(b -> new FileManager())
            .flatMap(manager -> manager.openFile(title, extensionFilters))
            .map(Path::toAbsolutePath)
            .map(Path::toString);
    }

    private void openFileToCheck(MouseEvent event) {
        openFile(
            event.getButton(),
            language.getString("hashtools.controller.application_controller.open_file_to_check_dialog"),
            ExtensionFilter.ALL.get(language)
        ).ifPresent(field1::setText);
    }

    private void openFileToGenerate(MouseEvent event) {
        openFile(
            event.getButton(),
            language.getString("hashtools.controller.application_controller.open_file_to_generate_dialog"),
            ExtensionFilter.ALL.get(language)
        ).ifPresent(field1::setText);
    }

    private void openFirstFileToCompare(MouseEvent event) {
        openFile(
            event.getButton(),
            language.getString("hashtools.controller.application_controller.open_first_file_to_compare_dialog"),
            ExtensionFilter.ALL.get(language)
        ).ifPresent(field1::setText);
    }

    private void openHashFile(MouseEvent event) {
        openFile(
            event.getButton(),
            language.getString("hashtools.controller.application_controller.open_hash_file_dialog"),
            ExtensionFilter.HASH.get(language),
            ExtensionFilter.ALL.get(language)
        ).ifPresent(field2::setText);
    }

    private void openOutputFile(MouseEvent event) {
        saveFile(
            event.getButton(),
            language.getString("hashtools.controller.application_controller.open_output_file_dialog")
        ).ifPresent(field2::setText);
    }

    private void openSecondFileToCompare(MouseEvent event) {
        openFile(
            event.getButton(),
            language.getString("hashtools.controller.application_controller.open_second_file_to_compare_dialog"),
            ExtensionFilter.ALL.get(language)
        ).ifPresent(field2::setText);
    }

    private void pasteClipboardContent(MouseEvent event) {
        if (event.getButton() != MouseButton.SECONDARY) return;
        if (!(event.getSource() instanceof TextField field)) return;

        String content = Clipboard
            .getSystemClipboard()
            .getString();

        field.setText(content);
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

    private void runService() {
        new Thread(
            () -> Optional
                .ofNullable(groupRunMode.getSelectedToggle())
                .map(Toggle::getProperties)
                .map(properties -> properties.get(SERVICE_RUNNABLE))
                .filter(Runnable.class::isInstance)
                .map(Runnable.class::cast)
                .orElseThrow(() -> new PropertyException("There is a problem with the button run mode properties"))
                .run(),
            "Runner Thread"
        ).start();
    }

    private Optional<String> saveFile(MouseButton button, String title) {
        return Optional
            .of(button)
            .filter(b -> b == MouseButton.SECONDARY)
            .map(b -> new FileManager())
            .flatMap(manager -> manager.saveFile(title))
            .map(Path::toAbsolutePath)
            .map(Path::toString);
    }

    private void selectAllAlgorithms() {
        paneAlgorithm
            .getChildren()
            .stream()
            .filter(CheckBox.class::isInstance)
            .map(CheckBox.class::cast)
            .forEach(checkBox -> checkBox.setSelected(true));
    }

    private void selectNoAlgorithms() {
        paneAlgorithm
            .getChildren()
            .stream()
            .filter(CheckBox.class::isInstance)
            .map(CheckBox.class::cast)
            .forEach(checkBox -> checkBox.setSelected(false));
    }

    private void setFileOpeningHandler() {
        if (buttonCheck.isSelected()) {
            field1.setOnMouseClicked(
                checkUseFile1.isSelected()
                    ? this::openFileToCheck
                    : this::pasteClipboardContent
            );
            field2.setOnMouseClicked(
                checkUseFile2.isSelected()
                    ? this::openHashFile
                    : this::pasteClipboardContent
            );
        } else if (buttonCompare.isSelected()) {
            field1.setOnMouseClicked(this::openFirstFileToCompare);
            field2.setOnMouseClicked(this::openSecondFileToCompare);
        } else if (buttonGenerate.isSelected()) {
            field1.setOnMouseClicked(
                checkUseFile1.isSelected()
                    ? this::openFileToGenerate
                    : this::pasteClipboardContent
            );
            field2.setOnMouseClicked(this::openOutputFile);
        }
    }

    private void setLabelsText() {
        if (buttonCheck.isSelected()) {
            label1.setText(language.getString(
                checkUseFile1.isSelected()
                    ? "hashtools.fxml.application.label_1.check_file"
                    : "hashtools.fxml.application.label_1.check_text"
            ));
            label2.setText(language.getString(
                checkUseFile2.isSelected()
                    ? "hashtools.fxml.application.label_2.check_file"
                    : "hashtools.fxml.application.label_2.check_text"
            ));
        } else if (buttonCompare.isSelected()) {
            label1.setText(language.getString(
                "hashtools.fxml.application.label_1.compare_file"
            ));
            label2.setText(language.getString(
                "hashtools.fxml.application.label_2.compare_file"
            ));
        } else if (buttonGenerate.isSelected()) {
            label1.setText(language.getString(
                checkUseFile1.isSelected()
                    ? "hashtools.fxml.application.label_1.generate_file"
                    : "hashtools.fxml.application.label_1.generate_text"
            ));
            label2.setText(language.getString(
                "hashtools.fxml.application.label_2.output_file"
            ));
        }
    }

    private void showPaneAlgorithmContextMenu(MouseEvent event) {
        if (event.getButton() != MouseButton.SECONDARY) {
            paneAlgorithmContextMenu.hide();
            return;
        }

        paneAlgorithmContextMenu.show(
            paneAlgorithm,
            event.getScreenX(),
            event.getScreenY()
        );
    }

    private void showPaneAlgorithmContextMenu(ContextMenuEvent event) {
        paneAlgorithmContextMenu.show(
            paneAlgorithm,
            event.getScreenX(),
            event.getScreenY()
        );
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(TITLE);
        stage.setScene(createScene(FXML_PATH));
        stage.centerOnScreen();
        stage.show();

        notifyPreloader(new PreloaderController.CloseNotification());
    }


    private class CheckerRunnable implements Runnable {
        @Override
        public void run() {
            DigestUpdater digestUpdater = checkUseFile1.isSelected()
                ? DigestUpdaterFactory.create(Path.of(field1.getText()))
                : DigestUpdaterFactory.create(field1.getText());

            Identifiable identifiable = checkUseFile1.isSelected()
                ? IdentifiableFactory.create(Path.of(field1.getText()))
                : IdentifiableFactory.create(field1.getText());

            OfficialDataGetter officialDataGetter = checkUseFile2.isSelected()
                ? OfficialDataGetterFactory.create(Path.of(field2.getText()))
                : OfficialDataGetterFactory.create(field2.getText());

            CheckerRequest request = CheckerRequest
                .builder()
                .digestUpdater(digestUpdater)
                .identifiable(identifiable)
                .officialDataGetter(officialDataGetter)
                .build();

            CheckerResponse response = new CheckerService()
                .run(request);

            String formattedContent = new CheckerResponseFormatter()
                .format(response);

            areaStatus.setText(formattedContent);
        }
    }

    private class ComparatorRunnable implements Runnable {
        @Override
        public void run() {
            log.debug("Running the comparator service");
        }
    }

    private class GeneratorRunnable implements Runnable {
        @Override
        public void run() {
            DigestUpdater digestUpdater = checkUseFile1.isSelected()
                ? DigestUpdaterFactory.create(Path.of(field1.getText()))
                : DigestUpdaterFactory.create(field1.getText());

            Identifiable identifiable = checkUseFile1.isSelected()
                ? IdentifiableFactory.create(Path.of(field1.getText()))
                : IdentifiableFactory.create(field1.getText());

            List<Algorithm> algorithms = paneAlgorithm
                .getChildren()
                .stream()
                .filter(CheckBox.class::isInstance)
                .map(CheckBox.class::cast)
                .filter(CheckBox::isSelected)
                .map(CheckBox::getText)
                .map(new AlgorithmFinder()::find)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

            GeneratorRequest request = GeneratorRequest
                .builder()
                .identifiable(identifiable)
                .digestUpdater(digestUpdater)
                .algorithms(algorithms)
                .build();

            GeneratorResponse response = new GeneratorService()
                .run(request);

            String formattedContent = new GeneratorResponseFormatter()
                .format(response);

            areaStatus.setText(formattedContent);
        }
    }
}
