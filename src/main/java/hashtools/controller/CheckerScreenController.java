package hashtools.controller;

import hashtools.condition.FileIsMissingCondition;
import hashtools.condition.FileIsNotTextFileCondition;
import hashtools.condition.FileSizeIsNotBetweenCondition;
import hashtools.condition.MouseButtonIsPrimary;
import hashtools.domain.CheckerRequest;
import hashtools.domain.CheckerResponse;
import hashtools.domain.Extension;
import hashtools.domain.Resource;
import hashtools.formatter.CLICheckerResponseFormatter;
import hashtools.identification.FileIdentification;
import hashtools.messagedigest.FileUpdater;
import hashtools.notification.FooterButtonActionNotification;
import hashtools.notification.Notification;
import hashtools.notification.NotificationReceiver;
import hashtools.notification.NotificationSender;
import hashtools.notification.ScreenCloseNotification;
import hashtools.notification.SplashStartNotification;
import hashtools.notification.SplashStopNotification;
import hashtools.officialchecksum.FileOfficialChecksumGetter;
import hashtools.operation.ConditionalOperation;
import hashtools.operation.Operation;
import hashtools.operation.OperationPerformer;
import hashtools.operation.SendNotification;
import hashtools.operation.ShowMessageDialogOperation;
import hashtools.operation.ShowOpenFileDialog;
import hashtools.operation.ShowSaveFileDialog;
import hashtools.operation.StartSplashScreen;
import hashtools.operation.StopSplashScreen;
import hashtools.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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
        return new FooterButtonActionNotification(
            new ConditionalOperation(NO_CONDITION, new GoToMainScreen()),
            new ConditionalOperation(NO_CONDITION, new GoToChecksumScreen())
        );
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
            new ShowOpenFileDialog(
                "Select the checksums file",
                System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                List.of(Extension.HASH.getFilter(language), Extension.ALL.getFilter(language)),
                lblScreenChecksumContent,
                pnlRoot.getScene().getWindow()
            )
        );
    }

    @FXML
    private void pnlScreenInputContentMouseClicked(MouseEvent event) {
        OperationPerformer.performAsync(
            new MouseButtonIsPrimary(event),
            new ShowOpenFileDialog(
                "Select the file to check",
                System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                Extension.getAllExtensions(language),
                lblScreenInputContent,
                pnlRoot.getScene().getWindow()
            )
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


    private class GoToChecksumScreen implements Operation {
        @Override
        public void perform() {
            showScreen(pnlScreenChecksum);

            sendNotification(new FooterButtonActionNotification(
                new ConditionalOperation(NO_CONDITION, new GoToInputScreen()),
                new ConditionalOperation(NO_CONDITION, new RunModule())
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

            Operation saveFile = new ShowSaveFileDialog(
                "Choose where to save",
                System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                txtResult.getText(),
                pnlRoot.getScene().getWindow()
            );

            sendNotification(new FooterButtonActionNotification(
                new ConditionalOperation(NO_CONDITION, new GoToChecksumScreen()),
                new ConditionalOperation(NO_CONDITION, saveFile)
            ));
        }
    }

    private final class RunModule implements Operation {

        private static final int CHECKSUM_FILE_MIN_SIZE = 1;
        private static final int CHECKSUM_FILE_MAX_SIZE = 5_000;

        @Override
        public void perform() {
            Path inputFile = Path.of(lblScreenInputContent.getText());
            Path checksumFile = Path.of(lblScreenChecksumContent.getText());


            if (new FileIsMissingCondition(inputFile).isTrue()) {
                OperationPerformer.performAsync(new ShowMessageDialogOperation("Warning", "Input file is not provided"));
                OperationPerformer.performAsync(new GoToInputScreen());
                return;
            }

            if (new FileIsMissingCondition(checksumFile).isTrue()) {
                OperationPerformer.performAsync(new ShowMessageDialogOperation("Warning", "Checksums file is not provided"));
                OperationPerformer.performAsync(new GoToChecksumScreen());
                return;
            }

            if (new FileIsNotTextFileCondition(checksumFile).isTrue()) {
                OperationPerformer.performAsync(new ShowMessageDialogOperation("Warning", "Checksums file is not a text file"));
                OperationPerformer.performAsync(new GoToChecksumScreen());
                return;
            }

            if (new FileSizeIsNotBetweenCondition(checksumFile, CHECKSUM_FILE_MIN_SIZE, CHECKSUM_FILE_MAX_SIZE).isTrue()) {
                OperationPerformer.performAsync(new ShowMessageDialogOperation("Warning", "Checksums file is too big to be valid"));
                OperationPerformer.performAsync(new GoToChecksumScreen());
                return;
            }


            OperationPerformer.performAsync(new StartSplashScreen(pnlRoot));
            OperationPerformer.performAsync(new SendNotification(CheckerScreenController.this, new SplashStartNotification()));


            CheckerRequest request = new CheckerRequest();
            request.setInput(new FileUpdater(inputFile));
            request.setIdentification(new FileIdentification(inputFile));
            request.setOfficialChecksumGetter(new FileOfficialChecksumGetter(checksumFile));

            Service service = new Service();
            CheckerResponse response = service.run(request);

            String result = service.format(response, new CLICheckerResponseFormatter());
            txtResult.setText(result);


            OperationPerformer.performAsync(new StopSplashScreen(pnlRoot));
            OperationPerformer.performAsync(new SendNotification(CheckerScreenController.this, new SplashStopNotification()));
            OperationPerformer.performAsync(new GoToResultScreen());
        }
    }
}
