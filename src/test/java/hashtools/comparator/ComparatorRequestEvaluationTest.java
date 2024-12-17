package hashtools.comparator;

import hashtools.comparator.exception.MissingInputFile1Exception;
import hashtools.comparator.exception.MissingInputFile2Exception;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ComparatorRequestEvaluationTest {

    @SuppressWarnings("FieldCanBeLocal")
    private static Path
        nullFile,
        nonExistentFile,
        folder,
        validFile;

    private static ComparatorRequest
        nullRequest,
        nullFile1Request,
        missingFile1Request,
        folderFile1Request,
        nullFile2Request,
        missingFile2Request,
        folderFile2Request,
        validRequest;


    @AfterAll
    static void cleanup() throws Exception {
        Files.deleteIfExists(folder);
        Files.deleteIfExists(validFile);
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
            Arguments.of(true, NullPointerException.class, nullRequest),
            Arguments.of(true, MissingInputFile1Exception.class, nullFile1Request),
            Arguments.of(true, MissingInputFile1Exception.class, missingFile1Request),
            Arguments.of(true, MissingInputFile1Exception.class, folderFile1Request),
            Arguments.of(true, MissingInputFile2Exception.class, nullFile2Request),
            Arguments.of(true, MissingInputFile2Exception.class, missingFile2Request),
            Arguments.of(true, MissingInputFile2Exception.class, folderFile2Request),
            Arguments.of(false, null, validRequest)
        );
    }

    @BeforeAll
    static void setup() throws Exception {
        nullFile = null;
        folder = Files.createTempDirectory("_folder_");
        validFile = Files.createTempFile("_valid_", "");

        nonExistentFile = Files.createTempFile("_non-existent_", "");
        Files.deleteIfExists(nonExistentFile);


        nullRequest = null;

        nullFile1Request = new ComparatorRequest();
        nullFile1Request.setInputFile1(nullFile);
        nullFile1Request.setInputFile2(validFile);

        missingFile1Request = new ComparatorRequest();
        missingFile1Request.setInputFile1(nonExistentFile);
        missingFile1Request.setInputFile2(validFile);

        folderFile1Request = new ComparatorRequest();
        folderFile1Request.setInputFile1(folder);
        folderFile1Request.setInputFile2(validFile);

        nullFile2Request = new ComparatorRequest();
        nullFile2Request.setInputFile1(validFile);
        nullFile2Request.setInputFile2(nullFile);

        missingFile2Request = new ComparatorRequest();
        missingFile2Request.setInputFile1(validFile);
        missingFile2Request.setInputFile2(nonExistentFile);

        folderFile2Request = new ComparatorRequest();
        folderFile2Request.setInputFile1(validFile);
        folderFile2Request.setInputFile2(folder);

        validRequest = new ComparatorRequest();
        validRequest.setInputFile1(validFile);
        validRequest.setInputFile2(validFile);
    }


    @ParameterizedTest
    @MethodSource(value = "getTests")
    void evaluate(boolean shouldThrow, Class<? extends Throwable> expected, ComparatorRequest request) {
        Executable executable = () -> new ComparatorRequestEvaluation(request).evaluate();

        if (shouldThrow) {
            assertThrows(expected, executable);
        } else {
            assertDoesNotThrow(executable);
        }
    }
}
