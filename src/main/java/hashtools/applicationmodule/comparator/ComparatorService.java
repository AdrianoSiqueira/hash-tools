package hashtools.applicationmodule.comparator;

import hashtools.coremodule.Algorithm;
import hashtools.coremodule.ChecksumGenerator;
import hashtools.coremodule.Evaluation;
import hashtools.coremodule.Formatter;
import hashtools.coremodule.identification.Identification;
import hashtools.coremodule.messagedigest.MessageDigestUpdater;
import hashtools.coremodule.threadpool.ThreadPool;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@RequiredArgsConstructor
public class ComparatorService {

    private final ResourceBundle languageBundle;

    public String formatResponse(ComparatorResponse response) {
        return Formatter.format(
            response,
            new ComparatorResponseFormatter(languageBundle)
        );
    }

    public ComparatorResponse processRequest(ComparatorRequest request)
    throws ExecutionException, InterruptedException {
        Evaluation.evaluate(new ComparatorRequestEvaluation(request));

        ComparatorChecksum checksum = new ComparatorChecksum();
        checksum.setAlgorithm(Algorithm.MD5);

        List<Future<String>> futureChecksums = new LinkedList<>();
        ChecksumGenerator generator = new ChecksumGenerator();

        try (ExecutorService threadPool = ThreadPool.newFixedDaemon("ComparatorThreadPool")) {
            futureChecksums.add(
                threadPool.submit(() ->
                    generator.generate(
                        checksum.getAlgorithm(),
                        MessageDigestUpdater.of(request.getInputFile1())
                    )
                )
            );

            futureChecksums.add(
                threadPool.submit(() ->
                    generator.generate(
                        checksum.getAlgorithm(),
                        MessageDigestUpdater.of(request.getInputFile2())
                    )
                )
            );
        }

        checksum.setHash1(futureChecksums.get(0).get());
        checksum.setHash2(futureChecksums.get(1).get());

        ComparatorResponse response = new ComparatorResponse();
        response.setIdentification1(Identification.of(request.getInputFile1()));
        response.setIdentification2(Identification.of(request.getInputFile2()));
        response.setChecksum(checksum);
        return response;
    }
}
