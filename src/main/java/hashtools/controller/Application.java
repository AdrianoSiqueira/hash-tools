package hashtools.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

@Slf4j
public class Application extends javafx.application.Application {
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
    private Button buttonCheck;
    @FXML
    private Button buttonCompare;
    @FXML
    private Button buttonGenerate;
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

    private Pane loadFxml(URL url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);

        try {
            return loader.load();
        } catch (IOException e) {
            log.error("Could not load fxml: '" + url + "'", e);
            return null;
        }
    }

    @Override
    public void start(Stage stage) {
        /*
         * Attempts to create a scene loading the fxml
         * file. If it fails the scene will be blank.
         */
        Optional
            .of(getClass())
            .map(clazz -> clazz.getResource(FXML_PATH))
            .map(this::loadFxml)
            .map(Scene::new)
            .ifPresent(stage::setScene);

        stage.setTitle(TITLE);
        stage.centerOnScreen();
        stage.show();
    }
}
