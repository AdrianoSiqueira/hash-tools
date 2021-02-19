package handler;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

public class FieldDragEnablerHandler implements EventHandler<DragEvent> {

    @Override
    public void handle(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
    }
}