package hashtools.service;

import hashtools.domain.CheckerRequest;
import hashtools.domain.CheckerResponse;
import hashtools.domain.Checksum;
import hashtools.domain.ChecksumPair;
import hashtools.threadpool.ThreadPoolManager;
import hashtools.utility.ChecksumGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CheckerService implements Service<CheckerRequest, CheckerResponse> {

    private double calculateIntegrityPercentage(List<ChecksumPair> checksumPairs) {
        if (checksumPairs.isEmpty()) {
            return 0.0;
        }

        return checksumPairs
                   .stream()
                   .filter(ChecksumPair::matches)
                   .count()
               * 100.0 / checksumPairs.size();
    }

    private List<ChecksumPair> generateChecksumPairs(CheckerRequest request) {
        ChecksumGenerator  generator         = new ChecksumGenerator();
        List<ChecksumPair> checksumPairs     = new ArrayList<>();
        List<Checksum>     officialChecksums = getOfficialChecksums(request);

        ExecutorService executor = ThreadPoolManager.newDaemon(
            getClass().getSimpleName()
        );

        for (Checksum checksum : officialChecksums) {
            executor.execute(() -> {
                String generated = generator.generate(
                    checksum.getAlgorithm(),
                    request.getDigestUpdater()
                );

                ChecksumPair checksumPair = ChecksumPair
                    .builder()
                    .algorithm(checksum.getAlgorithm())
                    .checksum1(checksum.getChecksum())
                    .checksum2(generated)
                    .build();

                checksumPairs.add(checksumPair);
            });
        }

        ThreadPoolManager.terminate(executor);

        return checksumPairs;
    }

    private String getIdentification(CheckerRequest request) {
        return request
            .getIdentifiable()
            .identify();
    }

    private List<Checksum> getOfficialChecksums(CheckerRequest request) {
        return request
            .getOfficialDataGetter()
            .get();
    }

    @Override
    public CheckerResponse run(CheckerRequest request) {
        List<ChecksumPair> checksumPairs       = generateChecksumPairs(request);
        double             integrityPercentage = calculateIntegrityPercentage(checksumPairs);
        String             identification      = getIdentification(request);

        return CheckerResponse
            .builder()
            .checksumPairs(checksumPairs)
            .integrityPercentage(integrityPercentage)
            .identification(identification)
            .build();
    }
}
