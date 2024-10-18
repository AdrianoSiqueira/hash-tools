package hashtools.controller;

import hashtools.condition.MouseButtonIsPrimary;
import hashtools.domain.CheckerRequest;
import hashtools.domain.CheckerResponse;
import hashtools.domain.Extension;
import hashtools.formatter.CLICheckerResponseFormatter;
import hashtools.identification.FileIdentification;
import hashtools.messagedigest.FileUpdater;
import hashtools.notification.FooterButtonActionNotification;
import hashtools.notification.Notification;
import hashtools.notification.NotificationReceiver;
import hashtools.notification.NotificationSender;
import hashtools.notification.ScreenCloseNotification;
import hashtools.officialchecksum.FileOfficialChecksumGetter;
import hashtools.operation.ConditionalOperation;
import hashtools.operation.Operation;
import hashtools.operation.OperationPerformer;
import hashtools.operation.StartSplashScreen;
import hashtools.operation.StopSplashScreen;
import hashtools.service.Service;
import hashtools.util.DialogUtil;
import hashtools.util.FileUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import static hashtools.domain.Resource.StaticImplementation.NO_CONDITION;

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


    private Collection<NotificationReceiver> receivers;
    private Collection<Pane> screenPanes;
    private ResourceBundle language;


    @Override
    public Notification getCallerNotification() {
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle language) {
        this.language = language;
        receivers = new ArrayList<>();

        screenPanes = List.of(
            pnlScreenInput,
            pnlScreenChecksum,
            pnlScreenSplash,
            pnlScreenResult
        );

        OperationPerformer.performAsync(new GoToInputScreen());
    }

    @FXML
    private void pnlScreenChecksumContentMouseClicked(MouseEvent event) {
        OperationPerformer.performAsync(
            new MouseButtonIsPrimary(event),
            new OpenChecksumFile()
        );
    }

    @FXML
    private void pnlScreenInputContentMouseClicked(MouseEvent event) {
        OperationPerformer.performAsync(
            new MouseButtonIsPrimary(event),
            new OpenInputFile()
        );
    }

    @Override
    public void registerNotificationReceiver(NotificationReceiver receiver) {
        receivers.add(receiver);
    }

    @Override
    public void sendNotification(Notification notification) {
        receivers.forEach(receiver -> receiver.receiveNotification(notification));
    }

    @Override
    public void showScreen(Pane screen) {
        screenPanes.forEach(pane -> pane.setVisible(false));
        screen.setVisible(true);
    }


    private final class CheckFile implements Operation {
        @Override
        public void perform() {
            Path inputFile = Path.of(lblScreenInputContent.getText());
            Path checksumFile = Path.of(lblScreenChecksumContent.getText());

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

            sendNotification(new FooterButtonActionNotification(
                new ConditionalOperation(NO_CONDITION, new GoToInputScreen()),
                new ConditionalOperation(NO_CONDITION, new GoToSplashScreen())
            ));
        }
    }

    private final class GoToInputScreen implements Operation {
        @Override
        public void perform() {
            showScreen(pnlScreenInput);

            sendNotification(new FooterButtonActionNotification(
                new ConditionalOperation(NO_CONDITION, new GoToMainScreen()),
                new ConditionalOperation(NO_CONDITION, new GoToChecksumScreen())
            ));
        }
    }

    private final class GoToMainScreen implements Operation {
        @Override
        public void perform() {
            sendNotification(new ScreenCloseNotification());
        }
    }

    private final class GoToResultScreen implements Operation {
        @Override
        public void perform() {
            showScreen(pnlScreenResult);

            sendNotification(new FooterButtonActionNotification(
                new ConditionalOperation(NO_CONDITION, new GoToChecksumScreen()),
                new ConditionalOperation(NO_CONDITION, new SaveResultToFile())
            ));
        }
    }

    private class GoToSplashScreen implements Operation {
        @Override
        public void perform() {
            showScreen(pnlScreenSplash);

            OperationPerformer.performAsync(new StartSplashScreen(pnlRoot));
            OperationPerformer.perform(new CheckFile());
            OperationPerformer.performAsync(new StopSplashScreen(pnlRoot));
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
                .ifPresent(lblScreenChecksumContent::setText)
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
                .ifPresent(lblScreenInputContent::setText)
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
}
