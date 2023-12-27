package hashtools.domain.identification;

import java.nio.file.Path;

public class IdentifiableFactory {

    public static Identifiable create(Path file) {
        return new FileIdentifiable(file);
    }

    public static Identifiable create(String string) {
        return new StringIdentifiable(string);
    }
}
