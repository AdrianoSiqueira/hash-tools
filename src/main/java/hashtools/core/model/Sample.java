package hashtools.core.model;

import lombok.Data;

@Data
public class Sample {

    private String inputData;

    private HashAlgorithm algorithm;
    private String        officialHash;
    private String        calculatedHash;
    private Result        result;
}
