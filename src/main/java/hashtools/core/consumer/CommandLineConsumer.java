package hashtools.core.consumer;

import hashtools.core.factory.RuntimeDataFormatterFactory;
import hashtools.core.model.RuntimeData;

public class CommandLineConsumer implements RuntimeDataConsumer {

    @Override
    public void consume(RuntimeData runtimeData) {
        String content = new RuntimeDataFormatterFactory()
                .getFormatter(runtimeData.isChecking())
                .format(runtimeData);

        System.out.println(content);
    }
}
