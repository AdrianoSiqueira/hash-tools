package hashtools.checker;

import hashtools.checker.officialchecksum.OfficialChecksumExtractor;
import hashtools.shared.ChecksumGenerator;
import hashtools.shared.Evaluation;
import hashtools.shared.Formatter;
import hashtools.shared.identification.Identification;
import hashtools.shared.messagedigest.MessageDigestUpdater;
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

        List<CheckerChecksum> checksums = OfficialChecksumExtractor
            .of(request.getChecksumFile())
            .extract();

        try (ExecutorService threadPool = ThreadPool.newFixedDaemon("CheckerThreadPool")) {
            ChecksumGenerator generator = new ChecksumGenerator();
            MessageDigestUpdater updater = MessageDigestUpdater.of(request.getInputFile());

            for (CheckerChecksum checksum : checksums) {
                threadPool.execute(() -> {
                    String hash = generator.generate(
                        checksum.getAlgorithm(),
                        updater
                    );

                    checksum.setGeneratedHash(hash);
                });
            }
        }

        CheckerResponse response = new CheckerResponse();
        response.setIdentification(Identification.of(request.getInputFile()));
        response.setChecksums(checksums);
        return response;
    }
}
