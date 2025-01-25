package hashtools.applicationmodule.comparator;

import hashtools.coremodule.Evaluation;
import hashtools.coremodule.Formatter;
import hashtools.coremodule.checksumgenerator.Algorithm;
import hashtools.coremodule.checksumgenerator.FileChecksumGenerator;
import hashtools.coremodule.identification.FileIdentification;
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

    private static final Algorithm ALGORITHM = Algorithm.MD5;

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


        List<Future<String>> futureChecksums = new LinkedList<>();


        try (ExecutorService threadPool = ThreadPool.newFixedDaemon("ComparatorThreadPool")) {
            futureChecksums.add(
                threadPool.submit(() ->
                    new FileChecksumGenerator(
                        ALGORITHM,
                        request.getInputFile1()
                    ).generate()
                )
            );

            futureChecksums.add(
                threadPool.submit(() ->
                    new FileChecksumGenerator(
                        ALGORITHM,
                        request.getInputFile1()
                    ).generate()
                )
            );
        }


        ComparatorChecksum checksum = new ComparatorChecksum();
        checksum.setAlgorithm(ALGORITHM);
        checksum.setHash1(futureChecksums.get(0).get());
        checksum.setHash2(futureChecksums.get(1).get());


        ComparatorResponse response = new ComparatorResponse();
        response.setIdentification1(new FileIdentification(request.getInputFile1()));
        response.setIdentification2(new FileIdentification(request.getInputFile2()));
        response.setChecksum(checksum);
        return response;
    }
}
