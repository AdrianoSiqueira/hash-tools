package hashtools.core.formatter.data;

import hashtools.core.model.Data;
import hashtools.core.model.Hash;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeneratorDataFormatterTest {

    private DataFormatter formatter = new GeneratorDataFormatter();

    private static Data getData() {
        Hash hash = new Hash();
        hash.setGenerated("123");

        Data data = new Data();
        data.setInputText("input.txt");
        data.getHashes().add(hash);
        return data;
    }

    private static String getExpected() {
        return "123  \"input.txt\"";
    }

    private static List<Arguments> getTests() {
        return List.of(
                Arguments.of(null, null),
                Arguments.of(getData(), getExpected())
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getTests")
    @DisplayName(value = "Formats the data object when generating")
    void format(Data data, String expected) {
        assertEquals(expected, formatter.format(data));
    }
}
