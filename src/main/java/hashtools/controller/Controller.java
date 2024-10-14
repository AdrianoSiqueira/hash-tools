package hashtools.controller;

import javafx.fxml.Initializable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Controller implements Initializable {

    /**
     * <p>
     * Resets the UI state clearing text fields, applying
     * default selection to checkboxes and so on.
     * </p>
     */
    protected abstract void resetUI();
}
