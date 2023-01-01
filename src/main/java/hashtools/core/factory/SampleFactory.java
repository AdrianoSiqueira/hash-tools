package hashtools.core.factory;

import hashtools.core.model.Sample;
import hashtools.core.service.AlgorithmService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SampleFactory {

    public List<Sample> createSamples(List<String> algorithms) {
        AlgorithmService service = new AlgorithmService();

        return algorithms.stream()
                         .filter(service::algorithmIsValid)
                         .map(algorithm -> {
                             Sample sample = new Sample();
                             sample.setAlgorithm(algorithm);
                             return sample;
                         })
                         .collect(Collectors.toList());
    }

    public List<Sample> createSamples(Path officialFile) {
        AlgorithmFactory factory = new AlgorithmFactory();
        List<String>     lines   = new ArrayList<>();

        try {
            lines.addAll(Files.readAllLines(officialFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines.stream()
                    .map(line -> line.split(" "))
                    .flatMap(Stream::of)
                    .map(hash -> {
                        try {
                            String algorithm = factory.getAlgorithm(hash.length());

                            Sample sample = new Sample();
                            sample.setAlgorithm(algorithm);
                            sample.setOfficialHash(hash);
                            return sample;
                        } catch (IllegalArgumentException ignored) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
    }

    public List<Sample> createSamples(String officialHash) {
        if (officialHash == null)
            throw new NullPointerException("Official hash cannot be null");

        try {
            AlgorithmFactory factory   = new AlgorithmFactory();
            String           algorithm = factory.getAlgorithm(officialHash.length());

            Sample sample = new Sample();
            sample.setAlgorithm(algorithm);
            sample.setOfficialHash(officialHash);

            return List.of(sample);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
