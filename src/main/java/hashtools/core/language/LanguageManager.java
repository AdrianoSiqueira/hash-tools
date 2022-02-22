package hashtools.core.language;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * <p>Handles the management of multiple languages.</p>
 *
 * @author Adriano Siqueira
 * @version 2.1.0
 * @since 1.0.0
 */
public class LanguageManager {

    private static final String         NAME   = "hashtools.core.language.Language";
    private static       ResourceBundle bundle = ResourceBundle.getBundle(NAME);


    public static String get(String key) {
        return bundle.getString(key);
    }

    public static ResourceBundle getBundle() {
        return bundle;
    }

    public static void setLanguage(Locale locale) {
        Optional.ofNullable(locale)
                .ifPresent(l -> bundle = ResourceBundle.getBundle(NAME, l));
    }
}
