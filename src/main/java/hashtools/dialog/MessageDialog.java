package hashtools.dialog;

import hashtools.domain.Environment;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Builder
public class MessageDialog extends Dialog<ButtonType> implements DialogController {

    private static final String FXML_PATH = "/hashtools/fxml/message-dialog.fxml";
    private static final ButtonType DEFAULT_BUTTON = ButtonType.OK;

    @FXML
    public Label labelContent;

    private String title;
    private String content;
    private List<ButtonType> buttons;

    private int timeoutAmount;
    private TimeUnit timeoutUnit;
    private List<Runnable> callbacks;

    public final MessageDialog configure() {
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
        labelContent.setText(content);
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
    public static class MessageDialogBuilder {

        private MessageDialogBuilder labelContent(Label labelContent) {
            return this;
        }

        public MessageDialogBuilder timeout(int timeoutAmount, TimeUnit timeoutUnit) {
            this.timeoutAmount = timeoutAmount;
            this.timeoutUnit = timeoutUnit;
            return this;
        }

        private MessageDialogBuilder timeoutAmount(int timeoutAmount) {
            return this;
        }

        private MessageDialogBuilder timeoutUnit(TimeUnit timeoutUnit) {
            return this;
        }
    }
}
