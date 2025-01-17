package hashtools.applicationmodule.comparator;

import hashtools.applicationmodule.comparator.exception.MissingInputFile1Exception;
import hashtools.applicationmodule.comparator.exception.MissingInputFile2Exception;
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

public class ComparatorController implements Initializable, NotificationSender, TransitionedScreen {

    @FXML
    private Pane pnlRoot;
    @FXML
    private Pane pnlScreenInput1;
    @FXML
    private Pane pnlScreenInput1Content;
    @FXML
    private Pane pnlScreenInput2;
    @FXML
    private Pane pnlScreenInput2Content;
    @FXML
    private Pane pnlScreenSplash;
    @FXML
    private Pane pnlScreenResult;

    @FXML
    private Labeled lblScreenInput1Header;
    @FXML
    private Labeled lblScreenInput1Content;
    @FXML
    private Labeled lblScreenInput2Header;
    @FXML
    private Labeled lblScreenInput2Content;
    @FXML
    private Labeled lblScreenSplashContent;
    @FXML
    private Labeled lblScreenResultHeader;

    @FXML
    private TextInputControl txtScreenResultContent;


    private Collection<NotificationReceiver> receivers;
    private Collection<Pane> screenPanes;
    private ResourceBundle comparatorLanguage;
    private ResourceBundle sharedLanguage;


    @Override
    public Notification getCallerNotification() {
        return new FooterButtonActionNotification(
            new GoToMainScreen(),
            new GoToInputScreen2()
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle language) {
        comparatorLanguage = language;
        sharedLanguage = ResourceBundle.getBundle(Resource.Language.SHARED);
        receivers = new ArrayList<>();

        screenPanes = List.of(
            pnlScreenInput1,
            pnlScreenInput2,
            pnlScreenSplash,
            pnlScreenResult
        );

        Operation.perform(
            THREAD_POOL,
            new GoToInputScreen1()
        );
    }

    @FXML
    private void pnlScreenInput1ContentMouseClicked(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event.getButton()),
                new ShowOpenFileDialogOperation(
                    comparatorLanguage.getString("comparator-controller.dialog.title.open-file-1"),
                    System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                    Extension.getAllExtensions(sharedLanguage),
                    lblScreenInput1Content,
                    pnlRoot.getScene().getWindow()
                )
            )
        );
    }

    @FXML
    private void pnlScreenInput2ContentMouseClicked(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event.getButton()),
                new ShowOpenFileDialogOperation(
                    comparatorLanguage.getString("comparator-controller.dialog.title.open-file-2"),
                    System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                    Extension.getAllExtensions(sharedLanguage),
                    lblScreenInput2Content,
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


    private final class GoToInputScreen1 extends Operation {
        @Override
        protected void perform() {
            showScreen(pnlScreenInput1);

            sendNotification(
                new FooterButtonActionNotification(
                    new GoToMainScreen(),
                    new GoToInputScreen2()
                )
            );
        }
    }

    private final class GoToInputScreen2 extends Operation {
        @Override
        protected void perform() {
            showScreen(pnlScreenInput2);

            sendNotification(
                new FooterButtonActionNotification(
                    new GoToInputScreen1(),
                    new RunModule()
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
                    new GoToInputScreen2(),
                    new ShowSaveFileDialogOperation(
                        comparatorLanguage.getString("comparator-controller.dialog.title.save-file"),
                        System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                        txtScreenResultContent.getText(),
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
                    ComparatorController.this,
                    new SplashStartNotification()
                )
            );

            ComparatorRequest request = new ComparatorRequest();
            request.setInputFile1(Path.of(lblScreenInput1Content.getText()));
            request.setInputFile2(Path.of(lblScreenInput2Content.getText()));

            try {
                ComparatorService service = new ComparatorService(comparatorLanguage);
                ComparatorResponse response = service.processRequest(request);

                String result = service.formatResponse(response);
                txtScreenResultContent.setText(result);

                Operation.perform(
                    THREAD_POOL,
                    new GoToResultScreen()
                );
            } catch (MissingInputFile1Exception e) {
                Operation.perform(
                    THREAD_POOL,
                    new ShowMessageDialogOperation(
                        comparatorLanguage.getString("comparator-controller.dialog.title.warning"),
                        comparatorLanguage.getString("comparator-controller.dialog.content.missing-file-1")
                    ),
                    new GoToInputScreen1()
                );
            } catch (MissingInputFile2Exception e) {
                Operation.perform(
                    THREAD_POOL,
                    new ShowMessageDialogOperation(
                        comparatorLanguage.getString("comparator-controller.dialog.title.warning"),
                        comparatorLanguage.getString("comparator-controller.dialog.content.missing-file-2")
                    ),
                    new GoToInputScreen2()
                );
            } catch (ExecutionException | InterruptedException e) {
                StringWriter stackTraceContent = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTraceContent));

                Operation.perform(
                    THREAD_POOL,
                    new ShowMessageDialogOperation(
                        comparatorLanguage.getString("checker-controller.dialog.title.warning"),
                        stackTraceContent.toString()
                    )
                );
            }

            Operation.perform(
                THREAD_POOL,
                new StopSplashOperation(pnlRoot),
                new SendNotificationOperation(
                    ComparatorController.this,
                    new SplashStopNotification()
                )
            );
        }
    }
}
