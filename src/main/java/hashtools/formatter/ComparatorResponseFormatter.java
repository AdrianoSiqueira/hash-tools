package hashtools.formatter;

import hashtools.domain.ComparatorResponse;
import hashtools.language.Language;

import java.util.ResourceBundle;

public class ComparatorResponseFormatter implements Formatter<ComparatorResponse> {

    @Override
    public String format(ComparatorResponse response) {
        ResourceBundle language = Language
            .INSTANCE
            .getBundle();

        String status = response.isMatches()
            ? language.getString("hashtools.formatter.comparator_response_formatter.equal")
            : language.getString("hashtools.formatter.comparator_response_formatter.different");

        String layout = """
                        %s: %s
                        %s: %s
                        %s: %s
                        """;

        return layout.formatted(
            language.getString("hashtools.formatter.comparator_response_formatter.file_1"),
            response.getChecksumPair().getChecksum1(),
            language.getString("hashtools.formatter.comparator_response_formatter.file_2"),
            response.getChecksumPair().getChecksum2(),
            language.getString("hashtools.formatter.comparator_response_formatter.status"),
            status
        );
    }
}
