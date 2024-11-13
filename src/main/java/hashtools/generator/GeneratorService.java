package hashtools.generator;

import hashtools.shared.ChecksumGenerator;
import hashtools.shared.Algorithm;
import hashtools.shared.Formatter;
import hashtools.shared.RequestProcessor;
import hashtools.shared.ResponseFormatter;
import hashtools.shared.threadpool.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class GeneratorService implements RequestProcessor<GeneratorRequest, GeneratorResponse>, ResponseFormatter<GeneratorResponse> {

    @Override
    public String formatResponse(GeneratorResponse response, Formatter<GeneratorResponse> formatter) {
        return formatter.format(response);
    }

    @Override
    public GeneratorResponse processRequest(GeneratorRequest request) {
        List<GeneratorChecksum> checksums = new ArrayList<>();

        try (ExecutorService executor = ThreadPool.newFixedDaemon("GeneratorThreadPool")) {
            ChecksumGenerator generator = new ChecksumGenerator();

            for (Algorithm algorithm : request.getAlgorithms()) {
                executor.execute(() -> {
                    String hash = generator.generate(
                        algorithm,
                        request.getInput()
                    );

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
