package hashtools.checker;

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
}
