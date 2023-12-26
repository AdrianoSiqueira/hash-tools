package hashtools.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChecksumPairTest {

    private static Stream<Arguments> getResultTests() {
        return Stream
            .<Arguments>builder()
            .add(Arguments.of(ChecksumPair.builder().checksum1(null).checksum2(null).build(), false))
            .add(Arguments.of(ChecksumPair.builder().checksum1(null).checksum2("").build(), false))
            .add(Arguments.of(ChecksumPair.builder().checksum1("").checksum2(null).build(), false))
            .add(Arguments.of(ChecksumPair.builder().checksum1("").checksum2("").build(), true))
            .add(Arguments.of(ChecksumPair.builder().checksum1("a").checksum2("a").build(), true))
            .add(Arguments.of(ChecksumPair.builder().checksum1("a").checksum2("A").build(), true))
            .build();
    }

    @ParameterizedTest
    @MethodSource(value = "getResultTests")
    void matches(ChecksumPair input, boolean output) {
        assertEquals(output, input.matches());
    }
}
