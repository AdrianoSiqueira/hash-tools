package hashtools.applicationmodule.checker;

import hashtools.applicationmodule.checker.CheckerRequest;
import hashtools.applicationmodule.checker.CheckerRequestEvaluation;
import hashtools.applicationmodule.checker.exception.InvalidChecksumFileSizeException;
import hashtools.applicationmodule.checker.exception.InvalidChecksumFileTypeException;
import hashtools.applicationmodule.checker.exception.MissingChecksumFileException;
import hashtools.applicationmodule.checker.exception.MissingInputFileException;
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

class CheckerRequestEvaluationTest {

    public static final int MD5_LENGTH = 32;

    @SuppressWarnings("FieldCanBeLocal")
    private static Path
        nullFile,
        folder,
        image,
        smallFile,
        bigFile,
        inputFile,
        checksumFile;

    private static CheckerRequest
        nullRequest,
        nullInputRequest,
        folderInputRequest,
        nullChecksumRequest,
        folderChecksumRequest,
        imageChecksumRequest,
        smallChecksumRequest,
        bigChecksumRequest,
        validRequest;


    @AfterAll
    static void cleanup() throws Exception {
        Files.deleteIfExists(folder);
        Files.deleteIfExists(image);
        Files.deleteIfExists(bigFile);
        Files.deleteIfExists(inputFile);
        Files.deleteIfExists(checksumFile);
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
            Arguments.of(true, NullPointerException.class, nullRequest),
            Arguments.of(true, MissingInputFileException.class, nullInputRequest),
            Arguments.of(true, MissingInputFileException.class, folderInputRequest),
            Arguments.of(true, MissingChecksumFileException.class, nullChecksumRequest),
            Arguments.of(true, MissingChecksumFileException.class, folderChecksumRequest),
            Arguments.of(true, InvalidChecksumFileTypeException.class, imageChecksumRequest),
            Arguments.of(true, InvalidChecksumFileSizeException.class, smallChecksumRequest),
            Arguments.of(true, InvalidChecksumFileSizeException.class, bigChecksumRequest),
            Arguments.of(false, null, validRequest)
        );
    }

    @BeforeAll
    static void setup() throws Exception {
        nullFile = null;
        folder = Files.createTempDirectory("_folder_");
        image = Files.createTempFile("_image_", ".jpg");
        smallFile = Files.createTempFile("_small_", ".txt");
        inputFile = Files.createTempFile("_input_", ".txt");

        bigFile = Files.createTempFile("_big_", ".txt");
        Files.writeString(bigFile, "a".repeat(Short.MAX_VALUE));

        checksumFile = Files.createTempFile("_valid-checksum_", ".txt");
        Files.writeString(checksumFile, "A".repeat(MD5_LENGTH));


        nullRequest = null;

        nullInputRequest = new CheckerRequest();
        nullInputRequest.setInputFile(nullFile);
        nullInputRequest.setChecksumFile(checksumFile);

        folderInputRequest = new CheckerRequest();
        folderInputRequest.setInputFile(folder);
        folderInputRequest.setChecksumFile(checksumFile);

        nullChecksumRequest = new CheckerRequest();
        nullChecksumRequest.setInputFile(inputFile);
        nullChecksumRequest.setChecksumFile(nullFile);

        folderChecksumRequest = new CheckerRequest();
        folderChecksumRequest.setInputFile(inputFile);
        folderChecksumRequest.setChecksumFile(folder);

        imageChecksumRequest = new CheckerRequest();
        imageChecksumRequest.setInputFile(inputFile);
        imageChecksumRequest.setChecksumFile(image);

        smallChecksumRequest = new CheckerRequest();
        smallChecksumRequest.setInputFile(inputFile);
        smallChecksumRequest.setChecksumFile(smallFile);

        bigChecksumRequest = new CheckerRequest();
        bigChecksumRequest.setInputFile(inputFile);
        bigChecksumRequest.setChecksumFile(bigFile);

        validRequest = new CheckerRequest();
        validRequest.setInputFile(inputFile);
        validRequest.setChecksumFile(checksumFile);
    }

    @ParameterizedTest
    @MethodSource(value = "getTests")
    void evaluate(boolean shouldThrow, Class<? extends Throwable> expected, CheckerRequest request) {
        Executable executable = () -> new CheckerRequestEvaluation(request).evaluate();

        if (shouldThrow) {
            assertThrows(expected, executable);
        } else {
            assertDoesNotThrow(executable);
        }
    }
}
