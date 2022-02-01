package hashtools.core.model;

import aslib.security.SHAType;
import hashtools.core.service.FileService;
import hashtools.core.util.FilePathDetector;

import java.nio.file.Path;

/**
 * <p>Model class for individual sample objects.</p>
 *
 * @author Adriano Siqueira
 * @version 2.0.0
 * @since 1.0.0
 */
public class Sample {

    private String  input;
    private boolean usingFile;

    private Object  object;
    private boolean usingFileAsObject;

    private SHAType algorithm;
    private String  officialHash;
    private String  calculatedHash;
    private Result  result;


    public Object getInput() {
        return usingFile
               ? Path.of(input)
               : input;
    }

    public Sample setInput(String input) {
        if (input == null) return this;

        this.input = input;
        this.usingFile = FileService.stringIsFilePath(input);
        return this;
    }

    public boolean isUsingFile() {
        return usingFile;
    }

    @Deprecated(forRemoval = true)
    public Object getObject() {
        return object;
    }

    @Deprecated(forRemoval = true)
    public Sample setObject(Object object) {
        if (object instanceof Path path) {
            this.object = path;
            this.usingFileAsObject = true;
        } else if (new FilePathDetector().test(object)) {
            this.object = Path.of(object.toString());
            this.usingFileAsObject = true;
        } else {
            this.object = object.toString();
            this.usingFileAsObject = false;
        }

        return this;
    }

    @Deprecated(forRemoval = true)
    public boolean isUsingFileAsObject() {
        return usingFileAsObject;
    }

    public SHAType getAlgorithm() {
        return algorithm;
    }

    public Sample setAlgorithm(SHAType algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public String getOfficialHash() {
        return officialHash;
    }

    public Sample setOfficialHash(String officialHash) {
        this.officialHash = officialHash;
        return this;
    }

    public String getCalculatedHash() {
        return calculatedHash;
    }

    public Sample setCalculatedHash(String calculatedHash) {
        this.calculatedHash = calculatedHash;
        return this;
    }

    public Result getResult() {
        return result;
    }

    public Sample setResult(Result result) {
        this.result = result;
        return this;
    }


    @Override
    public String toString() {
        return "Sample{" +
               "input=" + input +
               ", usingFile=" + usingFile +
               ", algorithm=" + algorithm +
               ", officialHash='" + officialHash + '\'' +
               ", calculatedHash='" + calculatedHash + '\'' +
               ", result=" + result +
               '}';
    }
}
