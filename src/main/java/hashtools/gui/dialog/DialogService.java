package hashtools.gui.dialog;

import javafx.scene.control.Alert;

/**
 * <p>
 * Abstraction layer to create an {@link Alert} instance using the
 * {@link AlertBuilder} class.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class DialogService {

    public static Alert showMessageDialog(String title, String header, String content) {
        return createAlertBuilderInstance(title, header, content)
                .build();
    }

    public static Alert showStackTraceDialog(String title, String header, String content, Throwable throwable) {
        return createAlertBuilderInstance(title, header, content)
                .throwable(throwable)
                .build();
    }

    private static AlertBuilder createAlertBuilderInstance(String title, String header, String content) {
        return new AlertBuilder()
                .title(title)
                .headerText(header)
                .contentText(content);
    }
}
