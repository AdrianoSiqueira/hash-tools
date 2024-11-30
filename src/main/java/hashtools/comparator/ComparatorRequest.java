package hashtools.comparator;

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
public class ComparatorRequest {

    private Path inputFile1;
    private Path inputFile2;

    public final Identification createNewIdentification1() {
        return new FileIdentification(inputFile1);
    }

    public final Identification createNewIdentification2() {
        return new FileIdentification(inputFile2);
    }

    public final MessageDigestUpdater createNewMessageDigestUpdater1() {
        return new FileMessageDigestUpdater(inputFile1);
    }

    public final MessageDigestUpdater createNewMessageDigestUpdater2() {
        return new FileMessageDigestUpdater(inputFile2);
    }
}
