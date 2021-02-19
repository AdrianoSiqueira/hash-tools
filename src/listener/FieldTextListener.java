package listener;

import controller.AppUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class FieldTextListener implements ChangeListener<String> {

    private final AppUI controller;

    public FieldTextListener(AppUI controller) {
        this.controller = controller;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        controller.setDoneDetails(0.0);
    }
}
