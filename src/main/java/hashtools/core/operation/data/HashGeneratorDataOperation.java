package hashtools.core.operation.data;

import hashtools.core.model.Data;
import hashtools.core.model.Hash;
import hashtools.core.service.HashService;
import hashtools.core.service.ParallelismService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class HashGeneratorDataOperation implements DataOperation {

    private final HashService service;

    public HashGeneratorDataOperation() {
        service = new HashService();
    }

    private void generateHash(Data data, Hash hash) {
        hash.setGenerated(
                data.isUsingInputFile()
                ? service.generate(hash.getAlgorithm(), data.getInputFile())
                : service.generate(hash.getAlgorithm(), data.getInputText())
        );
    }

    @Override
    public void perform(Data data) {
        List<Hash> hashes = data.getHashes();

        ExecutorService            executor = ParallelismService.INSTANCE.getFixedThreadPool();
        List<CompletableFuture<?>> futures  = new ArrayList<>();

        hashes.forEach(hash -> futures.add(CompletableFuture.runAsync(
                () -> generateHash(data, hash),
                executor
        )));

        try {
            CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
                             .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
