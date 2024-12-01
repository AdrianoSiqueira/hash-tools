package hashtools.shared.operation;

import hashtools.shared.DialogUtil;
import javafx.application.Platform;
import javafx.scene.control.Labeled;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.util.Collection;

@RequiredArgsConstructor
public class ShowOpenFileDialog extends Operation {

    private final String title;
    private final String initialDirectory;
    private final Collection<FileChooser.ExtensionFilter> filters;
    private final Labeled labeled;
    private final Window ownerWindow;

    @Override
    protected void perform() {
        Platform.runLater(() -> DialogUtil
            .showOpenDialog(title, initialDirectory, filters, ownerWindow)
            .map(Path::toString)
            .ifPresent(labeled::setText)
        );
    }
}
