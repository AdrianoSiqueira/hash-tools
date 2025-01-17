package hashtools.checker;

import hashtools.checker.officialchecksum.OfficialChecksumExtractor;
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
public class CheckerService {

    private final ResourceBundle languageBundle;

    public String formatResponse(CheckerResponse response) {
        return Formatter.format(
            response,
            new CheckerResponseFormatter(languageBundle)
        );
    }

    public CheckerResponse processRequest(CheckerRequest request)
    throws ExecutionException, InterruptedException {
        Evaluation.evaluate(new CheckerRequestEvaluation(request));

        List<CheckerChecksum> checksums = OfficialChecksumExtractor
            .of(request.getChecksumFile())
            .extract();

        List<Future<String>> futureChecksums = new LinkedList<>();
        ChecksumGenerator generator = new ChecksumGenerator();
        MessageDigestUpdater updater = MessageDigestUpdater.of(request.getInputFile());

        try (ExecutorService threadPool = ThreadPool.newFixedDaemon("CheckerThreadPool")) {
            for (CheckerChecksum checksum : checksums) {
                futureChecksums.add(
                    threadPool.submit(() ->
                        generator.generate(
                            checksum.getAlgorithm(),
                            updater
                        )
                    )
                );
            }
        }

        for (int i = 0; i < checksums.size(); i++) {
            String generatedChecksum = futureChecksums
                .get(i)
                .get();

            checksums
                .get(i)
                .setGeneratedHash(generatedChecksum);
        }

        CheckerResponse response = new CheckerResponse();
        response.setIdentification(Identification.of(request.getInputFile()));
        response.setChecksums(checksums);
        return response;
    }
}
