package hashtools.comparator;

import hashtools.shared.identification.Identification;
import hashtools.shared.messagedigest.MessageDigestUpdater;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ComparatorRequest {
    private MessageDigestUpdater input1;
    private MessageDigestUpdater input2;
    private Identification identification1;
    private Identification identification2;
}
