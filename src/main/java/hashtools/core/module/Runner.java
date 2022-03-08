package hashtools.core.module;

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


    @Override
    public void run() {
        ExecutorService executor      = ParallelismService.INSTANCE.getExecutor();
        SampleService   sampleService = new SampleService();
        HashService     hashService   = new HashService();
        ResultService   resultService = new ResultService();

        List<Sample> samples = switch (environment.getRunMode()) {
            case CHECKER -> sampleService.createSampleList(environment.getOfficialData());
            case GENERATOR -> sampleService.createSampleList(environment.getAlgorithms());
        };

        List<CompletableFuture<Sample>> futures = new ArrayList<>();

        samples.forEach(sample -> {
            CompletableFuture<Sample> future = CompletableFuture.completedFuture(sample)
                                                                .thenApplyAsync(s -> generate(hashService, s), executor);

            if (environment.getRunMode() == RunMode.CHECKER) {
                future.thenApplyAsync(s -> analyse(resultService, s), executor);
            }

            futures.add(future);
        });

        SampleContainer sampleContainer = new SampleContainer();
        sampleContainer.setSamples(samples);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
        allOf.whenComplete((unused, throwable) -> {
            calculateReliabilityPercentage(resultService, samples, sampleContainer);
            consume(sampleContainer);
        });
    }
}
