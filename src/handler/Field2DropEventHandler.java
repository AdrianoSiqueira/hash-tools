package handler;

import aslib.filemanager.FileExtension;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import language.LanguageManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class Field2DropEventHandler implements EventHandler<DragEvent> {

    private final Logger logger = Logger.getGlobal();

    @Override
    public void handle(DragEvent event) {
        if (!(event.getSource() instanceof TextField)) {
            logger.warning(LanguageManager.get("Source.is.not.a.TextField.instance."));
            return;
        }

        Dragboard dragboard = event.getDragboard();
        TextField field     = (TextField) event.getSource();
        String    content   = "";

        if (field.isEditable()) {
            if (dragboard.hasFiles()) {
                Path path = dragboard.getFiles().get(0).toPath();

                if (Files.isRegularFile(path)) {
                    String fileName = path.getFileName().toString();
                    int    index    = fileName.lastIndexOf('.');

                    if (index > 0) {
                        String fileExtension = "*" + fileName.substring(index)
                                                             .toLowerCase();
                        FileExtension hashExtension = FileExtension.HASH;
                        boolean       valid         = false;

                        for (String extension : hashExtension.getExtensions()) {
                            if (extension.equals(fileExtension)) {
                                content = path.toAbsolutePath()
                                              .toString();
                                valid   = true;
                                break;
                            }
                        }

                        if (!valid) {
                            logger.warning(LanguageManager.get("File.type.is.not.valid."));
                        }
                    } else {
                        logger.warning(LanguageManager.get("File.must.contains.an.extension."));
                    }
                } else {
                    logger.warning(LanguageManager.get("Directories.are.not.allowed."));
                }
            } else {
                content = dragboard.getString();
            }
        } else {
            if (dragboard.hasFiles()) {
                Path path = dragboard.getFiles().get(0).toPath();

                if (Files.isRegularFile(path)) {
                    content = path.toAbsolutePath().toString();
                } else {
                    logger.warning(LanguageManager.get("Directories.are.not.allowed."));
                }
            } else {
                logger.warning(LanguageManager.get("Only.regular.files.with.extension.are.allowed.in.generator.mode."));
            }
        }

        field.setText(content);
    }
}