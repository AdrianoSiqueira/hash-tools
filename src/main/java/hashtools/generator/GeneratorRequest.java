package hashtools.generator;

import hashtools.shared.Algorithm;
import hashtools.shared.identification.FileIdentification;
import hashtools.shared.identification.Identification;
import hashtools.shared.messagedigest.FileMessageDigestUpdater;
import hashtools.shared.messagedigest.MessageDigestUpdater;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.file.Path;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class GeneratorRequest {

    private Path inputFile;
    private List<Algorithm> algorithms;

    public final Identification createNewIdentification() {
        return new FileIdentification(inputFile);
    }

    public final MessageDigestUpdater createNewMessageDigestUpdater() {
        return new FileMessageDigestUpdater(inputFile);
    }
}
