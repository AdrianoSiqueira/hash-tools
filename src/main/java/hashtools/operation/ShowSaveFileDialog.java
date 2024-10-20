package hashtools.operation;

import hashtools.util.DialogUtil;
import hashtools.util.FileUtil;
import javafx.stage.Window;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShowSaveFileDialog implements Operation {

    private final String title;
    private final String initialDirectory;
    private final String content;
    private final Window ownerWindow;

    @Override
    public void perform() {
        DialogUtil
            .showSaveDialog(title, initialDirectory, ownerWindow)
            .ifPresent(file -> FileUtil.replaceContent(content, file));
    }
}
