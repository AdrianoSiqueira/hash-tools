package hashtools.domain.officialdata;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class OfficialDataGetterFactoryTest {

    @Test
    void createFile() {
        assertInstanceOf(
            FileOfficialDataGetter.class,
            OfficialDataGetterFactory.create(Path.of(""))
        );
    }

    @Test
    void createString() {
        assertInstanceOf(
            StringOfficialDataGetter.class,
            OfficialDataGetterFactory.create("")
        );
    }
}
