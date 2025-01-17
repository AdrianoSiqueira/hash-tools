package hashtools.coremodule.operation;

import hashtools.coremodule.DialogUtil;
import javafx.application.Platform;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShowMessageDialogOperation extends Operation {

    private final String title;
    private final String message;

    @Override
    protected void perform() {
        Platform.runLater(() -> DialogUtil.showMessageDialog(title, message));
    }
}
