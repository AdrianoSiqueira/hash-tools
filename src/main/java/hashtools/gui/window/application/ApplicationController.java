package hashtools.gui.window.application;

import hashtools.core.language.LanguageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <p>
 * Application screen controller class.
 * </p>
 *
 * @author Adriano Siqueira
 */
public class ApplicationController implements Initializable {

    @FXML private HBox      paneRoot;
    @FXML private VBox      leftPane;
    @FXML private StackPane rightPane;

    @FXML private Button buttonChecker;
    @FXML private Button buttonGenerator;
    @FXML private Button buttonHowToUse;
    @FXML private Button buttonAbout;


    private void addUserDataToLeftPaneButtons() {
        buttonChecker.setUserData(getClass().getResource("/hashtools/gui/screen/checker/Checker.fxml"));
        buttonGenerator.setUserData(getClass().getResource("/hashtools/gui/screen/generator/Generator.fxml"));
        buttonHowToUse.setUserData(getClass().getResource("/hashtools/gui/screen/manual/Manual.fxml"));
        buttonAbout.setUserData(getClass().getResource("/hashtools/gui/screen/about/About.fxml"));
    }

    private Node loadFromFxml(URL url) throws IOException {
        return FXMLLoader.load(url, LanguageManager.getBundle());
    }

    @FXML
    private void openModuleScreen(ActionEvent event) {
        if (!(event.getSource() instanceof Button)) return;

        Button button = (Button) event.getSource();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addUserDataToLeftPaneButtons();

        buttonChecker.fire();
    }
}
