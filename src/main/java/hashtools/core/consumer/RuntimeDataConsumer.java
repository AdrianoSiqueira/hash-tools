package hashtools.core.consumer;

import hashtools.core.model.RuntimeData;

public interface RuntimeDataConsumer {

    void consume(RuntimeData runtimeData);
}
