package hashtools.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Checksum {
    private Algorithm algorithm;
    private String    checksum;
}
