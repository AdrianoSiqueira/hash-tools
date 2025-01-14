package hashtools.generator;

import hashtools.shared.Algorithm;
import hashtools.shared.ChecksumGenerator;
import hashtools.shared.Evaluation;
import hashtools.shared.Formatter;
import hashtools.shared.identification.Identification;
import hashtools.shared.messagedigest.MessageDigestUpdater;
import hashtools.shared.threadpool.ThreadPool;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
            MessageDigestUpdater updater = MessageDigestUpdater.of(request.getInputFile());

            for (Algorithm algorithm : request.getAlgorithms()) {
                executor.execute(() -> {
                    try {
                        String hash = generator.generate(
                            algorithm,
                            updater
                        );

                        GeneratorChecksum checksum = new GeneratorChecksum();
                        checksum.setAlgorithm(algorithm);
                        checksum.setHash(hash);

                        checksums.add(checksum);
                    } catch (IOException | NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        GeneratorResponse response = new GeneratorResponse();
        response.setIdentification(Identification.of(request.getInputFile()));
        response.setChecksums(checksums);
        return response;
    }
}
