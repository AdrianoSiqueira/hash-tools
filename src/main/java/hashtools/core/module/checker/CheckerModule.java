package hashtools.core.module.checker;

import hashtools.core.model.Result;
import hashtools.core.model.Sample;
import hashtools.core.model.SampleContainer;
import hashtools.core.service.HashService;
import hashtools.core.service.SampleService;

import java.util.List;
import java.util.concurrent.Callable;

public class CheckerModule implements Callable<SampleContainer> {

    private final String inputData;
    private final String officialData;


    public CheckerModule(String inputData, String officialData) {
        this.inputData = inputData;
        this.officialData = officialData;
    }


    private double runCheckerModuleAndRetrieveReliabilityPercentage(SampleContainer sampleContainer) {
        HashService      hashService      = new HashService();
        ResultCalculator resultCalculator = new ResultCalculator();

        return sampleContainer.getSamples()
                              .stream()
                              .parallel()
                              .peek(this::setInputDataToSample)
                              .peek(hashService::generate)
                              .peek(resultCalculator)
                              .map(Sample::getResult)
                              .filter(result -> result == Result.SAFE)
                              .count()
               * 100.0 / sampleContainer.getSamples().size();
    }

    private void setInputDataToSample(Sample sample) {
        sample.setInputData(inputData);
    }


    @Override
    public SampleContainer call() {
        List<Sample> samples = new SampleService().createSampleList(officialData);

        SampleContainer sampleContainer = new SampleContainer();
        sampleContainer.setSamples(samples);

        double percentage = !sampleContainer.getSamples().isEmpty()
                            ? runCheckerModuleAndRetrieveReliabilityPercentage(sampleContainer)
                            : 0.0;

        sampleContainer.setReliabilityPercentage(percentage);
        return sampleContainer;
    }
}
