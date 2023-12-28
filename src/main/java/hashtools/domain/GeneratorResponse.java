package hashtools.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GeneratorResponse {
    private List<Checksum> checksums;
    private String         identification;
}
