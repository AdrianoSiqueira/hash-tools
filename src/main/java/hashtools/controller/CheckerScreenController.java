package hashtools.controller;

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
import hashtools.operation.ShowOpenFileDialog;
import hashtools.operation.ShowSaveFileDialog;
import hashtools.operation.StartSplashScreen;
import hashtools.operation.StopSplashScreen;
import hashtools.service.Service;
import hashtools.util.FXUtil;
import javafx.collections.ObservableMap;
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
import java.util.HashMap;
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


    @Override
    public Notification getCallerNotification() {
        return new FooterButtonActionNotification(
            new ConditionalOperation(NO_CONDITION, new GoToMainScreen()),
            new ConditionalOperation(NO_CONDITION, new GoToChecksumScreen())
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle language) {
        receivers = new ArrayList<>();

        screenPanes = List.of(
            pnlScreenInput,
            pnlScreenChecksum,
            pnlScreenSplash,
            pnlScreenResult
        );

        pnlScreenInputContent
            .getProperties()
            .putAll(new HashMap<>() {{
                put(Resource.PropertyKey.DIALOG_TITLE, "Select the file to check");
                put(Resource.PropertyKey.DIALOG_FILTER, Extension.getAllExtensions(language));
                put(Resource.PropertyKey.LABELED, lblScreenInputContent);
            }});

        pnlScreenChecksumContent
            .getProperties()
            .putAll(new HashMap<>() {{
                put(Resource.PropertyKey.DIALOG_TITLE, "Select the checksums file");
                put(Resource.PropertyKey.DIALOG_FILTER, List.of(Extension.HASH.getFilter(language), Extension.ALL.getFilter(language)));
                put(Resource.PropertyKey.LABELED, lblScreenChecksumContent);
            }});

        OperationPerformer.performAsync(new GoToInputScreen());
    }

    @FXML
    private void pnlInputContentMouseClicked(MouseEvent event) {
        ObservableMap<Object, Object> properties = FXUtil
            .getNode(event)
            .getProperties();

        @SuppressWarnings("unchecked")
        Operation openFile = new ShowOpenFileDialog(
            (String) properties.get(Resource.PropertyKey.DIALOG_TITLE),
            System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
            (Collection<FileChooser.ExtensionFilter>) properties.get(Resource.PropertyKey.DIALOG_FILTER),
            (Labeled) properties.get(Resource.PropertyKey.LABELED),
            pnlRoot.getScene().getWindow()
        );

        OperationPerformer.performAsync(
            new MouseButtonIsPrimary(event),
            openFile
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

    private class GoToSplashScreen implements Operation {
        @Override
        public void perform() {
            showScreen(pnlScreenSplash);

            OperationPerformer.performAsync(new StartSplashScreen(pnlRoot));
            OperationPerformer.performAsync(new SendNotification(CheckerScreenController.this, new SplashStartNotification()));
            OperationPerformer.perform(new CheckFile());
            OperationPerformer.performAsync(new StopSplashScreen(pnlRoot));
            OperationPerformer.performAsync(new SendNotification(CheckerScreenController.this, new SplashStopNotification()));
            OperationPerformer.performAsync(new GoToResultScreen());
        }
    }
}
