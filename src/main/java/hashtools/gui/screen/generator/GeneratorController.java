package hashtools.gui.screen.generator;

import aslib.filemanager.FileExtension;
import aslib.filemanager.FileOpener;
import aslib.security.SHAType;
import hashtools.core.language.LanguageManager;
import hashtools.core.module.generator.GeneratorModule;
import hashtools.core.service.FileService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * <p>
 * Generator screen controller class.
 * </p>
 *
 * @author Adriano Siqueira
 */
public class GeneratorController implements Initializable {

    @FXML private GridPane paneRoot;
    @FXML private GridPane paneAlgorithms;

    @FXML private TextField fieldInput;
    @FXML private TextField fieldOutput;

    @FXML private Button buttonOpenInput;
    @FXML private Button buttonOpenOutput;
    @FXML private Button buttonGenerate;

    @FXML private CheckBox checkMD5;

    private Scene  currentScene;
    private Parent currentRoot;


    private boolean isNotReadyToRun() {
        boolean inputFieldIsEmpty    = fieldInput.getText().isBlank();
        boolean outputFieldIsEmpty   = fieldOutput.getText().isBlank();
        boolean noCheckBoxIsSelected = retrieveSelectedCheckBoxes().isEmpty();

        return inputFieldIsEmpty || outputFieldIsEmpty || noCheckBoxIsSelected;
    }

    private void addUserDataToPaneAlgorithmsCheckBoxes() {
        retrievePaneAlgorithmCheckBoxStream()
                .forEach(cb -> {
                    String  name    = cb.getText().replace("-", "");
                    SHAType shaType = SHAType.valueOf(name);
                    cb.setUserData(shaType);
                });
    }

    @FXML
    private void analyzeDragContent(DragEvent event) {
        Dragboard      dragboard = event.getDragboard();
        TransferMode[] transferModes;

        if (event.getSource() == fieldInput) {
            transferModes = TransferMode.ANY;
        } else if (dragboard.hasFiles()) {
            Path path = dragboard.getFiles().get(0).toPath();

            transferModes = FileService.pathHasRequiredExtension(path, FileExtension.HASH)
                            ? TransferMode.ANY
                            : TransferMode.NONE;
        } else {
            transferModes = TransferMode.NONE;
        }

        event.acceptTransferModes(transferModes);
    }

    private List<SHAType> createAlgorithmListFromCheckBoxes() {
        return retrieveSelectedCheckBoxes()
                .stream()
                .map(CheckBox::getUserData)
                .map(SHAType.class::cast)
                .toList();
    }

    @FXML
    private void invertAlgorithmsSelection(ActionEvent event) {
        retrievePaneAlgorithmCheckBoxStream()
                .forEach(cb -> cb.setSelected(!cb.isSelected()));
    }

    @FXML
    private void moveTooltipWithMouse(MouseEvent event) {
        if (!(event.getSource() instanceof Control control)) return;

        Tooltip tooltip = control.getTooltip();
        tooltip.setX(event.getScreenX() + 10);
        tooltip.setY(event.getScreenY() + 10);
    }

    @FXML
    private void openInputFile(ActionEvent event) {
        FileOpener fileOpener = new FileOpener();
        String     title      = LanguageManager.get("Select.input.file");

        Optional.ofNullable(fileOpener.openFile(title, FileExtension.ALL))
                .ifPresent(f -> fieldInput.setText(f.getAbsolutePath()));
    }

    @FXML
    private void openOutputFile(ActionEvent event) {
        FileOpener fileOpener = new FileOpener();
        String     title      = LanguageManager.get("Select.where.to.save.hashes");

        Optional.ofNullable(fileOpener.openFileToSave(title, FileExtension.HASH))
                .ifPresent(f -> fieldOutput.setText(f.getAbsolutePath()));
    }

    @FXML
    private void pasteContentFromDragAndDrop(DragEvent event) {
        if (!(event.getSource() instanceof TextField field)) return;

        Dragboard dragboard = event.getDragboard();

        String content = dragboard.hasFiles()
                         ? dragboard.getFiles().get(0).getAbsolutePath()
                         : dragboard.getString();

        field.setText(content);
    }

    private Stream<CheckBox> retrievePaneAlgorithmCheckBoxStream() {
        return paneAlgorithms.getChildren()
                             .stream()
                             .filter(CheckBox.class::isInstance)
                             .map(CheckBox.class::cast);
    }

    private List<CheckBox> retrieveSelectedCheckBoxes() {
        return retrievePaneAlgorithmCheckBoxStream()
                .filter(CheckBox::isSelected)
                .toList();
    }

    @FXML
    private void runGenerationModule(ActionEvent event) {
        new Thread(() -> {
            if (isNotReadyToRun()) return;

            startSplash();
            runGeneratorModule();
            stopSplash();
        }).start();
    }

    private void runGeneratorModule() {
        new GeneratorModule(
                fieldInput.getText(),
                fieldOutput.getText(),
                createAlgorithmListFromCheckBoxes()
        ).call();
    }

    @FXML
    private void selectAllAlgorithms(ActionEvent event) {
        retrievePaneAlgorithmCheckBoxStream()
                .forEach(cb -> cb.setSelected(true));
    }

    @FXML
    private void selectNoAlgorithms(ActionEvent event) {
        retrievePaneAlgorithmCheckBoxStream()
                .forEach(cb -> cb.setSelected(false));
    }

    @FXML private void showContextMenu(ContextMenuEvent event){
        Window window = checkMD5.getScene().getWindow();
        double anchorX = event.getScreenX();
        double anchorY = event.getScreenY();

        checkMD5.getContextMenu()
                .show(window, anchorX, anchorY);
    }

    @SuppressWarnings("ConstantConditions")
    private void startSplash() {
        currentScene = paneRoot.getScene();
        currentRoot = currentScene.getRoot();

        try {
            currentScene.setRoot(FXMLLoader.load(getClass().getResource("/hashtools/gui/screen/splash/Splash.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopSplash() {
        Platform.runLater(() -> currentScene.setRoot(currentRoot));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addUserDataToPaneAlgorithmsCheckBoxes();
    }
}
