package hashtools.checker;

import hashtools.checker.officialchecksum.FileOfficialChecksumExtractor;
import hashtools.shared.Extension;
import hashtools.shared.Resource;
import hashtools.shared.TransitionedScreen;
import hashtools.shared.condition.FileIsRegularFileCondition;
import hashtools.shared.condition.FileIsNotTextFileCondition;
import hashtools.shared.condition.FileSizeIsNotBetweenCondition;
import hashtools.shared.condition.MouseButtonIsPrimaryCondition;
import hashtools.shared.identification.FileIdentification;
import hashtools.shared.messagedigest.FileMessageDigestUpdater;
import hashtools.shared.notification.FooterButtonActionNotification;
import hashtools.shared.notification.Notification;
import hashtools.shared.notification.NotificationReceiver;
import hashtools.shared.notification.NotificationSender;
import hashtools.shared.notification.ScreenCloseNotification;
import hashtools.shared.notification.SplashStartNotification;
import hashtools.shared.notification.SplashStopNotification;
import hashtools.shared.operation.ConditionalOperation;
import hashtools.shared.operation.Operation;
import hashtools.shared.operation.OperationPerformer;
import hashtools.shared.operation.SendNotification;
import hashtools.shared.operation.ShowMessageDialogOperation;
import hashtools.shared.operation.ShowOpenFileDialog;
import hashtools.shared.operation.ShowSaveFileDialog;
import hashtools.shared.operation.StartSplashScreen;
import hashtools.shared.operation.StopSplashScreen;
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

import static hashtools.shared.Resource.StaticImplementation.NO_CONDITION;

public class CheckerController implements Initializable, NotificationSender, TransitionedScreen {

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
            new MouseButtonIsPrimaryCondition(event),
            new ShowOpenFileDialog(
                language.getString("hashtools.checker.checker-controller.dialog.title.open-checksum"),
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
            new MouseButtonIsPrimaryCondition(event),
            new ShowOpenFileDialog(
                language.getString("hashtools.checker.checker-controller.dialog.title.open-file"),
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
                language.getString("hashtools.checker.checker-controller.dialog.title.save-file"),
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


            String title = language.getString("hashtools.checker.checker-controller.dialog.title.warning");

            if (new FileIsRegularFileCondition(inputFile).isTrue()) {
                OperationPerformer.performAsync(new ShowMessageDialogOperation(title, language.getString("hashtools.checker.checker-controller.dialog.content.missing-file")));
                OperationPerformer.performAsync(new GoToInputScreen());
                return;
            }

            if (new FileIsRegularFileCondition(checksumFile).isTrue()) {
                OperationPerformer.performAsync(new ShowMessageDialogOperation(title, language.getString("hashtools.checker.checker-controller.dialog.content.missing-checksum")));
                OperationPerformer.performAsync(new GoToChecksumScreen());
                return;
            }

            if (new FileIsNotTextFileCondition(checksumFile).isTrue()) {
                OperationPerformer.performAsync(new ShowMessageDialogOperation(title, language.getString("hashtools.checker.checker-controller.dialog.content.checksum-not-text")));
                OperationPerformer.performAsync(new GoToChecksumScreen());
                return;
            }

            if (new FileSizeIsNotBetweenCondition(checksumFile, CHECKSUM_FILE_MIN_SIZE, CHECKSUM_FILE_MAX_SIZE).isTrue()) {
                OperationPerformer.performAsync(new ShowMessageDialogOperation(title, language.getString("hashtools.checker.checker-controller.dialog.content.checksum-too-big")));
                OperationPerformer.performAsync(new GoToChecksumScreen());
                return;
            }


            OperationPerformer.performAsync(new StartSplashScreen(pnlRoot));
            OperationPerformer.performAsync(new SendNotification(CheckerController.this, new SplashStartNotification()));


            CheckerRequest request = new CheckerRequest();
            request.setInput(new FileMessageDigestUpdater(inputFile));
            request.setIdentification(new FileIdentification(inputFile));
            request.setOfficialChecksumExtractor(new FileOfficialChecksumExtractor(checksumFile));

            CheckerService service = new CheckerService();
            CheckerResponse response = service.processRequest(request);

            String result = service.formatResponse(response, new CheckerResponseFormatter(language));
            txtResult.setText(result);


            OperationPerformer.performAsync(new StopSplashScreen(pnlRoot));
            OperationPerformer.performAsync(new SendNotification(CheckerController.this, new SplashStopNotification()));
            OperationPerformer.performAsync(new GoToResultScreen());
        }
    }
}
