package hashtools.gui.screen.generator;

import aslib.security.SHAType;
import hashtools.core.module.generator.GeneratorModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

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

    private boolean isNotReadyToRun() {
        return fieldInput.getText().isBlank() ||
               fieldOutput.getText().isBlank();
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
        return paneAlgorithms.getChildren()
                             .stream()
                             .map(CheckBox.class::cast)
                             .filter(CheckBox::isSelected)
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

    @FXML
    private void runGenerationModule(ActionEvent event) {
        if (isNotReadyToRun()) return;

        List<SHAType> algorithms = createAlgorithmListFromCheckBoxes();

        new GeneratorModule(
                fieldInput.getText(),
                fieldOutput.getText(),
                algorithms
        ).call();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addUserDataToPaneAlgorithmsCheckBoxes();
    }
}
