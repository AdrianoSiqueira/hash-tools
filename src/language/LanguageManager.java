package language;

import java.util.ResourceBundle;

public class LanguageManager {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            LanguageManager.class.getPackage().getName() +
            ".Language");

    public static String get(String key) {
        return BUNDLE.getString(key);
    }

    public static ResourceBundle getBundle() {
        return BUNDLE;
    }
}