package hashtools.comparator;

import hashtools.coremodule.identification.Identification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ComparatorResponse {
    private Identification identification1;
    private Identification identification2;
    private ComparatorChecksum checksum;
}
