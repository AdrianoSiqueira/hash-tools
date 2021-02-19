package handler;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import language.LanguageManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class Field1DropEventHandler implements EventHandler<DragEvent> {

    private final Logger logger = Logger.getGlobal();

    @Override
    public void handle(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        String    content   = "";

        if (dragboard.hasFiles()) {
            Path path = dragboard.getFiles()
                                 .get(0)
                                 .toPath();

            if (Files.isRegularFile(path)) {
                content = path.toAbsolutePath()
                              .toString();
            } else {
                logger.warning(LanguageManager.get("Directories.are.not.allowed."));
            }
        } else {
            content = dragboard.getString();
        }

        if (event.getSource() instanceof TextField) {
            TextField field = (TextField) event.getSource();
            field.setText(content);
        } else {
            logger.warning(LanguageManager.get("Source.is.not.a.TextField.instance."));
        }
    }
}