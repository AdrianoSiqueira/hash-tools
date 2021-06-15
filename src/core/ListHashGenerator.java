package core;

import aslib.security.HashCalculator;
import language.LanguageManager;
import model.Sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListHashGenerator {

    public static void generate(List<Sample> samples) {
        Objects.requireNonNull(samples,
                               LanguageManager.get("Object.cannot.be.null."));

        HashCalculator calculator = new HashCalculator();
        List<Thread>   threadList = new ArrayList<>();

        samples.forEach(sample -> {
            Thread thread = new Thread(new SampleHashGenerator(calculator, sample));
            thread.start();

            threadList.add(thread);
        });

        while (!threadList.isEmpty()) {
            try {
                Thread thread = threadList.remove(0);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
