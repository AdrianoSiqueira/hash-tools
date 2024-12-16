package hashtools.checker;

import hashtools.checker.exception.InvalidChecksumFileSizeException;
import hashtools.checker.exception.InvalidChecksumFileTypeException;
import hashtools.checker.exception.MissingChecksumFileException;
import hashtools.checker.exception.MissingInputFileException;
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

class CheckerRequestEvaluationTest {

    public static final int MD5_LENGTH = 32;

    @SuppressWarnings("FieldCanBeLocal")
    private static Path
        nullFile,
        image,
        smallFile,
        bigFile,
        inputFile,
        checksumFile;

    private static CheckerRequest
        nullRequest,
        missingInputFile,
        missingChecksumFile,
        invalidChecksumFileType,
        invalidChecksumFileSizeSmall,
        invalidChecksumFileSizeBig,
        validRequest;


    @AfterAll
    static void cleanup() throws Exception {
        Files.deleteIfExists(image);
        Files.deleteIfExists(bigFile);
        Files.deleteIfExists(inputFile);
        Files.deleteIfExists(checksumFile);
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
            Arguments.of(NullPointerException.class, nullRequest),
            Arguments.of(MissingInputFileException.class, missingInputFile),
            Arguments.of(MissingChecksumFileException.class, missingChecksumFile),
            Arguments.of(InvalidChecksumFileTypeException.class, invalidChecksumFileType),
            Arguments.of(InvalidChecksumFileSizeException.class, invalidChecksumFileSizeSmall),
            Arguments.of(InvalidChecksumFileSizeException.class, invalidChecksumFileSizeBig)
        );
    }

    @BeforeAll
    static void setup() throws Exception {
        nullFile = null;
        image = Files.createTempFile("_image_", ".jpg");
        smallFile = Files.createTempFile("_small_", ".txt");
        inputFile = Files.createTempFile("_input_", ".txt");

        bigFile = Files.createTempFile("_big_", ".txt");
        Files.writeString(bigFile, "a".repeat(Short.MAX_VALUE));

        checksumFile = Files.createTempFile("_valid-checksum_", ".txt");
        Files.writeString(checksumFile, "A".repeat(MD5_LENGTH));


        nullRequest = null;

        missingInputFile = new CheckerRequest();
        missingInputFile.setInputFile(nullFile);
        missingInputFile.setChecksumFile(checksumFile);

        missingChecksumFile = new CheckerRequest();
        missingChecksumFile.setInputFile(inputFile);
        missingChecksumFile.setChecksumFile(nullFile);

        invalidChecksumFileType = new CheckerRequest();
        invalidChecksumFileType.setInputFile(inputFile);
        invalidChecksumFileType.setChecksumFile(image);

        invalidChecksumFileSizeSmall = new CheckerRequest();
        invalidChecksumFileSizeSmall.setInputFile(inputFile);
        invalidChecksumFileSizeSmall.setChecksumFile(smallFile);

        invalidChecksumFileSizeBig = new CheckerRequest();
        invalidChecksumFileSizeBig.setInputFile(inputFile);
        invalidChecksumFileSizeBig.setChecksumFile(bigFile);

        validRequest = new CheckerRequest();
        validRequest.setInputFile(inputFile);
        validRequest.setChecksumFile(checksumFile);
    }

    @ParameterizedTest
    @MethodSource(value = "getTests")
    void evaluate(Class<? extends Throwable> expected, CheckerRequest request) {
        assertThrows(
            expected,
            () -> new CheckerRequestEvaluation(request).evaluate()
        );
    }

    @Test
    void evaluateDoesNotThrowExceptionWhenRequestIsValid() {
        assertDoesNotThrow(
            () -> new CheckerRequestEvaluation(validRequest).evaluate()
        );
    }
}
