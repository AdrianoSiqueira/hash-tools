package hashtools.core.consumer;

import hashtools.core.formatter.Formatter;
import hashtools.core.formatter.RuntimeDataFormatter;
import hashtools.core.model.RuntimeData;

public class CommandLineConsumer implements RuntimeDataConsumer{

    @Override
    public void consume(RuntimeData runtimeData) {
        Formatter<RuntimeData> formatter = new RuntimeDataFormatter();
        String                 content   = formatter.format(runtimeData);

        System.out.println(content);
    }
}
