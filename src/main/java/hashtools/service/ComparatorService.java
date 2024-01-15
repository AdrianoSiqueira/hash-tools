package hashtools.service;

import hashtools.domain.Algorithm;
import hashtools.domain.ChecksumPair;
import hashtools.domain.ComparatorRequest;
import hashtools.domain.ComparatorResponse;
import hashtools.factory.ThreadPoolFactory;
import hashtools.utility.ChecksumGenerator;

import java.util.concurrent.ExecutorService;

public class ComparatorService implements Service<ComparatorRequest, ComparatorResponse> {

    private static final Algorithm ALGORITHM = Algorithm.MD5;

    private ChecksumPair generateChecksums(ComparatorRequest request) {
        ChecksumGenerator generator = new ChecksumGenerator();
        String[]          generated = new String[]{"", ""};

        try (ExecutorService executor = ThreadPoolFactory.DAEMON.create()) {
            executor.execute(() -> generated[0] = generator.generate(
                ALGORITHM,
                request.getDigestUpdater1()
            ));

            executor.execute(() -> generated[1] = generator.generate(
                ALGORITHM,
                request.getDigestUpdater2()
            ));
        }

        return ChecksumPair
            .builder()
            .algorithm(ALGORITHM)
            .checksum1(generated[0])
            .checksum2(generated[1])
            .build();
    }

    @Override
    public ComparatorResponse run(ComparatorRequest request) {
        ChecksumPair checksumPair = generateChecksums(request);

        return ComparatorResponse
            .builder()
            .checksumPair(checksumPair)
            .matches(checksumPair.matches())
            .build();
    }
}
