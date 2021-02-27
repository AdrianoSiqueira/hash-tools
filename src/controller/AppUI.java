package controller;

import aslib.filemanager.FileExtension;
import aslib.filemanager.FileOpener;
import aslib.fx.util.MessageDialog;
import aslib.net.ConnectionStatus;
import aslib.security.SHAType;
import handler.Field1DropEventHandler;
import handler.Field2DropEventHandler;
import handler.FieldDragEnablerHandler;
import handler.HashCheckerHandler;
import handler.HashGeneratorHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import language.LanguageManager;
import listener.FieldTextListener;
import listener.PaneGeneratorExpansionListener;
import main.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class AppUI implements Initializable {

    private final Logger logger = Logger.getGlobal();

    @FXML private ContextMenu contextMenu;

    @FXML private MenuBar menuBar;
    @FXML private Menu    menuFile;
    @FXML private Menu    menuHelp;

    @FXML private MenuItem itemClose;
    @FXML private MenuItem itemManual;
    @FXML private MenuItem itemAbout;
    @FXML private MenuItem itemSelectAll;
    @FXML private MenuItem itemSelectNone;
    @FXML private MenuItem itemInvertSelection;

    @FXML private Label labelTitle;
    @FXML private Label label1;
    @FXML private Label label2;

    @FXML private TextField field1;
    @FXML private TextField field2;

    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private Button buttonRun;

    @FXML private ProgressBar progressBar;

    @FXML private BorderPane paneRoot;
    @FXML private TitledPane paneGenerator;
    @FXML private GridPane   paneAlgorithm;
    @FXML private GridPane   paneContent;

    @FXML private CheckBox checkMD5;
    @FXML private CheckBox checkSHA1;
    @FXML private CheckBox checkSHA224;
    @FXML private CheckBox checkSHA256;
    @FXML private CheckBox checkSHA384;
    @FXML private CheckBox checkSHA512;

    private List<CheckBox> checkBoxList;

    @FXML
    private void close() {
        Main.getStage().close();
    }

    @FXML
    private void enableDragAndDrop(DragEvent event) {
        new FieldDragEnablerHandler()
                .handle(event);
    }

    @FXML
    private void invertSelection() {
        checkBoxList.forEach(checkBox -> checkBox.setSelected(!checkBox.isSelected()));
    }

    @FXML
    private void openAboutScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AboutUI.fxml"),
                                               LanguageManager.getBundle());

            Parent root = loader.load();
            root.getStylesheets().add("/css/AboutUI.css");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/icon/information.png"));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openFile1() {
        FileOpener opener = new FileOpener();
        Optional.ofNullable(opener.openFile(paneGenerator.isExpanded()
                                            ? LanguageManager.get("Select.a.file.to.generate")
                                            : LanguageManager.get("Select.a.file.to.check"),
                                            FileExtension.ALL))
                .ifPresent(file -> field1.setText(file.getAbsolutePath()));
    }

    @FXML
    private void openFile2() {
        FileOpener     opener = new FileOpener();
        Optional<File> optional;

        if (paneGenerator.isExpanded()) {
            optional = Optional.ofNullable(opener.openFileToSave(
                    LanguageManager.get("Choose.where.to.save"),
                    FileExtension.ALL));
        } else {
            optional = Optional.ofNullable(opener.openFile(
                    LanguageManager.get("Select.the.hash.file"),
                    FileExtension.HASH));
        }

        optional.ifPresent(file -> field2.setText(file.getAbsolutePath()));
    }

    @FXML
    private void openManual() {
        if (ConnectionStatus.checkConnection().equals(ConnectionStatus.ONLINE)) {
            Main.getHostService()
                .showDocument("https://github.com/AdrianoSiqueira/HashTools/wiki");
        } else {
            logger.log(Level.WARNING,
                       LanguageManager.get("No.internet.connection."));

            new MessageDialog.Builder()
                    .alertType(Alert.AlertType.WARNING)
                    .title(LanguageManager.get("HashTools"))
                    .header(LanguageManager.get("Internet.connection"))
                    .content(LanguageManager.get("No.internet.connection."))
                    .favIcon(new Image("/icon/manual.png"))
                    .create()
                    .showAndWait();
        }
    }

    @FXML
    private void selectAll() {
        checkBoxList.forEach(checkBox -> checkBox.setSelected(true));
    }

    @FXML
    private void selectNone() {
        checkBoxList.forEach(checkBox -> checkBox.setSelected(false));
    }

    @FXML
    private void showContextMenu(MouseEvent event) {
        if (event.getButton().equals(MouseButton.SECONDARY)) {
            contextMenu.show(paneAlgorithm,
                             event.getScreenX(),
                             event.getScreenY());
        }
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public Menu getMenuFile() {
        return menuFile;
    }

    public Menu getMenuHelp() {
        return menuHelp;
    }

    public MenuItem getItemClose() {
        return itemClose;
    }

    public MenuItem getItemManual() {
        return itemManual;
    }

    public MenuItem getItemAbout() {
        return itemAbout;
    }

    public MenuItem getItemSelectAll() {
        return itemSelectAll;
    }

    public MenuItem getItemSelectNone() {
        return itemSelectNone;
    }

    public MenuItem getItemInvertSelection() {
        return itemInvertSelection;
    }

    public Label getLabelTitle() {
        return labelTitle;
    }

    public Label getLabel1() {
        return label1;
    }

    public Label getLabel2() {
        return label2;
    }

    public TextField getField1() {
        return field1;
    }

    public TextField getField2() {
        return field2;
    }

    public Button getButton1() {
        return button1;
    }

    public Button getButton2() {
        return button2;
    }

    public Button getButtonRun() {
        return buttonRun;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public CheckBox getCheckMD5() {
        return checkMD5;
    }

    public CheckBox getCheckSHA1() {
        return checkSHA1;
    }

    public CheckBox getCheckSHA224() {
        return checkSHA224;
    }

    public CheckBox getCheckSHA256() {
        return checkSHA256;
    }

    public CheckBox getCheckSHA384() {
        return checkSHA384;
    }

    public CheckBox getCheckSHA512() {
        return checkSHA512;
    }

    public BorderPane getPaneRoot() {
        return paneRoot;
    }

    public TitledPane getPaneGenerator() {
        return paneGenerator;
    }

    public GridPane getPaneAlgorithm() {
        return paneAlgorithm;
    }

    public GridPane getPaneContent() {
        return paneContent;
    }

    public List<CheckBox> getCheckBoxList() {
        return checkBoxList;
    }

    public void setComponentsDisable(boolean disable) {
        field1.setDisable(disable);
        field2.setDisable(disable);

        button1.setDisable(disable);
        button2.setDisable(disable);
        buttonRun.setDisable(disable);

        checkBoxList.forEach(checkBox -> checkBox.setDisable(disable));

        menuBar.setDisable(disable);
        paneGenerator.setDisable(disable);
    }

    private void setDetails(boolean disable, String text, Cursor cursor, double progress) {
        this.setComponentsDisable(disable);

        Platform.runLater(() -> {
            buttonRun.setText(text);
            paneRoot.setCursor(cursor);
            progressBar.setProgress(progress);
        });
    }

    public void setDoneDetails(double progress) {
        this.setDetails(false, LanguageManager.get("Run"), Cursor.DEFAULT, progress);
    }

    public void setRunningDetails() {
        this.setDetails(true, LanguageManager.get("Running"), Cursor.WAIT, -1.0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureButton();
        configureCheckBox();
        configureField();
        configurePane();
        configureProgressBar();
    }

    private void configureButton() {
        buttonRun.setOnAction(paneGenerator.isExpanded()
                              ? new HashGeneratorHandler(this)
                              : new HashCheckerHandler(this));
    }

    private void configureCheckBox() {
        checkBoxList = Arrays.asList(checkMD5,
                                     checkSHA1,
                                     checkSHA224,
                                     checkSHA256,
                                     checkSHA384,
                                     checkSHA512);

        checkMD5.setUserData(SHAType.MD5);
        checkSHA1.setUserData(SHAType.SHA1);
        checkSHA224.setUserData(SHAType.SHA224);
        checkSHA256.setUserData(SHAType.SHA256);
        checkSHA384.setUserData(SHAType.SHA384);
        checkSHA512.setUserData(SHAType.SHA512);
    }

    private void configureField() {
        field1.textProperty()
              .addListener(new FieldTextListener(this));
        field1.setOnDragDropped(new Field1DropEventHandler());

        field2.textProperty()
              .addListener(new FieldTextListener(this));
        field2.setOnDragDropped(new Field2DropEventHandler());
    }

    private void configurePane() {
        paneGenerator.expandedProperty()
                     .addListener(new PaneGeneratorExpansionListener(this));
    }

    private void configureProgressBar() {
        progressBar.widthProperty()
                   .addListener((observable, oldValue, newValue) ->
                                        progressBar.setStyle("-fx-indeterminate-bar-length: " +
                                                             progressBar.getWidth() +
                                                             "px;"));
    }
}