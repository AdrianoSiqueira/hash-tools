package hashtools.applicationmodule.checker;

import hashtools.coremodule.Evaluation;
import hashtools.coremodule.Formatter;
import hashtools.coremodule.checksumgenerator.Algorithm;
import hashtools.coremodule.checksumgenerator.FileChecksumGenerator;
import hashtools.coremodule.identification.Identification;
import hashtools.coremodule.officialchecksum.FileOfficialChecksumExtractor;
import hashtools.coremodule.threadpool.ThreadPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@RequiredArgsConstructor
@Slf4j
public class CheckerService {

    private final ResourceBundle languageBundle;

    private List<String> extractOfficialChecksums(Path checksumFile)
    throws IOException {
        FileOfficialChecksumExtractor extractor = new FileOfficialChecksumExtractor(checksumFile);
        return extractor.extract();
    }

    public String formatResponse(CheckerResponse response) {
        return Formatter.format(
            response,
            new CheckerResponseFormatter(languageBundle)
        );
    }

    public CheckerResponse processRequest(CheckerRequest request)
    throws ExecutionException, IOException, InterruptedException {
        Evaluation.evaluate(new CheckerRequestEvaluation(request));


        List<String> officialHashes = extractOfficialChecksums(request.getChecksumFile());
        List<Future<CheckerChecksum>> futureChecksums = new LinkedList<>();
        List<CheckerChecksum> checksums = new ArrayList<>();


        try (ExecutorService threadPool = ThreadPool.newFixedDaemon("CheckerThreadPool")) {
            for (String officialHash : officialHashes) {
                Callable<CheckerChecksum> generateChecksum = () -> {
                    Algorithm algorithm = Algorithm
                        .from(officialHash.length())
                        .orElse(null);

                    if (algorithm == null) {
                        /*
                         * If official hash is not valid,
                         * we return a null CheckerChecksum
                         * that will need to be checked
                         * when getting from the Future.
                         */
                        log.debug("Ignoring invalid checksum: '{}'", officialHash);
                        return null;
                    }


                    String generatedHash = new FileChecksumGenerator(
                        algorithm,
                        request.getInputFile()
                    ).generate();


                    CheckerChecksum checksum = new CheckerChecksum();
                    checksum.setAlgorithm(algorithm);
                    checksum.setOfficialHash(officialHash);
                    checksum.setGeneratedHash(generatedHash);
                    return checksum;
                };

                Future<CheckerChecksum> future = threadPool.submit(generateChecksum);
                futureChecksums.add(future);
            }
        }


        for (Future<CheckerChecksum> futureChecksum : futureChecksums) {
            /*
             * Using Optional because thread pool may return
             * a null CheckerChecksum when the official hash
             * is not valid.
             */
            Optional
                .ofNullable(futureChecksum.get())
                .ifPresent(checksums::add);
        }


        CheckerResponse response = new CheckerResponse();
        response.setIdentification(Identification.of(request.getInputFile()));
        response.setChecksums(checksums);
        return response;
    }
}