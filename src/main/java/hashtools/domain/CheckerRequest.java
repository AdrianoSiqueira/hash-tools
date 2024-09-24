package hashtools.domain;

import hashtools.identification.Identification;
import hashtools.messagedigest.Updater;
import hashtools.officialchecksum.OfficialChecksumGetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CheckerRequest {
    private Updater input;
    private Identification identification;
    private OfficialChecksumGetter officialChecksumGetter;
}
