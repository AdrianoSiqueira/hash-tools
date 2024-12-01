package hashtools.generator;

import hashtools.generator.exception.InvalidAlgorithmSelectionException;
import hashtools.generator.exception.MissingInputFileException;
import hashtools.shared.Algorithm;
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

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import static hashtools.shared.Resource.Software.THREAD_POOL;

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
            new GoToMainScreen(),
            new GoToAlgorithmScreen()
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

        Operation.perform(new GoToInputScreen(), THREAD_POOL);
    }

    @FXML
    private void pnlScreenInputContentMouseClicked(MouseEvent event) {
        Operation.perform(
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event),
                new ShowOpenFileDialog(
                    language.getString("hashtools.generator.generator-controller.dialog.title.open-file"),
                    System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                    Extension.getAllExtensions(language),
                    lblScreenInputContent,
                    pnlRoot.getScene().getWindow()
                )
            ),
            THREAD_POOL
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


    private final class GoToAlgorithmScreen extends Operation {
        @Override
        protected void perform() {
            showScreen(pnlScreenAlgorithm);

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
                new GoToAlgorithmScreen()
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
                language.getString("hashtools.generator.generator-controller.dialog.title.save-file"),
                System.getProperty(Resource.PropertyKey.HOME_DIRECTORY),
                txtScreenResultContent.getText(),
                pnlRoot.getScene().getWindow()
            );

            sendNotification(new FooterButtonActionNotification(
                new GoToAlgorithmScreen(),
                saveFile
            ));
        }
    }

    private final class RunModule extends Operation {
        @Override
        protected void perform() {
            String dialogTitle = language.getString("hashtools.generator.generator-controller.dialog.title.warning");

            Operation.perform(new StartSplashScreen(pnlRoot), THREAD_POOL);
            Operation.perform(new SendNotification(GeneratorController.this, new SplashStartNotification()), THREAD_POOL);

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
            request.setInputFile(Path.of(lblScreenInputContent.getText()));
            request.setAlgorithms(algorithms);

            try {
                GeneratorService service = new GeneratorService();
                GeneratorResponse response = service.processRequest(request);

                String result = service.formatResponse(response, new GeneratorResponseFormatter());
                txtScreenResultContent.setText(result);

                Operation.perform(new GoToResultScreen(), THREAD_POOL);
            } catch (MissingInputFileException e) {
                Operation.perform(new ShowMessageDialogOperation(dialogTitle, language.getString("hashtools.generator.generator-controller.dialog.content.missing-file")), THREAD_POOL);
                Operation.perform(new GoToInputScreen(), THREAD_POOL);
            } catch (InvalidAlgorithmSelectionException e) {
                Operation.perform(new ShowMessageDialogOperation(dialogTitle, language.getString("hashtools.generator.generator-controller.dialog.content.missing-algorithm")), THREAD_POOL);
                Operation.perform(new GoToAlgorithmScreen(), THREAD_POOL);
            }

            Operation.perform(new StopSplashScreen(pnlRoot), THREAD_POOL);
            Operation.perform(new SendNotification(GeneratorController.this, new SplashStopNotification()), THREAD_POOL);
        }
    }
}
