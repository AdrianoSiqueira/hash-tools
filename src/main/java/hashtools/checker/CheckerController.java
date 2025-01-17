package hashtools.checker;

import hashtools.checker.exception.InvalidChecksumFileSizeException;
import hashtools.checker.exception.InvalidChecksumFileTypeException;
import hashtools.checker.exception.MissingChecksumFileException;
import hashtools.checker.exception.MissingInputFileException;
import hashtools.coremodule.Extension;
import hashtools.coremodule.Resource;
import hashtools.coremodule.TransitionedScreen;
import hashtools.coremodule.condition.MouseButtonIsPrimaryCondition;
import hashtools.coremodule.notification.FooterButtonActionNotification;
import hashtools.coremodule.notification.Notification;
import hashtools.coremodule.notification.NotificationReceiver;
import hashtools.coremodule.notification.NotificationSender;
import hashtools.coremodule.notification.ScreenCloseNotification;
import hashtools.coremodule.notification.SplashStartNotification;
import hashtools.coremodule.notification.SplashStopNotification;
import hashtools.coremodule.operation.ConditionalOperation;
import hashtools.coremodule.operation.Operation;
import hashtools.coremodule.operation.SendNotificationOperation;
import hashtools.coremodule.operation.ShowMessageDialogOperation;
import hashtools.coremodule.operation.ShowOpenFileDialogOperation;
import hashtools.coremodule.operation.ShowSaveFileDialogOperation;
import hashtools.coremodule.operation.StartSplashOperation;
import hashtools.coremodule.operation.StopSplashOperation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import static hashtools.coremodule.Resource.Software.THREAD_POOL;

public class CheckerController implements Initializable, NotificationSender, TransitionedScreen {

    @FXML
    private Pane pnlRoot;
    @FXML
    private Pane pnlScreenInput;
    @FXML
    private Pane pnlScreenInputContent;
    @FXML
    private Pane pnlScreenChecksum;
    @FXML
    private Pane pnlScreenChecksumContent;
    @FXML
    private Pane pnlScreenSplash;
    @FXML
    private Pane pnlScreenResult;

    @FXML
    private Labeled lblScreenInputHeader;
    @FXML
    private Labeled lblScreenInputContent;
    @FXML
    private Labeled lblScreenChecksumHeader;
    @FXML
    private Labeled lblScreenChecksumContent;
    @FXML
    private Labeled lblScreenSplashContent;
    @FXML
    private Labeled lblScreenResultHeader;

    @FXML
    private TextInputControl txtResult;


    private Collection<NotificationReceiver> receivers;
    private Collection<Pane> screenPanes;
    private ResourceBundle checkerLanguage;
    private ResourceBundle sharedLanguage;


    @Override
    public Notification getCallerNotification() {
        return new FooterButtonActionNotification(
            new GoToMainScreen(),
            new GoToChecksumScreen()
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle language) {
        checkerLanguage = language;
        sharedLanguage = ResourceBundle.getBundle(Resource.Language.SHARED);
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
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event.getButton()),
                new ShowOpenFileDialogOperation(
                    checkerLanguage.getString("checker-controller.dialog.title.open-checksum"),
                    System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                    List.of(
                        Extension.HASH.getFilter(sharedLanguage),
                        Extension.ALL.getFilter(sharedLanguage)
                    ),
                    lblScreenChecksumContent,
                    pnlRoot.getScene().getWindow()
                )
            )
        );
    }

    @FXML
    private void pnlScreenInputContentMouseClicked(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event.getButton()),
                new ShowOpenFileDialogOperation(
                    checkerLanguage.getString("checker-controller.dialog.title.open-file"),
                    System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                    Extension.getAllExtensions(sharedLanguage),
                    lblScreenInputContent,
                    pnlRoot.getScene().getWindow()
                )
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


    private class GoToChecksumScreen extends Operation {
        @Override
        protected void perform() {
            showScreen(pnlScreenChecksum);

            sendNotification(
                new FooterButtonActionNotification(
                    new GoToInputScreen(),
                    new RunModule()
                )
            );
        }
    }

    private final class GoToInputScreen extends Operation {
        @Override
        protected void perform() {
            showScreen(pnlScreenInput);

            sendNotification(
                new FooterButtonActionNotification(
                    new GoToMainScreen(),
                    new GoToChecksumScreen()
                )
            );
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

            sendNotification(
                new FooterButtonActionNotification(
                    new GoToChecksumScreen(),
                    new ShowSaveFileDialogOperation(
                        checkerLanguage.getString("checker-controller.dialog.title.save-file"),
                        System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                        txtResult.getText(),
                        pnlRoot.getScene().getWindow()
                    )
                )
            );
        }
    }

    private final class RunModule extends Operation {
        @Override
        protected void perform() {
            Operation.perform(
                THREAD_POOL,
                new StartSplashOperation(pnlRoot),
                new SendNotificationOperation(
                    CheckerController.this,
                    new SplashStartNotification()
                )
            );

            CheckerRequest request = new CheckerRequest();
            request.setInputFile(Path.of(lblScreenInputContent.getText()));
            request.setChecksumFile(Path.of(lblScreenChecksumContent.getText()));

            try {
                CheckerService service = new CheckerService(checkerLanguage);
                CheckerResponse response = service.processRequest(request);

                String result = service.formatResponse(response);
                txtResult.setText(result);

                Operation.perform(
                    THREAD_POOL,
                    new GoToResultScreen()
                );
            } catch (MissingInputFileException e) {
                Operation.perform(
                    THREAD_POOL,
                    new ShowMessageDialogOperation(
                        checkerLanguage.getString("checker-controller.dialog.title.warning"),
                        checkerLanguage.getString("checker-controller.dialog.content.missing-file")
                    ),
                    new GoToInputScreen()
                );
            } catch (MissingChecksumFileException e) {
                Operation.perform(
                    THREAD_POOL,
                    new ShowMessageDialogOperation(
                        checkerLanguage.getString("checker-controller.dialog.title.warning"),
                        checkerLanguage.getString("checker-controller.dialog.content.missing-checksum")
                    ),
                    new GoToChecksumScreen()
                );
            } catch (InvalidChecksumFileTypeException e) {
                Operation.perform(
                    THREAD_POOL,
                    new ShowMessageDialogOperation(
                        checkerLanguage.getString("checker-controller.dialog.title.warning"),
                        checkerLanguage.getString("checker-controller.dialog.content.checksum-not-text")
                    ),
                    new GoToChecksumScreen()
                );
            } catch (InvalidChecksumFileSizeException e) {
                Operation.perform(
                    THREAD_POOL,
                    new ShowMessageDialogOperation(
                        checkerLanguage.getString("checker-controller.dialog.title.warning"),
                        checkerLanguage.getString("checker-controller.dialog.content.checksum-too-big")
                    ),
                    new GoToChecksumScreen()
                );
            } catch (ExecutionException | InterruptedException e) {
                StringWriter stackTraceContent = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTraceContent));

                Operation.perform(
                    THREAD_POOL,
                    new ShowMessageDialogOperation(
                        checkerLanguage.getString("checker-controller.dialog.title.warning"),
                        stackTraceContent.toString()
                    )
                );
            }

            Operation.perform(
                THREAD_POOL,
                new StopSplashOperation(pnlRoot),
                new SendNotificationOperation(
                    CheckerController.this,
                    new SplashStopNotification()
                )
            );
        }
    }
}
