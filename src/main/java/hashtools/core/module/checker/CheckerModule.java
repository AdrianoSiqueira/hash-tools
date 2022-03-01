package hashtools.core.module.checker;

import hashtools.core.model.Result;
import hashtools.core.model.Sample;
import hashtools.core.model.SampleList;
import hashtools.core.module.generator.HashGenerator;
import hashtools.core.service.SampleService;

import java.util.List;
import java.util.concurrent.Callable;

public class CheckerModule implements Callable<SampleList> {

    private final String inputData;
    private final String officialData;


    public CheckerModule(String inputData, String officialData) {
        this.inputData = inputData;
        this.officialData = officialData;
    }


    private double runCheckerModuleAndRetrieveReliabilityPercentage(SampleList sampleList) {
        HashGenerator    hashGenerator    = new HashGenerator();
        ResultCalculator resultCalculator = new ResultCalculator();

        return sampleList.getSamples()
                         .stream()
                         .parallel()
                         .peek(this::setInputDataToSample)
                         .peek(hashGenerator)
                         .peek(resultCalculator)
                         .map(Sample::getResult)
                         .map(Result::getScore)
                         .reduce(Double::sum)
                         .orElse(0.0)
               * 100 / sampleList.getMaxPossibleScore();
    }

    private void setInputDataToSample(Sample sample) {
        sample.setInputData(inputData);
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
