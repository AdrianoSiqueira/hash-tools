package hashtools.domain.identification;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class IdentifiableFactoryTest {

    void createFile() {
        assertInstanceOf(
            FileIdentifiable.class,
            IdentifiableFactory.create(Path.of(""))
        );
    }

    void createString() {
        assertInstanceOf(
            StringIdentifiable.class,
            IdentifiableFactory.create("")
        );
    }
}
