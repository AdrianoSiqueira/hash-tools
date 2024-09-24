package hashtools.formatter;

import hashtools.domain.Algorithm;
import hashtools.domain.CheckerChecksum;
import hashtools.domain.CheckerResponse;

import java.util.Comparator;
import java.util.stream.Collectors;

public class CLICheckerResponseFormatter implements Formatter<CheckerResponse> {

    private static final String LAYOUT = """
                                         %s
                                         %s
                                         %s
                                         %s
                                         """;

    @Override
    public String format(CheckerResponse response) {
        return response
            .getChecksums()
            .stream()
            .sorted(new Ascending())
            .map(checksum -> LAYOUT.formatted(
                checksum.getAlgorithm(),
                checksum.getOfficialHash(),
                checksum.getGeneratedHash(),
                checksum.matches() ? "Matches" : "Does not match"))
            .collect(Collectors.joining(System.lineSeparator(), "", System.lineSeparator()))
            .concat("Reliability: " + response.calculateReliabilityPercentage() + "%");
    }


    private static class Ascending implements Comparator<CheckerChecksum> {

        @Override
        public int compare(CheckerChecksum c1, CheckerChecksum c2) {
            return Comparator
                .comparing(Algorithm::getLength)
                .compare(c1.getAlgorithm(), c2.getAlgorithm());
        }
    }
}
