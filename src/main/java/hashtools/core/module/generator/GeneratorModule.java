package hashtools.core.module.generator;

import aslib.filemanager.FileWriter;
import hashtools.core.model.Sample;
import hashtools.core.model.SampleList;
import hashtools.core.util.SampleFromShaType;
import hashtools.core.util.ShaTypeFromObject;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * <p>
 * Class responsible for executing the generation module.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.1
 * @since 2.0.0
 */
public class GeneratorModule implements Callable<SampleList> {

    private final Object  inputObject;
    private final Path    destination;
    private final List<?> algorithms;


    /**
     * <p>
     * Creates an instance of {@link GeneratorModule} class.
     * </p>
     *
     * <p>
     * The input object can be a file, a path to a file, or any string.
     * </p>
     *
     * <p>
     * The destination file will be erased to contain only the new data. It will
     * be created if it doesn't exist.
     * </p>
     *
     * @param inputObject Object used to generate hashes. Not null.
     * @param destination File where generated hashes will be saved.
     * @param algorithms  Algorithms used to generate the hashes.
     *
     * @since 1.0.0
     */
    public GeneratorModule(Object inputObject, Object destination, List<?> algorithms) {
        this.inputObject = inputObject;
        this.destination = Path.of(destination.toString());
        this.algorithms = algorithms;
    }

    /**
     * <p>
     * Clears the content of destination file. The file will be created if
     * necessary.
     * </p>
     *
     * @since 1.0.0
     */
    private void clearDestination() {
        FileWriter writer = new FileWriter();
        writer.write("", destination);
    }

    /**
     * <p>
     * Sets the test object to the sample.
     * </p>
     *
     * <p>
     * This method is designed to be used within a stream.
     * </p>
     *
     * @param sample Sample where the object will be set.
     *
     * @since 1.0.0
     */
    private void setObjectToSample(Sample sample) {
        sample.setObject(inputObject);
    }


    /**
     * <p>
     * Executes the entire sequence of generating the hashes.
     * </p>
     *
     * <p>
     * This method generates the hashes and writes the data to the destination
     * file.
     * </p>
     *
     * @return A {@link SampleList} object with the generated data.
     *
     * @since 1.0.0
     */
    @Override
    public SampleList call() {
        this.clearDestination();

        SampleList sampleList = new SampleList();

        algorithms.stream()
                  .parallel()
                  .map(new ShaTypeFromObject())
                  .filter(Objects::nonNull)
                  .map(new SampleFromShaType())
                  .peek(this::setObjectToSample)
                  .peek(new HashGenerator())
                  .peek(new ResultWriter(destination))
                  .forEach(sampleList::add);

        return sampleList;
    }
}
