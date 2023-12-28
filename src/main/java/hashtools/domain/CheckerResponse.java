package hashtools.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CheckerResponse {
    private List<ChecksumPair> checksumPairs;
    private double             integrityPercentage;
    private String             identification;
}
