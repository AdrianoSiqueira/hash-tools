package hashtools.domain;

import hashtools.domain.algorithm.Algorithm;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Checksum {
    private Algorithm algorithm;
    private String    checksum;
}
