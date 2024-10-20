package hashtools.util;

import hashtools.condition.ThreadIsNotJavaFx;
import hashtools.operation.OperationPerformer;
import hashtools.operation.ThrowException;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
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
        OperationPerformer.perform(
            new ThreadIsNotJavaFx(),
            new ThrowException(new IllegalStateException("This method must be called within a JavaFX thread"))
        );

        FileChooser chooser = createFileChooser(title, initialDirectory);
        chooser.getExtensionFilters().setAll(filters);

        return Optional
            .ofNullable(chooser.showOpenDialog(ownerWindow))
            .map(new FileToAbsolutePath());
    }

    public static Optional<Path> showSaveDialog(String title, String initialDirectory, Window ownerWindow) {
        OperationPerformer.perform(
            new ThreadIsNotJavaFx(),
            new ThrowException(new IllegalStateException("This method must be called within a JavaFX thread"))
        );

        FileChooser chooser = createFileChooser(title, initialDirectory);

        return Optional
            .ofNullable(chooser.showSaveDialog(ownerWindow))
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
