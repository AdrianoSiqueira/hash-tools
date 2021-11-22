package hashtools.core.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * <p>Regular file checker. It checks if an object is a regular file.</p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @see #test
 * @since 2.0.0
 */
public class FilePathDetector implements Predicate<Object> {

    /**
     * <p>Checks if the object informed is an existing regular file, or a path
     * to an existing regular file.</p>
     *
     * <p>TRUE is returned if and only if the object is an existing regular
     * file, or a path to an existing regular file.</p>
     *
     * @param object Object that will be tested.
     *
     * @return TRUE if it is an existing regular file or a path to an existing regular file.
     *
     * @since 1.0.0
     */
    @Override
    public boolean test(Object object) {
        return Optional.ofNullable(object)
                       .map(o -> {
                           if (o instanceof Path path) {
                               return Files.isRegularFile(path);
                           } else if (o instanceof File file) {
                               return Files.isRegularFile(file.toPath());
                           } else {
                               Path path = Path.of(o.toString());
                               return Files.isRegularFile(path);
                           }
                       })
                       .orElse(false);
    }
}
