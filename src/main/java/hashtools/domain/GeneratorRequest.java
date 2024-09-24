package hashtools.domain;

import hashtools.identification.Identification;
import hashtools.messagedigest.Updater;
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
