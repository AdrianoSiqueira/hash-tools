package hashtools.comparator;

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

}
