package hashtools.core.module.generator;

import hashtools.core.model.HashAlgorithm;
import hashtools.core.model.Sample;
import hashtools.core.model.SampleContainer;
import hashtools.core.service.HashService;
import hashtools.core.service.SampleService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

public class GeneratorModule implements Callable<SampleContainer> {

    private final String              inputData;
    private final Path                destination;
    private final List<HashAlgorithm> algorithms;


    public GeneratorModule(String inputData, String destination, List<HashAlgorithm> algorithms) {
        this.inputData = inputData;
        this.destination = Path.of(destination);
        this.algorithms = algorithms;
    }


    private void clearDestination() {
        try {
            Files.writeString(destination, "", StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setInputDataToSample(Sample sample) {
        sample.setInputData(inputData);
    }


    @Override
    public SampleContainer call() {
        if (algorithms.isEmpty()) return null;

        this.clearDestination();

        SampleContainer sampleContainer = new SampleContainer();
        HashService     hashService     = new HashService();

        algorithms.stream()
                  .parallel()
                  .map(new SampleService()::createSampleFromAlgorithm)
                  .filter(Optional::isPresent)
                  .map(Optional::get)
                  .peek(this::setInputDataToSample)
                  .peek(hashService::generate)
                  .forEach(sampleContainer::add);

        return sampleContainer;
    }
}
