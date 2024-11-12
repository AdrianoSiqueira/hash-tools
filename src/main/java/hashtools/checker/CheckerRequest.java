package hashtools.checker;

import hashtools.shared.identification.Identification;
import hashtools.shared.messagedigest.MessageDigestUpdater;
import hashtools.checker.officialchecksum.OfficialChecksumExtractor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CheckerRequest {
    private MessageDigestUpdater input;
    private Identification identification;
    private OfficialChecksumExtractor officialChecksumExtractor;
}
