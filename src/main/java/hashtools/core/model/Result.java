package hashtools.core.model;

import hashtools.core.language.LanguageManager;
import lombok.RequiredArgsConstructor;

@Deprecated
@RequiredArgsConstructor
public enum Result {

    SAFE("Safe"),
    UNSAFE("Unsafe");


    private final String text;

    public String getText() {
        return LanguageManager.get(text);
    }
}
