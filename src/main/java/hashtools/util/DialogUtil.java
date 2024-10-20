package hashtools.util;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class DialogUtil {

    private static FileChooser createFileChooser(String title, String initialDirectory) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.setInitialDirectory(new File(initialDirectory));
        return chooser;
    }

    private static FileChooser createFileChooser(String title, String initialDirectory, Collection<FileChooser.ExtensionFilter> filters) {
        FileChooser chooser = createFileChooser(title, initialDirectory);
        chooser.getExtensionFilters().setAll(filters);
        return chooser;
    }

    public static Optional<Path> showOpenDialog(String title, String initialDirectory, Collection<FileChooser.ExtensionFilter> filters, Window ownerWindow) {
        AtomicReference<File> fileWrapper = new AtomicReference<>();

        Platform.runLater(() -> {
            File file = DialogUtil
                .createFileChooser(title, initialDirectory, filters)
                .showOpenDialog(ownerWindow);

            fileWrapper.set(file);
        });

        return Optional
            .ofNullable(fileWrapper.get())
            .map(new FileToAbsolutePath());
    }

    public static Optional<Path> showSaveDialog(String title, String initialDirectory, Window ownerWindow) {
        AtomicReference<File> fileWrapper = new AtomicReference<>();

        Platform.runLater(() -> {
            File file = DialogUtil
                .createFileChooser(title, initialDirectory)
                .showSaveDialog(ownerWindow);

            fileWrapper.set(file);
        });

        return Optional
            .ofNullable(fileWrapper.get())
            .map(new FileToAbsolutePath());
    }


    private static final class FileToAbsolutePath implements Function<File, Path> {
        @Override
        public Path apply(File file) {
            return file
                .toPath()
                .toAbsolutePath();
        }
    }
}
