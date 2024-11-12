package hashtools.comparator;

import hashtools.service.ChecksumService;
import hashtools.shared.Algorithm;
import hashtools.shared.Formatter;
import hashtools.shared.RequestProcessor;
import hashtools.shared.ResponseFormatter;
import hashtools.shared.threadpool.ThreadPool;

import java.util.concurrent.ExecutorService;

public class ComparatorService implements RequestProcessor<ComparatorRequest, ComparatorResponse>, ResponseFormatter<ComparatorResponse> {

    @Override
    public String formatResponse(ComparatorResponse response, Formatter<ComparatorResponse> formatter) {
        return formatter.format(response);
    }

    @Override
    public ComparatorResponse processRequest(ComparatorRequest request) {
        ComparatorChecksum checksum = new ComparatorChecksum();
        checksum.setAlgorithm(Algorithm.MD5);

        try (ExecutorService executor = ThreadPool.newFixedDaemon("ComparatorThreadPool")) {
            ChecksumService checksumService = new ChecksumService();

            executor.execute(() -> {
                String hash = checksumService.generate(
                    checksum.getAlgorithm(),
                    request.getInput1()
                );

                checksum.setHash1(hash);
            });

            executor.execute(() -> {
                String hash = checksumService.generate(
                    checksum.getAlgorithm(),
                    request.getInput2()
                );

                checksum.setHash2(hash);
            });
        }

        ComparatorResponse response = new ComparatorResponse();
        response.setIdentification1(request.getIdentification1());
        response.setIdentification2(request.getIdentification2());
        response.setChecksum(checksum);
        return response;
    }
}
