package hashtools.core.service;

import hashtools.core.model.HashAlgorithm;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SampleServiceTest {

    private SampleService service = new SampleService();


    private static Path createEmptyFile() {
        try {
            return Files.createTempFile(null, null);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static Path createFilledFile() {
        try {
            Path file = createEmptyFile();
            Files.writeString(file, "11111111111111111111111111111111");

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static List<Arguments> getExceptionTestsCreateSampleListAlgorithms() {
        return List.of(
                Arguments.of(NullPointerException.class, null)
        );
    }

    private static List<Arguments> getResultTestsCreateSampleFromAlgorithm() {
        return List.of(
                Arguments.of(false, null),
                Arguments.of(true, HashAlgorithm.MD5)
        );
    }

    private static List<Arguments> getResultTestsCreateSampleListAlgorithms() {
        return List.of(
                Arguments.of(true, List.of()),
                Arguments.of(false, List.of(HashAlgorithm.MD5))
        );
    }

    private static List<Arguments> getResultTestsCreateSampleListFile() {
        return List.of(
                Arguments.of(true, createEmptyFile()),
                Arguments.of(false, createFilledFile())
        );
    }

    private static List<Arguments> getResultTestsCreateSampleListHash() {
        return List.of(
                Arguments.of(true, null),
                Arguments.of(true, ""),
                Arguments.of(false, "11111111111111111111111111111111")
        );
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTestsCreateSampleFromAlgorithm")
    void createSampleFromAlgorithm(boolean result, HashAlgorithm algorithm) {
        assertEquals(result, service.createSampleFromAlgorithm(algorithm).isPresent());
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTestsCreateSampleListHash")
    void createSampleList(boolean result, String hash) {
        assertEquals(result, service.createSampleList(hash).isEmpty());
    }

    @ParameterizedTest
    @MethodSource(value = "getResultTestsCreateSampleListAlgorithms")
    void createSampleList(boolean result, List<HashAlgorithm> algorithms) {
        assertEquals(result, service.createSampleList(algorithms).isEmpty());
    }

    @ParameterizedTest
    @MethodSource(value = "getExceptionTestsCreateSampleListAlgorithms")
    void createSampleList(Class<? extends Throwable> result, List<HashAlgorithm> algorithms) {
        assertThrows(result, () -> service.createSampleList(algorithms));
    }

    @ParameterizedTest
    @MethodSource(value = "getResultTestsCreateSampleListFile")
    void createSampleList(boolean result, Path file) {
        assertEquals(result, service.createSampleList(file.toAbsolutePath().toString()).isEmpty());
    }
}
