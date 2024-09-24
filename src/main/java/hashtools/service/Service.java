package hashtools.service;

import hashtools.domain.Algorithm;
import hashtools.domain.CheckerChecksum;
import hashtools.domain.CheckerRequest;
import hashtools.domain.CheckerResponse;
import hashtools.domain.ComparatorChecksum;
import hashtools.domain.ComparatorRequest;
import hashtools.domain.ComparatorResponse;
import hashtools.domain.GeneratorChecksum;
import hashtools.domain.GeneratorRequest;
import hashtools.domain.GeneratorResponse;
import hashtools.domain.ThreadPool;
import hashtools.formatter.Formatter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Service {

    public <RESPONSE_TYPE> String format(RESPONSE_TYPE response, Formatter<RESPONSE_TYPE> formatter) {
        return formatter.format(response);
    }

    public CheckerResponse run(CheckerRequest request) {
        List<CheckerChecksum> checksums = request
            .getOfficialChecksumGetter()
            .get();

        try (ExecutorService executor = ThreadPool.newFixedDaemon()) {
            ChecksumService checksumService = new ChecksumService();

            for (CheckerChecksum checksum : checksums) {
                executor.execute(() -> {
                    String hash = checksumService.generate(checksum.getAlgorithm(), request.getInput());
                    checksum.setGeneratedHash(hash);
                });
            }
        }

        CheckerResponse response = new CheckerResponse();
        response.setIdentification(request.getIdentification());
        response.setChecksums(checksums);
        return response;
    }

    public ComparatorResponse run(ComparatorRequest request) {
        ComparatorChecksum checksum = new ComparatorChecksum();
        checksum.setAlgorithm(Algorithm.MD5);

        try (ExecutorService executor = ThreadPool.newFixedDaemon()) {
            ChecksumService checksumService = new ChecksumService();

            executor.execute(() -> {
                String hash = checksumService.generate(checksum.getAlgorithm(), request.getInput1());
                checksum.setHash1(hash);
            });

            executor.execute(() -> {
                String hash = checksumService.generate(checksum.getAlgorithm(), request.getInput2());
                checksum.setHash2(hash);
            });
        }

        ComparatorResponse response = new ComparatorResponse();
        response.setIdentification1(request.getIdentification1());
        response.setIdentification2(request.getIdentification2());
        response.setChecksum(checksum);
        return response;
    }

    public GeneratorResponse run(GeneratorRequest request) {
        List<GeneratorChecksum> checksums = new ArrayList<>();

        try (ExecutorService executor = ThreadPool.newFixedDaemon()) {
            ChecksumService checksumService = new ChecksumService();

            for (Algorithm algorithm : request.getAlgorithms()) {
                executor.execute(() -> {
                    String hash = checksumService.generate(algorithm, request.getInput());

                    GeneratorChecksum checksum = new GeneratorChecksum();
                    checksum.setAlgorithm(algorithm);
                    checksum.setHash(hash);

                    checksums.add(checksum);
                });
            }
        }

        GeneratorResponse response = new GeneratorResponse();
        response.setIdentification(request.getIdentification());
        response.setChecksums(checksums);
        return response;
    }
}