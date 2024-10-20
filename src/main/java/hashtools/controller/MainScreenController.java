package hashtools.controller;

import hashtools.condition.KeyboardKeyIsActionKey;
import hashtools.condition.MouseButtonIsPrimary;
import hashtools.domain.Resource;
import hashtools.notification.FooterButtonActionNotification;
import hashtools.notification.Notification;
import hashtools.notification.NotificationReceiver;
import hashtools.notification.ScreenCloseNotification;
import hashtools.notification.ScreenOpenNotification;
import hashtools.notification.SplashStartNotification;
import hashtools.notification.SplashStopNotification;
import hashtools.operation.ArmNode;
import hashtools.operation.ConditionalOperation;
import hashtools.operation.DisableChildren;
import hashtools.operation.DisarmNode;
import hashtools.operation.EnableChildren;
import hashtools.operation.HideNode;
import hashtools.operation.OpenScreen;
import hashtools.operation.OperationPerformer;
import hashtools.operation.SetPaneChildren;
import hashtools.operation.ShowNode;
import hashtools.util.FXUtil;
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

public class MainScreenController implements Initializable, NotificationReceiver {

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


    @FXML
    private void btnFooterBackAction(ActionEvent event) {
        OperationPerformer.perform(btnFooterBackAction);
    }

    @FXML
    private void btnFooterNextAction(ActionEvent event) {
        OperationPerformer.performAsync(btnFooterNextAction);
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
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
            new OpenScreen(this, FXUtil.getUserData(event, String.class), pnlContent)
        );
    }

    @FXML
    private void pnlMenuItemMouseClicked(MouseEvent event) {
        OperationPerformer.performAsync(
            new MouseButtonIsPrimary(event),
            new OpenScreen(this, FXUtil.getUserData(event, String.class), pnlContent)
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
            OperationPerformer.performAsync(new DisableChildren(pnlFooter));
        }

        public void handle(SplashStopNotification notification) {
            OperationPerformer.performAsync(new EnableChildren(pnlFooter));
        }
    }
}
