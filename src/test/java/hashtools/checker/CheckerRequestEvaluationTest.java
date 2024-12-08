package hashtools.checker;

import hashtools.checker.exception.InvalidChecksumFileSizeException;
import hashtools.checker.exception.InvalidChecksumFileTypeException;
import hashtools.checker.exception.MissingChecksumFileException;
import hashtools.checker.exception.MissingInputFileException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckerRequestEvaluationTest {

    @SuppressWarnings("FieldCanBeLocal")
    private static Path
        nullFile,
        image,
        hugeFile,
        inputFile,
        checksumFile;

    private static CheckerRequest
        nullRequest,
        missingInputFile,
        missingChecksumFile,
        invalidChecksumFileType,
        invalidChecksumFileSize,
        validRequest;


    @AfterAll
    static void cleanup() throws Exception {
        Files.deleteIfExists(image);
        Files.deleteIfExists(hugeFile);
        Files.deleteIfExists(inputFile);
        Files.deleteIfExists(checksumFile);
    }

    @BeforeAll
    static void setup() throws Exception {
        nullFile = null;
        image = Files.createTempFile("", ".jpg");
        inputFile = Files.createTempFile("", ".txt");

        hugeFile = Files.createTempFile("", ".txt");
        Files.writeString(hugeFile, "a".repeat(Short.MAX_VALUE));

        checksumFile = Files.createTempFile("", ".txt");
        Files.writeString(checksumFile, "A".repeat(32));


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

        invalidChecksumFileSize = new CheckerRequest();
        invalidChecksumFileSize.setInputFile(inputFile);
        invalidChecksumFileSize.setChecksumFile(hugeFile);

        validRequest = new CheckerRequest();
        validRequest.setInputFile(inputFile);
        validRequest.setChecksumFile(checksumFile);
    }


    @Test
    void evaluateDoesNotThrowExceptionWhenRequestIsValid() {
        assertDoesNotThrow(
            () -> new CheckerRequestEvaluation(validRequest).evaluate()
        );
    }

    @Test
    void evaluateThrowsInvalidChecksumFileSizeExceptionWhenChecksumFileIsTooBigOrSmall() {
        assertThrows(
            InvalidChecksumFileSizeException.class,
            () -> new CheckerRequestEvaluation(invalidChecksumFileSize).evaluate()
        );
    }

    @Test
    void evaluateThrowsInvalidChecksumFileTypeExceptionWhenChecksumFileIsNotTextFile() {
        assertThrows(
            InvalidChecksumFileTypeException.class,
            () -> new CheckerRequestEvaluation(invalidChecksumFileType).evaluate()
        );
    }

    @Test
    void evaluateThrowsMissingChecksumFileExceptionWhenChecksumFileIsMissing() {
        assertThrows(
            MissingChecksumFileException.class,
            () -> new CheckerRequestEvaluation(missingChecksumFile).evaluate()
        );
    }

    @Test
    void evaluateThrowsMissingInputFileExceptionWhenInputFileIsMissing() {
        assertThrows(
            MissingInputFileException.class,
            () -> new CheckerRequestEvaluation(missingInputFile).evaluate()
        );
    }

    @Test
    void evaluateThrowsNullPointerExceptionWhenRequestIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new CheckerRequestEvaluation(nullRequest).evaluate()
        );
    }
}
