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
class CheckerHashFactoryTest {

    private static List<String> nullOfficialHashes;
    private static List<String> noValidOfficialHashes;
    private static List<String> validOfficialHashes;

    private static List<Hash> nullHashes;
    private static List<Hash> noValidHashes;
    private static List<Hash> validHashes;

    private HashFactory factory;

    @BeforeAll
    static void createData() {
        nullOfficialHashes = null;
        nullHashes         = null;

        noValidOfficialHashes = List.of();
        noValidHashes         = List.of();

        Hash hash = new Hash();
        hash.setAlgorithm("MD5");
        hash.setOfficial("11111111111111111111111111111111");

        validOfficialHashes = List.of("11111111111111111111111111111111");
        validHashes         = List.of(hash);
    }

    private static List<Arguments> getTests() {
        return List.of(
                Arguments.of(nullOfficialHashes, nullHashes),
                Arguments.of(noValidOfficialHashes, noValidHashes),
                Arguments.of(validOfficialHashes, validHashes)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getTests")
    @DisplayName(value = "Creates hash list")
    void create(List<String> officialHashes, List<Hash> expected) {
        factory = new CheckerHashFactory(officialHashes);

        assertEquals(expected, factory.create());
    }
}
