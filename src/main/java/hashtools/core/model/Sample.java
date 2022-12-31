package hashtools.core.model;

import java.nio.file.Path;

public class Sample {

    private Object input;

    private String algorithm;
    private String officialHash;
    private String calculatedHash;

    public String getAlgorithm() {
        return algorithm;
    }

    public String getCalculatedHash() {
        return calculatedHash;
    }

    public Path getInputFile() {
        if (!(input instanceof Path))
            throw new ClassCastException("Cannot cast input object to path");

        return ((Path) input);
    }

    public String getInputText() {
        if (!(input instanceof String))
            throw new ClassCastException("Cannot cast input object to string");

        return ((String) input);
    }

    public String getOfficialHash() {
        return officialHash;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setCalculatedHash(String calculatedHash) {
        this.calculatedHash = calculatedHash;
    }

    public void setInputFile(Path file) {
        this.input = file;
    }

    public void setInputText(String text) {
        this.input = text;
    }

    public void setOfficialHash(String officialHash) {
        this.officialHash = officialHash;
    }
}
