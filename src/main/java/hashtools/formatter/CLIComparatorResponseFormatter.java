package hashtools.formatter;

import hashtools.domain.ComparatorResponse;

public class CLIComparatorResponseFormatter implements Formatter<ComparatorResponse> {

    private static final String LAYOUT = """
                                         %s  %s
                                         %s  %s
                                         %s\
                                         """;

    @Override
    public String format(ComparatorResponse response) {
        return LAYOUT.formatted(
            response.getChecksum().getHash1(),
            response.getIdentification1().identity(),
            response.getChecksum().getHash2(),
            response.getIdentification2().identity(),
            response.getChecksum().matches() ? "Equals" : "Not equals"
        );
    }
}
