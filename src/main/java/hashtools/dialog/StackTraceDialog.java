package hashtools.dialog;

import hashtools.domain.Environment;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.stage.Screen;
import lombok.Builder;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Builder
public class StackTraceDialog extends Dialog<ButtonType> implements DialogController {

    private static final String FXML_PATH = "/hashtools/fxml/stack-trace-dialog.fxml";
    private static final ButtonType DEFAULT_BUTTON = ButtonType.OK;

    @FXML
    private TextArea areaContent;

    private String title;
    private Throwable content;
    private List<ButtonType> buttons;

    private int timeoutAmount;
    private TimeUnit timeoutUnit;
    private List<Runnable> callbacks;

    public final StackTraceDialog configure() {
        configureDialogPane();
        configureTitle();
        configureContent();
        configureButtons();
        configureCallbacks();
        configureSize();
        configureTimeout();
        return this;
    }

    private void configureButtons() {
        var buttonList = Optional
            .ofNullable(buttons)
            .orElse(List.of())
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toCollection(ArrayList::new));

        if (buttonList.isEmpty()) {
            buttonList.add(DEFAULT_BUTTON);
        }

        getDialogPane()
            .getButtonTypes()
            .setAll(buttonList);
    }

    private void configureCallbacks() {
        if (callbacks == null) {
            return;
        }

        callbacks.forEach(Runnable::run);
    }

    private void configureContent() {
        if (content == null) {
            return;
        }

        var stringWriter = new StringWriter();
        var printWriter = new PrintWriter(stringWriter);

        content.printStackTrace(printWriter);
        areaContent.setText(stringWriter.toString());
    }

    private void configureDialogPane() {
        setDialogPane(createDialogPane(FXML_PATH, this));
    }

    private void configureSize() {
        setResizable(true);

        var prefWidth = Screen
            .getPrimary()
            .getBounds()
            .getWidth()
            * 0.25;

        getDialogPane()
            .setPrefWidth(prefWidth);
    }

    private void configureTimeout() {
        if (timeoutUnit == null) {
            return;
        }

        Environment.Software.THREAD_POOL.execute(() -> {
            try {
                timeoutUnit.sleep(timeoutAmount);
                Platform.runLater(this::close);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void configureTitle() {
        setTitle(title);
    }


    @SuppressWarnings("unused")
    public static class StackTraceDialogBuilder {

        private StackTraceDialogBuilder areaContent(TextArea areaContent) {
            return this;
        }

        public StackTraceDialogBuilder timeout(int timeoutAmount, TimeUnit timeoutUnit) {
            this.timeoutAmount = timeoutAmount;
            this.timeoutUnit = timeoutUnit;
            return this;
        }

        private StackTraceDialogBuilder timeoutAmount(int timeoutAmount) {
            return this;
        }

        private StackTraceDialogBuilder timeoutUnit(TimeUnit timeoutUnit) {
            return this;
        }
    }
}
