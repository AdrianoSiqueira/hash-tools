package hashtools.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ComparatorResponse {
    private ChecksumPair checksumPair;
    private boolean      matches;
}
