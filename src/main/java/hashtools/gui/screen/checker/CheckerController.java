package hashtools.gui.screen.checker;

import hashtools.core.language.LanguageManager;
import hashtools.core.model.SampleList;
import hashtools.core.module.checker.CheckerModule;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Checker screen controller class.
 * </p>
 *
 * @author Adriano Siqueira
 */
public class CheckerController implements Initializable {

    @FXML private VBox       paneRoot;
    @FXML private GridPane   paneForm;
    @FXML private TitledPane titledPane;
    @FXML private ScrollPane scrollPane;
    @FXML private StackPane  paneResult;

    @FXML private TextField fieldInput;
    @FXML private TextField fieldOfficial;

    @FXML private Button buttonOpenInput;
    @FXML private Button buttonOpenOfficial;
    @FXML private Button buttonCheck;

    @FXML private Label labelResult;

    private Scene  currentScene;
    private Parent currentRoot;

    private boolean isNotReadyToRun() {
        boolean fieldInputIsEmpty    = fieldInput.getText().isBlank();
        boolean fieldOfficialIsEmpty = fieldOfficial.getText().isBlank();

        return fieldInputIsEmpty || fieldOfficialIsEmpty;
    }

    private int calculateIdealTabSize(String... strings) {
        return Arrays.stream(strings)
                     .map(String::length)
                     .reduce(Math::max)
                     .orElse(0);
    }

    private String formatResult(SampleList sampleList) {
        StringJoiner joiner = new StringJoiner("-".repeat(150),
                                               "-".repeat(150),
                                               "-".repeat(150));

        String s1 = LanguageManager.get("Algorithm");
        String s2 = LanguageManager.get("Calculated");
        String s3 = LanguageManager.get("Official");
        String s4 = LanguageManager.get("Result");

        int idealSize = calculateIdealTabSize(s1, s2, s3, s4);

        String algorithm  = String.format("%" + idealSize + "s: ", s1);
        String calculated = String.format("%" + idealSize + "s: ", s2);
        String official   = String.format("%" + idealSize + "s: ", s3);
        String result     = String.format("%" + idealSize + "s: ", s4);

        sampleList.getSamples()
                  .forEach(s -> {
                      String ls = System.lineSeparator();

                      String content = ls +
                                       algorithm + s.getAlgorithm().getName() + ls +
                                       official + s.getOfficialHash() + ls +
                                       calculated + s.getCalculatedHash() + ls +
                                       result + s.getResult().getText() + ls;

                      joiner.add(content);
                  });

        return joiner.toString();
    }

    @FXML
    private void openInputFile(ActionEvent event) {
        fieldInput.setText("/home/adriano/IdeaProjects/HashTools/temp-files/light-sample.zip");
    }

    @FXML
    private void openOfficialFile(ActionEvent event) {
        fieldOfficial.setText("/home/adriano/IdeaProjects/HashTools/temp-files/light-sample.txt");
    }

    private void process() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ignored) {}
    }

    private void runCheckerModule() {
        SampleList sampleList = new CheckerModule(
                fieldInput.getText(),
                fieldOfficial.getText()
        ).call();

        String result = formatResult(sampleList);
        labelResult.setText(result);
    }

    @FXML
    private void runCheckingModule(ActionEvent event) {
        new Thread(() -> {
            if (isNotReadyToRun()) return;

            startSplash();
            runCheckerModule();
            stopSplash();
        }).start();
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

    private void writeResult() {
        labelResult.setText("""
                             ------------------------------------------------------------------------------------------------------------------------------------------------------
                              Algorithm: MD-5
                               Official: 904d96a9d2c9393d24c289d8ea79cd47
                             Calculated: 904d96a9d2c9393d24c289d8ea79cd47
                                 Status: Safe
                             ------------------------------------------------------------------------------------------------------------------------------------------------------
                              Algorithm: SHA-1
                               Official: 470881e437ae957a49451f8e50ad162d7bc5cda9
                             Calculated: 470881e437ae957a49451f8e50ad162d7bc5cda9
                                 Status: Safe
                             ------------------------------------------------------------------------------------------------------------------------------------------------------
                              Algorithm: SHA-224
                               Official: 5ed2a377fd4855e9d6a0fa1a0ad456e9c7453f69e780c3eebceec1ef
                             Calculated: 5ed2a377fd4855e9d6a0fa1a0ad456e9c7453f69e780c3eebceec1ef
                                 Status: Safe
                             ------------------------------------------------------------------------------------------------------------------------------------------------------
                              Algorithm: SHA-256
                               Official: d448623bc40f18b241328a046e447bfea4ab260ac753ef20a49f342f5fa6f23f
                             Calculated: d448623bc40f18b241328a046e447bfea4ab260ac753ef20a49f342f5fa6f23f
                                 Status: Safe
                             ------------------------------------------------------------------------------------------------------------------------------------------------------
                              Algorithm: SHA-384
                               Official: 4b0151650b54c59f82f23cf966d7104c18317f3bc2426c0b74f7db108251d403f28f225136d506954e65cc3ae6030e1e
                             Calculated: 4b0151650b54c59f82f23cf966d7104c18317f3bc2426c0b74f7db108251d403f28f225136d506954e65cc3ae6030e1e
                                 Status: Safe
                             ------------------------------------------------------------------------------------------------------------------------------------------------------
                              Algorithm: SHA-512
                               Official: befb819daf5ad9907814ef693396cfadbec82af978b4c427d0410a4d258f9bfc0e92b244fdc741554f23b86a25218a9938ce77110516c3e4b040eb6643af4771
                             Calculated: befb819daf5ad9907814ef693396cfadbec82af978b4c427d0410a4d258f9bfc0e92b244fdc741554f23b86a25218a9938ce77110516c3e4b040eb6643af4771
                                 Status: Safe
                            ------------------------------------------------------------------------------------------------------------------------------------------------------
                             """);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
