package hashtools.service;

import hashtools.domain.Algorithm;
import hashtools.domain.Checksum;
import hashtools.domain.GeneratorRequest;
import hashtools.domain.GeneratorResponse;
import hashtools.utility.ChecksumGenerator;

import java.util.ArrayList;
import java.util.List;

public class GeneratorService implements Service<GeneratorRequest, GeneratorResponse> {

    private static String getIdentification(GeneratorRequest request) {
        return request
            .getIdentifiable()
            .identify();
    }

    private List<Checksum> generateChecksums(GeneratorRequest request) {
        ChecksumGenerator generator = new ChecksumGenerator();
        List<Checksum>    checksums = new ArrayList<>();

        for (Algorithm algorithm : request.getAlgorithms()) {
            String generated = generator.generate(
                algorithm,
                request.getDigestUpdater()
            );

            Checksum checksum = Checksum
                .builder()
                .algorithm(algorithm)
                .checksum(generated)
                .build();

            checksums.add(checksum);
        }

        return checksums;
    }

    @Override
    public GeneratorResponse run(GeneratorRequest request) {
        List<Checksum> checksums      = generateChecksums(request);
        String         identification = getIdentification(request);

        return GeneratorResponse
            .builder()
            .checksums(checksums)
            .identification(identification)
            .build();
    }
}
