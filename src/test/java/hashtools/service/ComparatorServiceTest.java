package hashtools.service;

import hashtools.domain.Algorithm;
import hashtools.domain.ChecksumPair;
import hashtools.domain.ComparatorRequest;
import hashtools.domain.ComparatorResponse;
import hashtools.domain.messagedigest.DigestUpdaterFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComparatorServiceTest {

    private static Path file1;
    private static Path file2;

    private ComparatorService service = new ComparatorService();

    @BeforeAll
    static void createFiles()
    throws IOException {
        file1 = Files.createTempFile(null, null);
        file2 = Files.createTempFile(null, null);
    }

    @AfterAll
    static void deleteFiles()
    throws IOException {
        Files.deleteIfExists(file1);
        Files.deleteIfExists(file2);
    }

    public static Stream<Arguments> getResultTests() {
        ChecksumPair checksumPair = ChecksumPair
            .builder()
            .algorithm(Algorithm.MD5)
            .checksum1("d41d8cd98f00b204e9800998ecf8427e")
            .checksum2("d41d8cd98f00b204e9800998ecf8427e")
            .build();

        ComparatorRequest request = ComparatorRequest
            .builder()
            .digestUpdater1(DigestUpdaterFactory.create(file1))
            .digestUpdater2(DigestUpdaterFactory.create(file2))
            .build();

        ComparatorResponse response = ComparatorResponse
            .builder()
            .checksumPair(checksumPair)
            .matches(checksumPair.matches())
            .build();

        return Stream
            .<Arguments>builder()
            .add(Arguments.of(request, response))
            .build();
    }

    @ParameterizedTest
    @MethodSource(value = "getResultTests")
    void run(ComparatorRequest input, ComparatorResponse output) {
        assertEquals(output, service.run(input));
    }
}
