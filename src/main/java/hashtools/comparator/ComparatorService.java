package hashtools.comparator;

import hashtools.shared.Algorithm;
import hashtools.shared.ChecksumGenerator;
import hashtools.shared.Evaluation;
import hashtools.shared.Formatter;
import hashtools.shared.threadpool.ThreadPool;
import lombok.RequiredArgsConstructor;

import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public class ComparatorService {

    private final ResourceBundle languageBundle;

    public String formatResponse(ComparatorResponse response) {
        return Formatter.format(
            response,
            new ComparatorResponseFormatter(languageBundle)
        );
    }

    public ComparatorResponse processRequest(ComparatorRequest request) {
        Evaluation.evaluate(new ComparatorRequestEvaluation(request));

        ComparatorChecksum checksum = new ComparatorChecksum();
        checksum.setAlgorithm(Algorithm.MD5);

        try (ExecutorService executor = ThreadPool.newFixedDaemon("ComparatorThreadPool")) {
            ChecksumGenerator generator = new ChecksumGenerator();

            executor.execute(() -> {
                String hash = generator.generate(
                    checksum.getAlgorithm(),
                    request.createNewMessageDigestUpdater1()
                );

                checksum.setHash1(hash);
            });

            executor.execute(() -> {
                String hash = generator.generate(
                    checksum.getAlgorithm(),
                    request.createNewMessageDigestUpdater2()
                );

                checksum.setHash2(hash);
            });
        }

        ComparatorResponse response = new ComparatorResponse();
        response.setIdentification1(request.createNewIdentification1());
        response.setIdentification2(request.createNewIdentification2());
        response.setChecksum(checksum);
        return response;
    }
}
