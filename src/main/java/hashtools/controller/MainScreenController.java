package hashtools.controller;

import hashtools.condition.KeyboardKeyIsActionKey;
import hashtools.condition.MouseButtonIsPrimary;
import hashtools.notification.Notification;
import hashtools.notification.NotificationReceiver;
import hashtools.operation.ArmNode;
import hashtools.operation.DisarmNode;
import hashtools.operation.OpenScreen;
import hashtools.operation.Operation;
import hashtools.operation.OperationPerformer;
import hashtools.util.FXUtil;
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
        pnlMenuGenerator,
        pnlMainMenu,
        pnlChecker,
        pnlComparator,
        pnlGenerator;

    @FXML
    private Button
        btnFooterBack,
        btnFooterNext;

    @FXML
    private Labeled lblFooterSpacer;


    @Override
    public void initialize(URL url, ResourceBundle bundle) {

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
    }


    private final class OpenMainMenu implements Operation {
        @Override
        public void perform() {
            pnlContent
                .getChildren()
                .setAll(pnlMenu);
        }
    }
}
