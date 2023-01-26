package hashtools.core.service;

import java.util.ResourceBundle;

public class LanguageService {

    private static final String         BASE_NAME = "hashtools.core.language.Language";
    public               ResourceBundle resourceBundle;

    public LanguageService() {
        this.resourceBundle = ResourceBundle.getBundle(BASE_NAME);
    }

    public String get(String key) {
        return resourceBundle.getString(key);
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
