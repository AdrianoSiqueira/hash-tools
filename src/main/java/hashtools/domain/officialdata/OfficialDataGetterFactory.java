package hashtools.domain.officialdata;

import java.nio.file.Path;

public class OfficialDataGetterFactory {

    public static OfficialDataGetter create(Path file) {
        return new FileOfficialDataGetter(file);
    }

    public static OfficialDataGetter create(String string) {
        return new StringOfficialDataGetter(string);
    }
}
