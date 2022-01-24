package hashtools.gui.screen.generator;

import aslib.security.SHAType;
import hashtools.core.module.generator.GeneratorModule;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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


    private Scene  currentScene;
    private Parent currentRoot;


    private boolean isNotReadyToRun() {
        boolean inputFieldIsEmpty    = fieldInput.getText().isBlank();
        boolean outputFieldIsEmpty   = fieldOutput.getText().isBlank();
        boolean noCheckBoxIsSelected = retrieveSelectedCheckBoxes().isEmpty();

        return inputFieldIsEmpty || outputFieldIsEmpty || noCheckBoxIsSelected;
    }

    private void addUserDataToPaneAlgorithmsCheckBoxes() {
        paneAlgorithms.getChildren()
                      .stream()
                      .filter(CheckBox.class::isInstance)
                      .map(CheckBox.class::cast)
                      .forEach(cb -> {
                          String  name    = cb.getText().replace("-", "");
                          SHAType shaType = SHAType.valueOf(name);
                          cb.setUserData(shaType);
                      });
    }

    private List<SHAType> createAlgorithmListFromCheckBoxes() {
        return retrieveSelectedCheckBoxes()
                .stream()
                .map(CheckBox::getUserData)
                .map(SHAType.class::cast)
                .toList();
    }

    @FXML
    private void openInputFile(ActionEvent event) {
        // TODO Call file opener dialog
        fieldInput.setText("/home/adriano/IdeaProjects/HashTools/temp-files/light-sample.zip");
    }

    @FXML
    private void openOutputFile(ActionEvent event) {
        // TODO Call file saver dialog
        fieldOutput.setText("/home/adriano/IdeaProjects/HashTools/temp-files/light-sample-generated.txt");
    }

    private List<CheckBox> retrieveSelectedCheckBoxes() {
        return paneAlgorithms.getChildren()
                             .stream()
                             .filter(CheckBox.class::isInstance)
                             .map(CheckBox.class::cast)
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
