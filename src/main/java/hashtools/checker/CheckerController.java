package hashtools.checker;

import hashtools.checker.exception.InvalidChecksumFileSizeException;
import hashtools.checker.exception.InvalidChecksumFileTypeException;
import hashtools.checker.exception.MissingChecksumFileException;
import hashtools.checker.exception.MissingInputFileException;
import hashtools.shared.Extension;
import hashtools.shared.Resource;
import hashtools.shared.TransitionedScreen;
import hashtools.shared.condition.Condition;
import hashtools.shared.condition.MouseButtonIsPrimaryCondition;
import hashtools.shared.notification.FooterButtonActionNotification;
import hashtools.shared.notification.Notification;
import hashtools.shared.notification.NotificationReceiver;
import hashtools.shared.notification.NotificationSender;
import hashtools.shared.notification.ScreenCloseNotification;
import hashtools.shared.notification.SplashStartNotification;
import hashtools.shared.notification.SplashStopNotification;
import hashtools.shared.operation.ConditionalOperation;
import hashtools.shared.operation.Operation;
import hashtools.shared.operation.SendNotification;
import hashtools.shared.operation.ShowMessageDialogOperation;
import hashtools.shared.operation.ShowOpenFileDialog;
import hashtools.shared.operation.ShowSaveFileDialog;
import hashtools.shared.operation.StartSplashOperation;
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

import static hashtools.shared.Resource.Software.THREAD_POOL;

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
            new GoToMainScreen(),
            new GoToChecksumScreen()
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

        Operation.perform(
            THREAD_POOL,
            new GoToInputScreen()
        );
    }

    @FXML
    private void pnlScreenChecksumContentMouseClicked(MouseEvent event) {
        Condition condition = new MouseButtonIsPrimaryCondition(event);

        Operation operation = new ShowOpenFileDialog(
            language.getString("hashtools.checker.checker-controller.dialog.title.open-checksum"),
            System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
            List.of(Extension.HASH.getFilter(language), Extension.ALL.getFilter(language)),
            lblScreenChecksumContent,
            pnlRoot.getScene().getWindow()
        );

        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(condition, operation)
        );
    }

    @FXML
    private void pnlScreenInputContentMouseClicked(MouseEvent event) {
        Condition condition = new MouseButtonIsPrimaryCondition(event);

        Operation operation = new ShowOpenFileDialog(
            language.getString("hashtools.checker.checker-controller.dialog.title.open-file"),
            System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
            Extension.getAllExtensions(language),
            lblScreenInputContent,
            pnlRoot.getScene().getWindow()
        );

        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(condition, operation)
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


    private class GoToChecksumScreen extends Operation {
        @Override
        protected void perform() {
            showScreen(pnlScreenChecksum);

            sendNotification(new FooterButtonActionNotification(
                new GoToInputScreen(),
                new RunModule()
            ));
        }
    }

    private final class GoToInputScreen extends Operation {
        @Override
        protected void perform() {
            showScreen(pnlScreenInput);

            sendNotification(new FooterButtonActionNotification(
                new GoToMainScreen(),
                new GoToChecksumScreen()
            ));
        }
    }

    private final class GoToMainScreen extends Operation {
        @Override
        protected void perform() {
            sendNotification(new ScreenCloseNotification());
        }
    }

    private final class GoToResultScreen extends Operation {
        @Override
        protected void perform() {
            showScreen(pnlScreenResult);

            Operation saveFile = new ShowSaveFileDialog(
                language.getString("hashtools.checker.checker-controller.dialog.title.save-file"),
                System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                txtResult.getText(),
                pnlRoot.getScene().getWindow()
            );

            sendNotification(new FooterButtonActionNotification(
                new GoToChecksumScreen(),
                saveFile
            ));
        }
    }

    private final class RunModule extends Operation {
        @Override
        protected void perform() {
            String dialogTitle = language.getString("hashtools.checker.checker-controller.dialog.title.warning");

            Operation.perform(
                THREAD_POOL,
                new StartSplashOperation(pnlRoot),
                new SendNotification(CheckerController.this, new SplashStartNotification())
            );

            CheckerRequest request = new CheckerRequest();
            request.setInputFile(Path.of(lblScreenInputContent.getText()));
            request.setChecksumFile(Path.of(lblScreenChecksumContent.getText()));

            try {
                CheckerService service = new CheckerService();
                CheckerResponse response = service.processRequest(request);

                String result = service.formatResponse(response, new CheckerResponseFormatter(language));
                txtResult.setText(result);

                Operation.perform(
                    THREAD_POOL,
                    new GoToResultScreen()
                );
            } catch (MissingInputFileException e) {
                Operation.perform(
                    THREAD_POOL,
                    new ShowMessageDialogOperation(dialogTitle, language.getString("hashtools.checker.checker-controller.dialog.content.missing-file")),
                    new GoToInputScreen()
                );
            } catch (MissingChecksumFileException e) {
                Operation.perform(
                    THREAD_POOL,
                    new ShowMessageDialogOperation(dialogTitle, language.getString("hashtools.checker.checker-controller.dialog.content.missing-checksum")),
                    new GoToChecksumScreen()
                );
            } catch (InvalidChecksumFileTypeException e) {
                Operation.perform(
                    THREAD_POOL,
                    new ShowMessageDialogOperation(dialogTitle, language.getString("hashtools.checker.checker-controller.dialog.content.checksum-not-text")),
                    new GoToChecksumScreen()
                );
            } catch (InvalidChecksumFileSizeException e) {
                Operation.perform(
                    THREAD_POOL,
                    new ShowMessageDialogOperation(dialogTitle, language.getString("hashtools.checker.checker-controller.dialog.content.checksum-too-big")),
                    new GoToChecksumScreen()
                );
            }

            Operation.perform(
                THREAD_POOL,
                new StopSplashScreen(pnlRoot),
                new SendNotification(CheckerController.this, new SplashStopNotification())
            );
        }
    }
}
