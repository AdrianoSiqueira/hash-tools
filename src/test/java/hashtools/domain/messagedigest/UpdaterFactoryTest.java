package hashtools.domain.messagedigest;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class UpdaterFactoryTest {

    @Test
    void createFile() {
        assertInstanceOf(
            FileUpdater.class,
            UpdaterFactory.create(Path.of(""))
        );
    }

    @Test
    void createString() {
        assertInstanceOf(
            StringUpdater.class,
            UpdaterFactory.create("")
        );
    }
}
