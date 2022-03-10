package hashtools.core.model;

import hashtools.core.language.LanguageManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RunMode {

    CHECKER("Checker"),
    GENERATOR("Generator");


    private final String text;

    public String getText() {
        return LanguageManager.get(text);
    }
}
