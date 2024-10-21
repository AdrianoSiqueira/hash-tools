package hashtools.util;

import hashtools.condition.ThreadIsNotJavaFx;
import hashtools.operation.OperationPerformer;
import hashtools.operation.ThrowException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

    public static void showMessageDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }

    public static Optional<Path> showOpenDialog(String title, String initialDirectory, Collection<FileChooser.ExtensionFilter> filters, Window ownerWindow) {
        OperationPerformer.perform(
            new ThreadIsNotJavaFx(),
            new ThrowException(new IllegalStateException("This method must be called within a JavaFX thread"))
        );

        return Optional
            .of(createFileChooser(title, initialDirectory, filters))
            .map(chooser -> chooser.showOpenDialog(ownerWindow))
            .map(new FileToAbsolutePath());
    }

    public static Optional<Path> showSaveDialog(String title, String initialDirectory, Window ownerWindow) {
        OperationPerformer.perform(
            new ThreadIsNotJavaFx(),
            new ThrowException(new IllegalStateException("This method must be called within a JavaFX thread"))
        );

        return Optional
            .of(createFileChooser(title, initialDirectory))
            .map(chooser -> chooser.showSaveDialog(ownerWindow))
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
