package hashtools.checker;

import hashtools.checker.officialchecksum.FileOfficialChecksumExtractor;
import hashtools.checker.officialchecksum.OfficialChecksumExtractor;
import hashtools.shared.identification.FileIdentification;
import hashtools.shared.identification.Identification;
import hashtools.shared.messagedigest.FileMessageDigestUpdater;
import hashtools.shared.messagedigest.MessageDigestUpdater;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.file.Path;

@NoArgsConstructor
@Getter
@Setter
public class CheckerRequest {

    private Path inputFile;
    private Path checksumFile;

    public final Identification createNewIdentification() {
        return new FileIdentification(inputFile);
    }

    public final MessageDigestUpdater createNewMessageDigestUpdater() {
        return new FileMessageDigestUpdater(inputFile);
    }

    public final OfficialChecksumExtractor createNewOfficialChecksumExtractor() {
        return new FileOfficialChecksumExtractor(checksumFile);
    }
}
