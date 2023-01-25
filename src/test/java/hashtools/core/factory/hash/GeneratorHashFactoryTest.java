package hashtools.core.factory.hash;

import hashtools.core.model.Hash;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("FieldCanBeLocal")
class GeneratorHashFactoryTest {

    private static List<String> nullAlgorithmNames;
    private static List<String> noValidAlgorithmNames;
    private static List<String> validAlgorithmNames;

    private static List<Hash> nullHashes;
    private static List<Hash> noValidHashes;
    private static List<Hash> validHashes;

    private HashFactory factory;

    @BeforeAll
    static void createData() {
        nullAlgorithmNames = null;
        nullHashes         = null;

        noValidAlgorithmNames = List.of();
        noValidHashes         = List.of();

        Hash hash = new Hash();
        hash.setAlgorithm("MD5");

        validAlgorithmNames = List.of("md5");
        validHashes         = List.of(hash);
    }

    private static List<Arguments> getTests() {
        return List.of(
                Arguments.of(nullAlgorithmNames, nullHashes),
                Arguments.of(noValidAlgorithmNames, noValidHashes),
                Arguments.of(validAlgorithmNames, validHashes)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getTests")
    @DisplayName(value = "Creates hash list")
    void create(List<String> algorithmNames, List<Hash> expected) {
        factory = new GeneratorHashFactory(algorithmNames);

        assertEquals(expected, factory.create());
    }
}
