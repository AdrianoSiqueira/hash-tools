package hashtools.service;

import hashtools.domain.Algorithm;
import hashtools.domain.Checksum;
import hashtools.domain.GeneratorRequest;
import hashtools.domain.GeneratorResponse;
import hashtools.factory.ThreadPoolFactory;
import hashtools.utility.ChecksumGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class GeneratorService implements Service<GeneratorRequest, GeneratorResponse> {

    private List<Checksum> generateChecksums(GeneratorRequest request) {
        ChecksumGenerator generator = new ChecksumGenerator();
        List<Checksum>    checksums = new ArrayList<>();

        try (ExecutorService executor = ThreadPoolFactory.DAEMON.create()) {
            for (Algorithm algorithm : request.getAlgorithms()) {
                executor.execute(() -> {
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
                });
            }
        }

        return checksums;
    }

    private String getIdentification(GeneratorRequest request) {
        return request
            .getIdentifiable()
            .identify();
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