package hashtools.checker;

import hashtools.shared.ChecksumGenerator;
import hashtools.shared.Evaluation;
import hashtools.shared.Formatter;
import hashtools.shared.threadpool.ThreadPool;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public class CheckerService {

    private final ResourceBundle languageBundle;

    public String formatResponse(CheckerResponse response) {
        return Formatter.format(
            response,
            new CheckerResponseFormatter(languageBundle)
        );
    }

    public CheckerResponse processRequest(CheckerRequest request) {
        Evaluation.evaluate(new CheckerRequestEvaluation(request));

        List<CheckerChecksum> checksums = request
            .createNewOfficialChecksumExtractor()
            .extract();

        try (ExecutorService threadPool = ThreadPool.newFixedDaemon("CheckerThreadPool")) {
            ChecksumGenerator generator = new ChecksumGenerator();

            for (CheckerChecksum checksum : checksums) {
                threadPool.execute(() -> {
                    String hash = generator.generate(
                        checksum.getAlgorithm(),
                        request.createNewMessageDigestUpdater()
                    );

                    checksum.setGeneratedHash(hash);
                });
            }
        }

        CheckerResponse response = new CheckerResponse();
        response.setIdentification(request.createNewIdentification());
        response.setChecksums(checksums);
        return response;
    }
}
