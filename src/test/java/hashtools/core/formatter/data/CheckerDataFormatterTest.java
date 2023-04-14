package hashtools.core.formatter.data;

import hashtools.core.model.Data;
import hashtools.core.model.Hash;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckerDataFormatterTest {

    private static Locale        oldLocale;
    private        DataFormatter formatter = new CheckerDataFormatter();

    private static Data getData() {
        Hash hash = new Hash();
        hash.setAlgorithm("MD5");
        hash.setOfficial("123");
        hash.setGenerated("123");

        Data data = new Data();
        data.getHashes().add(hash);
        return data;
    }

    private static String getExpected() {
        return "-".repeat(139) + "\n" +
               "Algorithm: MD5\n" +
               " Official: 123\n" +
               "Generated: 123\n" +
               "   Result: Safe\n" +
               "-".repeat(139) + "\n" +
               "Safety Percentage: 0.00 %";
    }

    private static List<Arguments> getTests() {
        return List.of(
                Arguments.of(null, null),
                Arguments.of(getData(), getExpected())
        );
    }

    @AfterAll
    static void restoreLocale() {
        Locale.setDefault(oldLocale);
    }

    @BeforeAll
    static void setLocale() {
        oldLocale = Locale.getDefault();
        Locale.setDefault(new Locale("en", "US"));
    }

    @ParameterizedTest
    @MethodSource(value = "getTests")
    @DisplayName(value = "Formats the data object when checking")
    void format(Data data, String expected) {
        assertEquals(expected, formatter.format(data));
    }
}
