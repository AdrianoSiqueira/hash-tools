package hashtools.core.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.function.Consumer;

@Deprecated
@Getter
@Setter
@ToString
public class Environment {

    private String                    inputData;
    private List<HashAlgorithm>       algorithms;
    private RunMode                   runMode;
    private Consumer<SampleContainer> consumer;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String otherData;


    public String getOfficialData() {
        return otherData;
    }

    public void setOfficialData(String officialData) {
        this.otherData = officialData;
    }

    public String getOutputData() {
        return otherData;
    }

    public void setOutputData(String outputData) {
        this.otherData = outputData;
    }
}
