package hashtools.core.model;

import hashtools.core.consumer.data.DataConsumer;
import hashtools.core.consumer.data.FileDataConsumer;
import hashtools.core.factory.hash.CheckerHashFactory;
import hashtools.core.factory.hash.GeneratorHashFactory;
import hashtools.core.factory.hash.HashFactory;
import hashtools.core.formatter.data.CheckerDataFormatter;
import hashtools.core.formatter.data.DataFormatter;
import hashtools.core.formatter.data.GeneratorDataFormatter;
import hashtools.core.service.FileService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Data {

    private String  inputText;
    private Path    inputFile;
    private boolean usingInputFile;

    private double safetyPercentage;

    private DataFormatter formatter;
    private HashFactory   factory;

    private List<String>       algorithmNames;
    private List<String>       officialHashes;
    private List<Hash>         hashes;
    private List<DataConsumer> consumers;

    public Data() {
        algorithmNames = new ArrayList<>();
        officialHashes = new ArrayList<>();
        consumers      = new ArrayList<>();
        hashes         = new ArrayList<>();
    }

    public List<DataConsumer> getConsumers() {
        return consumers;
    }

    public HashFactory getFactory() {
        return factory;
    }

    public DataFormatter getFormatter() {
        return formatter;
    }

    public List<Hash> getHashes() {
        return hashes;
    }

    public Path getInputFile() {
        return inputFile;
    }

    public String getInputText() {
        return inputText;
    }

    public double getSafetyPercentage() {
        return safetyPercentage;
    }

    public boolean isUsingInputFile() {
        return usingInputFile;
    }

    public void setAlgorithms(String... algorithms) {
        this.algorithmNames.addAll(List.of(algorithms));
    }

    public void setChecking() {
        formatter = new CheckerDataFormatter();
        factory   = new CheckerHashFactory(officialHashes);
    }

    public void setConsumers(DataConsumer... consumers) {
        this.consumers.addAll(List.of(consumers));
    }

    public void setGenerating() {
        formatter = new GeneratorDataFormatter();
        factory   = new GeneratorHashFactory(algorithmNames);
    }

    public void setHashes(List<Hash> hashes) {
        this.hashes.addAll(hashes);
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
        FileService service = new FileService();
        officialHashes.addAll(service.readOfficialFile(officialFile));
    }

    public void setOfficialHash(String officialHash) {
        officialHashes.add(officialHash);
    }

    public void setOutputFile(Path outputFile) {
        setConsumers(new FileDataConsumer(outputFile));
    }

    public void setSafetyPercentage(double safetyPercentage) {
        this.safetyPercentage = safetyPercentage;
    }
}
