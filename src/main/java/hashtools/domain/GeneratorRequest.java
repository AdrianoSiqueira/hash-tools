package hashtools.domain;

import hashtools.domain.identification.Identifiable;
import hashtools.domain.messagedigest.DigestUpdater;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GeneratorRequest {
    private List<Algorithm> algorithms;
    private DigestUpdater   digestUpdater;
    private Identifiable    identifiable;
}
