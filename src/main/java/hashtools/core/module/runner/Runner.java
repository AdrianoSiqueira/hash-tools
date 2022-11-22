package hashtools.core.module.runner;

import hashtools.core.model.Environment;
import hashtools.core.model.Result;
import hashtools.core.model.RunMode;
import hashtools.core.model.Sample;
import hashtools.core.model.SampleContainer;
import hashtools.core.service.HashService;
import hashtools.core.service.ParallelismService;
import hashtools.core.service.ResultService;
import hashtools.core.service.SampleService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public class Runner implements Runnable {

    private final Environment environment;


    private Sample analyse(ResultService resultService, Sample sample) {
        Result result = resultService.calculateResult(sample);
        sample.setResult(result);
        return sample;
    }

    private void calculateReliabilityPercentage(ResultService resultService, SampleContainer sampleContainer) {
        double percentage = resultService.calculateReliabilityPercentage(sampleContainer.getSamples());
        sampleContainer.setReliabilityPercentage(percentage);
    }

    private void consume(SampleContainer sampleContainer) {
        environment.getConsumer()
                   .accept(sampleContainer);
    }

    private Sample generate(HashService hashService, Sample sample) {
        hashService.generate(sample);
        return sample;
    }

    private Sample setInputData(String inputData, Sample sample) {
        sample.setInputData(inputData);
        return sample;
    }


    @Override
    public void run() {
        ExecutorService executor      = ParallelismService.FIXED_THREAD_POOL.getExecutor();
        SampleService   sampleService = new SampleService();
        HashService     hashService   = new HashService();
        ResultService   resultService = new ResultService();

        List<Sample> samples = null;

        switch (environment.getRunMode()) {
            case CHECKER:
                samples = sampleService.createSampleList(environment.getOfficialData());
                break;
            case GENERATOR:
                samples = sampleService.createSampleList(environment.getAlgorithms());
                break;
        }

        List<CompletableFuture<Sample>> futures = new ArrayList<>();

        samples.forEach(sample -> {
            CompletableFuture<Sample> future = CompletableFuture.supplyAsync(() -> setInputData(environment.getInputData(), sample), executor)
                                                                .thenApplyAsync(s -> generate(hashService, s), executor);

            if (environment.getRunMode() == RunMode.CHECKER) {
                future = future.thenApplyAsync(s -> analyse(resultService, s), executor);
            }

            futures.add(future);
        });

        SampleContainer sampleContainer = new SampleContainer();
        sampleContainer.setSamples(samples);

        try {
            CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        calculateReliabilityPercentage(resultService, sampleContainer);
        consume(sampleContainer);
    }
}
