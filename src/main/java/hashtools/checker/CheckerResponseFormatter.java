package hashtools.checker;

import hashtools.shared.Algorithm;
import hashtools.shared.Formatter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CheckerResponseFormatter implements Formatter<CheckerResponse> {

    private static final String LAYOUT = """
                                         %s
                                         %s
                                         %s
                                         %s
                                         """;

    private final ResourceBundle language;

    @Override
    public String format(CheckerResponse response) {
        String matches = language.getString("hashtools.checker.checker-response-formatter.matches");
        String notMatches = language.getString("hashtools.checker.checker-response-formatter.not-matches");
        String reliability = language.getString("hashtools.checker.checker-response-formatter.reliability");
        String statusMask = "%s: %.2f%%";

        return response
            .getChecksums()
            .stream()
            .sorted(new Ascending())
            .map(checksum -> LAYOUT.formatted(
                checksum.getAlgorithm(),
                checksum.getOfficialHash(),
                checksum.getGeneratedHash(),
                checksum.matches() ? matches : notMatches))
            .collect(Collectors.joining(System.lineSeparator(), "", System.lineSeparator()))
            .concat(statusMask.formatted(reliability, response.calculateReliabilityPercentage()));
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
