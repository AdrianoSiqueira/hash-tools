package hashtools.core.util;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * <p>String splitter.</p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @see #apply
 * @since 2.0.0
 */
public class LineSplitter implements Function<String, Stream<String>> {

    /**
     * <p>Splits the given string into a string stream.</p>
     *
     * @param string String that will be split.
     *
     * @return A stream composed of the split strings.
     *
     * @since 1.0.0
     */
    @Override
    public Stream<String> apply(String string) {
        return Stream.of(string.split(" "));
    }
}
