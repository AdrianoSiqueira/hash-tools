package hashtools.formatter;

import hashtools.domain.GeneratorResponse;

import java.util.stream.Collectors;

public class GeneratorResponseFormatter implements Formatter<GeneratorResponse> {

    @Override
    public String format(GeneratorResponse response) {
        String layout = """
                        %s  %s
                        """;

        return response
            .getChecksums()
            .stream()
            .map(checksum -> layout.formatted(
                checksum.getChecksum(),
                response.getIdentification()))
            .collect(Collectors.joining());
    }
}
