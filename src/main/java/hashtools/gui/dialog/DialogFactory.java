package hashtools.gui.dialog;

import aslib.fx.dialog.MessageDialogBuilder;
import aslib.fx.dialog.StackTraceDialogBuilder;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

/**
 * <p>
 * Abstraction layer to instantiate message dialogs. The classes within extends
 * {@link MessageDialogBuilder} and {@link StackTraceDialogBuilder} allowing
 * the usage of custom stylesheet.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class DialogFactory {

    private static Alert configureDialog(Alert alert) {
        DialogPane pane = alert.getDialogPane();

        pane.getStyleClass()
            .add("dialog-pane");

        pane.getStylesheets()
            .add(String.valueOf(DialogFactory.class.getResource("/hashtools/gui/dialog/AlertStyle.css")));

        pane.setId(switch (alert.getAlertType()) {
            case NONE -> "none";
            case INFORMATION -> "information";
            case WARNING -> "warning";
            case CONFIRMATION -> "confirmation";
            case ERROR -> "error";
        });

        return alert;
    }


    public static class MessageDialog extends MessageDialogBuilder {
        @Override
        public Alert build() {
            return configureDialog(super.build());
        }
    }

    public static class StackTraceDialog extends StackTraceDialogBuilder {
        @Override
        public Alert build() {
            return configureDialog(super.build());
        }
    }
}
