package hashtools.coremodule.officialchecksum;

import java.io.IOException;
import java.util.List;

public interface OfficialChecksumExtractor {

    List<String> extract()
    throws IOException;
}
