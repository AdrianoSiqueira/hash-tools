package hashtools.controller;

import hashtools.condition.KeyboardKeyIsActionKey;
import hashtools.condition.MouseButtonIsPrimary;
import hashtools.domain.Resource;
import hashtools.notification.Notification;
import hashtools.notification.NotificationReceiver;
import hashtools.notification.ScreenCloseNotification;
import hashtools.notification.ScreenOpenNotification;
import hashtools.operation.ArmNode;
import hashtools.operation.DisarmNode;
import hashtools.operation.OpenScreen;
import hashtools.operation.Operation;
import hashtools.operation.OperationPerformer;
import hashtools.util.FXUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    private EventHandler<ActionEvent>
        btnFooterBackAction,
        btnFooterNextAction;

    private NotificationHandler notificationHandler;


    @FXML
    private void btnFooterBackAction(ActionEvent event) {
        btnFooterBackAction.handle(event);
    }

    @FXML
    private void btnFooterNextAction(ActionEvent event) {
        btnFooterNextAction.handle(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        notificationHandler = new NotificationHandler();

        btnFooterBackAction = Resource.EventHandler.NO_ACTION_EVENT;
        btnFooterNextAction = Resource.EventHandler.NO_ACTION_EVENT;

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
            new OpenScreen(this, FXUtil.getFXMLPath(event), pnlContent)
        );
    }

    @FXML
    private void pnlMenuItemMouseClicked(MouseEvent event) {
        OperationPerformer.performAsync(
            new MouseButtonIsPrimary(event),
            new OpenScreen(this, FXUtil.getFXMLPath(event), pnlContent)
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
            case ScreenCloseNotification n -> notificationHandler.handle(n);
            case ScreenOpenNotification n -> notificationHandler.handle(n);
            default -> {}
        }
    }


    private final class NotificationHandler {

        public void handle(ScreenCloseNotification notification) {
            OperationPerformer.performAsync(new OpenMainMenu());
        }

        public void handle(ScreenOpenNotification notification) {
            OperationPerformer.performAsync(new ShowFooter());
        }
    }

    private final class OpenMainMenu implements Operation {
        @Override
        public void perform() {
            pnlFooter.setVisible(false);

            pnlContent
                .getChildren()
                .setAll(pnlMenu);
        }
    }

    private final class ShowFooter implements Operation {
        @Override
        public void perform() {
            pnlFooter.setVisible(true);
        }
    }
}
