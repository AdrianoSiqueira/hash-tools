package hashtools.core.module.generator;

import hashtools.core.model.Sample;
import hashtools.core.model.SampleList;
import hashtools.core.util.SampleFromShaType;
import hashtools.core.util.ShaTypeFromObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class GeneratorModule implements Callable<SampleList> {

    private final Object  inputObject;
    private final Path    destination;
    private final List<?> algorithms;


    public GeneratorModule(Object inputObject, Object destination, List<?> algorithms) {
        this.inputObject = inputObject;
        this.destination = Path.of(destination.toString());
        this.algorithms = algorithms;
    }


    private void clearDestination() {
        try {
            Files.writeString(destination, "", StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setObjectToSample(Sample sample) {
        sample.setObject(inputObject);
    }


    @Override
    public SampleList call() {
        if (algorithms.isEmpty()) return null;

        this.clearDestination();

        SampleList        sampleList        = new SampleList();
        ShaTypeFromObject shaTypeFromObject = new ShaTypeFromObject();
        SampleFromShaType sampleFromShaType = new SampleFromShaType();
        HashGenerator     hashGenerator     = new HashGenerator();
        ResultWriter      resultWriter      = new ResultWriter(destination);

        Comparator<Sample> comparator = (o1, o2) -> Comparator.comparing((Sample s) -> s.getAlgorithm().getLength())
                                                              .compare(o1, o2);

        algorithms.stream()
                  .parallel()
                  .map(shaTypeFromObject)
                  .filter(Objects::nonNull)
                  .map(sampleFromShaType)
                  .peek(this::setObjectToSample)
                  .peek(hashGenerator)
                  .toList()
                  .stream()
                  .sorted(comparator)
                  .peek(resultWriter)
                  .forEach(sampleList::add);

        return sampleList;
    }
}
