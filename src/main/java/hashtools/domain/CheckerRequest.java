package hashtools.domain;

import hashtools.domain.identification.Identifiable;
import hashtools.domain.messagedigest.DigestUpdater;
import hashtools.domain.officialdata.OfficialDataGetter;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CheckerRequest {
    private DigestUpdater      digestUpdater;
    private OfficialDataGetter officialDataGetter;
    private Identifiable       identifiable;
}
