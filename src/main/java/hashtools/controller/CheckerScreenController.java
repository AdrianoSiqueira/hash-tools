package hashtools.controller;

import hashtools.condition.MouseButtonIsPrimary;
import hashtools.domain.CheckerRequest;
import hashtools.domain.CheckerResponse;
import hashtools.domain.Extension;
import hashtools.formatter.CLICheckerResponseFormatter;
import hashtools.identification.FileIdentification;
import hashtools.messagedigest.FileUpdater;
import hashtools.officialchecksum.FileOfficialChecksumGetter;
import hashtools.operation.Operation;
import hashtools.operation.ReplaceFileContent;
import hashtools.service.Service;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

public class CheckerScreenController extends TransitionedScreenController {

    @FXML
    private Pane
        pnlRoot,
        pnlScreenInput,
        pnlScreenChecksum,
        pnlScreenSplash,
        pnlScreenResult;

    @FXML
    private Labeled
        lblInput,
        lblChecksum;

    @FXML
    private TextInputControl txtResult;


    private ResourceBundle language;


    @Override
    public void initialize(URL url, ResourceBundle language) {
        this.language = language;

        screenPanes = List.of(
            pnlScreenInput,
            pnlScreenChecksum,
            pnlScreenSplash,
            pnlScreenResult
        );

        resetUI();
        operationPerformer.performAsync(new GoToInputScreen());
    }

    @FXML
    private void pnlChecksumMouseClicked(MouseEvent event) {
        operationPerformer.performAsync(
            new MouseButtonIsPrimary(event),
            new OpenChecksumFile()
        );
    }

    @FXML
    private void pnlInputMouseClicked(MouseEvent event) {
        operationPerformer.performAsync(
            new MouseButtonIsPrimary(event),
            new OpenInputFile()
        );
    }

    @Override
    protected void resetUI() {
        Platform.runLater(() -> {
            lblInput.setText("");
            lblChecksum.setText("");
        });

        txtResult.clear();
    }


    private final class CheckFile implements Operation {
        @Override
        public void perform() {
            Path inputFile = Path.of(lblInput.getText());
            Path checksumFile = Path.of(lblChecksum.getText());

            CheckerRequest request = new CheckerRequest();
            request.setInput(new FileUpdater(inputFile));
            request.setIdentification(new FileIdentification(inputFile));
            request.setOfficialChecksumGetter(new FileOfficialChecksumGetter(checksumFile));

            Service service = new Service();
            CheckerResponse response = service.run(request);

            String result = service.format(response, new CLICheckerResponseFormatter());
            txtResult.setText(result);
        }
    }

    private class GoToChecksumScreen implements Operation {
        @Override
        public void perform() {
            showScreenPane(pnlScreenChecksum);

            btnBackAction = new ConditionalAction(new GoToInputScreen());
            btnNextAction = new ConditionalAction(new GoToSplashScreen());
        }
    }

    private final class GoToInputScreen implements Operation {
        @Override
        public void perform() {
            showScreenPane(pnlScreenInput);

            btnBackAction = new ConditionalAction(new GoToMainScreen());
            btnNextAction = new ConditionalAction(new GoToChecksumScreen());
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

            btnBackAction = new ConditionalAction(new GoToChecksumScreen());
            btnNextAction = new ConditionalAction(new SaveResultToFile());
        }
    }

    private class GoToSplashScreen implements Operation {
        @Override
        public void perform() {
            showScreenPane(pnlScreenSplash);

            operationPerformer.performAsync(new StartSplash());
            operationPerformer.perform(new CheckFile());
            operationPerformer.performAsync(new StopSplash());
            operationPerformer.performAsync(new GoToResultScreen());
        }
    }

    private final class OpenChecksumFile implements Operation {
        @Override
        public void perform() {
            List<FileChooser.ExtensionFilter> filters = List.of(
                Extension.HASH.getFilter(language),
                Extension.ALL.getFilter(language)
            );

            Platform.runLater(() -> fileService
                .openFile(
                    "Select the checksums file",
                    System.getProperty("user.home"),
                    filters,
                    pnlRoot.getScene().getWindow())
                .map(Path::toString)
                .ifPresent(lblChecksum::setText)
            );
        }
    }

    private final class OpenInputFile implements Operation {
        @Override
        public void perform() {
            Platform.runLater(() -> fileService
                .openFile(
                    "Select a file to check",
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
            Platform.runLater(() -> fileService
                .openFileToSave(
                    "Choose where to save",
                    System.getProperty("user.home"),
                    pnlRoot.getScene().getWindow())
                .ifPresent(file -> operationPerformer.performAsync(new ReplaceFileContent(txtResult.getText(), file)))
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
