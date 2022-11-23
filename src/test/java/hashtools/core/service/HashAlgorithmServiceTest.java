package hashtools.core.service;

import hashtools.core.model.HashAlgorithm;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HashAlgorithmServiceTest {

    private HashAlgorithmService service = new HashAlgorithmService();


    private static List<Arguments> getExceptionTestsGetByLength() {
        return List.of(
                Arguments.of(NoSuchElementException.class, 0)
        );
    }

    private static List<Arguments> getExceptionTestsGetByName() {
        return List.of(
                Arguments.of(NoSuchElementException.class, ""),
                Arguments.of(NoSuchElementException.class, null)
        );
    }

    private static List<Arguments> getResultTestsArrayAlgorithmList() {
        return List.of(
                Arguments.of(0, new String[]{}),
                Arguments.of(3, new String[]{"", "MD5", "sha1", "ShA--------256"})
        );
    }

    private static List<Arguments> getResultTestsGetByLength() {
        return List.of(
                Arguments.of(HashAlgorithm.MD5, 32),
                Arguments.of(HashAlgorithm.SHA1, 40),
                Arguments.of(HashAlgorithm.SHA224, 56),
                Arguments.of(HashAlgorithm.SHA256, 64),
                Arguments.of(HashAlgorithm.SHA384, 96),
                Arguments.of(HashAlgorithm.SHA512, 128)
        );
    }

    private static List<Arguments> getResultTestsGetByName() {
        return List.of(
                Arguments.of(HashAlgorithm.MD5, "MD5"),
                Arguments.of(HashAlgorithm.SHA1, "SHA-1"),
                Arguments.of(HashAlgorithm.SHA224, "SHA-224"),
                Arguments.of(HashAlgorithm.SHA256, "SHA-256"),
                Arguments.of(HashAlgorithm.SHA384, "SHA-384"),
                Arguments.of(HashAlgorithm.SHA512, "SHA-512")
        );
    }

    private static List<Arguments> getResultTestsListAlgorithmList() {
        return List.of(
                Arguments.of(0, List.of()),
                Arguments.of(3, List.of("", "MD5", "sha1", "ShA--------256"))
        );
    }

    private static List<Arguments> getResultTestsSearchByLength() {
        return List.of(
                Arguments.of(false, 0),
                Arguments.of(true, 32),
                Arguments.of(true, 40),
                Arguments.of(true, 56),
                Arguments.of(true, 64),
                Arguments.of(true, 96),
                Arguments.of(true, 128)
        );
    }

    private static List<Arguments> getResultTestsSearchByName() {
        return List.of(
                Arguments.of(false, ""),
                Arguments.of(true, "MD5"),
                Arguments.of(true, "SHA-1"),
                Arguments.of(true, "SHA-224"),
                Arguments.of(true, "SHA-256"),
                Arguments.of(true, "SHA-384"),
                Arguments.of(true, "SHA-512")
        );
    }

    private static List<Arguments> getResultTestsStringHasValidLength() {
        return List.of(
                Arguments.of(false, null),
                Arguments.of(false, ""),
                Arguments.of(true, "11111111111111111111111111111111")
        );
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTestsArrayAlgorithmList")
    void convertToAlgorithmList(int size, String[] algorithms) {
        assertEquals(size, service.convertToAlgorithmList(algorithms).size());
    }

    @ParameterizedTest
    @MethodSource(value = "getResultTestsListAlgorithmList")
    void convertToAlgorithmList(int size, List<String> algorithms) {
        assertEquals(size, service.convertToAlgorithmList(algorithms).size());
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTestsGetByLength")
    void getByLength(HashAlgorithm result, int length) {
        assertEquals(result, service.getByLength(length));
    }

    @ParameterizedTest
    @MethodSource(value = "getExceptionTestsGetByLength")
    void getByLength(Class<? extends Throwable> result, int length) {
        assertThrows(result, () -> service.getByLength(length));
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTestsGetByName")
    void getByName(HashAlgorithm result, String name) {
        assertEquals(result, service.getByName(name));
    }

    @ParameterizedTest
    @MethodSource(value = "getExceptionTestsGetByName")
    void getByName(Class<? extends Throwable> result, String name) {
        assertThrows(result, () -> service.getByName(name));
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTestsSearchByLength")
    void searchByLength(boolean result, int length) {
        assertEquals(result, service.searchByLength(length).isPresent());
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTestsSearchByName")
    void searchByName(boolean result, String name) {
        assertEquals(result, service.searchByName(name).isPresent());
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTestsStringHasValidLength")
    void stringHasValidLength(boolean result, String string) {
        assertEquals(result, service.stringHasValidLength(string));
    }
}
