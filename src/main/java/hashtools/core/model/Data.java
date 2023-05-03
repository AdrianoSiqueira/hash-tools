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
import java.util.Objects;

/**
 * <p>
 * Stores all the data needed to execute the application.
 * </p>
 */
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

    /**
     * <p>
     * Creates an instance of {@link Data} initializing all the lists.
     * </p>
     */
    public Data() {
        algorithmNames = new ArrayList<>();
        officialHashes = new ArrayList<>();
        consumers      = new ArrayList<>();
        hashes         = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        Data data = (Data) o;

        return usingInputFile == data.usingInputFile &&
               data.safetyPercentage == safetyPercentage &&
               Objects.equals(inputText, data.inputText) &&
               Objects.equals(inputFile, data.inputFile) &&
               Objects.equals(algorithmNames, data.algorithmNames) &&
               Objects.equals(officialHashes, data.officialHashes) &&
               Objects.equals(hashes, data.hashes);
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

    @Override
    public int hashCode() {
        return Objects.hash(
                inputText,
                inputFile,
                usingInputFile,
                safetyPercentage,
                algorithmNames,
                officialHashes,
                hashes
        );
    }

    public boolean isUsingInputFile() {
        return usingInputFile;
    }

    /**
     * <p>
     * Adds the algorithm names to the algorithm name list.
     * </p>
     *
     * @param algorithms Algorithms to be added.
     */
    public void setAlgorithms(String... algorithms) {
        this.algorithmNames.addAll(List.of(algorithms));
    }

    /**
     * <p>
     * Sets the {@link CheckerDataFormatter} as the data formatter,
     * and the {@link CheckerHashFactory} as the hash factory.
     * </p>
     */
    public void setChecking() {
        formatter = new CheckerDataFormatter();
        factory   = new CheckerHashFactory(officialHashes);
    }

    /**
     * <p>
     * Adds the consumers to the consumer list.
     * </p>
     *
     * @param consumers Consumers to be added.
     */
    public void setConsumers(DataConsumer... consumers) {
        this.consumers.addAll(List.of(consumers));
    }

    /**
     * <p>
     * Sets the {@link GeneratorDataFormatter} as the data formatter,
     * and the {@link GeneratorHashFactory} as the hash factory.
     * </p>
     */
    public void setGenerating() {
        formatter = new GeneratorDataFormatter();
        factory   = new GeneratorHashFactory(algorithmNames);
    }

    /**
     * <p>
     * Adds the hashes to the hash list.
     * </p>
     *
     * @param hashes Hashes to be added.
     */
    public void setHashes(List<Hash> hashes) {
        this.hashes.addAll(hashes);
    }

    /**
     * <p>
     * Sets the input file and sets the flag to indicate that it is
     * using an input file.
     * </p>
     *
     * @param inputFile File to be checked or generated.
     */
    public void setInputFile(Path inputFile) {
        this.inputFile      = inputFile;
        this.usingInputFile = true;
    }

    /**
     * <p>
     * Sets the input text and sets the flag to indicate that it is
     * using an input text.
     * </p>
     *
     * @param inputText Text to be checked or generated.
     */
    public void setInputText(String inputText) {
        this.inputText      = inputText;
        this.usingInputFile = false;
    }

    /**
     * <p>
     * Reads the given file and stores the official hashes within it.
     * </p>
     *
     * @param officialFile From where to get the official hashes.
     */
    public void setOfficialFile(Path officialFile) {
        FileService service = new FileService();
        officialHashes.addAll(service.readOfficialFile(officialFile));
    }

    /**
     * <p>
     * Adds the official hash to the official hash list.
     * </p>
     *
     * @param officialHash Hash to be added.
     */
    public void setOfficialHash(String officialHash) {
        officialHashes.add(officialHash);
    }

    /**
     * <p>
     * Configures a file consumer to use the given file as the destination
     * of the results.
     * </p>
     *
     * @param outputFile Where to save the results.
     */
    public void setOutputFile(Path outputFile) {
        setConsumers(new FileDataConsumer(outputFile));
    }

    public void setSafetyPercentage(double safetyPercentage) {
        this.safetyPercentage = safetyPercentage;
    }

    @Override
    public String toString() {
        return "Data{" +
               "inputText='" + inputText + '\'' +
               ", inputFile=" + inputFile +
               ", usingInputFile=" + usingInputFile +
               ", safetyPercentage=" + safetyPercentage +
               ", formatter=" + formatter +
               ", factory=" + factory +
               ", algorithmNames=" + algorithmNames +
               ", officialHashes=" + officialHashes +
               ", hashes=" + hashes +
               ", consumers=" + consumers +
               '}';
    }
}
