package hashtools.gui.dialog;

import hashtools.core.model.FileExtension;
import javafx.application.Platform;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * <p> Handles all operations involving opening files and directories. </p>
 *
 * <p> This class must be called inside of a JavaFX Thread, otherwise an
 * exception will be thrown. </p>
 *
 * @author Adriano Siqueira
 * @version 2.0.1
 * @since 1.0.0
 */
public class FileOpenerDialog {

    private final Stage stage;

    /**
     * <p> Creates a {@link FileOpenerDialog} instance. </p>
     *
     * @throws IllegalStateException If it is not in a JavaFX thread.
     * @since 2.0.0
     */
    public FileOpenerDialog()
    throws IllegalStateException {
        if (!Platform.isFxApplicationThread()) {
            throw new IllegalStateException("This class must be called inside a JavaFX thread.");
        }

        this.stage = new Stage();
    }

    /**
     * <p> Show the dialog to open a single file. </p>
     *
     * <p> The dialog will open in user home folder. </p>
     *
     * @param title      Title of the dialog.
     * @param extensions Extension filter for the dialog. If it is null, then it will use the platform default.
     *
     * @return The selected file or null if the dialog is closed.
     *
     * @since 2.0.0
     */
    public Optional<File> openFile(String title, FileExtension... extensions) {
        return openFile(title, null, extensions);
    }

    /**
     * <p> Show the dialog to open a single file. </p>
     *
     * <p> It will try to open the dialog in the provided folder. If it fails,
     * then the dialog will open in user home folder. </p>
     *
     * @param title            Title of the dialog.
     * @param initialDirectory Where to open the dialog.
     * @param extensions       Extension filter for the dialog. If it is null, then it will use the platform default.
     *
     * @return The selected file or null if the dialog is closed.
     *
     * @since 2.0.0
     */
    public Optional<File> openFile(String title, File initialDirectory, FileExtension... extensions) {
        FileChooser chooser = getFileChooser(title, initialDirectory, extensions);
        File        file    = chooser.showOpenDialog(stage);

        stage.close();
        return Optional.ofNullable(file);
    }

    /**
     * <p> Show the dialog to open a file to save. </p>
     *
     * <p> The dialog will open in user home folder. </p>
     *
     * @param title      Title of the dialog.
     * @param extensions Extension filter for the dialog. If it is null, then it will use the platform default.
     *
     * @return The selected files or null if the dialog is closed.
     *
     * @since 2.0.0
     */
    public Optional<File> openFileToSave(String title, FileExtension... extensions) {
        return openFileToSave(title, null, extensions);
    }

    /**
     * <p> Show the dialog to open a file to save. </p>
     *
     * <p> It will try to open the dialog in the provided folder. If it fails,
     * then the dialog will open in user home folder. </p>
     *
     * @param title            Title of the dialog.
     * @param initialDirectory Where to open the dialog initially.
     * @param extensions       Extension filter for the dialog. If it is null, then it will use the platform default.
     *
     * @return The selected files or null if the dialog is closed.
     *
     * @since 2.0.0
     */
    public Optional<File> openFileToSave(String title, File initialDirectory, FileExtension... extensions) {
        FileChooser chooser = getFileChooser(title, initialDirectory, extensions);
        File        file    = chooser.showSaveDialog(stage);

        stage.close();
        return Optional.ofNullable(file);
    }

    /**
     * <p> Show the dialog to open multiple files. </p>
     *
     * <p> The dialog will open in user home folder. </p>
     *
     * @param title      Title of the dialog.
     * @param extensions Extension filter for the dialog. If it is null, then it will use the platform default.
     *
     * @return The selected files or null if the dialog is closed.
     *
     * @since 2.0.0
     */
    public List<File> openFiles(String title, FileExtension... extensions) {
        return openFiles(title, null, extensions);
    }

    /**
     * <p> Show the dialog to open multiple files. </p>
     *
     * <p> It will try to open the dialog in the provided folder. If it fails,
     * then the dialog will open in user home folder. </p>
     *
     * @param title            Title of the dialog.
     * @param initialDirectory Where to open the dialog initially.
     * @param extensions       Extension filter for the dialog. If it is null, then it will use the platform default.
     *
     * @return The selected files or null if the dialog is closed.
     *
     * @since 2.0.0
     */
    public List<File> openFiles(String title, File initialDirectory, FileExtension... extensions) {
        FileChooser chooser = getFileChooser(title, initialDirectory, extensions);
        List<File>  files   = chooser.showOpenMultipleDialog(stage);

        stage.close();
        return files;
    }

    /**
     * <p> Show the dialog to open a single folder. </p>
     *
     * <p> The dialog will open in user home folder. </p>
     *
     * @param title Title of the dialog.
     *
     * @return The selected folder or null if the dialog is closed.
     *
     * @since 2.0.0
     */
    public Optional<File> openFolder(String title) {
        return openFolder(title, null);
    }

    /**
     * <p> Show the dialog to open a single folder. </p>
     *
     * <p> It will try to open the dialog in the provided folder. If it fails,
     * then the dialog will open in user home folder. </p>
     *
     * @param title            Title of the dialog.
     * @param initialDirectory Where to open the dialog initially.
     *
     * @return The selected folder or null if the dialog is closed.
     *
     * @since 2.0.0
     */
    public Optional<File> openFolder(String title, File initialDirectory) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(title);
        chooser.setInitialDirectory(
                initialDirectory != null
                ? initialDirectory
                : new File(System.getProperty("user.home"))
        );
        File file = chooser.showDialog(stage);

        stage.close();
        return Optional.ofNullable(file);
    }

    /**
     * <p> Creates and configures a {@link FileChooser} instance. </p>
     *
     * <p> It will try to open the dialog in the provided folder. If it fails,
     * then the dialog will open in user home folder. </p>
     *
     * @param title            Title of the dialog.
     * @param initialDirectory Where to open the dialog initially.
     * @param extensions       Extension filter for the dialog. If it is null, then it will use the platform default.
     *
     * @return A FileChoose instance.
     *
     * @since 2.0.0
     */
    private FileChooser getFileChooser(String title, File initialDirectory, FileExtension... extensions) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.setInitialDirectory(
                initialDirectory != null && initialDirectory.isDirectory()
                ? initialDirectory
                : new File(System.getProperty("user.home"))
        );

        if (extensions != null && extensions.length > 0) {
            for (FileExtension extension : extensions) {
                chooser.getExtensionFilters().add(extension.getFilter());
            }
        }

        return chooser;
    }
}
