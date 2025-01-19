package hashtools.applicationmodule.generator;

import hashtools.coremodule.checksumgenerator.Algorithm;
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
}
