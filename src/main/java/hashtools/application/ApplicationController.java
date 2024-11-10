package hashtools.application;

import hashtools.shared.condition.KeyboardKeyIsActionKey;
import hashtools.shared.condition.MouseButtonIsPrimary;
import hashtools.shared.Resource;
import hashtools.shared.notification.FooterButtonActionNotification;
import hashtools.shared.notification.Notification;
import hashtools.shared.notification.NotificationReceiver;
import hashtools.shared.notification.ScreenCloseNotification;
import hashtools.shared.notification.ScreenOpenNotification;
import hashtools.shared.notification.SplashStartNotification;
import hashtools.shared.notification.SplashStopNotification;
import hashtools.shared.operation.ArmNode;
import hashtools.shared.operation.ConditionalOperation;
import hashtools.shared.operation.DisarmNode;
import hashtools.shared.operation.HideNode;
import hashtools.shared.operation.OpenScreen;
import hashtools.shared.operation.OperationPerformer;
import hashtools.shared.operation.SetPaneChildren;
import hashtools.shared.operation.ShowNode;
import hashtools.shared.operation.StartSplashScreen;
import hashtools.shared.operation.StopSplashScreen;
import hashtools.shared.FXUtil;
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

    private ConditionalOperation
        btnFooterBackAction,
        btnFooterNextAction;

    private NotificationHandler notificationHandler;

    private ResourceBundle language;


    @FXML
    private void btnFooterBackAction(ActionEvent event) {
        OperationPerformer.perform(btnFooterBackAction);
    }

    @FXML
    private void btnFooterNextAction(ActionEvent event) {
        OperationPerformer.performAsync(btnFooterNextAction);
    }

    @Override
    public void initialize(URL url, ResourceBundle language) {
        this.language = language;
        notificationHandler = new NotificationHandler();

        btnFooterBackAction = Resource.ConditionalOperation.NO_ACTION;
        btnFooterNextAction = Resource.ConditionalOperation.NO_ACTION;

        pnlMenuChecker.setUserData(Resource.FXMLPath.CHECKER_SCREEN);
        pnlMenuComparator.setUserData(Resource.FXMLPath.COMPARATOR_SCREEN);
        pnlMenuGenerator.setUserData(Resource.FXMLPath.GENERATOR_SCREEN);
    }

    @FXML
    private void pnlMenuItemKeyPressed(KeyEvent event) {
        OperationPerformer.performAsync(
            new KeyboardKeyIsActionKey(event),
            new ArmNode(FXUtil.getNode(event))
        );
    }

    @FXML
    private void pnlMenuItemKeyReleased(KeyEvent event) {
        OperationPerformer.performAsync(
            new KeyboardKeyIsActionKey(event),
            new DisarmNode(FXUtil.getNode(event))
        );

        OperationPerformer.performAsync(
            new KeyboardKeyIsActionKey(event),
            new OpenScreen(this, FXUtil.getUserData(event, String.class), language, pnlContent)
        );
    }

    @FXML
    private void pnlMenuItemMouseClicked(MouseEvent event) {
        OperationPerformer.performAsync(
            new MouseButtonIsPrimary(event),
            new OpenScreen(this, FXUtil.getUserData(event, String.class), language, pnlContent)
        );
    }

    @FXML
    private void pnlMenuItemMousePressed(MouseEvent event) {
        OperationPerformer.performAsync(
            new MouseButtonIsPrimary(event),
            new ArmNode(FXUtil.getNode(event))
        );
    }

    @FXML
    private void pnlMenuItemMouseReleased(MouseEvent event) {
        OperationPerformer.performAsync(
            new MouseButtonIsPrimary(event),
            new DisarmNode(FXUtil.getNode(event))
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
            OperationPerformer.performAsync(new SetPaneChildren(pnlContent, pnlMenu));
            OperationPerformer.performAsync(new HideNode(pnlFooter));
        }

        public void handle(ScreenOpenNotification notification) {
            OperationPerformer.performAsync(new ShowNode(pnlFooter));
        }

        public void handle(SplashStartNotification notification) {
            OperationPerformer.performAsync(new StartSplashScreen(pnlFooter));
        }

        public void handle(SplashStopNotification notification) {
            OperationPerformer.performAsync(new StopSplashScreen(pnlFooter));
        }
    }
}
