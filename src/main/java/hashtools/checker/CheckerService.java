package hashtools.checker;

import hashtools.shared.ChecksumGenerator;
import hashtools.shared.Formatter;
import hashtools.shared.RequestProcessor;
import hashtools.shared.ResponseFormatter;
import hashtools.shared.threadpool.ThreadPool;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class CheckerService implements RequestProcessor<CheckerRequest, CheckerResponse>, ResponseFormatter<CheckerResponse> {

    @Override
    public String formatResponse(CheckerResponse response, Formatter<CheckerResponse> formatter) {
        return formatter.format(response);
    }

    @Override
    public CheckerResponse processRequest(CheckerRequest request) {
        List<CheckerChecksum> checksums = request
            .getOfficialChecksumExtractor()
            .extract();

        try (ExecutorService threadPool = ThreadPool.newFixedDaemon("CheckerThreadPool")) {
            ChecksumGenerator generator = new ChecksumGenerator();

            for (CheckerChecksum checksum : checksums) {
                threadPool.execute(() -> {
                    String hash = generator.generate(
                        checksum.getAlgorithm(),
                        request.getInput()
                    );

                    checksum.setGeneratedHash(hash);
                });
            }
        }

        CheckerResponse response = new CheckerResponse();
        response.setIdentification(request.getIdentification());
        response.setChecksums(checksums);
        return response;
    }
}
