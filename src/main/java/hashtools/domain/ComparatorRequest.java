package hashtools.domain;

import hashtools.identification.Identification;
import hashtools.messagedigest.Updater;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ComparatorRequest {
    private Updater input1;
    private Updater input2;
    private Identification identification1;
    private Identification identification2;
}
