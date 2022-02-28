package hashtools.core.model;

import hashtools.core.language.LanguageManager;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileExtensionTest {

    @Test
    void getFilter_descriptionIsTranslated() {
        Locale currentLocale = LanguageManager.getBundle().getLocale();
        LanguageManager.setLanguage(new Locale("pt"));

        assertEquals("Tudo", FileExtension.ALL.getFilter().getDescription());

        LanguageManager.setLanguage(currentLocale);
    }
}
