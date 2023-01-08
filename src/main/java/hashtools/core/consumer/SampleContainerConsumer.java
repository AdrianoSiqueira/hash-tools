package hashtools.core.consumer;

import hashtools.core.model.SampleContainer;

import java.util.function.Consumer;
import java.util.stream.Stream;

@Deprecated
public interface SampleContainerConsumer extends Consumer<SampleContainer> {

    String formatResult(SampleContainer sampleContainer);

    default int getLargerLength(String... strings) {
        return Stream.of(strings)
                     .map(String::length)
                     .reduce(Math::max)
                     .orElse(0);
    }
}
