package hashtools.domain.messagedigest;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class DigestUpdaterFactoryTest {

    @Test
    void createFile() {
        assertInstanceOf(
            FileDigestUpdater.class,
            DigestUpdaterFactory.create(Path.of(""))
        );
    }

    @Test
    void createString() {
        assertInstanceOf(
            StringDigestUpdater.class,
            DigestUpdaterFactory.create("")
        );
    }
}
