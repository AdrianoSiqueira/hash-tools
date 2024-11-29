package hashtools.generator;

import hashtools.shared.Algorithm;
import hashtools.shared.Extension;
import hashtools.shared.Resource;
import hashtools.shared.TransitionedScreen;
import hashtools.shared.condition.FileIsRegularFileCondition;
import hashtools.shared.condition.MouseButtonIsPrimary;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import static hashtools.shared.Resource.StaticImplementation.NO_CONDITION;

@Slf4j
public class GeneratorController implements Initializable, NotificationSender, TransitionedScreen {

    @FXML
    private Pane
        pnlRoot,
        pnlScreenInput,
        pnlScreenInputContent,
        pnlScreenAlgorithm,
        pnlScreenAlgorithmContent,
        pnlScreenSplash,
        pnlScreenResult;

    @FXML
    private Labeled
        lblScreenInputHeader,
        lblScreenInputContent,
        lblScreenAlgorithmHeader,
        lblScreenSplashContent,
        lblScreenResultHeader;

    @FXML
    private CheckBox
        chkMd5,
        chkSha1,
        chkSha224,
        chkSha256,
        chkSha384,
        chkSha512;

    @FXML
    private TextInputControl txtScreenResultContent;


    private Collection<NotificationReceiver> receivers;
    private Collection<Pane> screenPanes;
    private ResourceBundle language;


    @Override
    public Notification getCallerNotification() {
        return new FooterButtonActionNotification(
            new ConditionalOperation(NO_CONDITION, new GoToMainScreen()),
            new ConditionalOperation(NO_CONDITION, new GoToAlgorithmScreen())
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle language) {
        this.language = language;
        receivers = new ArrayList<>();

        screenPanes = List.of(
            pnlScreenInput,
            pnlScreenAlgorithm,
            pnlScreenSplash,
            pnlScreenResult
        );

        OperationPerformer.performAsync(new GoToInputScreen());
    }

    @FXML
    private void pnlScreenInputContentMouseClicked(MouseEvent event) {
        OperationPerformer.performAsync(
            new MouseButtonIsPrimary(event),
            new ShowOpenFileDialog(
                language.getString("hashtools.generator.generator-controller.dialog.title.open-file"),
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


    private final class GoToAlgorithmScreen implements Operation {
        @Override
        public void perform() {
            showScreen(pnlScreenAlgorithm);

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
                new ConditionalOperation(NO_CONDITION, new GoToAlgorithmScreen())
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
                language.getString("hashtools.generator.generator-controller.dialog.title.save-file"),
                System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                txtScreenResultContent.getText(),
                pnlRoot.getScene().getWindow()
            );

            sendNotification(new FooterButtonActionNotification(
                new ConditionalOperation(NO_CONDITION, new GoToAlgorithmScreen()),
                new ConditionalOperation(NO_CONDITION, saveFile)
            ));
        }
    }

    private final class RunModule implements Operation {
        @Override
        public void perform() {
            Path inputFile = Path.of(lblScreenInputContent.getText());


            String title = language.getString("hashtools.generator.generator-controller.dialog.title.warning");

            if (new FileIsRegularFileCondition(inputFile).isTrue()) {
                OperationPerformer.performAsync(new ShowMessageDialogOperation(title, language.getString("hashtools.generator.generator-controller.dialog.content.missing-file")));
                OperationPerformer.performAsync(new GoToInputScreen());
                return;
            }


            OperationPerformer.performAsync(new StartSplashScreen(pnlRoot));
            OperationPerformer.performAsync(new SendNotification(GeneratorController.this, new SplashStartNotification()));


            List<Algorithm> algorithms = pnlScreenAlgorithmContent
                .getChildren()
                .stream()
                .filter(CheckBox.class::isInstance)
                .map(CheckBox.class::cast)
                .filter(CheckBox::isSelected)
                .map(CheckBox::getText)
                .map(name -> Algorithm.from(name).orElseThrow())
                .toList();

            GeneratorRequest request = new GeneratorRequest();
            request.setInput(new FileMessageDigestUpdater(inputFile));
            request.setIdentification(new FileIdentification(inputFile));
            request.setAlgorithms(algorithms);

            GeneratorService service = new GeneratorService();
            GeneratorResponse response = service.processRequest(request);

            String result = service.formatResponse(response, new GeneratorResponseFormatter());
            txtScreenResultContent.setText(result);


            OperationPerformer.performAsync(new StopSplashScreen(pnlRoot));
            OperationPerformer.performAsync(new SendNotification(GeneratorController.this, new SplashStopNotification()));
            OperationPerformer.performAsync(new GoToResultScreen());
        }
    }
}
