package hashtools.domain;

import hashtools.domain.messagedigest.DigestUpdater;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ComparatorRequest {
    private DigestUpdater digestUpdater1;
    private DigestUpdater digestUpdater2;
}
