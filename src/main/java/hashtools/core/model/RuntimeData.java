package hashtools.core.model;

import hashtools.core.consumer.CommandLineConsumer;
import hashtools.core.consumer.FileConsumer;
import hashtools.core.consumer.RuntimeDataConsumer;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RuntimeData {

    private boolean checking;
    private boolean usingInputFile;
    private boolean usingOfficialFile;
    private boolean usingOutputFile;

    private String inputText;
    private String officialHash;

    private Path inputFile;
    private Path officialFile;
    private Path outputFile;

    private Map<String, Hash>   hashes;
    private RuntimeDataConsumer consumer;

    private RuntimeData(
            boolean checking,
            boolean usingInputFile,
            boolean usingOfficialFile,
            boolean usingOutputFile,
            String inputText,
            String officialHash,
            Path inputFile,
            Path officialFile,
            Path outputFile,
            Map<String, Hash> hashes,
            RuntimeDataConsumer consumer
    ) {
        this.checking          = checking;
        this.usingInputFile    = usingInputFile;
        this.usingOfficialFile = usingOfficialFile;
        this.usingOutputFile   = usingOutputFile;
        this.inputText         = inputText;
        this.officialHash      = officialHash;
        this.inputFile         = inputFile;
        this.officialFile      = officialFile;
        this.outputFile        = outputFile;
        this.hashes            = hashes;
        this.consumer          = consumer;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public void consume() {
        consumer.consume(this);
    }

    public Map<String, Hash> getHashes() {
        return hashes;
    }

    public Path getInputFile() {
        return inputFile;
    }

    public String getInputText() {
        return inputText;
    }

    public Path getOfficialFile() {
        return officialFile;
    }

    public String getOfficialHash() {
        return officialHash;
    }

    public Path getOutputFile() {
        return outputFile;
    }

    public double getSafetyPercentage() {
        List<Hash> safeHashes = hashes.values()
                                      .stream()
                                      .filter(Hash::matches)
                                      .collect(Collectors.toList());

        return hashes.size() != 0
               ? safeHashes.size() * 100.0 / hashes.size()
               : 0.0;
    }

    public boolean isUsingInputFile() {
        return usingInputFile;
    }

    public boolean isUsingOfficialFile() {
        return usingOfficialFile;
    }

    public boolean isUsingOutputFile() {
        return usingOutputFile;
    }

    public void setHashes(Map<String, Hash> hashes) {
        this.hashes = hashes;
    }

    public void setInputFile(Path inputFile) {
        this.inputFile      = inputFile;
        this.usingInputFile = true;
    }

    public void setInputText(String inputText) {
        this.inputText      = inputText;
        this.usingInputFile = false;
    }

    public void setOfficialFile(Path officialFile) {
        this.officialFile      = officialFile;
        this.usingOfficialFile = true;
    }

    public void setOfficialHash(String officialHash) {
        this.officialHash      = officialHash;
        this.usingOfficialFile = false;
    }

    public void setOutputFile(Path outputFile) {
        this.outputFile      = outputFile;
        this.usingOutputFile = true;
    }


    public static class Builder {

        private boolean checking;
        private boolean usingInputFile;
        private boolean usingOfficialFile;
        private boolean usingOutputFile;

        private String inputText;
        private String officialHash;

        private Path inputFile;
        private Path officialFile;
        private Path outputFile;

        private Map<String, Hash>   hashes;
        private RuntimeDataConsumer consumer;

        private Builder() {
            this.hashes   = new HashMap<>(6);
            this.consumer = new CommandLineConsumer();
        }

        public Builder algorithms(String... algorithms) {
            Stream.of(algorithms)
                  .forEach(algorithm -> hashes.put(algorithm, new Hash()));
            return this;
        }

        public Builder checking() {
            this.checking = true;
            return this;
        }

        public RuntimeData createRuntimeData() {
            return new RuntimeData(
                    checking,
                    usingInputFile,
                    usingOfficialFile,
                    usingOutputFile,
                    inputText,
                    officialHash,
                    inputFile,
                    officialFile,
                    outputFile,
                    hashes,
                    consumer);
        }

        public Builder generating() {
            this.checking = false;
            return this;
        }

        public Builder inputFile(Path inputFile) {
            this.inputFile      = inputFile;
            this.usingInputFile = true;
            return this;
        }

        public Builder inputText(String inputText) {
            this.inputText      = inputText;
            this.usingInputFile = false;
            return this;
        }

        public Builder officialFile(Path officialFile) {
            this.officialFile      = officialFile;
            this.usingOfficialFile = true;
            return this;
        }

        public Builder officialHash(String officialHash) {
            this.officialHash      = officialHash;
            this.usingOfficialFile = false;
            return this;
        }

        public Builder outputFile(Path outputFile) {
            this.outputFile      = outputFile;
            this.usingOutputFile = true;
            this.consumer        = new FileConsumer();
            return this;
        }
    }
}
