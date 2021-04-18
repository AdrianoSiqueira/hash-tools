package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MainWindow implements Initializable {

    private final Logger logger = Logger.getGlobal();

    public MenuBar menuBar;

    public Menu menuFile;
    public Menu menuHelp;

    public MenuItem itemClose;
    public MenuItem itemOnlineManual;
    public MenuItem itemAbout;

    public Label labelTitleChecker;
    public Label labelCheckerInput;
    public Label labelCheckerOfficial;
    public Label labelGeneratorTitle;
    public Label labelGeneratorInput;
    public Label labelGeneratorOutput;
    public Label labelGeneratorAlgorithms;

    public TextField fieldCheckerInput;
    public TextField fieldCheckerOfficial;
    public TextField fieldGeneratorInput;
    public TextField fieldGeneratorOutput;

    public Button buttonCheck;
    public Button buttonGeneratorRun;

    public ProgressBar progressReliability;
    public ProgressBar progressGeneratorStatus;

    public CheckBox checkAlgorithmMD5;
    public CheckBox checkAlgorithmSHA1;
    public CheckBox checkAlgorithmSHA224;
    public CheckBox checkAlgorithmSHA256;
    public CheckBox checkAlgorithmSHA384;
    public CheckBox checkAlgorithmSHA512;

    public Tooltip tooltipCheckerInput;
    public Tooltip tooltipCheckerOfficial;
    public Tooltip tooltipCheckerRun;
    public Tooltip tooltipCheckerReliability;
    public Tooltip tooltipGeneratorInput;
    public Tooltip tooltipGeneratorOutput;
    public Tooltip tooltipGeneratorRun;
    public Tooltip tooltipGeneratorStatus;

    public TabPane paneRoot;
    public Tab     tabChecker;
    public Tab     tabGenerator;

    public GridPane paneChecker;
    public GridPane paneGenerator;
    public GridPane paneGeneratorAlgorithms;

    public HBox paneCheckerRun;
    public HBox paneGeneratorRun;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
