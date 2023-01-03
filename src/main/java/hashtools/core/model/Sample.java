package hashtools.core.model;

import java.nio.file.Path;

public class Sample {

    private Object  input;
    private boolean usingInputText;

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
        if (usingInputText)
            throw new ClassCastException("Cannot cast input object to path");

        return ((Path) input);
    }

    public String getInputText() {
        if (!usingInputText)
            throw new ClassCastException("Cannot cast input object to string");

        return ((String) input);
    }

    public String getOfficialHash() {
        return officialHash;
    }

    public boolean hashesMatches() {
        if (officialHash == null) return false;
        if (calculatedHash == null) return false;

        return officialHash.equalsIgnoreCase(calculatedHash);
    }

    public boolean isUsingInputText() {
        return usingInputText;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setCalculatedHash(String calculatedHash) {
        this.calculatedHash = calculatedHash;
    }

    public void setInputFile(Path file) {
        this.input          = file;
        this.usingInputText = false;
    }

    public void setInputText(String text) {
        this.input          = text;
        this.usingInputText = true;
    }

    public void setOfficialHash(String officialHash) {
        this.officialHash = officialHash;
    }
}
