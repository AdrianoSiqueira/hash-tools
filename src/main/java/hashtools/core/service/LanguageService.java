package hashtools.core.service;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p>
 * Handles the language support using the properties file matching the
 * machine language.
 * </p>
 */
public class LanguageService {

    private static final String         BASE_NAME = "hashtools.core.language.Language";
    public               ResourceBundle resourceBundle;

    /**
     * <p>
     * Initializes the {@link ResourceBundle} with the default
     * {@link Locale} of the machine.
     * </p>
     */
    public LanguageService() {
        this.resourceBundle = ResourceBundle.getBundle(BASE_NAME);
    }

    /**
     * <p>
     * Gets the translation for the given key.
     * </p>
     *
     * @param key Used to get the translation.
     *
     * @return The translation from the given key.
     */
    public String get(String key) {
        return resourceBundle.getString(key);
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
