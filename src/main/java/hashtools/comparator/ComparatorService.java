package hashtools.comparator;

import hashtools.shared.Algorithm;
import hashtools.shared.ChecksumGenerator;
import hashtools.shared.Evaluation;
import hashtools.shared.Formatter;
import hashtools.shared.identification.Identification;
import hashtools.shared.messagedigest.MessageDigestUpdater;
import hashtools.shared.threadpool.ThreadPool;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
                try {
                    String hash = generator.generate(
                        checksum.getAlgorithm(),
                        MessageDigestUpdater.of(request.getInputFile1())
                    );

                    checksum.setHash1(hash);
                } catch (IOException | NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            });

            executor.execute(() -> {
                try {
                    String hash = generator.generate(
                        checksum.getAlgorithm(),
                        MessageDigestUpdater.of(request.getInputFile2())
                    );

                    checksum.setHash2(hash);
                } catch (IOException | NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        ComparatorResponse response = new ComparatorResponse();
        response.setIdentification1(Identification.of(request.getInputFile1()));
        response.setIdentification2(Identification.of(request.getInputFile2()));
        response.setChecksum(checksum);
        return response;
    }
}
