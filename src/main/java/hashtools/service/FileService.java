package hashtools.service;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Optional;

public class FileService {

    private FileChooser createFileChooser(String title, String initialDirectory) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(new File(initialDirectory));
        return fileChooser;
    }

    private Path fileToAbsolutePath(File file) {
        return file
            .toPath()
            .toAbsolutePath();
    }

    public final Optional<Path> openFile(String title, String initialDirectory, Collection<FileChooser.ExtensionFilter> filters, Window ownerWindow) {
        if (!Platform.isFxApplicationThread()) {
            throw new IllegalStateException("This method must be called within a JavaFX thread");
        }

        FileChooser chooser = createFileChooser(title, initialDirectory);
        chooser.getExtensionFilters().setAll(filters);

        return Optional
            .ofNullable(chooser.showOpenDialog(ownerWindow))
            .map(this::fileToAbsolutePath);
    }

    public final Optional<Path> openFileToSave(String title, String initialDirectory, Window ownerWindow) {
        if (!Platform.isFxApplicationThread()) {
            throw new IllegalStateException("This method must be called within a JavaFX thread");
        }

        FileChooser chooser = createFileChooser(title, initialDirectory);

        return Optional
            .ofNullable(chooser.showSaveDialog(ownerWindow))
            .map(this::fileToAbsolutePath);
    }

    public final void replaceContent(String content, Path file) {
        try {
            Files.writeString(
                file,
                content,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
