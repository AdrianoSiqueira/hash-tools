package hashtools.utility;

import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class FileManager {

    private static FileChooser createFileChooser(String title) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        return chooser;
    }

    public Optional<Path> openFile(String title, FileChooser.ExtensionFilter... filters) {
        FileChooser chooser = createFileChooser(title);
        chooser.getExtensionFilters().setAll(filters);

        return processResult(chooser.showOpenDialog(null));
    }

    private Optional<Path> processResult(File file) {
        return Optional
            .ofNullable(file)
            .map(File::toPath)
            .map(Path::toAbsolutePath);
    }

    public Optional<Path> saveFile(String title) {
        FileChooser chooser = createFileChooser(title);

        return processResult(chooser.showSaveDialog(null));
    }
}
