package hashtools.application;

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
import hashtools.shared.operation.ArmNodeOperation;
import hashtools.shared.operation.ConditionalOperation;
import hashtools.shared.operation.DisarmNodeOperation;
import hashtools.shared.operation.OpenScreenOperation;
import hashtools.shared.operation.Operation;
import hashtools.shared.operation.RemoveNodeFromPaneOperation;
import hashtools.shared.operation.SetNodeToPaneOperation;
import hashtools.shared.operation.StartSplashOperation;
import hashtools.shared.operation.StopSplashOperation;
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
    private Pane pnlRoot;
    @FXML
    private Pane pnlContent;
    @FXML
    private Pane pnlFooter;
    @FXML
    private Pane pnlMenu;
    @FXML
    private Pane pnlMenuChecker;
    @FXML
    private Pane pnlMenuComparator;
    @FXML
    private Pane pnlMenuGenerator;

    @FXML
    private Button btnFooterBack;
    @FXML
    private Button btnFooterNext;

    @FXML
    private Labeled lblFooterSpacer;

    private Operation btnFooterBackAction;
    private Operation btnFooterNextAction;

    private NotificationHandler notificationHandler;


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
        notificationHandler = new NotificationHandler();

        btnFooterBackAction = Resource.StaticImplementation.NO_OPERATION;
        btnFooterNextAction = Resource.StaticImplementation.NO_OPERATION;

        Operation.perform(
            THREAD_POOL,
            new RemoveNodeFromPaneOperation(pnlRoot, pnlFooter)
        );
    }

    @FXML
    private void pnlMenuCheckerKeyPressed(KeyEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new KeyboardKeyIsActionKeyCondition(event.getCode()),
                new ArmNodeOperation(pnlMenuChecker)
            )
        );
    }

    @FXML
    private void pnlMenuCheckerKeyReleased(KeyEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new KeyboardKeyIsActionKeyCondition(event.getCode()),
                new DisarmNodeOperation(pnlMenuChecker)
            ),
            new ConditionalOperation(
                new KeyboardKeyIsActionKeyCondition(event.getCode()),
                new OpenScreenOperation(this, Resource.FXMLPath.CHECKER_SCREEN, ResourceBundle.getBundle(Resource.ResourceBundle.LANGUAGE), pnlContent)
            )
        );
    }

    @FXML
    private void pnlMenuCheckerMouseClicked(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event.getButton()),
                new OpenScreenOperation(this, Resource.FXMLPath.CHECKER_SCREEN, ResourceBundle.getBundle(Resource.ResourceBundle.LANGUAGE), pnlContent)
            )
        );
    }

    @FXML
    private void pnlMenuCheckerMousePressed(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event.getButton()),
                new ArmNodeOperation(pnlMenuChecker)
            )
        );
    }

    @FXML
    private void pnlMenuCheckerMouseReleased(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event.getButton()),
                new DisarmNodeOperation(pnlMenuChecker)
            )
        );
    }

    @FXML
    private void pnlMenuComparatorKeyPressed(KeyEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new KeyboardKeyIsActionKeyCondition(event.getCode()),
                new ArmNodeOperation(pnlMenuComparator)
            )
        );
    }

    @FXML
    private void pnlMenuComparatorKeyReleased(KeyEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new KeyboardKeyIsActionKeyCondition(event.getCode()),
                new DisarmNodeOperation(pnlMenuComparator)
            ),
            new ConditionalOperation(
                new KeyboardKeyIsActionKeyCondition(event.getCode()),
                new OpenScreenOperation(this, Resource.FXMLPath.COMPARATOR_SCREEN, ResourceBundle.getBundle(Resource.ResourceBundle.LANGUAGE), pnlContent)
            )
        );
    }

    @FXML
    private void pnlMenuComparatorMouseClicked(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event.getButton()),
                new OpenScreenOperation(this, Resource.FXMLPath.COMPARATOR_SCREEN, ResourceBundle.getBundle(Resource.ResourceBundle.LANGUAGE), pnlContent)
            )
        );
    }

    @FXML
    private void pnlMenuComparatorMousePressed(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event.getButton()),
                new ArmNodeOperation(pnlMenuComparator)
            )
        );
    }

    @FXML
    private void pnlMenuComparatorMouseReleased(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event.getButton()),
                new DisarmNodeOperation(pnlMenuComparator)
            )
        );
    }

    @FXML
    private void pnlMenuGeneratorKeyPressed(KeyEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new KeyboardKeyIsActionKeyCondition(event.getCode()),
                new ArmNodeOperation(pnlMenuGenerator)
            )
        );
    }

    @FXML
    private void pnlMenuGeneratorKeyReleased(KeyEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new KeyboardKeyIsActionKeyCondition(event.getCode()),
                new DisarmNodeOperation(pnlMenuGenerator)
            ),
            new ConditionalOperation(
                new KeyboardKeyIsActionKeyCondition(event.getCode()),
                new OpenScreenOperation(this, Resource.FXMLPath.GENERATOR_SCREEN, ResourceBundle.getBundle(Resource.ResourceBundle.LANGUAGE), pnlContent)
            )
        );
    }

    @FXML
    private void pnlMenuGeneratorMouseClicked(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event.getButton()),
                new OpenScreenOperation(this, Resource.FXMLPath.GENERATOR_SCREEN, ResourceBundle.getBundle(Resource.ResourceBundle.LANGUAGE), pnlContent)
            )
        );
    }

    @FXML
    private void pnlMenuGeneratorMousePressed(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event.getButton()),
                new ArmNodeOperation(pnlMenuGenerator)
            )
        );
    }

    @FXML
    private void pnlMenuGeneratorMouseReleased(MouseEvent event) {
        Operation.perform(
            THREAD_POOL,
            new ConditionalOperation(
                new MouseButtonIsPrimaryCondition(event.getButton()),
                new DisarmNodeOperation(pnlMenuGenerator)
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
                new StartSplashOperation(pnlFooter)
            );
        }

        public void handle(SplashStopNotification notification) {
            Operation.perform(
                THREAD_POOL,
                new StopSplashOperation(pnlFooter)
            );
        }
    }
}
