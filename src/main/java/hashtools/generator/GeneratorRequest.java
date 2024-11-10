package hashtools.generator;

import hashtools.shared.Algorithm;
import hashtools.shared.identification.Identification;
import hashtools.shared.messagedigest.Updater;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class GeneratorRequest {
    private Updater input;
    private Identification identification;
    private List<Algorithm> algorithms;
}
