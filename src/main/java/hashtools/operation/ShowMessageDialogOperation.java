package hashtools.operation;

import hashtools.util.DialogUtil;
import javafx.application.Platform;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShowMessageDialogOperation implements Operation {

    private final String title;
    private final String message;

    @Override
    public void perform() {
        Platform.runLater(() -> DialogUtil.showMessageDialog(title, message));
    }
}
