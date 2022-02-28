package hashtools.core.module.checker;

import hashtools.core.model.Result;
import hashtools.core.model.Sample;
import hashtools.core.model.SampleList;
import hashtools.core.module.generator.HashGenerator;
import hashtools.core.service.SampleService;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

public class CheckerModule implements Callable<SampleList> {

    private final String objectToCheck;
    private final String officialData;


    public CheckerModule(String objectToCheck, String officialData) {
        this.objectToCheck = objectToCheck;
        this.officialData = officialData;
    }


    private List<Sample> createListOfSample() {
        return new FilePathDetector().test(officialData)
               ? new SamplesFromFile().apply(Path.of(officialData))
               : List.of(new SampleFromHash().apply((officialData)));
    }

    private double runCheckerModuleAndRetrieveReliabilityPercentage(SampleList sampleList) {
        HashGenerator    hashGenerator    = new HashGenerator();
        ResultCalculator resultCalculator = new ResultCalculator();

        return sampleList.getSamples()
                         .stream()
                         .parallel()
                         .peek(this::setObjectToSample)
                         .peek(hashGenerator)
                         .peek(resultCalculator)
                         .map(Sample::getResult)
                         .map(Result::getScore)
                         .reduce(Double::sum)
                         .orElse(0.0)
               * 100 / sampleList.getMaxPossibleScore();
    }

    private void setObjectToSample(Sample sample) {
        sample.setObject(objectToCheck);
    }


    @Override
    public SampleList call() {
        List<Sample> samples = new SampleService().createSampleList(officialData);

        SampleList sampleList = new SampleList();
        sampleList.setSamples(samples);

        double percentage = sampleList.getMaxPossibleScore() != 0
                            ? runCheckerModuleAndRetrieveReliabilityPercentage(sampleList)
                            : 0.0;

        sampleList.setReliabilityPercentage(percentage);
        return sampleList;
    }
}
