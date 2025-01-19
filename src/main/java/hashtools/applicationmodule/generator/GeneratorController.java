package hashtools.applicationmodule.generator;

import hashtools.coremodule.checksumgenerator.Algorithm;
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
import hashtools.applicationmodule.generator.exception.InvalidAlgorithmSelectionException;
import hashtools.applicationmodule.generator.exception.MissingInputFileException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
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

public class GeneratorController implements Initializable, NotificationSender, TransitionedScreen {

    @FXML
    private Pane pnlRoot;
    @FXML
    private Pane pnlScreenInput;
    @FXML
    private Pane pnlScreenInputContent;
    @FXML
    private Pane pnlScreenAlgorithm;
    @FXML
    private Pane pnlScreenAlgorithmContent;
    @FXML
    private Pane pnlScreenSplash;
    @FXML
    private Pane pnlScreenResult;

    @FXML
    private Labeled lblScreenInputHeader;
    @FXML
    private Labeled lblScreenInputContent;
    @FXML
    private Labeled lblScreenAlgorithmHeader;
    @FXML
    private Labeled lblScreenSplashContent;
    @FXML
    private Labeled lblScreenResultHeader;

    @FXML
    private CheckBox chkMd5;
    @FXML
    private CheckBox chkSha1;
    @FXML
    private CheckBox chkSha224;
    @FXML
    private CheckBox chkSha256;
    @FXML
    private CheckBox chkSha384;
    @FXML
    private CheckBox chkSha512;

    @FXML
    private TextInputControl txtScreenResultContent;


    private Collection<NotificationReceiver> receivers;
    private Collection<Pane> screenPanes;
    private ResourceBundle generatorLanguage;
    private ResourceBundle sharedLanguage;


    @Override
    public Notification getCallerNotification() {
        return new FooterButtonActionNotification(
            new GoToMainScreen(),
            new GoToAlgorithmScreen()
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle language) {
        generatorLanguage = language;
        sharedLanguage = ResourceBundle.getBundle(Resource.Language.SHARED);
        receivers = new ArrayList<>();

        screenPanes = List.of(
            pnlScreenInput,
            pnlScreenAlgorithm,
            pnlScreenSplash,
            pnlScreenResult
        );

        Operation.perform(
            THREAD_POOL,
            new GoToInputScreen()
        );
    }

    @FXML
    private void pnlScreenInputContentMouseClicked(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event.getButton()),
                new ShowOpenFileDialogOperation(
                    generatorLanguage.getString("generator-controller.dialog.title.open-file"),
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


    private final class GoToAlgorithmScreen extends Operation {
        @Override
        protected void perform() {
            showScreen(pnlScreenAlgorithm);

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
                    new GoToAlgorithmScreen()
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
                    new GoToAlgorithmScreen(),
                    new ShowSaveFileDialogOperation(
                        generatorLanguage.getString("generator-controller.dialog.title.save-file"),
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
                    GeneratorController.this,
                    new SplashStartNotification()
                )
            );

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

                String result = service.formatResponse(response);
                txtScreenResultContent.setText(result);

                Operation.perform(
                    THREAD_POOL,
                    new GoToResultScreen()
                );
            } catch (MissingInputFileException e) {
                Operation.perform(
                    THREAD_POOL,
                    new ShowMessageDialogOperation(
                        generatorLanguage.getString("generator-controller.dialog.title.warning"),
                        generatorLanguage.getString("generator-controller.dialog.content.missing-file")
                    ),
                    new GoToInputScreen()
                );
            } catch (InvalidAlgorithmSelectionException e) {
                Operation.perform(
                    THREAD_POOL,
                    new ShowMessageDialogOperation(
                        generatorLanguage.getString("generator-controller.dialog.title.warning"),
                        generatorLanguage.getString("generator-controller.dialog.content.missing-algorithm")
                    ),
                    new GoToAlgorithmScreen()
                );
            } catch (ExecutionException | InterruptedException e) {
                StringWriter stackTraceContent = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTraceContent));

                Operation.perform(
                    THREAD_POOL,
                    new ShowMessageDialogOperation(
                        generatorLanguage.getString("checker-controller.dialog.title.warning"),
                        stackTraceContent.toString()
                    )
                );
            }

            Operation.perform(
                THREAD_POOL,
                new StopSplashOperation(pnlRoot),
                new SendNotificationOperation(
                    GeneratorController.this,
                    new SplashStopNotification()
                )
            );
        }
    }
}
