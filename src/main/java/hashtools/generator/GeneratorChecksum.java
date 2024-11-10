package hashtools.generator;

import hashtools.shared.Algorithm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GeneratorChecksum {
    private Algorithm algorithm;
    private String hash;
}
