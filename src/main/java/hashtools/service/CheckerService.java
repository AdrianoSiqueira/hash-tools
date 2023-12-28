package hashtools.service;

import hashtools.domain.CheckerRequest;
import hashtools.domain.CheckerResponse;
import hashtools.domain.Checksum;
import hashtools.domain.ChecksumPair;
import hashtools.utility.ChecksumGenerator;

import java.util.ArrayList;
import java.util.List;

public class CheckerService implements Service<CheckerRequest, CheckerResponse> {

    private static double calculateIntegrityPercentage(List<ChecksumPair> checksumPairs) {
        if (checksumPairs.isEmpty()) {
            return 0.0;
        }

        return checksumPairs
                   .stream()
                   .filter(ChecksumPair::matches)
                   .count()
               * 100.0 / checksumPairs.size();
    }

    private static List<ChecksumPair> generateChecksumPairs(CheckerRequest request, List<Checksum> officialChecksums) {
        ChecksumGenerator  generator     = new ChecksumGenerator();
        List<ChecksumPair> checksumPairs = new ArrayList<>();

        for (Checksum checksum : officialChecksums) {
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
        }
        return checksumPairs;
    }

    private static String getIdentification(CheckerRequest request) {
        return request
            .getIdentifiable()
            .identify();
    }

    private static List<Checksum> getOfficialChecksums(CheckerRequest request) {
        return request
            .getOfficialDataGetter()
            .get();
    }

    @Override
    public CheckerResponse run(CheckerRequest request) {
        List<Checksum>     officialChecksums   = getOfficialChecksums(request);
        List<ChecksumPair> checksumPairs       = generateChecksumPairs(request, officialChecksums);
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
