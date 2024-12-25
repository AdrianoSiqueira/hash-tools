package hashtools.generator;

import hashtools.shared.Algorithm;
import hashtools.shared.ChecksumGenerator;
import hashtools.shared.Evaluation;
import hashtools.shared.Formatter;
import hashtools.shared.threadpool.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class GeneratorService {

    public String formatResponse(GeneratorResponse response) {
        return Formatter.format(
            response,
            new GeneratorResponseFormatter()
        );
    }

    public GeneratorResponse processRequest(GeneratorRequest request) {
        Evaluation.evaluate(new GeneratorRequestEvaluation(request));

        List<GeneratorChecksum> checksums = new ArrayList<>();

        try (ExecutorService executor = ThreadPool.newFixedDaemon("GeneratorThreadPool")) {
            ChecksumGenerator generator = new ChecksumGenerator();

            for (Algorithm algorithm : request.getAlgorithms()) {
                executor.execute(() -> {
                    String hash = generator.generate(
                        algorithm,
                        request.createNewMessageDigestUpdater()
                    );

                    GeneratorChecksum checksum = new GeneratorChecksum();
                    checksum.setAlgorithm(algorithm);
                    checksum.setHash(hash);

                    checksums.add(checksum);
                });
            }
        }

        GeneratorResponse response = new GeneratorResponse();
        response.setIdentification(request.createNewIdentification());
        response.setChecksums(checksums);
        return response;
    }
}
