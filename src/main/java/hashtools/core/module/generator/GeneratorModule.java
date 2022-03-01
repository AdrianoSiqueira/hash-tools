package hashtools.core.module.generator;

import hashtools.core.model.HashAlgorithm;
import hashtools.core.model.Sample;
import hashtools.core.model.SampleList;
import hashtools.core.service.SampleService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

public class GeneratorModule implements Callable<SampleList> {

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
    public SampleList call() {
        if (algorithms.isEmpty()) return null;

        this.clearDestination();

        SampleList    sampleList    = new SampleList();
        HashGenerator hashGenerator = new HashGenerator();
        ResultWriter  resultWriter  = new ResultWriter(destination);

        Comparator<Sample> comparator = (o1, o2) -> Comparator.comparing((Sample s) -> s.getAlgorithm().getLength())
                                                              .compare(o1, o2);

        algorithms.stream()
                  .parallel()
                  .map(new SampleService()::createSampleFromAlgorithm)
                  .filter(Optional::isPresent)
                  .map(Optional::get)
                  .peek(this::setInputDataToSample)
                  .peek(hashGenerator)
                  .toList()
                  .stream()
                  .sorted(comparator)
                  .peek(resultWriter)
                  .forEach(sampleList::add);

        return sampleList;
    }
}
