package hashtools.controller;

import hashtools.condition.FileIsMissingCondition;
import hashtools.condition.MouseButtonIsPrimary;
import hashtools.domain.ComparatorRequest;
import hashtools.domain.ComparatorResponse;
import hashtools.domain.Extension;
import hashtools.domain.Resource;
import hashtools.formatter.CLIComparatorResponseFormatter;
import hashtools.identification.FileIdentification;
import hashtools.messagedigest.FileUpdater;
import hashtools.notification.FooterButtonActionNotification;
import hashtools.notification.Notification;
import hashtools.notification.NotificationReceiver;
import hashtools.notification.NotificationSender;
import hashtools.notification.ScreenCloseNotification;
import hashtools.notification.SplashStartNotification;
import hashtools.notification.SplashStopNotification;
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

public class ComparatorScreenController implements Initializable, NotificationSender, TransitionedScreen {

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
            new MouseButtonIsPrimary(event),
            new ShowOpenFileDialog(
                "Select the first file to compare",
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
            new MouseButtonIsPrimary(event),
            new ShowOpenFileDialog(
                "Select the second file to compare",
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
                "Choose where to save",
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
            Path inputFile1 = Path.of(lblScreenInput1Content.getText());
            Path inputFile2 = Path.of(lblScreenInput2Content.getText());


            if (new FileIsMissingCondition(inputFile1).isTrue()) {
                OperationPerformer.performAsync(new ShowMessageDialogOperation("Warning", "First input file is not provided"));
                OperationPerformer.performAsync(new GoToInputScreen1());
                return;
            }

            if (new FileIsMissingCondition(inputFile2).isTrue()) {
                OperationPerformer.performAsync(new ShowMessageDialogOperation("Warning", "Second input file is not provided"));
                OperationPerformer.performAsync(new GoToInputScreen2());
                return;
            }


            OperationPerformer.performAsync(new StartSplashScreen(pnlRoot));
            OperationPerformer.performAsync(new SendNotification(ComparatorScreenController.this, new SplashStartNotification()));


            ComparatorRequest request = new ComparatorRequest();
            request.setInput1(new FileUpdater(inputFile1));
            request.setInput2(new FileUpdater(inputFile2));
            request.setIdentification1(new FileIdentification(inputFile1));
            request.setIdentification2(new FileIdentification(inputFile2));

            Service service = new Service();
            ComparatorResponse response = service.run(request);

            String result = service.format(response, new CLIComparatorResponseFormatter());
            txtScreenResultContent.setText(result);


            OperationPerformer.performAsync(new StopSplashScreen(pnlRoot));
            OperationPerformer.performAsync(new SendNotification(ComparatorScreenController.this, new SplashStopNotification()));
            OperationPerformer.performAsync(new GoToResultScreen());
        }
    }
}
