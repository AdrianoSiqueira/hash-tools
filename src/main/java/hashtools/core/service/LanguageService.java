package hashtools.core.service;

import java.util.ResourceBundle;

public class LanguageService {

    private static final String DEFAULT_NAME = "hashtools.core.language.Language";

    public ResourceBundle bundle;

    public LanguageService() {
        this.bundle = ResourceBundle.getBundle(DEFAULT_NAME);
    }

    public LanguageService(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String get(String key) {
        return bundle.getString(key);
    }
}
