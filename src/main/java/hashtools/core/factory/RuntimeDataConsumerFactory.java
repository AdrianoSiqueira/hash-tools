package hashtools.core.factory;

import hashtools.core.consumer.RuntimeDataCommandLineConsumer;
import hashtools.core.consumer.RuntimeDataConsumer;
import hashtools.core.consumer.RuntimeDataFileConsumer;

import java.nio.file.Path;

public class RuntimeDataConsumerFactory {

    public RuntimeDataConsumer getConsumer(Path outputFile) {
        return outputFile != null
               ? new RuntimeDataFileConsumer()
               : new RuntimeDataCommandLineConsumer();
    }
}
