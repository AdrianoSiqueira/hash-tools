package hashtools.generator;

import hashtools.generator.exception.InvalidAlgorithmSelectionException;
import hashtools.generator.exception.MissingInputFileException;
import hashtools.coremodule.Algorithm;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeneratorRequestEvaluationTest {

    @SuppressWarnings("FieldCanBeLocal")
    private static Path
        nullFile,
        nonExistentFile,
        folder,
        validFile;

    @SuppressWarnings("FieldCanBeLocal")
    private static List<Algorithm>
        nullList,
        emptyList,
        validList;

    private static GeneratorRequest
        nullRequest,
        nullFileRequest,
        missingFileRequest,
        folderFileRequest,
        nullListRequest,
        emptyListRequest,
        validRequest;


    @AfterAll
    static void cleanup() throws Exception {
        Files.deleteIfExists(folder);
        Files.deleteIfExists(validFile);
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
            Arguments.of(true, NullPointerException.class, nullRequest),
            Arguments.of(true, MissingInputFileException.class, nullFileRequest),
            Arguments.of(true, MissingInputFileException.class, missingFileRequest),
            Arguments.of(true, MissingInputFileException.class, folderFileRequest),
            Arguments.of(true, InvalidAlgorithmSelectionException.class, nullListRequest),
            Arguments.of(false, null, emptyListRequest),
            Arguments.of(false, null, validRequest)
        );
    }

    @BeforeAll
    static void setup() throws Exception {
        nullFile = null;
        folder = Files.createTempDirectory("_folder_");
        validFile = Files.createTempFile("", "");

        nonExistentFile = Files.createTempFile("", "");
        Files.deleteIfExists(nonExistentFile);


        nullList = null;
        emptyList = List.of();
        validList = List.of(Algorithm.MD5);


        nullRequest = null;

        nullFileRequest = new GeneratorRequest();
        nullFileRequest.setInputFile(nullFile);
        nullFileRequest.setAlgorithms(validList);

        missingFileRequest = new GeneratorRequest();
        missingFileRequest.setInputFile(nonExistentFile);
        missingFileRequest.setAlgorithms(validList);

        folderFileRequest = new GeneratorRequest();
        folderFileRequest.setInputFile(folder);
        folderFileRequest.setAlgorithms(validList);

        nullListRequest = new GeneratorRequest();
        nullListRequest.setInputFile(validFile);
        nullListRequest.setAlgorithms(nullList);

        emptyListRequest = new GeneratorRequest();
        emptyListRequest.setInputFile(validFile);
        emptyListRequest.setAlgorithms(emptyList);

        validRequest = new GeneratorRequest();
        validRequest.setInputFile(validFile);
        validRequest.setAlgorithms(validList);
    }


    @ParameterizedTest
    @MethodSource(value = "getTests")
    void evaluate(boolean shouldThrow, Class<? extends Throwable> expected, GeneratorRequest request) {
        Executable executable = () -> new GeneratorRequestEvaluation(request).evaluate();

        if (shouldThrow) {
            assertThrows(expected, executable);
        } else {
            assertDoesNotThrow(executable);
        }
    }
}
