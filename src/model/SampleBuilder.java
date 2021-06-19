package model;

import aslib.security.SHAType;

public class SampleBuilder {

    private Object  object;
    private SHAType shaType;
    private String  officialHash;
    private String  calculatedHash;

    public SampleBuilder object(Object object) {
        this.object = object;
        return this;
    }

    public SampleBuilder shaType(SHAType shaType) {
        this.shaType = shaType;
        return this;
    }

    public SampleBuilder officialHash(String officialHash) {
        this.officialHash = officialHash;
        return this;
    }

    public SampleBuilder calculatedHash(String calculatedHash) {
        this.calculatedHash = calculatedHash;
        return this;
    }

    public Sample create() {
        return new Sample(object, shaType, officialHash, calculatedHash);
    }
}
