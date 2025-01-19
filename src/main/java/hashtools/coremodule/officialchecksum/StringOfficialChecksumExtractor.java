package hashtools.coremodule.officialchecksum;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StringOfficialChecksumExtractor implements OfficialChecksumExtractor {

    private final String string;

    @Override
    public List<String> extract() {
        String checksum = string.split(" ")[0];
        return List.of(checksum);
    }
}
