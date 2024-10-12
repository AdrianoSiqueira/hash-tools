package hashtools.controller;

import hashtools.condition.MouseButtonIsPrimary;
import hashtools.domain.Algorithm;
import hashtools.domain.Extension;
import hashtools.domain.GeneratorRequest;
import hashtools.domain.GeneratorResponse;
import hashtools.formatter.CLIGeneratorResponseFormatter;
import hashtools.identification.FileIdentification;
import hashtools.messagedigest.FileUpdater;
import hashtools.operation.Operation;
import hashtools.operation.OperationPerformer;
import hashtools.operation.ReplaceFileContent;
import hashtools.service.Service;
import hashtools.util.DialogUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
public class GeneratorScreenController extends TransitionedScreenController {

    @FXML
    private Pane
        pnlScreenInput,
        pnlScreenAlgorithm,
        pnlScreenSplash,
        pnlScreenResult,
        pnlRoot,
        pnlAlgorithm;

    @FXML
    private Labeled lblInput;

    @FXML
    private TextInputControl txtResult;


    private ResourceBundle language;


    @Override
    public void initialize(URL url, ResourceBundle language) {
        this.language = language;

        screenPanes = List.of(
            pnlScreenInput,
            pnlScreenAlgorithm,
            pnlScreenSplash,
            pnlScreenResult
        );

        resetUI();

        OperationPerformer.performAsync(
            threadPool,
            new GoToInputScreen()
        );
    }

    @FXML
    private void pnlInputMouseClicked(MouseEvent event) {
        OperationPerformer.performAsync(
            threadPool,
            new MouseButtonIsPrimary(event),
            new OpenInputFile()
        );
    }

    @Override
    protected void resetUI() {
        Platform.runLater(() -> lblInput.setText(""));
        txtResult.clear();

        pnlAlgorithm
            .getChildren()
            .stream()
            .filter(CheckBox.class::isInstance)
            .map(CheckBox.class::cast)
            .forEach(checkBox -> checkBox.setSelected(true));
    }


    private final class GenerateChecksums implements Operation {
        @Override
        public void perform() {
            Path inputFile = Path.of(lblInput.getText());

            List<Algorithm> algorithms = pnlAlgorithm
                .getChildren()
                .stream()
                .filter(CheckBox.class::isInstance)
                .map(CheckBox.class::cast)
                .filter(CheckBox::isSelected)
                .map(CheckBox::getText)
                .map(name -> Algorithm.from(name).orElseThrow())
                .toList();

            GeneratorRequest request = new GeneratorRequest();
            request.setInput(new FileUpdater(inputFile));
            request.setIdentification(new FileIdentification(inputFile));
            request.setAlgorithms(algorithms);

            Service service = new Service();
            GeneratorResponse response = service.run(request);

            String result = service.format(response, new CLIGeneratorResponseFormatter());
            txtResult.setText(result);
        }
    }

    private final class GoToAlgorithmScreen implements Operation {
        @Override
        public void perform() {
            showScreenPane(pnlScreenAlgorithm);

            btnBackAction = new ConditionalAction(new GoToInputScreen());
            btnNextAction = new ConditionalAction(new GoToSplashScreen());
        }
    }

    private final class GoToInputScreen implements Operation {
        @Override
        public void perform() {
            showScreenPane(pnlScreenInput);

            btnBackAction = new ConditionalAction(new GoToMainScreen());
            btnNextAction = new ConditionalAction(new GoToAlgorithmScreen());
        }
    }

    private final class GoToMainScreen implements Operation {
        @Override
        public void perform() {
            resetUI();
            pnlRoot.setVisible(false);
        }
    }

    private final class GoToResultScreen implements Operation {
        @Override
        public void perform() {
            showScreenPane(pnlScreenResult);

            btnBackAction = new ConditionalAction(new GoToAlgorithmScreen());
            btnNextAction = new ConditionalAction(new SaveResultToFile());
        }
    }

    private final class GoToSplashScreen implements Operation {
        @Override
        public void perform() {
            showScreenPane(pnlScreenSplash);

            OperationPerformer.performAsync(
                threadPool,
                new StartSplash()
            );

            OperationPerformer.perform(new GenerateChecksums());

            OperationPerformer.performAsync(
                threadPool,
                new StopSplash()
            );

            OperationPerformer.performAsync(
                threadPool,
                new GoToResultScreen()
            );
        }
    }

    private final class OpenInputFile implements Operation {
        @Override
        public void perform() {
            Platform.runLater(() -> DialogUtil
                .showOpenDialog(
                    "Select a file to generate",
                    System.getProperty("user.home"),
                    Extension.getAllExtensions(language),
                    pnlRoot.getScene().getWindow())
                .map(Path::toString)
                .ifPresent(lblInput::setText)
            );
        }
    }

    private final class SaveResultToFile implements Operation {
        @Override
        public void perform() {
            Platform.runLater(() -> DialogUtil
                .showSaveDialog(
                    "Choose where to save",
                    System.getProperty("user.home"),
                    pnlRoot.getScene().getWindow())
                .ifPresent(file -> OperationPerformer.performAsync(
                    threadPool,
                    new ReplaceFileContent(txtResult.getText(), file)
                ))
            );
        }
    }

    private final class StartSplash implements Operation {
        @Override
        public void perform() {
            // TODO Replace this statement with a css rule
            pnlRoot.setCursor(Cursor.WAIT);
            pnlRoot.pseudoClassStateChanged(DISABLED, true);
            pnlRoot
                .getChildren()
                .forEach(node -> node.setDisable(true));
        }
    }

    private final class StopSplash implements Operation {
        @Override
        public void perform() {
            // TODO Replace this statement with a css rule
            pnlRoot.setCursor(Cursor.DEFAULT);
            pnlRoot.pseudoClassStateChanged(DISABLED, false);
            pnlRoot
                .getChildren()
                .forEach(node -> node.setDisable(false));
        }
    }
}
