package hashtools.comparator;

import hashtools.comparator.exception.MissingInputFile1Exception;
import hashtools.comparator.exception.MissingInputFile2Exception;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

// TODO Check when file is folder
class ComparatorRequestEvaluationTest {

    @SuppressWarnings("FieldCanBeLocal")
    private static Path
        nullFile,
        nonExistentFile,
        validFile;

    private static ComparatorRequest
        nullRequest,
        nullFile1,
        missingFile1,
        nullFile2,
        missingFile2,
        validRequest;


    @AfterAll
    static void cleanup() throws Exception {
        Files.deleteIfExists(validFile);
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
            Arguments.of(NullPointerException.class, nullRequest),
            Arguments.of(MissingInputFile1Exception.class, nullFile1),
            Arguments.of(MissingInputFile1Exception.class, missingFile1),
            Arguments.of(MissingInputFile2Exception.class, nullFile2),
            Arguments.of(MissingInputFile2Exception.class, missingFile2)
        );
    }

    @BeforeAll
    static void setup() throws Exception {
        nullFile = null;
        validFile = Files.createTempFile("_valid_", "");

        nonExistentFile = Files.createTempFile("_non-existent_", "");
        Files.deleteIfExists(nonExistentFile);


        nullRequest = null;

        nullFile1 = new ComparatorRequest();
        nullFile1.setInputFile1(nullFile);
        nullFile1.setInputFile2(validFile);

        missingFile1 = new ComparatorRequest();
        missingFile1.setInputFile1(nonExistentFile);
        missingFile1.setInputFile2(validFile);

        nullFile2 = new ComparatorRequest();
        nullFile2.setInputFile1(validFile);
        nullFile2.setInputFile2(nullFile);

        missingFile2 = new ComparatorRequest();
        missingFile2.setInputFile1(validFile);
        missingFile2.setInputFile2(nonExistentFile);

        validRequest = new ComparatorRequest();
        validRequest.setInputFile1(validFile);
        validRequest.setInputFile2(validFile);
    }


    @ParameterizedTest
    @MethodSource(value = "getTests")
    void evaluate(Class<? extends Throwable> expected, ComparatorRequest request) {
        assertThrows(
            expected,
            () -> new ComparatorRequestEvaluation(request).evaluate()
        );
    }

    @Test
    void evaluateDoesNotThrowExceptionWhenRequestIsValid() {
        assertDoesNotThrow(
            () -> new ComparatorRequestEvaluation(validRequest).evaluate()
        );
    }
}
