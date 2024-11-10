package hashtools.comparator;

import hashtools.shared.Formatter;
import lombok.RequiredArgsConstructor;

import java.util.ResourceBundle;

@RequiredArgsConstructor
public class CLIComparatorResponseFormatter implements Formatter<ComparatorResponse> {

    private static final String LAYOUT = """
                                         %s  %s
                                         %s  %s
                                         %s\
                                         """;

    private final ResourceBundle language;

    @Override
    public String format(ComparatorResponse response) {
        String
            equals = language.getString("hashtools.formatter.cli-comparator-response-formatter.equals"),
            notEquals = language.getString("hashtools.formatter.cli-comparator-response-formatter.not-equals");

        return LAYOUT.formatted(
            response.getChecksum().getHash1(),
            response.getIdentification1().identity(),
            response.getChecksum().getHash2(),
            response.getIdentification2().identity(),
            response.getChecksum().matches() ? equals : notEquals
        );
    }
}
