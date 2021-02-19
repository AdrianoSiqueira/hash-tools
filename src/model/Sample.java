package model;

import aslib.security.SHAType;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Sample {

    private Object  object;
    private boolean usingFile;

    private SHAType          shaType;
    private String           officialHash;
    private String           calculatedHash;
    private ReliabilityLevel reliabilityLevel;

    public Sample(Object object, SHAType shaType) {
        this(object, shaType, null);
    }

    public Sample(Object object, SHAType shaType, String officialHash) {
        setObject(object);
        setShaType(shaType);
        setOfficialHash(officialHash);
        setCalculatedHash("");
    }

    public static double calculateReliabilityPercentage(List<Sample> samples) {
        Objects.requireNonNull(samples);

        double score = samples.stream()
                              .map(Sample::getReliabilityLevel)
                              .mapToDouble(ReliabilityLevel::getScore)
                              .reduce(Double::sum)
                              .orElse(0.0);

        return samples.size() != 0
               ? score * 100 / samples.size()
               : 0.0;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        Objects.requireNonNull(object);

        if (object instanceof Path) {
            this.object    = object;
            this.usingFile = true;
        } else if (object instanceof File) {
            this.object    = ((File) object).toPath();
            this.usingFile = true;
        } else {
            this.object    = object.toString();
            this.usingFile = false;
        }
    }

    public boolean isUsingFile() {
        return usingFile;
    }

    public void setUsingFile(boolean usingFile) {
        this.usingFile = usingFile;
    }

    public SHAType getShaType() {
        return shaType;
    }

    public void setShaType(SHAType shaType) {
        this.shaType = Optional.ofNullable(shaType)
                               .orElse(SHAType.MD5);
    }

    public String getOfficialHash() {
        return officialHash;
    }

    public void setOfficialHash(String officialHash) {
        this.officialHash = Optional.ofNullable(officialHash)
                                    .orElse("");
    }

    public String getCalculatedHash() {
        return calculatedHash;
    }

    public void setCalculatedHash(String calculatedHash) {
        this.calculatedHash = Optional.ofNullable(calculatedHash)
                                      .orElse("");
    }

    public ReliabilityLevel getReliabilityLevel() {
        return reliabilityLevel;
    }

    public void setReliabilityLevel(ReliabilityLevel reliabilityLevel) {
        this.reliabilityLevel = reliabilityLevel;
    }

    public void calculateReliability() {
        if (officialHash.equals(calculatedHash)) {
            this.reliabilityLevel = ReliabilityLevel.SAFE;
        } else if (officialHash.equalsIgnoreCase(calculatedHash)) {
            this.reliabilityLevel = ReliabilityLevel.UNSURE;
        } else {
            this.reliabilityLevel = ReliabilityLevel.DANGEROUS;
        }
    }

    @Override
    public String toString() {
        return "Sample{" +
               "object='" + object + '\'' +
               ", usingFile=" + usingFile +
               ", sha=" + shaType +
               ", official='" + officialHash + '\'' +
               ", calculated='" + calculatedHash + '\'' +
               ", reliability=" + reliabilityLevel +
               '}';
    }
}