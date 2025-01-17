package hashtools.applicationmodule.generator;

import hashtools.coremodule.Algorithm;
import hashtools.coremodule.Formatter;

import java.util.Comparator;
import java.util.stream.Collectors;

public class GeneratorResponseFormatter implements Formatter<GeneratorResponse> {

    private static final String LAYOUT = "%s  %s";

    @Override
    public String format(GeneratorResponse response) {
        String identification = response
            .getIdentification()
            .identity();

        return response
            .getChecksums()
            .stream()
            .sorted(new Ascending())
            .map(GeneratorChecksum::getHash)
            .map(hash -> LAYOUT.formatted(hash, identification))
            .collect(Collectors.joining(System.lineSeparator()));
    }


    private static class Ascending implements Comparator<GeneratorChecksum> {

        @Override
        public int compare(GeneratorChecksum c1, GeneratorChecksum c2) {
            return Comparator
                .comparing(Algorithm::getLength)
                .compare(
                    c1.getAlgorithm(),
                    c2.getAlgorithm()
                );
        }
    }
}
