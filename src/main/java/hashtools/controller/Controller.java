package hashtools.controller;

import javafx.css.PseudoClass;
import javafx.fxml.Initializable;
import lombok.extern.slf4j.Slf4j;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Slf4j
public abstract class Controller implements Initializable {

    protected static final PseudoClass
        ARMED = PseudoClass.getPseudoClass("armed"),
        DISABLED = PseudoClass.getPseudoClass("disabled");


    /**
     * <p>
     * Resets the UI state clearing text fields, applying
     * default selection to checkboxes and so on.
     * </p>
     */
    protected abstract void resetUI();

    /**
     * <p>
     * Gets translation for the given dictionary entry. If
     * some issue occurs, the entry itself will be returned.
     * </p>
     *
     * @param dictionary Dictionary used to get translation.
     * @param entry      Dictionary entry to get translation.
     *
     * @return The translation for the given entry, or the
     * entry itself if translation fails.
     */
    protected final String translate(ResourceBundle dictionary, String entry) {
        try {
            return dictionary.getString(entry);
        } catch (NullPointerException e) {
            log.error("The dictionary or entry is null", e);
        } catch (MissingResourceException e) {
            log.error("The entry was not found in dictionary", e);
        } catch (Exception e) {
            log.error("Unknown issue", e);
        }

        return entry;
    }
}
