package model;

import aslib.security.HashCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Deprecated
public enum SharedResources {
    INSTANCE;

    private final ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors(),
            r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
    );

    private final HashCalculator hashCalculator = new HashCalculator();

    private final List<Sample> sampleList = new ArrayList<>();

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public HashCalculator getHashCalculator() {
        return hashCalculator;
    }

    public List<Sample> getSampleList() {
        return sampleList;
    }
}
