package hashtools.core.consumer;

import hashtools.core.model.RuntimeData;

public class CommandLineConsumer implements RuntimeDataConsumer {

    @Override
    public void consume(RuntimeData runtimeData) {
        String content = runtimeData.format();

        System.out.println(content);
    }
}
