package hashtools.generator;

import hashtools.generator.exception.InvalidAlgorithmSelectionException;
import hashtools.generator.exception.MissingInputFileException;
import hashtools.shared.Algorithm;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeneratorRequestEvaluationTest {

    private static Path
        nullFile,
        nonExistentFile,
        folder,
        validFile;

    private static List<Algorithm>
        nullList,
        emptyList,
        validList;


    @AfterAll
    static void cleanup() throws Exception {
        Files.deleteIfExists(folder);
        Files.deleteIfExists(validFile);
    }

    @BeforeAll
    static void setup() throws Exception {
        nullFile = null;
        folder = Files.createTempDirectory("");
        validFile = Files.createTempFile("", "");

        nonExistentFile = Files.createTempFile("", "");
        Files.deleteIfExists(nonExistentFile);


        nullList = null;
        emptyList = List.of();
        validList = List.of(Algorithm.MD5);
    }


    @Test
    void evaluateDoesNotThrowExceptionWhenRequestIsValid() {
        GeneratorRequest request = new GeneratorRequest();
        request.setInputFile(validFile);
        request.setAlgorithms(validList);

        assertDoesNotThrow(
            () -> new GeneratorRequestEvaluation(request).evaluate()
        );
    }

    @Test
    void evaluateThrowsInvalidAlgorithmSelectionExceptionWhenListIsEmpty() {
        GeneratorRequest request = new GeneratorRequest();
        request.setInputFile(validFile);
        request.setAlgorithms(emptyList);

        assertThrows(
            InvalidAlgorithmSelectionException.class,
            () -> new GeneratorRequestEvaluation(request).evaluate()
        );
    }

    @Test
    void evaluateThrowsInvalidAlgorithmSelectionExceptionWhenListIsNull() {
        GeneratorRequest request = new GeneratorRequest();
        request.setInputFile(validFile);
        request.setAlgorithms(nullList);

        assertThrows(
            InvalidAlgorithmSelectionException.class,
            () -> new GeneratorRequestEvaluation(request).evaluate()
        );
    }

    @Test
    void evaluateThrowsMissingInputFileExceptionWhenFileDoesNotExist() {
        GeneratorRequest request = new GeneratorRequest();
        request.setInputFile(nonExistentFile);
        request.setAlgorithms(validList);

        assertThrows(
            MissingInputFileException.class,
            () -> new GeneratorRequestEvaluation(request).evaluate()
        );
    }

    @Test
    void evaluateThrowsMissingInputFileExceptionWhenFileIsFolder() {
        GeneratorRequest request = new GeneratorRequest();
        request.setInputFile(folder);
        request.setAlgorithms(validList);

        assertThrows(
            MissingInputFileException.class,
            () -> new GeneratorRequestEvaluation(request).evaluate()
        );
    }

    @Test
    void evaluateThrowsMissingInputFileExceptionWhenFileIsNull() {
        GeneratorRequest request = new GeneratorRequest();
        request.setInputFile(nullFile);
        request.setAlgorithms(validList);

        assertThrows(
            MissingInputFileException.class,
            () -> new GeneratorRequestEvaluation(request).evaluate()
        );
    }

    @Test
    void evaluateThrowsNullPointerExceptionWhenRequestIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new GeneratorRequestEvaluation(null).evaluate()
        );
    }
}
