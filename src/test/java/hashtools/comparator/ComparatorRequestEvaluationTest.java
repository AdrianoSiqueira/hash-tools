package hashtools.comparator;

import hashtools.comparator.exception.MissingInputFile1Exception;
import hashtools.comparator.exception.MissingInputFile2Exception;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ComparatorRequestEvaluationTest {

    private static Path
        nullFile,
        nonExistentFile,
        file;


    @AfterAll
    static void cleanup() throws Exception {
        Files.deleteIfExists(file);
    }

    @BeforeAll
    static void setup() throws Exception {
        nullFile = null;
        file = Files.createTempFile("", "");

        nonExistentFile = Files.createTempFile("", "");
        Files.deleteIfExists(nonExistentFile);
    }


    @Test
    void evaluateDoesNotThrowExceptionWhenRequestIsValid() {
        ComparatorRequest request = new ComparatorRequest();
        request.setInputFile1(file);
        request.setInputFile2(file);

        assertDoesNotThrow(
            () -> new ComparatorRequestEvaluation(request).evaluate()
        );
    }

    @Test
    void evaluateThrowsMissingInputFile1ExceptionWhenFile1DoesNotExist() {
        ComparatorRequest request = new ComparatorRequest();
        request.setInputFile1(nonExistentFile);
        request.setInputFile2(file);

        assertThrows(
            MissingInputFile1Exception.class,
            () -> new ComparatorRequestEvaluation(request).evaluate()
        );
    }

    @Test
    void evaluateThrowsMissingInputFile1ExceptionWhenFile1IsNull() {
        ComparatorRequest request = new ComparatorRequest();
        request.setInputFile1(nullFile);
        request.setInputFile2(file);

        assertThrows(
            MissingInputFile1Exception.class,
            () -> new ComparatorRequestEvaluation(request).evaluate()
        );
    }

    @Test
    void evaluateThrowsMissingInputFile2ExceptionWhenFile2DoesNotExist() {
        ComparatorRequest request = new ComparatorRequest();
        request.setInputFile1(file);
        request.setInputFile2(nonExistentFile);

        assertThrows(
            MissingInputFile2Exception.class,
            () -> new ComparatorRequestEvaluation(request).evaluate()
        );
    }

    @Test
    void evaluateThrowsMissingInputFile2ExceptionWhenFile2IsNull() {
        ComparatorRequest request = new ComparatorRequest();
        request.setInputFile1(file);
        request.setInputFile2(nullFile);

        assertThrows(
            MissingInputFile2Exception.class,
            () -> new ComparatorRequestEvaluation(request).evaluate()
        );
    }

    @Test
    void evaluateThrowsNullPointerExceptionWhenRequestIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new ComparatorRequestEvaluation(null).evaluate()
        );
    }
}
