package hashtools.core.operation.data;

import hashtools.core.factory.thread.DaemonThreadPoolFactory;
import hashtools.core.model.Data;
import hashtools.core.model.Hash;
import hashtools.core.service.HashService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * This class generates the hash checksums for all {@link Hash} objects
 * within {@link Data} object.
 * </p>
 *
 * <p>
 * It uses a thread pool to increase performance and blocks until that
 * pool finishes its task. The thread pool uses daemon threads, which
 * can be interrupted when the application closes.
 * </p>
 */
public class HashGeneratorDataOperation implements DataOperation {

    /**
     * <p>
     * Generates the hash checksum. As the checksum is stored directly in the
     * {@link Hash} object, there is no need to return anything.
     * </p>
     *
     * @param data    Where the input data come from.
     * @param hash    Where to store the generated checksum.
     * @param service Handles the checksum generation.
     */
    private void generateHash(Data data, Hash hash, HashService service) {
        hash.setGenerated(
                data.isUsingInputFile()
                ? service.generate(hash.getAlgorithm(), data.getInputFile())
                : service.generate(hash.getAlgorithm(), data.getInputText())
        );
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void perform(Data data) {
        HashService service = new HashService();

        ExecutorService executor = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors(),
                new DaemonThreadPoolFactory(getClass().getSimpleName())
        );

        for (Hash hash : data.getHashes()) {
            executor.execute(() -> generateHash(data, hash, service));
        }

        try {
            /*
             * Waits for the thread pool to finish its task. We define a
             * large amount of time to ensure the task will be completed
             * before the timout reaches.
             *
             * After the task conclusion, all hashes within the data object
             * will be filled with the generated hash checksum.
             */
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
