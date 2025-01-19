package hashtools.applicationmodule.generator;

import hashtools.coremodule.checksumgenerator.Algorithm;
import hashtools.coremodule.checksumgenerator.ChecksumGenerator;
import hashtools.coremodule.Evaluation;
import hashtools.coremodule.Formatter;
import hashtools.coremodule.identification.Identification;
import hashtools.coremodule.messagedigest.MessageDigestUpdater;
import hashtools.coremodule.threadpool.ThreadPool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class GeneratorService {

    public String formatResponse(GeneratorResponse response) {
        return Formatter.format(
            response,
            new GeneratorResponseFormatter()
        );
    }

    public GeneratorResponse processRequest(GeneratorRequest request)
    throws ExecutionException, InterruptedException {
        Evaluation.evaluate(new GeneratorRequestEvaluation(request));

        List<GeneratorChecksum> checksums = new LinkedList<>();
        List<Future<String>> futureChecksums = new LinkedList<>();

        ChecksumGenerator generator = new ChecksumGenerator();
        MessageDigestUpdater updater = MessageDigestUpdater.of(request.getInputFile());

        try (ExecutorService threadPool = ThreadPool.newFixedDaemon("GeneratorThreadPool")) {
            for (Algorithm algorithm : request.getAlgorithms()) {
                futureChecksums.add(
                    threadPool.submit(() ->
                        generator.generate(
                            algorithm,
                            updater
                        )
                    )
                );

                GeneratorChecksum checksum = new GeneratorChecksum();
                checksum.setAlgorithm(algorithm);
                checksums.add(checksum);
            }
        }

        for (int i = 0; i < checksums.size(); i++) {
            String generatedChecksum = futureChecksums
                .get(i)
                .get();

            checksums
                .get(i)
                .setHash(generatedChecksum);
        }

        GeneratorResponse response = new GeneratorResponse();
        response.setIdentification(Identification.of(request.getInputFile()));
        response.setChecksums(checksums);
        return response;
    }
}
