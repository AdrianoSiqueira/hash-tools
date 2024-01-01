package hashtools.language;

import lombok.Getter;

import java.util.Locale;
import java.util.ResourceBundle;

@Getter
public enum Language {
    INSTANCE;

    private static final String BASE_NAME = "hashtools.language.language";

    private ResourceBundle bundle;

    public static void init() {
        INSTANCE.bundle = ResourceBundle.getBundle(
            BASE_NAME,
            Locale.getDefault()
        );
    }
}
