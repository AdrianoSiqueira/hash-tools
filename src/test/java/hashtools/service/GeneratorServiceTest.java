package hashtools.service;

import hashtools.domain.algorithm.Algorithm;
import hashtools.domain.Checksum;
import hashtools.domain.GeneratorRequest;
import hashtools.domain.GeneratorResponse;
import hashtools.domain.identification.IdentifiableFactory;
import hashtools.domain.messagedigest.DigestUpdaterFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GeneratorServiceTest {

    private GeneratorService service = new GeneratorService();

    public static Stream<Arguments> getResultTests() {
        List<Algorithm> algorithms = List.of(
            Algorithm.MD5,
            Algorithm.SHA1
        );

        List<Checksum> checksums = List.of(
            Checksum.builder().algorithm(Algorithm.MD5).checksum("d41d8cd98f00b204e9800998ecf8427e").build(),
            Checksum.builder().algorithm(Algorithm.SHA1).checksum("da39a3ee5e6b4b0d3255bfef95601890afd80709").build()
        );

        GeneratorRequest request = GeneratorRequest
            .builder()
            .algorithms(algorithms)
            .digestUpdater(DigestUpdaterFactory.create(""))
            .identifiable(IdentifiableFactory.create(""))
            .build();

        GeneratorResponse response = GeneratorResponse
            .builder()
            .checksums(checksums)
            .identification("")
            .build();

        return Stream
            .<Arguments>builder()
            .add(Arguments.of(request, response))
            .build();
    }

    @ParameterizedTest
    @MethodSource(value = "getResultTests")
    void run(GeneratorRequest input, GeneratorResponse output) {
        GeneratorResponse response = service.run(input);

        assertThat(response.getChecksums())
            .containsExactlyInAnyOrderElementsOf(output.getChecksums());

        assertThat(response.getIdentification())
            .isEqualTo(output.getIdentification());
    }
}
