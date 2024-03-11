package hashtools.formatter;

import hashtools.domain.CheckerResponse;
import hashtools.domain.ChecksumPair;
import hashtools.language.Language;

import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckerResponseFormatter implements Formatter<CheckerResponse> {

    private ResourceBundle language;

    private String[] applyPadding(int padding, String[] strings) {
        return Stream
            .of(strings)
            .map(string -> String.format(
                "%" + padding + "s",
                string))
            .toArray(String[]::new);
    }

    private int calculatePadding(String[] strings) {
        return Stream
            .of(strings)
            .map(String::length)
            .reduce(Integer::max)
            .orElse(0);
    }

    @Override
    public String format(CheckerResponse response) {
        language = Language
            .INSTANCE
            .getBundle();

        return formatResponse(response);
    }

    private String formatChecksumPair(ChecksumPair pair) {
        String layout = """
                        %s: %s
                        %s: %s
                        %s: %s
                        %s: %s
                        """;

        String[] headers = {
            language.getString("hashtools.formatter.checker_response_formatter.algorithm"),
            language.getString("hashtools.formatter.checker_response_formatter.official"),
            language.getString("hashtools.formatter.checker_response_formatter.generated"),
            language.getString("hashtools.formatter.checker_response_formatter.result")
        };

        int padding = calculatePadding(headers);
        headers = applyPadding(padding, headers);

        return layout.formatted(
            headers[0],
            pair.getAlgorithm(),
            headers[1],
            pair.getChecksum1(),
            headers[2],
            pair.getChecksum2(),
            headers[3],
            pair.matches()
        );
    }

    private String formatResponse(CheckerResponse response) {
        String layout = """
                        %s
                        %s
                        %s: %s%%
                        """;

        String separator = "-"
            .repeat(50);

        String formattedChecksums = response
            .getChecksumPairs()
            .stream()
            .map(this::formatChecksumPair)
            .collect(Collectors.joining(
                separator,
                separator.concat("\n"),
                separator
            ));

        return layout.formatted(
            response.getIdentification(),
            formattedChecksums,
            language.getString("hashtools.formatter.checker_response_formatter.integrity"),
            response.getIntegrityPercentage()
        );
    }
}
