package hashtools.comparator;

import hashtools.comparator.exception.MissingInputFile1Exception;
import hashtools.comparator.exception.MissingInputFile2Exception;
import hashtools.shared.Extension;
import hashtools.shared.Resource;
import hashtools.shared.TransitionedScreen;
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

public class ComparatorController implements Initializable, NotificationSender, TransitionedScreen {

    @FXML
    private Pane
        pnlRoot,
        pnlScreenInput1,
        pnlScreenInput1Content,
        pnlScreenInput2,
        pnlScreenInput2Content,
        pnlScreenSplash,
        pnlScreenResult;

    @FXML
    private Labeled
        lblScreenInput1Header,
        lblScreenInput1Content,
        lblScreenInput2Header,
        lblScreenInput2Content,
        lblScreenSplashContent,
        lblScreenResultHeader;

    @FXML
    private TextInputControl txtScreenResultContent;


    private Collection<NotificationReceiver> receivers;
    private Collection<Pane> screenPanes;
    private ResourceBundle language;


    @Override
    public Notification getCallerNotification() {
        return new FooterButtonActionNotification(
            new ConditionalOperation(NO_CONDITION, new GoToMainScreen()),
            new ConditionalOperation(NO_CONDITION, new GoToInputScreen2())
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle language) {
        this.language = language;
        receivers = new ArrayList<>();

        screenPanes = List.of(
            pnlScreenInput1,
            pnlScreenInput2,
            pnlScreenSplash,
            pnlScreenResult
        );

        OperationPerformer.performAsync(new GoToInputScreen1());
    }

    @FXML
    private void pnlScreenInput1ContentMouseClicked(MouseEvent event) {
        OperationPerformer.performAsync(
            new MouseButtonIsPrimaryCondition(event),
            new ShowOpenFileDialog(
                language.getString("hashtools.comparator.comparator-controller.dialog.title.open-file-1"),
                System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                Extension.getAllExtensions(language),
                lblScreenInput1Content,
                pnlRoot.getScene().getWindow()
            )
        );
    }

    @FXML
    private void pnlScreenInput2ContentMouseClicked(MouseEvent event) {
        OperationPerformer.performAsync(
            new MouseButtonIsPrimaryCondition(event),
            new ShowOpenFileDialog(
                language.getString("hashtools.comparator.comparator-controller.dialog.title.open-file-2"),
                System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                Extension.getAllExtensions(language),
                lblScreenInput2Content,
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


    private final class GoToInputScreen1 implements Operation {
        @Override
        public void perform() {
            showScreen(pnlScreenInput1);

            sendNotification(new FooterButtonActionNotification(
                new ConditionalOperation(NO_CONDITION, new GoToMainScreen()),
                new ConditionalOperation(NO_CONDITION, new GoToInputScreen2())
            ));
        }
    }

    private final class GoToInputScreen2 implements Operation {
        @Override
        public void perform() {
            showScreen(pnlScreenInput2);

            sendNotification(new FooterButtonActionNotification(
                new ConditionalOperation(NO_CONDITION, new GoToInputScreen1()),
                new ConditionalOperation(NO_CONDITION, new RunModule())
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
                language.getString("hashtools.comparator.comparator-controller.dialog.title.save-file"),
                System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                txtScreenResultContent.getText(),
                pnlRoot.getScene().getWindow()
            );

            sendNotification(new FooterButtonActionNotification(
                new ConditionalOperation(NO_CONDITION, new GoToInputScreen2()),
                new ConditionalOperation(NO_CONDITION, saveFile)
            ));
        }
    }

    private final class RunModule implements Operation {
        @Override
        public void perform() {
            String dialogTitle = language.getString("hashtools.comparator.comparator-controller.dialog.title.warning");

            OperationPerformer.performAsync(new StartSplashScreen(pnlRoot));
            OperationPerformer.performAsync(new SendNotification(ComparatorController.this, new SplashStartNotification()));

            ComparatorRequest request = new ComparatorRequest();
            request.setInputFile1(Path.of(lblScreenInput1Content.getText()));
            request.setInputFile2(Path.of(lblScreenInput2Content.getText()));

            try {
                ComparatorService service = new ComparatorService();
                ComparatorResponse response = service.processRequest(request);

                String result = service.formatResponse(response, new ComparatorResponseFormatter(language));
                txtScreenResultContent.setText(result);

                OperationPerformer.performAsync(new GoToResultScreen());
            } catch (MissingInputFile1Exception e) {
                OperationPerformer.performAsync(new ShowMessageDialogOperation(dialogTitle, language.getString("hashtools.comparator.comparator-controller.dialog.content.missing-file-1")));
                OperationPerformer.performAsync(new GoToInputScreen1());
            } catch (MissingInputFile2Exception e) {
                OperationPerformer.performAsync(new ShowMessageDialogOperation(dialogTitle, language.getString("hashtools.comparator.comparator-controller.dialog.content.missing-file-2")));
                OperationPerformer.performAsync(new GoToInputScreen2());
            }

            OperationPerformer.performAsync(new StopSplashScreen(pnlRoot));
            OperationPerformer.performAsync(new SendNotification(ComparatorController.this, new SplashStopNotification()));

        }
    }
}
