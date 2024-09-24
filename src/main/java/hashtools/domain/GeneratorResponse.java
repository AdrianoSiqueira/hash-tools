package hashtools.domain;

import hashtools.identification.Identification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class GeneratorResponse {
    private Identification identification;
    private List<GeneratorChecksum> checksums;
}
