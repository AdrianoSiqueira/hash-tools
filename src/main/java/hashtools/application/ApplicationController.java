package hashtools.application;

import hashtools.shared.JavaFXUtil;
import hashtools.shared.Resource;
import hashtools.shared.condition.KeyboardKeyIsActionKeyCondition;
import hashtools.shared.condition.MouseButtonIsPrimaryCondition;
import hashtools.shared.notification.FooterButtonActionNotification;
import hashtools.shared.notification.Notification;
import hashtools.shared.notification.NotificationReceiver;
import hashtools.shared.notification.ScreenCloseNotification;
import hashtools.shared.notification.ScreenOpenNotification;
import hashtools.shared.notification.SplashStartNotification;
import hashtools.shared.notification.SplashStopNotification;
import hashtools.shared.operation.AddNodeToPaneOperation;
import hashtools.shared.operation.ArmNode;
import hashtools.shared.operation.ConditionalOperation;
import hashtools.shared.operation.DisarmNode;
import hashtools.shared.operation.OpenScreen;
import hashtools.shared.operation.Operation;
import hashtools.shared.operation.RemoveNodeFromPaneOperation;
import hashtools.shared.operation.SetNodeToPaneOperation;
import hashtools.shared.operation.StartSplashScreen;
import hashtools.shared.operation.StopSplashScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

import static hashtools.shared.Resource.Software.THREAD_POOL;

public class ApplicationController implements Initializable, NotificationReceiver {

    @FXML
    private Pane
        pnlRoot,
        pnlContent,
        pnlFooter,
        pnlMenu,
        pnlMenuChecker,
        pnlMenuComparator,
        pnlMenuGenerator;

    @FXML
    private Button
        btnFooterBack,
        btnFooterNext;

    @FXML
    private Labeled lblFooterSpacer;

    private Operation
        btnFooterBackAction,
        btnFooterNextAction;

    private NotificationHandler notificationHandler;

    private ResourceBundle language;


    @FXML
    private void btnFooterBackAction(ActionEvent event) {
        Operation.perform(
            THREAD_POOL,
            btnFooterBackAction
        );
    }

    @FXML
    private void btnFooterNextAction(ActionEvent event) {
        Operation.perform(
            THREAD_POOL,
            btnFooterNextAction
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle language) {
        this.language = language;
        notificationHandler = new NotificationHandler();

        btnFooterBackAction = Resource.StaticImplementation.NO_OPERATION;
        btnFooterNextAction = Resource.StaticImplementation.NO_OPERATION;

        pnlMenuChecker.setUserData(Resource.FXMLPath.CHECKER_SCREEN);
        pnlMenuComparator.setUserData(Resource.FXMLPath.COMPARATOR_SCREEN);
        pnlMenuGenerator.setUserData(Resource.FXMLPath.GENERATOR_SCREEN);

        Operation.perform(
            THREAD_POOL,
            new RemoveNodeFromPaneOperation(pnlRoot, pnlFooter)
        );
    }

    @FXML
    private void pnlMenuItemKeyPressed(KeyEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new KeyboardKeyIsActionKeyCondition(event),
                new ArmNode(JavaFXUtil.getNode(event))
            )
        );
    }

    @FXML
    private void pnlMenuItemKeyReleased(KeyEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new KeyboardKeyIsActionKeyCondition(event),
                new DisarmNode(JavaFXUtil.getNode(event))
            ),
            new ConditionalOperation(
                new KeyboardKeyIsActionKeyCondition(event),
                new OpenScreen(this, JavaFXUtil.getUserData(event, String.class), language, pnlContent)
            )
        );
    }

    @FXML
    private void pnlMenuItemMouseClicked(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event),
                new OpenScreen(this, JavaFXUtil.getUserData(event, String.class), language, pnlContent)
            )
        );
    }

    @FXML
    private void pnlMenuItemMousePressed(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event),
                new ArmNode(JavaFXUtil.getNode(event))
            )
        );
    }

    @FXML
    private void pnlMenuItemMouseReleased(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event),
                new DisarmNode(JavaFXUtil.getNode(event))
            )
        );
    }

    @Override
    public void receiveNotification(Notification notification) {
        switch (notification) {
            case FooterButtonActionNotification n -> notificationHandler.handle(n);
            case ScreenCloseNotification n -> notificationHandler.handle(n);
            case ScreenOpenNotification n -> notificationHandler.handle(n);
            case SplashStartNotification n -> notificationHandler.handle(n);
            case SplashStopNotification n -> notificationHandler.handle(n);
            default -> {}
        }
    }


    private final class NotificationHandler {

        public void handle(FooterButtonActionNotification notification) {
            btnFooterBackAction = notification.getBtnFooterBackAction();
            btnFooterNextAction = notification.getBtnFooterNextAction();
        }

        public void handle(ScreenCloseNotification notification) {
            Operation.perform(
                THREAD_POOL,
                new SetNodeToPaneOperation(pnlContent, pnlMenu),
                new RemoveNodeFromPaneOperation(pnlRoot, pnlFooter)
            );
        }

        public void handle(ScreenOpenNotification notification) {
            Operation.perform(
                THREAD_POOL,
                new AddNodeToPaneOperation(pnlRoot, pnlFooter)
            );
        }

        public void handle(SplashStartNotification notification) {
            Operation.perform(
                THREAD_POOL,
                new StartSplashScreen(pnlFooter)
            );
        }

        public void handle(SplashStopNotification notification) {
            Operation.perform(
                THREAD_POOL,
                new StopSplashScreen(pnlFooter)
            );
        }
    }
}
