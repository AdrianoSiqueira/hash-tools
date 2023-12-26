package hashtools.domain.messagedigest;

import java.nio.file.Path;

public class UpdaterFactory {

    public static Updater create(Path file) {
        return new FileUpdater(file);
    }

    public static Updater create(String string) {
        return new StringUpdater(string);
    }
}
