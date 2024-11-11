package hashtools.service;

import hashtools.generator.GeneratorChecksum;
import hashtools.generator.GeneratorRequest;
import hashtools.generator.GeneratorResponse;
import hashtools.shared.Algorithm;
import hashtools.shared.Formatter;
import hashtools.shared.threadpool.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Service {

    public <RESPONSE_TYPE> String format(RESPONSE_TYPE response, Formatter<RESPONSE_TYPE> formatter) {
        return formatter.format(response);
    }

    public GeneratorResponse run(GeneratorRequest request) {
        List<GeneratorChecksum> checksums = new ArrayList<>();

        try (ExecutorService executor = ThreadPool.newFixedDaemon("GeneratorThreadPool")) {
            ChecksumService checksumService = new ChecksumService();

            for (Algorithm algorithm : request.getAlgorithms()) {
                executor.execute(() -> {
                    String hash = checksumService.generate(algorithm, request.getInput());

                    GeneratorChecksum checksum = new GeneratorChecksum();
                    checksum.setAlgorithm(algorithm);
                    checksum.setHash(hash);

                    checksums.add(checksum);
                });
            }
        }

        GeneratorResponse response = new GeneratorResponse();
        response.setIdentification(request.getIdentification());
        response.setChecksums(checksums);
        return response;
    }
}
