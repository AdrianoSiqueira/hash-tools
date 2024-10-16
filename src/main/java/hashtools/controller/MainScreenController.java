package hashtools.controller;

import hashtools.condition.KeyboardKeyIsActionKey;
import hashtools.condition.KeyboardKeyIsHomeKey;
import hashtools.condition.MouseButtonIsPrimary;
import hashtools.notification.Notification;
import hashtools.notification.NotificationReceiver;
import hashtools.operation.ArmNode;
import hashtools.operation.DisarmNode;
import hashtools.operation.OpenScreen;
import hashtools.operation.Operation;
import hashtools.operation.OperationPerformer;
import hashtools.util.FXUtil;
import javafx.beans.binding.BooleanBinding;
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


    private void bindPnlRootVisibility() {
        BooleanBinding shouldBeVisible = pnlChecker
            .visibleProperty().not()
            .and(pnlComparator.visibleProperty().not())
            .and(pnlGenerator.visibleProperty().not());

        pnlMainMenu
            .visibleProperty()
            .bind(shouldBeVisible);
    }

    @FXML
    private void closeScreen(KeyEvent event) {
        // TODO Remove this method when all the three run screens are done
        OperationPerformer.performAsync(
            new KeyboardKeyIsHomeKey(event),
            new OpenMainMenu()
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        bindPnlRootVisibility();
    }


    @FXML
    private void pnlCheckerKeyPressed(KeyEvent event) {
        OperationPerformer.performAsync(
            new KeyboardKeyIsActionKey(event),
            new ArmNode(FXUtil.getNode(event))
        );
    }


    @FXML
    private void pnlComparatorKeyPressed(KeyEvent event) {
        OperationPerformer.performAsync(
            new KeyboardKeyIsActionKey(event),
            new ArmNode(FXUtil.getNode(event))
        );
    }


    @FXML
    private void pnlGeneratorKeyPressed(KeyEvent event) {
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


    private final class OpenCheckerScreen implements Operation {
        @Override
        public void perform() {
            // TODO Remove this statement when the checker screen is done
            System.out.println(getClass().getSimpleName());
            pnlChecker.setVisible(true);
        }
    }

    private final class OpenComparatorScreen implements Operation {
        @Override
        public void perform() {
            // TODO Remove this statement when the comparator screen is done
            System.out.println(getClass().getSimpleName());
            pnlComparator.setVisible(true);
        }
    }

    private final class OpenGeneratorScreen implements Operation {
        @Override
        public void perform() {
            // TODO Remove this statement when the generator screen is done
            System.out.println(getClass().getSimpleName());
            pnlGenerator.setVisible(true);
        }
    }

    private final class OpenMainMenu implements Operation {
        @Override
        public void perform() {
            // TODO Remove this class when all the three run screens are done.
            pnlChecker.setVisible(false);
            pnlComparator.setVisible(false);
            pnlGenerator.setVisible(false);
        }
    }
}
