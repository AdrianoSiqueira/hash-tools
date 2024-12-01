package hashtools.shared.operation;

import hashtools.shared.DialogUtil;
import hashtools.shared.FileUtil;
import javafx.application.Platform;
import javafx.stage.Window;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShowSaveFileDialog extends Operation {

    private final String title;
    private final String initialDirectory;
    private final String content;
    private final Window ownerWindow;

    @Override
    protected void perform() {
        Platform.runLater(() -> DialogUtil
            .showSaveDialog(title, initialDirectory, ownerWindow)
            .ifPresent(file -> FileUtil.replaceContent(content, file))
        );
    }
}
