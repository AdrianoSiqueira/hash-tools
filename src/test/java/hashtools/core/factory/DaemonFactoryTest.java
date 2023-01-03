package hashtools.core.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DaemonFactoryTest {

    private DaemonFactory factory = new DaemonFactory();

    @Test
    @DisplayName(value = "Creates a daemon thread")
    void newThread() {
        assertTrue(factory.newThread(null).isDaemon());
    }
}
