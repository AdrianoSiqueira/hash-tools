package hashtools.domain.officialdata;

import hashtools.domain.Checksum;

import java.util.List;

public interface OfficialDataGetter {

    List<Checksum> get();
}
