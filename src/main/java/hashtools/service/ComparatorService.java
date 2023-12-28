package hashtools.service;

import hashtools.domain.Algorithm;
import hashtools.domain.ChecksumPair;
import hashtools.domain.ComparatorRequest;
import hashtools.domain.ComparatorResponse;
import hashtools.utility.ChecksumGenerator;

public class ComparatorService implements Service<ComparatorRequest, ComparatorResponse> {

    private static final Algorithm ALGORITHM = Algorithm.MD5;

    private String[] generateChecksums(ComparatorRequest request) {
        ChecksumGenerator generator = new ChecksumGenerator();

        return new String[]{
            generator.generate(ALGORITHM, request.getDigestUpdater1()),
            generator.generate(ALGORITHM, request.getDigestUpdater2())
        };
    }

    @Override
    public ComparatorResponse run(ComparatorRequest request) {
        String[] generated = generateChecksums(request);

        ChecksumPair checksumPair = ChecksumPair
            .builder()
            .algorithm(ALGORITHM)
            .checksum1(generated[0])
            .checksum2(generated[1])
            .build();

        return ComparatorResponse
            .builder()
            .checksumPair(checksumPair)
            .matches(checksumPair.matches())
            .build();
    }
}
