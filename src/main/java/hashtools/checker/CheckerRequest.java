package hashtools.checker;

import hashtools.shared.identification.Identification;
import hashtools.shared.messagedigest.Updater;
import hashtools.checker.officialchecksum.officialchecksum.OfficialChecksumGetter;
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
