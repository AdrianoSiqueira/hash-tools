package hashtools.service;

import hashtools.domain.algorithm.Algorithm;
import hashtools.domain.CheckerRequest;
import hashtools.domain.CheckerResponse;
import hashtools.domain.ChecksumPair;
import hashtools.domain.identification.IdentifiableFactory;
import hashtools.domain.messagedigest.DigestUpdaterFactory;
import hashtools.domain.officialdata.OfficialDataGetterFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckerServiceTest {

    private CheckerService service = new CheckerService();

    public static Stream<Arguments> getResultTests() {
        List<ChecksumPair> checksumPairs = List.of(
            ChecksumPair
                .builder()
                .algorithm(Algorithm.MD5)
                .checksum1("d41d8cd98f00b204e9800998ecf8427e")
                .checksum2("d41d8cd98f00b204e9800998ecf8427e")
                .build()
        );

        CheckerRequest request = CheckerRequest
            .builder()
            .digestUpdater(DigestUpdaterFactory.create(""))
            .identifiable(IdentifiableFactory.create(""))
            .officialDataGetter(OfficialDataGetterFactory.create("d41d8cd98f00b204e9800998ecf8427e"))
            .build();

        CheckerResponse response = CheckerResponse
            .builder()
            .checksumPairs(checksumPairs)
            .integrityPercentage(100.0)
            .identification("")
            .build();

        return Stream
            .<Arguments>builder()
            .add(Arguments.of(request, response))
            .build();
    }

    @ParameterizedTest
    @MethodSource(value = "getResultTests")
    void run(CheckerRequest input, CheckerResponse output) {
        assertEquals(output, service.run(input));
    }
}
