package hashtools.core.model;

import hashtools.core.language.LanguageManager;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileExtensionTest {

    public static List<Arguments> getResultTestsGetFilter() {
        return List.of(
                Arguments.of(new Locale("en"), "All"),
                Arguments.of(new Locale("fr"), "All"),
                Arguments.of(new Locale("pt"), "Tudo")
        );
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTestsGetFilter")
    void getFilter(Locale locale, String result) {
        Locale currentLocale = LanguageManager.getBundle().getLocale();
        LanguageManager.setLanguage(locale);

        assertEquals(result, FileExtension.ALL.getFilter().getDescription());

        LanguageManager.setLanguage(currentLocale);
    }
}
