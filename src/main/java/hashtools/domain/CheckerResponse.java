package hashtools.domain;

import hashtools.identification.Identification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CheckerResponse {
    private Identification identification;
    private List<CheckerChecksum> checksums;

    /**
     * @return 0.0 - 100.0
     */
    public final double calculateReliabilityPercentage() {
        if (checksums == null) {
            throw new IllegalStateException("Checksums are not stores yet");
        } else if (checksums.isEmpty()) {
            return 0.0;
        }

        return checksums
            .stream()
            .filter(CheckerChecksum::matches)
            .count()
            * 100.0
            / checksums.size();
    }
}
