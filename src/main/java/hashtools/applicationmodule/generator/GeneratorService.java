package hashtools.applicationmodule.generator;

import hashtools.coremodule.Evaluation;
import hashtools.coremodule.Formatter;
import hashtools.coremodule.checksumgenerator.Algorithm;
import hashtools.coremodule.checksumgenerator.FileChecksumGenerator;
import hashtools.coremodule.identification.Identification;
import hashtools.coremodule.threadpool.ThreadPool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
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
        List<Future<GeneratorChecksum>> futureChecksums = new LinkedList<>();


        try (ExecutorService threadPool = ThreadPool.newFixedDaemon("GeneratorThreadPool")) {
            for (Algorithm algorithm : request.getAlgorithms()) {
                Callable<GeneratorChecksum> generateChecksum = () -> {
                    String generatedChecksum = new FileChecksumGenerator(
                        algorithm,
                        request.getInputFile()
                    ).generate();


                    GeneratorChecksum checksum = new GeneratorChecksum();
                    checksum.setAlgorithm(algorithm);
                    checksum.setHash(generatedChecksum);
                    return checksum;
                };

                Future<GeneratorChecksum> future = threadPool.submit(generateChecksum);
                futureChecksums.add(future);
            }
        }


        for (Future<GeneratorChecksum> futureChecksum : futureChecksums) {
            GeneratorChecksum checksum = futureChecksum.get();
            checksums.add(checksum);
        }


        GeneratorResponse response = new GeneratorResponse();
        response.setIdentification(Identification.of(request.getInputFile()));
        response.setChecksums(checksums);
        return response;
    }
}
