package hashtools.core.module.checker;

import hashtools.core.model.Result;
import hashtools.core.model.Sample;
import hashtools.core.model.SampleList;
import hashtools.core.module.generator.HashGenerator;
import hashtools.core.util.FilePathDetector;
import hashtools.core.util.SampleFromHash;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>
 * Class responsible for executing the checking module.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class CheckerModule implements Callable<SampleList> {

    private final String objectToCheck;
    private final String officialData;


    /**
     * <p>
     * Creates an instance of {@link CheckerModule} class.
     * </p>
     *
     * <p>
     * This module needs two elements to run. The first is the object that will
     * be checked. It can be a path to a file or any other string. The second is
     * the official data. It can be a path to a file containing the official
     * hashes, or it can be the hash itself.
     * </p>
     *
     * @param objectToCheck Object that will be checked.
     * @param officialData  Object with the official data.
     *
     * @since 1.0.0
     */
    public CheckerModule(String objectToCheck, String officialData) {
        this.objectToCheck = objectToCheck;
        this.officialData = officialData;
    }


    /**
     * <p>
     * Create the sample list by analyzing the official data. If such data
     * references a file, the file will be read. Otherwise the method will use
     * it as a hash.
     * </p>
     *
     * @return A list of samples.
     *
     * @since 1.0.0
     */
    private List<Sample> createListOfSample() {
        return new FilePathDetector().test(officialData)
               ? new SamplesFromFile().apply(Path.of(officialData))
               : List.of(new SampleFromHash().apply((officialData)));
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
        sample.setObject(objectToCheck);
    }


    /**
     * <p>
     * This method performs the entire module sequence.
     * </p>
     *
     * <p>
     * Automatically generates the sample list from the official data, generates
     * the necessary hashes, determines the result of each one of the samples
     * and calculates the percentage of overall reliability.
     * </p>
     *
     * <p>
     * All this processing is done in a parallel stream, taking advantage of
     * multi-core processors.
     * </p>
     *
     * @return The {@link SampleList} object with all generated data.
     *
     * @since 1.0.0
     */
    @Override
    public SampleList call() {
        List<Sample> samples = createListOfSample();

        SampleList sampleList = new SampleList();
        sampleList.setSamples(samples);

        double percentage = sampleList.getMaxPossibleScore() != 0
                            ? sampleList.getSamples()
                                        .stream()
                                        .parallel()
                                        .peek(this::setObjectToSample)
                                        .peek(new HashGenerator())
                                        .peek(new ResultCalculator())
                                        .map(Sample::getResult)
                                        .map(Result::getScore)
                                        .reduce(Double::sum)
                                        .orElse(0.0)
                              * 100 / sampleList.getMaxPossibleScore()
                            : 0.0;

        sampleList.setReliabilityPercentage(percentage);
        return sampleList;
    }
}
