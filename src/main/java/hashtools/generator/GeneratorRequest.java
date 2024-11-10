package hashtools.generator;

import hashtools.shared.Algorithm;
import hashtools.shared.identification.Identification;
import hashtools.shared.messagedigest.MessageDigestUpdater;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class GeneratorRequest {
    private MessageDigestUpdater input;
    private Identification identification;
    private List<Algorithm> algorithms;
}
