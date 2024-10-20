package hashtools.operation;

import hashtools.util.DialogUtil;
import javafx.application.Platform;
import javafx.scene.control.Labeled;
import javafx.stage.Window;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

@RequiredArgsConstructor
public class ShowSaveFileDialog implements Operation {

    private final String title;
    private final String initialDirectory;
    private final Labeled labeled;
    private final Window ownerWindow;

    @Override
    public void perform() {
        Platform.runLater(() -> DialogUtil
            .showSaveDialog(title, initialDirectory, ownerWindow)
            .map(Path::toString)
            .ifPresent(labeled::setText)
        );
    }
}
