package hashtools.controller;

import hashtools.condition.MouseButtonIsPrimary;
import hashtools.domain.CheckerRequest;
import hashtools.domain.CheckerResponse;
import hashtools.domain.Extension;
import hashtools.domain.Resource;
import hashtools.formatter.CLICheckerResponseFormatter;
import hashtools.identification.FileIdentification;
import hashtools.messagedigest.FileUpdater;
import hashtools.notification.Notification;
import hashtools.notification.NotificationReceiver;
import hashtools.notification.NotificationSender;
import hashtools.officialchecksum.FileOfficialChecksumGetter;
import hashtools.operation.Operation;
import hashtools.operation.OperationPerformer;
import hashtools.service.Service;
import hashtools.util.DialogUtil;
import hashtools.util.FileUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.net.URL;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class CheckerScreenController implements Initializable, NotificationSender, TransitionedScreen {

    @FXML
    private Pane
        pnlRoot,
        pnlScreenInput,
        pnlScreenInputContent,
        pnlScreenChecksum,
        pnlScreenChecksumContent,
        pnlScreenSplash,
        pnlScreenResult;

    @FXML
    private Labeled
        lblScreenInputHeader,
        lblScreenInputContent,
        lblScreenChecksumHeader,
        lblScreenChecksumContent,
        lblScreenSplashContent,
        lblScreenResultHeader;

    @FXML
    private TextInputControl txtResult;


    private Collection<Pane> screenPanes;
    private ResourceBundle language;


    @Override
    public Notification getCallerNotification() {
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle language) {
        this.language = language;

        screenPanes = List.of(
            pnlScreenInput,
            pnlScreenChecksum,
            pnlScreenSplash,
            pnlScreenResult
        );

        OperationPerformer.performAsync(new GoToInputScreen());
    }

    @FXML
    private void pnlChecksumMouseClicked(MouseEvent event) {
        OperationPerformer.performAsync(
            new MouseButtonIsPrimary(event),
            new OpenChecksumFile()
        );
    }

    @FXML
    private void pnlInputMouseClicked(MouseEvent event) {
        OperationPerformer.performAsync(
            new MouseButtonIsPrimary(event),
            new OpenInputFile()
        );
    }

    @Override
    public void registerNotificationReceiver(NotificationReceiver receiver) {
    }

    @Override
    public void sendNotification(Notification notification) {
    }

    @Override
    public void showScreen(Pane screen) {
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
            showScreen(pnlScreenChecksum);

//            btnBackAction = new ConditionalAction(new GoToInputScreen());
//            btnNextAction = new ConditionalAction(new GoToSplashScreen());
        }
    }

    private final class GoToInputScreen implements Operation {
        @Override
        public void perform() {
            showScreen(pnlScreenInput);

//            btnBackAction = new ConditionalAction(new GoToMainScreen());
//            btnNextAction = new ConditionalAction(new GoToChecksumScreen());
        }
    }

    private final class GoToMainScreen implements Operation {
        @Override
        public void perform() {
            pnlRoot.setVisible(false);
        }
    }

    private final class GoToResultScreen implements Operation {
        @Override
        public void perform() {
            showScreen(pnlScreenResult);

//            btnBackAction = new ConditionalAction(new GoToChecksumScreen());
//            btnNextAction = new ConditionalAction(new SaveResultToFile());
        }
    }

    private class GoToSplashScreen implements Operation {
        @Override
        public void perform() {
            showScreen(pnlScreenSplash);

            OperationPerformer.performAsync(new StartSplash());
            OperationPerformer.perform(new CheckFile());
            OperationPerformer.performAsync(new StopSplash());
            OperationPerformer.performAsync(new GoToResultScreen());
        }
    }

    private final class OpenChecksumFile implements Operation {
        @Override
        public void perform() {
            List<FileChooser.ExtensionFilter> filters = List.of(
                Extension.HASH.getFilter(language),
                Extension.ALL.getFilter(language)
            );

            Platform.runLater(() -> DialogUtil
                .showOpenDialog(
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
            Platform.runLater(() -> DialogUtil
                .showOpenDialog(
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
            Platform.runLater(() -> DialogUtil
                .showSaveDialog(
                    "Choose where to save",
                    System.getProperty("user.home"),
                    pnlRoot.getScene().getWindow())
                .ifPresent(file -> FileUtil.replaceContent(txtResult.getText(), file))
            );
        }
    }

    private final class StartSplash implements Operation {
        @Override
        public void perform() {
            // TODO Replace this statement with a css rule
            pnlRoot.setCursor(Cursor.WAIT);
            pnlRoot.pseudoClassStateChanged(Resource.Static.DISABLED, true);
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
            pnlRoot.pseudoClassStateChanged(Resource.Static.DISABLED, false);
            pnlRoot
                .getChildren()
                .forEach(node -> node.setDisable(false));
        }
    }
}
