package core;

import aslib.security.HashCalculator;
import language.LanguageManager;
import model.Sample;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListHashGenerator {

    public static void generate(List<Sample> samples) {
        Objects.requireNonNull(samples,
                               LanguageManager.get("Object.cannot.be.null."));

        final HashCalculator calculator = new HashCalculator();

        List<Thread> threads = createThreadList(calculator, samples);
        threads.forEach(Thread::start);

        waitThreadsToFinish(threads);
    }

    private static List<Thread> createThreadList(HashCalculator calculator,
                                                 List<Sample> samples) {
        return samples.stream()
                      .map(sample -> new Thread(new SampleHashGenerator(calculator, sample)))
                      .collect(Collectors.toList());
    }

    private static void waitThreadsToFinish(List<Thread> threads) {
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
