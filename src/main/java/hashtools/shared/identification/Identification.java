package hashtools.shared.identification;

import java.nio.file.Path;

public interface Identification {

    static Identification of(String string) {
        return new StringIdentification(string);
    }

    static Identification of(Path file) {
        return new FileIdentification(file);
    }

    String identity();
}
